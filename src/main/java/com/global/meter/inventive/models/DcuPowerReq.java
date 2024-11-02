package com.global.meter.inventive.models;

import java.io.Serializable;

public class DcuPowerReq implements Serializable{

	private static final long serialVersionUID = 8578644653316048309L;
	
	private String meterId;
	private String messageType;
	private long requestId;
	private Payload payload;
	private String dcuMacId;
	private String dcuSerialNumber;
	private String hesDateTime;
	private String reportType ;
	private String batteryStatus;
	private String powerStatus;
	private String batteryRawvalue;
	private String voltageValue;
	private String fromDate;
	private String toDate;
	private String devType;
	private String replaceBy;
	private MeterDataHierarchy hier;
	public String getDcuMacId() {
		return dcuMacId;
	}
	public void setDcuMacId(String dcuMacId) {
		this.dcuMacId = dcuMacId;
	}
	public String getDcuSerialNumber() {
		return dcuSerialNumber;
	}
	public void setDcuSerialNumber(String dcuSerialNumber) {
		this.dcuSerialNumber = dcuSerialNumber;
	}
	public String getHesDateTime() {
		return hesDateTime;
	}
	public void setHesDateTime(String hesDateTime) {
		this.hesDateTime = hesDateTime;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public long getRequestId() {
		return requestId;
	}
	public void setRequestId(long requestId) {
		this.requestId = requestId;
	}
	public String getBatteryStatus() {
		return batteryStatus;
	}
	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
	}
	public String getPowerStatus() {
		return powerStatus;
	}
	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}
	public String getBatteryRawvalue() {
		return batteryRawvalue;
	}
	public void setBatteryRawvalue(String batteryRawvalue) {
		this.batteryRawvalue = batteryRawvalue;
	}
	public String getVoltageValue() {
		return voltageValue;
	}
	public void setVoltageValue(String voltageValue) {
		this.voltageValue = voltageValue;
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
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public MeterDataHierarchy getHier() {
		return hier;
	}
	public void setHier(MeterDataHierarchy hier) {
		this.hier = hier;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Payload getPayload() {
		return payload;
	}
	public void setPayload(Payload payload) {
		this.payload = payload;
	}
	public String getMeterId() {
		return meterId;
	}
	public String getReplaceBy() {
		return replaceBy;
	}
	public void setReplaceBy(String replaceBy) {
		this.replaceBy = replaceBy;
	}
	@Override
	public String toString() {
		return "DcuPowerReq [meterId=" + meterId + ", messageType=" + messageType + ", requestId=" + requestId
				+ ", payload=" + payload + ", dcuMacId=" + dcuMacId + ", dcuSerialNumber=" + dcuSerialNumber
				+ ", hesDateTime=" + hesDateTime + ", reportType=" + reportType + ", batteryStatus=" + batteryStatus
				+ ", powerStatus=" + powerStatus + ", batteryRawvalue=" + batteryRawvalue + ", voltageValue="
				+ voltageValue + ", fromDate=" + fromDate + ", toDate=" + toDate + ", devType=" + devType
				+ ", replaceBy=" + replaceBy + ", hier=" + hier + "]";
	}
}
