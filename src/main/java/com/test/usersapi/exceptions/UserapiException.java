package com.test.usersapi.exceptions;

public class UserapiException extends RuntimeException {
	private static final long serialVersionUID = -394775690454253758L;

	public UserapiException(String message) {
        super(message);
    }

    public UserapiException(String message, Throwable cause) {
        super(message, cause);
    }
}
