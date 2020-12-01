package com.bohaienko.pdextractor.utils;

import com.bohaienko.pdextractor.model.Extension;
import org.apache.commons.io.FilenameUtils;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

public class Common {
	public static void checkFileTypeForExtensions(String filePath, Extension[] extensions) {
		String fileExtension = FilenameUtils.getExtension(filePath);
		if (!Arrays.stream(extensions).map(Extension::value).collect(toList()).contains(fileExtension))
			throw new WrongFileTypeException(fileExtension, extensions);
	}

	public static String getFileName(String path) {
		return FilenameUtils.getName(path);
	}
}
