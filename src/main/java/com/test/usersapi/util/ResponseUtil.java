package com.test.usersapi.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.usersapi.domains.mapping.responses.ObjectResponse;
import com.test.usersapi.domains.mapping.responses.StringResponse;

public class ResponseUtil {

	public static ResponseEntity<StringResponse> handleException(StringResponse response, String message,
			Exception e, Logger logger) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		response.setBaseResponse(message + e.getMessage(), errors.toString());
		logger.severe(response.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	public static ResponseEntity<StringResponse> handleOk(StringResponse response, String message) {
        response.setBaseResponse(message, null);
        return ResponseEntity.ok(response);
	}

	public static ResponseEntity<StringResponse> handleOk(StringResponse response, String message, String data) {
        response.setBaseResponse(message, data);
        return ResponseEntity.ok(response);
	}


	public static ResponseEntity<ObjectResponse> handleException(ObjectResponse response, String message,
			Exception e, Logger logger) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		response.setBaseResponse(message + e.getMessage(), errors.toString());
		logger.severe(response.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	public static ResponseEntity<ObjectResponse> handleOk(ObjectResponse response, String message) {
        response.setBaseResponse(message, null);
        return ResponseEntity.ok(response);
	}

	public static ResponseEntity<ObjectResponse> handleOk(ObjectResponse response, String message, Object data) {
        response.setBaseResponse(message, data);
        return ResponseEntity.ok(response);
	}

}
