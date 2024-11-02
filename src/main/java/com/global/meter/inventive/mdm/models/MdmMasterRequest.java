package com.global.meter.inventive.mdm.models;

import com.global.meter.inventive.models.MeterDataHierarchy;

public class MdmMasterRequest {

	private String mdmId;
	private String ftpAddress;
	private String ftpLogin;
	private String ftpPassword;
	private boolean isActive;
	private String loginId;
	private String mdmAuthUrl;
	private String mdmName;
	private String loginPassword;
	private String userId;
	private String replaceBy;
	private String updatedBy;
	private MeterDataHierarchy hier;

	public String getMdmId() {
		return mdmId;
	}

	public void setMdmId(String mdmId) {
		this.mdmId = mdmId;
	}

	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	public String getFtpLogin() {
		return ftpLogin;
	}

	public void setFtpLogin(String ftpLogin) {
		this.ftpLogin = ftpLogin;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getMdmAuthUrl() {
		return mdmAuthUrl;
	}

	public void setMdmAuthUrl(String mdmAuthUrl) {
		this.mdmAuthUrl = mdmAuthUrl;
	}

	public String getMdmName() {
		return mdmName;
	}

	public void setMdmName(String mdmName) {
		this.mdmName = mdmName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public MeterDataHierarchy getHier() {
		return hier;
	}

	public void setHier(MeterDataHierarchy hier) {
		this.hier = hier;
	}

}
