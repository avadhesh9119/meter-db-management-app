package com.global.meter.inventive.models;

public class DevicesCommandRequest {

	private String deviceSerialNo;
	private String commandName;
	private String trackingId;
	private String commandCompletionDatetime;
	private String dcuSerialNo;
	private String dtName;
	private String feederName;
	private String mdasDatetime;
	private String utility;
	private String reason;
	private String status;
	private String subdivisionName;
	private String substationName;
	private String totalAttempts;
	private MeterDataHierarchy hier;
	private String batchId;
	private String hierName;
	private String hierValue;
	private String source;
	private String userId;
	private String replaceBy;
	
	public MeterDataHierarchy getHier() {
		return hier;
	}

	public void setHier(MeterDataHierarchy hier) {
		this.hier = hier;
	}

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

	public String getDcuSerialNo() {
		return dcuSerialNo;
	}

	public void setDcuSerialNo(String dcuSerialNo) {
		this.dcuSerialNo = dcuSerialNo;
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
	
	public String getReplaceBy() {
		return replaceBy;
	}

	public void setReplaceBy(String replaceBy) {
		this.replaceBy = replaceBy;
	}

	@Override
	public String toString() {
		return "DevicesCommandRequest [deviceSerialNo=" + deviceSerialNo + ", commandName=" + commandName
				+ ", trackingId=" + trackingId + ", commandCompletionDatetime=" + commandCompletionDatetime
				+ ", dcuSerialNo=" + dcuSerialNo + ", dtName=" + dtName + ", feederName=" + feederName
				+ ", mdasDatetime=" + mdasDatetime + ", utility=" + utility + ", reason=" + reason + ", status="
				+ status + ", subdivisionName=" + subdivisionName + ", substationName=" + substationName
				+ ", totalAttempts=" + totalAttempts + ", hier=" + hier + ", batchId=" + batchId + ", hierName="
				+ hierName + ", hierValue=" + hierValue + ", source=" + source + ", userId=" + userId + ", replaceBy="
				+ replaceBy + "]";
	}

}
