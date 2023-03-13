package com.test.usersapi.exceptions;

public class ExportException extends UserapiException {
	private static final long serialVersionUID = -394775690454253758L;

	public ExportException(String message) {
        super(message);
    }

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}
