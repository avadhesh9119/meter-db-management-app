package com.global.meter.inventive.models;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

public class NamePlateDeviceResponse {

	@PrimaryKey
	private String deviceSerialNo;

	private String mdasDateTime;

	private String category;

	private String deviceId;

	private String meterSerialNo;

	private String currentRatings;

	private String firmwareVersion;

	private String manufacturerName;

	private String manufacturerYear;

	private String meterType;

	private String status;

	private String ctRatio;

	private String ptRatio;

	private String subDivisionName;

	private String subStationName;

	private String feederName;

	private String dtName;

	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCurrentRatings() {
		return currentRatings;
	}

	public void setCurrentRatings(String currentRatings) {
		this.currentRatings = currentRatings;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturerYear() {
		return manufacturerYear;
	}

	public void setManufacturerYear(String manufacturerYear) {
		this.manufacturerYear = manufacturerYear;
	}

	public String getMdasDateTime() {
		return mdasDateTime;
	}

	public void setMdasDateTime(String mdasDateTime) {
		this.mdasDateTime = mdasDateTime;
	}

	public String getMeterSerialNo() {
		return meterSerialNo;
	}

	public void setMeterSerialNo(String meterSerialNo) {
		this.meterSerialNo = meterSerialNo;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubDivisionName() {
		return subDivisionName;
	}

	public void setSubDivisionName(String subDivisionName) {
		this.subDivisionName = subDivisionName;
	}

	public String getSubStationName() {
		return subStationName;
	}

	public void setSubStationName(String subStationName) {
		this.subStationName = subStationName;
	}

	public String getCtRatio() {
		return ctRatio;
	}

	public void setCtRatio(String ctRatio) {
		this.ctRatio = ctRatio;
	}

	public String getPtRatio() {
		return ptRatio;
	}

	public void setPtRatio(String ptRatio) {
		this.ptRatio = ptRatio;
	}

}
