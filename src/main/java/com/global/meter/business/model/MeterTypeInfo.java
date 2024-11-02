package com.global.meter.business.model;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "meter_type_info")
public class MeterTypeInfo implements Serializable{
	
	private static final long serialVersionUID = 5291117083048142492L;

	@PrimaryKey(value = "meter_type")
	private String meterType;

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
	@Column(value = "manufacturer")
	private String manufacturer;
	@Column(value = "metertype")
	private String metertypes;
	@Column(value = "mios_file")
	private String miosFile;
	@Column(value = "miosformat")
	private Boolean miosFormat;
	private Integer part;
	@Column(value = "systemtitle")
	private String systemTitle;
	@Column(value = "pushports")
	private String pushPorts;
	
	public MeterTypeInfo(String meterType, String authKey, String authMode, String cipheringKey, String cipheringMode,
			String highPwd, String lowPwd, String manufacturer, String metertypes, String miosFile, Boolean miosFormat,
			Integer part, String systemTitle) {
		super();
		this.meterType = meterType;
		this.authKey = authKey;
		this.authMode = authMode;
		this.cipheringKey = cipheringKey;
		this.cipheringMode = cipheringMode;
		this.highPwd = highPwd;
		this.lowPwd = lowPwd;
		this.manufacturer = manufacturer;
		this.metertypes = metertypes;
		this.miosFile = miosFile;
		this.miosFormat = miosFormat;
		this.part = part;
		this.systemTitle = systemTitle;
	}

	@Override
	public String toString() {
		return "MeterTypeInfo [meterType=" + meterType + ", authKey=" + authKey + ", authMode=" + authMode
				+ ", cipheringKey=" + cipheringKey + ", cipheringMode=" + cipheringMode + ", highPwd=" + highPwd
				+ ", lowPwd=" + lowPwd + ", manufacturer=" + manufacturer + ", metertypes=" + metertypes + ", miosFile="
				+ miosFile + ", miosFormat=" + miosFormat + ", part=" + part + ", systemTitle=" + systemTitle + "]";
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getFimrwarePwd() {
		return fimrwarePwd;
	}

	public void setFimrwarePwd(String fimrwarePwd) {
		this.fimrwarePwd = fimrwarePwd;
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

	public String getMetertypes() {
		return metertypes;
	}

	public void setMetertypes(String metertypes) {
		this.metertypes = metertypes;
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

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public MeterTypeInfo() {
	}

	public String getPushPorts() {
		return pushPorts;
	}

	public void setPushPorts(String pushPorts) {
		this.pushPorts = pushPorts;
	}
}
