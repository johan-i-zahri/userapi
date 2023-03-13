package com.test.usersapi.exceptions;

public class GithubApiException extends UserapiException {
	private static final long serialVersionUID = -394775690454253758L;

	public GithubApiException(String message) {
        super(message);
    }

    public GithubApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
