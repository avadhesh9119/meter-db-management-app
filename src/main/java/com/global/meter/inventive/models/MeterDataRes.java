package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MeterDataRes {

	private String meterNumber;
	
	private String subdivisionName;
	
	private String substationName;
	
	private String feederName;
	
	private String dtName;
	
	private String consumerAddress;

	private String consumerName;

	private String commissioningStatus;
	
	private String simNo;
	
	private String crn;
	
	private String crnNew;
	
	private String description;
	
	private String status;

	private String devMode;

	private String deviceType;

	private String ipAddress;

	private String port;
	
	private String latitude;
	
	private String longitude;

	private String manufacturer;
	
	private String network;
	
	private String consumerPhoneNumber;
	
	private String sanctionedLoad;
	
	private String createdBy;
	
	private String createdOnDatetime;
	
	private String lastUpdatedOnDatetime;
	
	private String lastUpdatedBy;
	
	private String installationDatetime;
	
	private String utility;
	
	private String dcuSerialNumber;
	
	private String source;
	
	private String mdmId;
	
	private String approvedBy;
	
	private String approvedDatetime;
	
	private String zone;
	
	private String circle;
	
	private String division;
	
	private String decommissioningReason;
	
	private String decommissioningReqBy;
	
	private String decommissioningReqDatetime;
	
	private String decommissioningStatus;
	
	private String meterNICId;
	
	private String modeOfComm;
	
	private String categoryLevel;
	
	private String authkey;
	
	private String cipheringKey;
	
	private String highPwd;
	
	private String lowPwd;
	
	private String firmwarePwd;
	
	private String lastCommunicationDatetime;

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
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

	public String getFeederName() {
		return feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
	}

	public String getDtName() {
		return dtName;
	}

	public void setDtName(String dtName) {
		this.dtName = dtName;
	}

	public String getConsumerAddress() {
		return consumerAddress;
	}

	public void setConsumerAddress(String consumerAddress) {
		this.consumerAddress = consumerAddress;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getCommissioningStatus() {
		return commissioningStatus;
	}

	public void setCommissioningStatus(String commissioningStatus) {
		this.commissioningStatus = commissioningStatus;
	}

	public String getSimNo() {
		return simNo;
	}

	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}

	public String getCrn() {
		return crn;
	}

	public void setCrn(String crn) {
		this.crn = crn;
	}

	public String getCrnNew() {
		return crnNew;
	}

	public void setCrnNew(String crnNew) {
		this.crnNew = crnNew;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDevMode() {
		return devMode;
	}

	public void setDevMode(String devMode) {
		this.devMode = devMode;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getConsumerPhoneNumber() {
		return consumerPhoneNumber;
	}

	public void setConsumerPhoneNumber(String consumerPhoneNumber) {
		this.consumerPhoneNumber = consumerPhoneNumber;
	}

	public String getSanctionedLoad() {
		return sanctionedLoad;
	}

	public void setSanctionedLoad(String sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOnDatetime() {
		return createdOnDatetime;
	}

	public void setCreatedOnDatetime(String createdOnDatetime) {
		this.createdOnDatetime = createdOnDatetime;
	}

	public String getLastUpdatedOnDatetime() {
		return lastUpdatedOnDatetime;
	}

	public void setLastUpdatedOnDatetime(String lastUpdatedOnDatetime) {
		this.lastUpdatedOnDatetime = lastUpdatedOnDatetime;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getInstallationDatetime() {
		return installationDatetime;
	}

	public void setInstallationDatetime(String installationDatetime) {
		this.installationDatetime = installationDatetime;
	}

	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
	}

	public String getDcuSerialNumber() {
		return dcuSerialNumber;
	}

	public void setDcuSerialNumber(String dcuSerialNumber) {
		this.dcuSerialNumber = dcuSerialNumber;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMdmId() {
		return mdmId;
	}

	public void setMdmId(String mdmId) {
		this.mdmId = mdmId;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovedDatetime() {
		return approvedDatetime;
	}

	public void setApprovedDatetime(String approvedDatetime) {
		this.approvedDatetime = approvedDatetime;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDecommissioningReason() {
		return decommissioningReason;
	}

	public void setDecommissioningReason(String decommissioningReason) {
		this.decommissioningReason = decommissioningReason;
	}

	public String getDecommissioningReqBy() {
		return decommissioningReqBy;
	}

	public void setDecommissioningReqBy(String decommissioningReqBy) {
		this.decommissioningReqBy = decommissioningReqBy;
	}

	public String getDecommissioningReqDatetime() {
		return decommissioningReqDatetime;
	}

	public void setDecommissioningReqDatetime(String decommissioningReqDatetime) {
		this.decommissioningReqDatetime = decommissioningReqDatetime;
	}

	public String getDecommissioningStatus() {
		return decommissioningStatus;
	}

	public void setDecommissioningStatus(String decommissioningStatus) {
		this.decommissioningStatus = decommissioningStatus;
	}

	public String getMeterNICId() {
		return meterNICId;
	}

	public void setMeterNICId(String meterNICId) {
		this.meterNICId = meterNICId;
	}

	public String getModeOfComm() {
		return modeOfComm;
	}

	public void setModeOfComm(String modeOfComm) {
		this.modeOfComm = modeOfComm;
	}

	public String getCategoryLevel() {
		return categoryLevel;
	}

	public void setCategoryLevel(String categoryLevel) {
		this.categoryLevel = categoryLevel;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getCipheringKey() {
		return cipheringKey;
	}

	public void setCipheringKey(String cipheringKey) {
		this.cipheringKey = cipheringKey;
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

	public String getFirmwarePwd() {
		return firmwarePwd;
	}

	public void setFirmwarePwd(String firmwarePwd) {
		this.firmwarePwd = firmwarePwd;
	}

	public String getLastCommunicationDatetime() {
		return lastCommunicationDatetime;
	}

	public void setLastCommunicationDatetime(String lastCommunicationDatetime) {
		this.lastCommunicationDatetime = lastCommunicationDatetime;
	}
	
	
}
