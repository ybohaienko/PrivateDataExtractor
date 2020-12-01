package com.bohaienko.pdextractor.model;

public enum Extension {
	CSV("csv"),
	XLS("xls"),
	XLSX("xlsx");

	private final String value;

	Extension(String s) {
		value = s;
	}

	public String value() {
		return value;
	}
}
