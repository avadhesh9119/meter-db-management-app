package com.global.meter.inventive.dashboard.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.global.meter.inventive.models.MeterDataRes;

@JsonInclude(Include.NON_NULL)
public class CommunicationSummaryDataResponse {
	
	@JsonProperty("HES Datetime")
	private String hesTimeStamp;
	
	@JsonProperty("Total Device")
	private String totalDevice;
	
	@JsonProperty("Communicating Device")
	private String communicating;
	
	@JsonProperty("Non-Communicating Device")
	private String nonCommunicating;
	
	@JsonProperty("Communicating Device Avg.")
	private String communicatingAvg;
	
	@JsonProperty("Non-Communicating Device Avg.")
	private String nonCommunicatingAvg;
	
	@JsonProperty("Hier Value")
	private String hierValue;
	
	@JsonProperty("Hier Name")
	private String hierName;
	
	private List<MeterDataRes> communicatingDevice;
	
	private List<MeterDataRes> nonCommunicatingDevice;

	public String getHesTimeStamp() {
		return hesTimeStamp;
	}

	public void setHesTimeStamp(String hesTimeStamp) {
		this.hesTimeStamp = hesTimeStamp;
	}

	public String getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(String totalDevice) {
		this.totalDevice = totalDevice;
	}

	public String getCommunicating() {
		return communicating;
	}

	public void setCommunicating(String communicating) {
		this.communicating = communicating;
	}

	public String getNonCommunicating() {
		return nonCommunicating;
	}

	public void setNonCommunicating(String nonCommunicating) {
		this.nonCommunicating = nonCommunicating;
	}

	public String getCommunicatingAvg() {
		return communicatingAvg;
	}

	public void setCommunicatingAvg(String communicatingAvg) {
		this.communicatingAvg = communicatingAvg;
	}

	public String getNonCommunicatingAvg() {
		return nonCommunicatingAvg;
	}

	public void setNonCommunicatingAvg(String nonCommunicatingAvg) {
		this.nonCommunicatingAvg = nonCommunicatingAvg;
	}

	public String getHierValue() {
		return hierValue;
	}

	public void setHierValue(String hierValue) {
		this.hierValue = hierValue;
	}

	public String getHierName() {
		return hierName;
	}

	public void setHierName(String hierName) {
		this.hierName = hierName;
	}

	public List<MeterDataRes> getCommunicatingDevice() {
		return communicatingDevice;
	}

	public void setCommunicatingDevice(List<MeterDataRes> communicatingDevice) {
		this.communicatingDevice = communicatingDevice;
	}

	public List<MeterDataRes> getNonCommunicatingDevice() {
		return nonCommunicatingDevice;
	}

	public void setNonCommunicatingDevice(List<MeterDataRes> nonCommunicatingDevice) {
		this.nonCommunicatingDevice = nonCommunicatingDevice;
	}

}
