package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DeviceBatchLogsDataResponse implements Serializable {

	private static final long serialVersionUID = 5658221707297317329L;

	@JsonProperty("Batch Id")
	private String batchId;

	@JsonProperty("File Name")
	private String fileName;

	@JsonProperty("Failure Records")
	private String failureRecords;

	@JsonProperty("Status")
	private String status;

	@JsonProperty("Success Records")
	private String successRecords;

	@JsonProperty("Total Records")
	private String totRecords;

	@JsonProperty("Username")
	private String userName;

	@JsonProperty("Completion Timestamp")
	private String completionTimestamp;

	@JsonProperty("HES Timestamp")
	private String hesTimestamp;

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFailureRecords() {
		return failureRecords;
	}

	public void setFailureRecords(String failureRecords) {
		this.failureRecords = failureRecords;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuccessRecords() {
		return successRecords;
	}

	public void setSuccessRecords(String successRecords) {
		this.successRecords = successRecords;
	}

	public String getTotRecords() {
		return totRecords;
	}

	public void setTotRecords(String totRecords) {
		this.totRecords = totRecords;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCompletionTimestamp() {
		return completionTimestamp;
	}

	public void setCompletionTimestamp(String completionTimestamp) {
		this.completionTimestamp = completionTimestamp;
	}

	public String getHesTimestamp() {
		return hesTimestamp;
	}

	public void setHesTimestamp(String hesTimestamp) {
		this.hesTimestamp = hesTimestamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DeviceBatchLogsDataResponse [batchId=" + batchId + ", fileName=" + fileName + ", failureRecords="
				+ failureRecords + ", status=" + status + ", successRecords=" + successRecords + ", totRecords="
				+ totRecords + ", userName=" + userName + ", completionTimestamp=" + completionTimestamp
				+ ", hesTimestamp=" + hesTimestamp + "]";
	}

}
