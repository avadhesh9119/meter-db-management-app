	package com.global.meter.inventive.models;


import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class DevicesCommandsBatchLogsResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3212327225693380122L;
	@JsonProperty("Batch Id")
	private String batchId;
	
	@JsonProperty("HES Timestamp")
	private String hesDatetime;
	
	@JsonProperty("Success")
	private int success;
	
	@JsonProperty("In Progress")
	private int inProgress;
	
	@JsonProperty("Failure")
	private int failure;
	
	@JsonProperty("Command Name")
	private String commandName;
	
	@JsonProperty("Hier Name")
	private String hierName;
	
	@JsonProperty("Hier Value")
	private String hierValue;
	
	@JsonProperty("Source")
	private String source;
	
	@JsonProperty("User Id")
	private String userId;
	
	@JsonProperty("Command Type")
	private String commandType;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getInProgress() {
		return inProgress;
	}

	public void setInProgress(int inProgress) {
		this.inProgress = inProgress;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
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

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	@Override
	public String toString() {
		return "DevicesCommandsBatchLogsResponse [batchId=" + batchId + ", hesDatetime=" + hesDatetime + ", success="
				+ success + ", inProgress=" + inProgress + ", failure=" + failure + ", commandName=" + commandName
				+ ", hierName=" + hierName + ", hierValue=" + hierValue + ", source=" + source + ", userId=" + userId
				+ ", commandType=" + commandType + "]";
	}

}