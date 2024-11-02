package com.global.meter.inventive.models;

import java.io.Serializable;

public class MasterPasswordRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2131248013475586593L;
	
	private String meterType;
	private String authKey;
	private String authMode;
	private String cipheringKey;
	private String cipheringMode;
	private String firmwarePwd;
	private String highPwd;
	private String lowPwd;
	private String manufacturer;
	private String miosFile;
	private Boolean miosFormat;
	private Integer part;
	private String pushPorts;
	private String systemTitle;
	
	public String getMeterType() {
		return meterType;
	}
	public void setMeterType(String meterType) {
		this.meterType = meterType;
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
	public String getFirmwarePwd() {
		return firmwarePwd;
	}
	public void setFirmwarePwd(String firmwarePwd) {
		this.firmwarePwd = firmwarePwd;
	}
	public String getHighPwd() {
		return highPwd;
	}
	public void setHighPwd(String highPwd) {
		this.highPwd = highPwd;
	}
	public String getLowPwd() {
		return lowPwd;
	}
	public void setLowPwd(String lowPwd) {
		this.lowPwd = lowPwd;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getMiosFile() {
		return miosFile;
	}
	public void setMiosFile(String miosFile) {
		this.miosFile = miosFile;
	}
	public Boolean getMiosFormat() {
		return miosFormat;
	}
	public void setMiosFormat(Boolean miosFormat) {
		this.miosFormat = miosFormat;
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
	
}
