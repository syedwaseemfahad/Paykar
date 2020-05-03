package com.paykar.exceptions;

public class EmptyResultDataAccessException extends Exception {
	private static final long serialVersionUID = -9079454849611061074L;

	public EmptyResultDataAccessException() {
		super();
	}

	public EmptyResultDataAccessException(final String message) {
		super(message);

	}
}
