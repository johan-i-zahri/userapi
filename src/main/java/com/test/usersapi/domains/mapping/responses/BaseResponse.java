package com.test.usersapi.domains.mapping.responses;

public class BaseResponse<T> {
    private String message;
    public T data;

    public BaseResponse() {
        super();
    }

    public void setBaseResponse(String message, T data) {
        this.message = message;
        setData(data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
