package com.global.meter.v3.inventive.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MeterCommunicationConfigRes {

	@JsonProperty("Manufacturer")
	private String manufacturer;
	
	@JsonProperty("Device Type")
	private String deviceType;

	@JsonProperty("Tracking Id")
	private String trackingId;
	
	@JsonProperty("Auth Key")
	private String authKey;
	
	@JsonProperty("Auth Mode")
	private String authMode;
	
	@JsonProperty("Ciphering Key")
	private String cipheringKey;
	
	@JsonProperty("Ciphering Mode")
	private String cipheringMode;

	@JsonProperty("Low Pwd")
	private String lowPwd;

	@JsonProperty("High Pwd")
	private String highPwd;
	
	@JsonProperty("Firmware Pwd")
	private String firmwarePwd;
	
	@JsonProperty("Part")
	private Integer part;
	
	@JsonProperty("Push Port")
	private String pushPorts;
	
	@JsonProperty("System Title")
	private String systemTitle;
	
	@JsonProperty("Created By")
	private String createdBy;
	
	@JsonProperty("Created Timestamp")
	private String createdTimestamp;
	
	@JsonProperty("Updated By")
	private String updatedBy;
	
	@JsonProperty("Updated Timestamp")
	private String updatedTimestamp;
	
	@JsonProperty("Mode of comm")
	private String modeOfComm;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public String getCipheringKey() {
		return cipheringKey;
	}

	public void setCipheringKey(String cipheringKey) {
		this.cipheringKey = cipheringKey;
	}

	public String getCipheringMode() {
		return cipheringMode;
	}

	public void setCipheringMode(String cipheringMode) {
		this.cipheringMode = cipheringMode;
	}

	public String getLowPwd() {
		return lowPwd;
	}

	public void setLowPwd(String lowPwd) {
		this.lowPwd = lowPwd;
	}

	public String getHighPwd() {
		return highPwd;
	}

	public void setHighPwd(String highPwd) {
		this.highPwd = highPwd;
	}

	public String getFirmwarePwd() {
		return firmwarePwd;
	}

	public void setFirmwarePwd(String firmwarePwd) {
		this.firmwarePwd = firmwarePwd;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public String getPushPorts() {
		return pushPorts;
	}

	public void setPushPorts(String pushPorts) {
		this.pushPorts = pushPorts;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(String createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(String updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getModeOfComm() {
		return modeOfComm;
	}

	public void setModeOfComm(String modeOfComm) {
		this.modeOfComm = modeOfComm;
	}

	@Override
	public String toString() {
		return "MeterCommunicationConfigRes [manufacturer=" + manufacturer + ", deviceType=" + deviceType
				+ ", trackingId=" + trackingId + ", authKey=" + authKey + ", authMode=" + authMode + ", cipheringKey="
				+ cipheringKey + ", cipheringMode=" + cipheringMode + ", lowPwd=" + lowPwd + ", highPwd=" + highPwd
				+ ", firmwarePwd=" + firmwarePwd + ", part=" + part + ", pushPorts=" + pushPorts + ", systemTitle="
				+ systemTitle + ", createdBy=" + createdBy + ", createdTimestamp=" + createdTimestamp + ", updatedBy="
				+ updatedBy + ", updatedTimestamp=" + updatedTimestamp + ", modeOfComm=" + modeOfComm + "]";
	}
}
