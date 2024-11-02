package com.global.meter.v3.inventive.models;

import java.util.List;

public class MeterResponseRawDataReq {

	private String deviceSerialNumber;
	private String trackingId;
	private String hesDateTime;
	private String profileType;
	private String fromDate;
	private String toDate;
	private String meterData;
	private List<String> obisCode;
	private MeterRawData data;
	public String getDeviceSerialNumber() {
		return deviceSerialNumber;
	}
	public void setDeviceSerialNumber(String deviceSerialNumber) {
		this.deviceSerialNumber = deviceSerialNumber;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getHesDateTime() {
		return hesDateTime;
	}
	public void setHesDateTime(String hesDateTime) {
		this.hesDateTime = hesDateTime;
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getMeterData() {
		return meterData;
	}
	public void setMeterData(String meterData) {
		this.meterData = meterData;
	}
	public List<String> getObisCode() {
		return obisCode;
	}
	public void setObisCode(List<String> obisCode) {
		this.obisCode = obisCode;
	}
	public MeterRawData getData() {
		return data;
	}
	public void setData(MeterRawData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "MeterResponseRawDataReq [deviceSerialNumber=" + deviceSerialNumber + ", trackingId=" + trackingId
				+ ", hesDateTime=" + hesDateTime + ", profileType=" + profileType + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", meterData=" + meterData + ", obisCode=" + obisCode + ", data=" + data + "]";
	}
	
	
}