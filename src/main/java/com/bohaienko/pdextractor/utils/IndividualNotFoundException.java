package com.bohaienko.pdextractor.utils;

public class IndividualNotFoundException extends RuntimeException {
	public IndividualNotFoundException(Long id) {
		super("Could not find individual " + id);
	}
}
