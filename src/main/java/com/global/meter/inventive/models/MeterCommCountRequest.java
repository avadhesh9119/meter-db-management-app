package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MeterCommCountRequest {
	private String utility;

	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
	}

	@Override
	public String toString() {
		return "MeterCommCountRequest [utility=" + utility + "]";
	}
	

	
	}

	

