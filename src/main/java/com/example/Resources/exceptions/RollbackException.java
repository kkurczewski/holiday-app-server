package com.example.Resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RollbackException extends RuntimeException {

	private static final long serialVersionUID = -3281561258072273455L;

	public RollbackException(Exception e) {
		super(e);
	}
}
