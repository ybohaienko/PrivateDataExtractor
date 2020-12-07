package com.bohaienko.pdextractor.utils;

import com.bohaienko.pdextractor.model.Extension;
import org.apache.commons.io.FilenameUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Commons {
	public static void checkFileTypeForExtensions(String filePath, Extension[] extensions) {
		String fileExtension = FilenameUtils.getExtension(filePath);
		if (!Arrays.stream(extensions).map(Extension::value).collect(toList()).contains(fileExtension))
			throw new WrongFileTypeException(fileExtension, extensions);
	}

	public static String getFileNameByFullPath(String fullPath) {
		return FilenameUtils.getName(fullPath);
	}

	public static String getLocationByFullPath(String fullPath) {
		return FilenameUtils.getFullPath(fullPath);
	}

	public static String timestamp() {
		String pattern = "EEE, yyyy.MM.dd HH:mm:ss";
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static Map<String, String> removeEmptyValues(Map<String, String> map) {
		map.values().removeIf(v -> v.isEmpty() || v.equals(" "));
		return map;
	}

	@SafeVarargs
	public static <T> List<T> intersect(List<T> first, List<T>... rest) {
		if (rest.length == 0)
			return first;
		List<T> second = rest[0];
		first = intersect(first, second);
		rest = Arrays.copyOfRange(rest, 1, rest.length);
		return intersect(first, rest);
	}

	public static <T> List<T> intersect(List<T> l1, List<T> l2) {
		List<T> inter = new ArrayList<>(l1);
		inter.retainAll(l2);
		return inter;
	}

	public static <T> List<T> removeDuplicates(List<T> list) {
		return list.stream().distinct().collect(Collectors.toList());
	}
}
