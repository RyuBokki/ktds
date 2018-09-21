package com.ktds.common.exceptions.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.ktds.common.exceptions.PolicyViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String noHandlerFoundExceptionHandler() {
		return "error/404";
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	public String runtimeExceptionHandler(RuntimeException e) throws UnsupportedEncodingException {
		// 에러가 왜 발생되는지 표시
		// 원래 쓰면 안됨.
		e.printStackTrace();
		
		if ( e instanceof PolicyViolationException ) {
			PolicyViolationException pve = (PolicyViolationException) e;
			return "redirect:" 
			+ pve.getRedirectUrl() 
			+ "?message=" 
			+ URLEncoder.encode(pve.getMessage(), "UTF-8");
		}
		
		return "error/500";
	}
}
