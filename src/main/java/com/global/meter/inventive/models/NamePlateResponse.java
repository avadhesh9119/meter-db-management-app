package com.global.meter.inventive.models;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NamePlateResponse {
	
	@PrimaryKey
	@JsonProperty("Meter No.")
	private String deviceSerialNo;
	
	@JsonProperty("HES Timestamp")
	private String mdasDateTime;
	
	@JsonProperty("Category")
	private String category;
	
	@JsonProperty("Name Plate Id")
	private String deviceId;
	
	@JsonProperty("Name Plate No.")
	private String meterSerialNo;
	
	@JsonProperty("Current Ratings")
	private String currentRatings;
	
	@JsonProperty("Firmware Version")
	private String firmwareVersion;
	
	@JsonProperty("Manufacturer Name")
	private String manufacturerName;
	
	@JsonProperty("Manufacturer Year")
	private String manufacturerYear;
	
	@JsonProperty("Meter Type")
	private String meterType;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("CT Ratio")
	private String ctRatio;
	
	@JsonProperty("PT Ratio")
	private String ptRatio;

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
