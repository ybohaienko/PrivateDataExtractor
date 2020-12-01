package com.bohaienko.pdextractor.utils;

public class NotSupportableFileTypeException extends RuntimeException {
	private static String message(String extention) {
		StringBuilder message = new StringBuilder("File type of extension: '")
				.append(extention)
				.append("' is not supportable");
		return message.toString();
	}

	public NotSupportableFileTypeException(String actualExtension) {
		super(message(actualExtension));
	}
}
