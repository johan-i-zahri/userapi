package com.test.usersapi.domains.mapping.responses;

public class StringResponse extends BaseResponse<String>{
	public StringResponse(String message, String data) {
		this.setMessage(message);
		this.setData(data);
	}
}
