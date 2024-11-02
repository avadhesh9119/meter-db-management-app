package com.global.meter.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FullDataMeterResponse implements Serializable{

	private static final long serialVersionUID = -2208836749730052839L;

	private List<MeterResponse> response;
	private Map<String, MeterResponse> fullDataRes;
	private String message;
	private String status;
	private String trackingId;
	private List<FullDataModel> responseList;

	public List<FullDataModel> getResponseList() {
		if(responseList == null) {
			responseList = new ArrayList<FullDataModel>();
		}
		return responseList;
	}

	public void setResponseList(List<FullDataModel> responseList) {
		this.responseList = responseList;
	}

	public void setFullDataRes(Map<String, MeterResponse> fullDataRes) {
		this.fullDataRes = fullDataRes;
	}

	public Map<String, MeterResponse> getFullDataRes() {
		return fullDataRes;
	}

	public void setFullDataRes(String cmd, MeterResponse meterResponse) {
		if(fullDataRes == null) {
			this.fullDataRes = new LinkedHashMap<String, MeterResponse>();
		}
		this.fullDataRes.put(cmd, meterResponse);
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

	public List<MeterResponse> getResponse() {
		if(response == null) {
			response = new ArrayList<MeterResponse>();
		}
		return response;
	}

	public void setResponse(List<MeterResponse> response) {
		this.response = response;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	
	
}
