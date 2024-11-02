package com.global.meter.v3.inventive.models;

import java.util.List;
import java.util.Map;

import com.global.meter.inventive.models.MeterDataHierarchy;
import com.global.meter.request.model.Device;

public class SingleConnectionCommandReq {
	
    private Device device;
	private String batchId;
	private List<String> prepayObisCodeList;
	private List<String> obisCodeList;
	private Map<String,String> configVals;
	private Map<String, String> dailyRangeDate;
	private Map<String, String> deltaRangeDate;
	private String source;
	private String userId;
	private String isDiscard;
	private String reqFrom;
	private MeterDataHierarchy hier;
	private String fromDate;
	private String toDate;
	private String replaceBy;
	private String command;
	private String trackingId;
	private String extBatchId;
	private String readWise;
	private String reason;
	private String manufacturer;
	private String devType;
	private String installation_datetime;
	private String deviceSerialNo;
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public List<String> getPrepayObisCodeList() {
		return prepayObisCodeList;
	}
	public void setPrepayObisCodeList(List<String> prepayObisCodeList) {
		this.prepayObisCodeList = prepayObisCodeList;
	}
	public List<String> getObisCodeList() {
		return obisCodeList;
	}
	public void setObisCodeList(List<String> obisCodeList) {
		this.obisCodeList = obisCodeList;
	}
	public Map<String, String> getConfigVals() {
		return configVals;
	}
	public void setConfigVals(Map<String, String> configVals) {
		this.configVals = configVals;
	}
	public Map<String, String> getDailyRangeDate() {
		return dailyRangeDate;
	}
	public void setDailyRangeDate(Map<String, String> dailyRangeDate) {
		this.dailyRangeDate = dailyRangeDate;
	}
	public Map<String, String> getDeltaRangeDate() {
		return deltaRangeDate;
	}
	public void setDeltaRangeDate(Map<String, String> deltaRangeDate) {
		this.deltaRangeDate = deltaRangeDate;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsDiscard() {
		return isDiscard;
	}
	public void setIsDiscard(String isDiscard) {
		this.isDiscard = isDiscard;
	}
	public String getReqFrom() {
		return reqFrom;
	}
	public void setReqFrom(String reqFrom) {
		this.reqFrom = reqFrom;
	}
	public MeterDataHierarchy getHier() {
		return hier;
	}
	public void setHier(MeterDataHierarchy hier) {
		this.hier = hier;
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
	public String getReplaceBy() {
		return replaceBy;
	}
	public void setReplaceBy(String replaceBy) {
		this.replaceBy = replaceBy;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getExtBatchId() {
		return extBatchId;
	}
	public void setExtBatchId(String extBatchId) {
		this.extBatchId = extBatchId;
	}
	public String getReadWise() {
		return readWise;
	}
	public void setReadWise(String readWise) {
		this.readWise = readWise;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getDevType() {
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public String getInstallation_datetime() {
		return installation_datetime;
	}
	public void setInstallation_datetime(String installation_datetime) {
		this.installation_datetime = installation_datetime;
	}
	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}
	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}
	@Override
	public String toString() {
		return "SingleConnectionCommandReq [device=" + device + ", batchId=" + batchId + ", prepayObisCodeList="
				+ prepayObisCodeList + ", obisCodeList=" + obisCodeList + ", configVals=" + configVals
				+ ", dailyRangeDate=" + dailyRangeDate + ", deltaRangeDate=" + deltaRangeDate + ", source=" + source
				+ ", userId=" + userId + ", isDiscard=" + isDiscard + ", reqFrom=" + reqFrom + ", hier=" + hier
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", replaceBy=" + replaceBy + ", command=" + command
				+ ", trackingId=" + trackingId + ", extBatchId=" + extBatchId + ", readWise=" + readWise + ", reason="
				+ reason + ", manufacturer=" + manufacturer + ", devType=" + devType + ", installation_datetime="
				+ installation_datetime + "]";
	}

}