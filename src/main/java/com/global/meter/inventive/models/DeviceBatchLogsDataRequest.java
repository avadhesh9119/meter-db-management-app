package com.global.meter.inventive.models;

/**
 * 
 * @author Nitin Sethi
 *
 */
public class DeviceBatchLogsDataRequest {
	
	private String batchId;
	private String fileName;
	private int failureRecords;
	private String status;
	private int successRecords;
	private int totRecords;
	private String userName;
	private String bulkType;
	
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
	public int getFailureRecords() {
		return failureRecords;
	}
	public void setFailureRecords(int failureRecords) {
		this.failureRecords = failureRecords;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSuccessRecords() {
		return successRecords;
	}
	public void setSuccessRecords(int successRecords) {
		this.successRecords = successRecords;
	}
	public int getTotRecords() {
		return totRecords;
	}
	public void setTotRecords(int totRecords) {
		this.totRecords = totRecords;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBulkType() {
		return bulkType;
	}
	public void setBulkType(String bulkType) {
		this.bulkType = bulkType;
	}
	
}
