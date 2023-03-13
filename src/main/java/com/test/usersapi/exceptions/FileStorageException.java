package com.test.usersapi.exceptions;

public class FileStorageException extends UserapiException {
	private static final long serialVersionUID = -6649652836861834182L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

