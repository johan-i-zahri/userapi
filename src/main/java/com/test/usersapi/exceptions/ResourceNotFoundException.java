package com.test.usersapi.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6194981831069258552L;

	public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
