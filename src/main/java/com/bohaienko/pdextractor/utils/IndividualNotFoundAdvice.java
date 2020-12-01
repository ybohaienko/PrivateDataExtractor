package com.bohaienko.pdextractor.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class IndividualNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(IndividualNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String individualNotFoundHandler(IndividualNotFoundException ex) {
		return ex.getMessage();
	}
}
