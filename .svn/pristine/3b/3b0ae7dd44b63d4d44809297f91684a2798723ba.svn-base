package com.global.meter.common.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FullDataModelResponse {

	private List<FullDataModel> responseList;
	private String trackingId;
	
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public List<FullDataModel> getResponseList() {
		if(responseList == null) {
			responseList = new ArrayList<FullDataModel>();
		}
		return responseList;
	}
	public void setResponseList(List<FullDataModel> responseList) {
		this.responseList = responseList;
	}
	
}
