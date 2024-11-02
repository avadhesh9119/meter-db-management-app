package com.global.meter.common.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class FullMeterDataResponse {

	private List<MeterResponse> response;
	private String message;
	private String status;
	private String trackingId;
	
	public List<MeterResponse> getResponse() {
		if(response == null) {
			this.response = new ArrayList<MeterResponse>();
		}
		return response;
	}
	
	public void setResponse(List<MeterResponse> response) {
		if(response == null) {
			this.response = new ArrayList<MeterResponse>();
		}
		this.response = response;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
