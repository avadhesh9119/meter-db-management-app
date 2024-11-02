package com.global.meter.request.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.global.meter.business.model.FirmwareData;
import com.global.meter.inventive.models.MeterDataHierarchy;

@JsonInclude(Include.NON_NULL)
public class DeviceCommand implements Serializable{
	
	private static final long serialVersionUID = 5162677294141286785L;
	
	private Device device;
	private String command;
	private String startDate;
	private String commandsVal;
	private String levelName;
	private String levelValue;
	private String trackingId;
	private String readWise;
	private List<String> obisCodeList;
	private List<String> prepayObisCodeList;
	private String cellId;
	private Map<String, String> configVals;
	private Map<String, String> configValsStatus;
	private String activateActivityCalDatetime;
	private String requestFrom;
	private FirmwareData firmwareData;
	private boolean skipOtherManufacturer;
	private MeterDataHierarchy hier;
	private List<String> commandList;
	private String batchId;
	private String source;
	private String userId;
	private String manufacturer;
	private Map<String, String> dailyRangeDate;
	private Map<String, String> deltaRangeDate;
	private String created;
	private String modified;
	private String updatedBy;
	private Map<String, String> firmwareVersion;
	
	
	public List<String> getCommandList() {
		return commandList;
	}
	public void setCommandList(List<String> commandList) {
		this.commandList = commandList;
	}
	public MeterDataHierarchy getHier() {
		return hier;
	}
	public void setHier(MeterDataHierarchy hier) {
		this.hier = hier;
	}
	public FirmwareData getFirmwareData() {
		return firmwareData;
	}
	public void setFirmwareData(FirmwareData firmwareData) {
		this.firmwareData = firmwareData;
	}
	public boolean isSkipOtherManufacturer() {
		return skipOtherManufacturer;
	}
	public void setSkipOtherManufacturer(boolean skipOtherManufacturer) {
		this.skipOtherManufacturer = skipOtherManufacturer;
	}
	public String getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	
	public String getActivateActivityCalDatetime() {
		return activateActivityCalDatetime;
	}
	public void setActivateActivityCalDatetime(String activateActivityCalDatetime) {
		this.activateActivityCalDatetime = activateActivityCalDatetime;
	}
	public String getReadWise() {
		return readWise;
	}
	public void setReadWise(String readWise) {
		this.readWise = readWise;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getCommandsVal() {
		return commandsVal;
	}
	public void setCommandsVal(String commandsVal) {
		this.commandsVal = commandsVal;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelValue() {
		return levelValue;
	}
	public void setLevelValue(String levelValue) {
		this.levelValue = levelValue;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public List<String> getObisCodeList() {
		return obisCodeList;
	}
	public void setObisCodeList(List<String> obisCodeList) {
		this.obisCodeList = obisCodeList;
	}
	public String getCellId() {
		return cellId;
	}
	public void setCellId(String cellId) {
		this.cellId = cellId;
	}
	public Map<String, String> getConfigVals() {
		return configVals;
	}
	public void setConfigVals(Map<String, String> configVals) {
		this.configVals = configVals;
	}
	public Map<String, String> getConfigValsStatus() {
		return configValsStatus;
	}
	public void setConfigValsStatus(Map<String, String> configValsStatus) {
		this.configValsStatus = configValsStatus;
	}
	public List<String> getPrepayObisCodeList() {
		return prepayObisCodeList;
	}
	public void setPrepayObisCodeList(List<String> prepayObisCodeList) {
		this.prepayObisCodeList = prepayObisCodeList;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Map<String, String> getFirmwareVersion() {
		return firmwareVersion;
	}
	public void setFirmwareVersion(Map<String, String> firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	
}
