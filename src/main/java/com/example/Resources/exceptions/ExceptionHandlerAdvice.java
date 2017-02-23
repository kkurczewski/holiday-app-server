package com.example.Resources.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
	
	final private Logger log = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler(MultipartException.class)
	public @ResponseBody String maxSizeExceededException(final WebRequest request, final MultipartException ex) {
		
		log.error("Ex: " + ex);
		return "<h2>Request was rejected because its files size is too big</h2>";
	}

}