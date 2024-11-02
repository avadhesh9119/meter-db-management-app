package com.global.meter.common.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class DeviceTrackingIDs implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String trackingId;
	private String deviceSno;
	private String message;
	private List<String> inprogressDevList;
	private List<String> missingFileDevList;
	
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getDeviceSno() {
		return deviceSno;
	}
	public void setDeviceSno(String deviceSno) {
		this.deviceSno = deviceSno;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getInprogressDevList() {
		return inprogressDevList;
	}
	public void setInprogressDevList(List<String> inprogressDevList) {
		this.inprogressDevList = inprogressDevList;
	}
	public List<String> getMissingFileDevList() {
		return missingFileDevList;
	}
	public void setMissingFileDevList(List<String> missingFileDevList) {
		this.missingFileDevList = missingFileDevList;
	}
	
	@Override
	public String toString() {
		return "DeviceTrackingIDs [trackingId=" + trackingId + ", deviceSno=" + deviceSno + ", message=" + message
				+ ", inprogressDevList=" + inprogressDevList + ", missingFileDevList=" + missingFileDevList + "]";
	}
}
