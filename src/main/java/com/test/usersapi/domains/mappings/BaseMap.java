package com.test.usersapi.domains.mappings;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BaseMap {
	
	public final static String STATUS_OK="OK";
	public final static String STATUS_ERROR="ERROR";
	
	@JsonProperty("status")
	private String status;
	@JsonProperty("message")
	private String message;
}
