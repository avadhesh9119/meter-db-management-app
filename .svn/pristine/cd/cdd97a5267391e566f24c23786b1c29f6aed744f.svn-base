package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ConfigurationReadDataLogsResponse {
	@JsonProperty("Trace ID")
	private String trackingId;

	@JsonProperty("Meter No.")
	private String deviceSerialNumber;

	@JsonProperty("HES Timestamp")
	private String mdasDateTime;

	@JsonProperty("Command Completion Timestamp")
	private String commandCompletionDateTime;

	@JsonProperty("Command Name")
	private Object commandName;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("Reason")
	private String reason;

	@JsonProperty("Attempts")
	private String totAttempts;

	@JsonProperty("Subdivision")
	private String subdivisionName;

	@JsonProperty("Substation")
	private String substationName;

	@JsonProperty("Feeder")
	private String feederName;
	
	@JsonProperty("DT")
	private String dtName;
	
	@JsonProperty("Source")
	private String source;
	
	@JsonProperty("UserId")
	private String userId;
	
	@JsonProperty("BatchId")
	private String batchId;

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getCommandCompletionDateTime() {
		return commandCompletionDateTime;
	}

	public void setCommandCompletionDateTime(String commandCompletionDateTime) {
		this.commandCompletionDateTime = commandCompletionDateTime;
	}

	public Object getCommandName() {
		return commandName;
	}

	public void setCommandName(Object commandName) {
		this.commandName = commandName;
	}

	public String getDtName() {
		return dtName;
	}

	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getMdasDateTime() {
		return mdasDateTime;
	}

	public void setMdasDateTime(String mdasDateTime) {
		this.mdasDateTime = mdasDateTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubdivisionName() {
		return subdivisionName;
	}

	public void setSubdivisionName(String subdivisionName) {
		this.subdivisionName = subdivisionName;
	}

	public String getSubstationName() {
		return substationName;
	}

	public void setSubstationName(String substationName) {
		this.substationName = substationName;
	}

	public String getTotAttempts() {
		return totAttempts;
	}

	public void setTotAttempts(String totAttempts) {
		this.totAttempts = totAttempts;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Override
	public String toString() {
		return "ConfigurationReadDataLogsResponse [trackingId=" + trackingId + ", deviceSerialNumber="
				+ deviceSerialNumber + ", mdasDateTime=" + mdasDateTime + ", commandCompletionDateTime="
				+ commandCompletionDateTime + ", commandName=" + commandName + ", status=" + status + ", reason="
				+ reason + ", totAttempts=" + totAttempts + ", subdivisionName=" + subdivisionName + ", substationName="
				+ substationName + ", feederName=" + feederName + ", dtName=" + dtName + ", source=" + source
				+ ", userId=" + userId + ", batchId=" + batchId + "]";
	}

}
