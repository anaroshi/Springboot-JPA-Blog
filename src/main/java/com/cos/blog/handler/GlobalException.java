package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalException {
	
	//@ExceptionHandler(value = IllegalArgumentException.class)
	@ExceptionHandler(value = Exception.class)
	public String handleArgumentException(IllegalArgumentException e) {
		return "<H1>"+e.getMessage()+"</H1>";
	}
}
