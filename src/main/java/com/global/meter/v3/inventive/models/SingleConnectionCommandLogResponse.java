package com.global.meter.v3.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class SingleConnectionCommandLogResponse {
	
	@JsonProperty("Tracking Id")
	private String trackingId;
	
	@JsonProperty("Meter No.")
	private String deviceSerialNumber;
	
	@JsonProperty("Command Name")
	private String commandName;
	
	@JsonProperty("HES Date")
	private String hesDate;

	@JsonProperty("Command Datetime")
	private String mdasDatetime;
	
	@JsonProperty("Command Completion Datetime")
	private String commandCompletionDatetime;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Total Attempts")
	private String totAttempts;

	@JsonProperty("Global Id")
	private String batchId;
	
	@JsonProperty("Batch Id")
	private String extBatchId;

	@JsonProperty("Command List")
	private String commandList;

	@JsonProperty("Device Type")
	private String deviceType;

	@JsonProperty("Utility")
	private String ownerName;

	@JsonProperty("Reason")
	private String reason;

	@JsonProperty("Source")
	private String source;

	@JsonProperty("User Id")
	private String userId;
	
	@JsonProperty("Cancelled By")
	private String cancelledBy;
	
	@JsonProperty("Retry Count")
	private String retryCount;
	
	@JsonProperty("relay Status")
	private String relayStatus;
	
	@JsonProperty("Range Date")
	private String rangeDate;
	
	public String getHesDate() {
		return hesDate;
	}

	public void setHesDate(String hesDate) {
		this.hesDate = hesDate;
	}

	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}

	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getCommandList() {
		return commandList;
	}

	public void setCommandList(String commandList) {
		this.commandList = commandList;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getMdasDatetime() {
		return mdasDatetime;
	}

	public void setMdasDatetime(String mdasDatetime) {
		this.mdasDatetime = mdasDatetime;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTotAttempts() {
		return totAttempts;
	}

	public void setTotAttempts(String totAttempts) {
		this.totAttempts = totAttempts;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExtBatchId() {
		return extBatchId;
	}

	public void setExtBatchId(String extBatchId) {
		this.extBatchId = extBatchId;
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

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(String retryCount) {
		this.retryCount = retryCount;
	}

	public String getRelayStatus() {
		return relayStatus;
	}

	public void setRelayStatus(String relayStatus) {
		this.relayStatus = relayStatus;
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public String getRangeDate() {
		return rangeDate;
	}

	public void setRangeDate(String rangeDate) {
		this.rangeDate = rangeDate;
	}
	
}