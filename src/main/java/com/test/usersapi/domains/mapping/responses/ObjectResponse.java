package com.test.usersapi.domains.mapping.responses;

public class ObjectResponse extends BaseResponse<Object>{
	public ObjectResponse(String message, Object data) {
		this.setMessage(message);
		this.setData(data);
	}
}
