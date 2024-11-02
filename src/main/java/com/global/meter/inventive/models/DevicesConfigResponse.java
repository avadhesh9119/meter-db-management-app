package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DevicesConfigResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9010058845710644512L;
	
	@JsonProperty("Trace Id")
	private String trackingId;
	
	@JsonProperty("Command Name")
	private String commandName;
	
	@JsonProperty("Meter No")
	private String meterNo;
	
	@JsonProperty("Command Completion Timestamp")
	private String cmdCompletionTimestamp;
	
	@JsonProperty("Command Timestamp")
	private String cmdTimestamp;
	
	@JsonProperty("Config Value")
	private String cmdVal;
	
	@JsonProperty("Dcu Serial No.")
	private String dcuSerialNo;
	
	@JsonProperty("HES Timestamp")
	private String mdasDatetime;
	
	@JsonProperty("Reason")
	private String reason;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Attemps")
	private String totalAttempts;
	
	@JsonProperty("Batch Id")
	private String batchId;
	
	@JsonProperty("Hier Name")
	private String hierName;
	
	@JsonProperty("Hier Value")
	private String hierValue;
	
	@JsonProperty("Source")
	private String source;
	
	@JsonProperty("UserId")
	private String userId;
	
	@JsonProperty("Subdivision Name")
	private String subdivisionName;
	
	@JsonProperty("Substation Name")
	private String substationName;
	
	@JsonProperty("Feeder")
	private String feeder;
	
	@JsonProperty("DT")
	private String dt;
	
	
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getCommandName() {
		return commandName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	public String getMeterNo() {
		return meterNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	public String getCmdCompletionTimestamp() {
		return cmdCompletionTimestamp;
	}
	public void setCmdCompletionTimestamp(String cmdCompletionTimestamp) {
		this.cmdCompletionTimestamp = cmdCompletionTimestamp;
	}
	public String getCmdTimestamp() {
		return cmdTimestamp;
	}
	public void setCmdTimestamp(String cmdTimestamp) {
		this.cmdTimestamp = cmdTimestamp;
	}
	public String getCmdVal() {
		return cmdVal;
	}
	public void setCmdVal(String cmdVal) {
		this.cmdVal = cmdVal;
	}
	public String getDcuSerialNo() {
		return dcuSerialNo;
	}
	public void setDcuSerialNo(String dcuSerialNo) {
		this.dcuSerialNo = dcuSerialNo;
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
	public String getTotalAttempts() {
		return totalAttempts;
	}
	public void setTotalAttempts(String totalAttempts) {
		this.totalAttempts = totalAttempts;
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
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public String getFeeder() {
		return feeder;
	}
	public void setFeeder(String feeder) {
		this.feeder = feeder;
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
	
	
}
