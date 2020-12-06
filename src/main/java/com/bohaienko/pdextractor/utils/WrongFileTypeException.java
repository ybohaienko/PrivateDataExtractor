package com.bohaienko.pdextractor.utils;

import com.bohaienko.pdextractor.model.Extension;

import java.util.Arrays;

public class WrongFileTypeException extends RuntimeException {
	private static String message(String actualExtension, Extension[] extensions) {
		StringBuilder message = new StringBuilder("File type of extension: '")
				.append(actualExtension)
				.append("' is not as expected. \nValid file types are: [");
		Arrays.asList(extensions).forEach(e -> message.append(e).append(" "));
		message.append("]");
		return message.toString();
	}

	WrongFileTypeException(String actualExtension, Extension[] extensions) {
		super(message(actualExtension, extensions));
	}
}
