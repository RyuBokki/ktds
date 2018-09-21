package com.ktds.common.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

public class PolicyViolationException extends RuntimeException {

	private String message;
	private String redirectUrl;
	
	public PolicyViolationException(String message, String redirectUrl) {
		this.message = message;
		this.redirectUrl = redirectUrl;
	}
	
	public String getMessage() {
		return message;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
}
