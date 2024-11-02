package com.global.meter.inventive.models;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DeviceCommandResponse {

	@JsonProperty("Trace ID")
	private String trackingId;
	
	@PrimaryKey
	@JsonProperty("Meter No.")
	private String deviceSerialNo;
	
	@JsonProperty("Command Name")
	private String commandName;
	
	@JsonProperty("HES Timestamp")
	private String mdasDatetime;
	
	@JsonProperty("Command completion Timestamp")
	private String commandCompletionDatetime;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Reason")
	private String reason;
	
	@JsonProperty("Attempts")
	private String totalAttempts;
	
	@JsonProperty("Batch Id")
	private String batchId;
	
	@JsonProperty("Hier Name")
	private String hierName;
	
	@JsonProperty("Hier Value")
	private String hierValue;

	@JsonProperty("Source")
	private String source;
	
	@JsonProperty("User Id")
	private String userId;
	
	@JsonProperty("Subdivision")
	private String subdivisionName;
	
	@JsonProperty("Substation")
	private String substationName;
	
	@JsonProperty("Feeder")
	private String feederName;
	
	@JsonProperty("DT")
	private String dtName;
	
	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getCommandCompletionDatetime() {
		return commandCompletionDatetime;
	}

	public void setCommandCompletionDatetime(String commandCompletionDatetime) {
		this.commandCompletionDatetime = commandCompletionDatetime;
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

	public String getMdasDatetime() {
		return mdasDatetime;
	}

	public void setMdasDatetime(String mdasDatetime) {
		this.mdasDatetime = mdasDatetime;
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

	public String getTotalAttempts() {
		return totalAttempts;
	}

	public void setTotalAttempts(String totalAttempts) {
		this.totalAttempts = totalAttempts;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	public String getHierName() {
		return hierName;
	}

	public void setHierName(String hierName) {
		this.hierName = hierName;
	}

	public String getHierValue() {
		return hierValue;
	}

	public void setHierValue(String hierValue) {
		this.hierValue = hierValue;
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

	@Override
	public String toString() {
		return "DeviceCommandResponse [trackingId=" + trackingId + ", deviceSerialNo=" + deviceSerialNo
				+ ", commandName=" + commandName + ", mdasDatetime=" + mdasDatetime + ", commandCompletionDatetime="
				+ commandCompletionDatetime + ", status=" + status + ", reason=" + reason + ", totalAttempts="
				+ totalAttempts + ", subdivisionName=" + subdivisionName + ", substationName=" + substationName
				+ ", feederName=" + feederName + ", dtName=" + dtName + ", batchId=" + batchId + ", hierName="
				+ hierName + ", hierValue=" + hierValue + ", source=" + source + ", userId=" + userId + "]";
	}
	
}
