package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "meter_pwds_history")
public class MeterPwdsHistory implements Serializable{
	
	private static final long serialVersionUID = 5291117083048142492L;

	@PrimaryKey(value = "manufacturer")
	private String manufacturer;
	
	@Column(value = "tracking_id")
	private String trackingId;
	
	@Column(value = "device_type")
	private String deviceType;
	
	@Column(value = "authkey")
	private String authKey;
	
	@Column(value = "authmode")
	private String authMode;
	
	@Column(value = "cipheringkey")
	private String cipheringKey;
	
	@Column(value = "cipheringmode")
	private String cipheringMode;
	
	@Column(value = "highpwd")
	private String highPwd;
	
	@Column(value = "firmwarepwd")
	private String fimrwarePwd;
	
	@Column(value = "lowpwd")
	private String lowPwd;
	
	private Integer part;
	
	@Column(value = "systemtitle")
	private String systemTitle;
	
	@Column(value = "pushports")
	private String pushPorts;

	@Column(value = "created_by")
	private String createdBy;
	
	@Column(value = "created_on")
	private Date createdOn;
	
	@Column(value = "updated_by")
	private String updatedBy;
	
	@Column(value = "updated_on")
	private Date updatedOn;
	
	@Column(value = "mode_of_comm")
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

	public String getHighPwd() {
		return highPwd;
	}

	public void setHighPwd(String highPwd) {
		this.highPwd = highPwd;
	}

	public String getFimrwarePwd() {
		return fimrwarePwd;
	}

	public void setFimrwarePwd(String fimrwarePwd) {
		this.fimrwarePwd = fimrwarePwd;
	}

	public String getLowPwd() {
		return lowPwd;
	}

	public void setLowPwd(String lowPwd) {
		this.lowPwd = lowPwd;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public String getPushPorts() {
		return pushPorts;
	}

	public void setPushPorts(String pushPorts) {
		this.pushPorts = pushPorts;
	}
	
	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getModeOfComm() {
		return modeOfComm;
	}

	public void setModeOfComm(String modeOfComm) {
		this.modeOfComm = modeOfComm;
	}

	@Override
	public String toString() {
		return "MeterPwdsHistory [manufacturer=" + manufacturer + ", trackingId=" + trackingId + ", deviceType="
				+ deviceType + ", authKey=" + authKey + ", authMode=" + authMode + ", cipheringKey=" + cipheringKey
				+ ", cipheringMode=" + cipheringMode + ", highPwd=" + highPwd + ", fimrwarePwd=" + fimrwarePwd
				+ ", lowPwd=" + lowPwd + ", part=" + part + ", systemTitle=" + systemTitle + ", pushPorts=" + pushPorts
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", modeOfComm=" + modeOfComm + "]";
	}
}
