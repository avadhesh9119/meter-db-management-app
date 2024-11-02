package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class FirmwareConfigResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5628037472352025497L;

	@JsonProperty("Trace Id")
	private String trackingId;
	
	@JsonProperty("Meter No")
	private String meterNo;
	
	@JsonProperty("HES Timestamp")
	private String mdasDatetime;
	
	@JsonProperty("Command Completion Timestamp")
	private String cmdCompletionTimestamp;
	
	@JsonProperty("File Name")
	private String cmdVal;
	
	@JsonProperty("File Version")
	private String version;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("Reason")
	private String reason;
	
	@JsonProperty("Total Attemps")
	private String totalAttempts;
	
	@JsonProperty("Source")
	private String source;
	
	@JsonProperty("User ID")
	private String userId;
	
	@JsonProperty("Utility")
	private String utility;
	
	@JsonProperty("Subdivision")
	private String subdivisionName;
	
	@JsonProperty("Substation")
	private String substationName;
	
	@JsonProperty("DT")
	private String dt;
	
	@JsonProperty("Feeder")
	private String feeder;
	
	@JsonProperty("Deleted By")
	private String deletedBy;
	
	@JsonProperty("Deleted On")
	private String deletedOn;
	
	@JsonProperty("Remarks")
	private String remarks;
	
	@JsonProperty("Created")
	private String created;
	
	@JsonProperty("Dcu Serial Number")
	private String dcuserialnumber;
	
	@JsonProperty("Device Type")
	private String deviceType;
	
	@JsonProperty("Modified")
	private String modified;
	
	@JsonProperty("Owner Name")
	private String ownerName;
	
	@JsonProperty("Updated By")
	private String updatedBy;
	
	@JsonProperty("Command Datetime")
	private String commandDatetime;

	@JsonProperty("Manufacturer")
	private String manufacturer;

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
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

	public String getCmdVal() {
		return cmdVal;
	}

	public void setCmdVal(String cmdVal) {
		this.cmdVal = cmdVal;
	}

	public String getMdasDatetime() {
		return mdasDatetime;
	}

	public void setMdasDatetime(String mdasDatetime) {
		this.mdasDatetime = mdasDatetime;
	}

	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getDeletedOn() {
		return deletedOn;
	}

	public void setDeletedOn(String deletedOn) {
		this.deletedOn = deletedOn;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getDcuserialnumber() {
		return dcuserialnumber;
	}

	public void setDcuserialnumber(String dcuserialnumber) {
		this.dcuserialnumber = dcuserialnumber;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCommandDatetime() {
		return commandDatetime;
	}

	public void setCommandDatetime(String commandDatetime) {
		this.commandDatetime = commandDatetime;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}	
	
}
