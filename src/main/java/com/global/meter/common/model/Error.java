package com.global.meter.common.model;

import org.springframework.stereotype.Component;

@Component
public class Error {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
