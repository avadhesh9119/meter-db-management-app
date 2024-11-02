package com.global.meter.inventive.dashboard.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DailySummaryReportResponse{

	private String meterNo;
	private String hesTimestamp;
	private String devicetype;
	private String dailyLoadProfileTimestamp;
	private String dailyProfileSLA;
	private String blockLoadCount;
	private String blockLoadFailureSlaAvg;
	private String blockLoadProfileSLA;
	private String blockLoadSuccessSlaAvg;
	private String firstBreath;
	private String lastGasp;
	private String instantaneous;
	private String instantPushData;
	private String namePlate;
	private String utility;
	private String manufacturer;
	private String subdivision;
	private String substation;
	private String feeder;
	private String dt;
	
	public String getMeterNo() {
		return meterNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	public String getHesTimestamp() {
		return hesTimestamp;
	}
	public void setHesTimestamp(String hesTimestamp) {
		this.hesTimestamp = hesTimestamp;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}
	public String getDailyLoadProfileTimestamp() {
		return dailyLoadProfileTimestamp;
	}
	public void setDailyLoadProfileTimestamp(String dailyLoadProfileTimestamp) {
		this.dailyLoadProfileTimestamp = dailyLoadProfileTimestamp;
	}
	public String getDailyProfileSLA() {
		return dailyProfileSLA;
	}
	public void setDailyProfileSLA(String dailyProfileSLA) {
		this.dailyProfileSLA = dailyProfileSLA;
	}
	public String getBlockLoadCount() {
		return blockLoadCount;
	}
	public void setBlockLoadCount(String blockLoadCount) {
		this.blockLoadCount = blockLoadCount;
	}
	public String getBlockLoadFailureSlaAvg() {
		return blockLoadFailureSlaAvg;
	}
	public void setBlockLoadFailureSlaAvg(String blockLoadFailureSlaAvg) {
		this.blockLoadFailureSlaAvg = blockLoadFailureSlaAvg;
	}
	public String getBlockLoadProfileSLA() {
		return blockLoadProfileSLA;
	}
	public void setBlockLoadProfileSLA(String blockLoadProfileSLA) {
		this.blockLoadProfileSLA = blockLoadProfileSLA;
	}
	public String getBlockLoadSuccessSlaAvg() {
		return blockLoadSuccessSlaAvg;
	}
	public void setBlockLoadSuccessSlaAvg(String blockLoadSuccessSlaAvg) {
		this.blockLoadSuccessSlaAvg = blockLoadSuccessSlaAvg;
	}
	public String getFirstBreath() {
		return firstBreath;
	}
	public void setFirstBreath(String firstBreath) {
		this.firstBreath = firstBreath;
	}
	public String getLastGasp() {
		return lastGasp;
	}
	public void setLastGasp(String lastGasp) {
		this.lastGasp = lastGasp;
	}
	public String getInstantaneous() {
		return instantaneous;
	}
	public void setInstantaneous(String instantaneous) {
		this.instantaneous = instantaneous;
	}
	public String getInstantPushData() {
		return instantPushData;
	}
	public void setInstantPushData(String instantPushData) {
		this.instantPushData = instantPushData;
	}
	public String getNamePlate() {
		return namePlate;
	}
	public void setNamePlate(String namePlate) {
		this.namePlate = namePlate;
	}
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getSubstation() {
		return substation;
	}
	public void setSubstation(String substation) {
		this.substation = substation;
	}
	public String getFeeder() {
		return feeder;
	}
	public void setFeeder(String feeder) {
		this.feeder = feeder;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	
	@Override
	public String toString() {
		return "DailySummaryReportResponse [meterNo=" + meterNo + ", hesTimestamp=" + hesTimestamp + ", devicetype="
				+ devicetype + ", dailyLoadProfileTimestamp=" + dailyLoadProfileTimestamp + ", dailyProfileSLA="
				+ dailyProfileSLA + ", blockLoadCount=" + blockLoadCount + ", blockLoadFailureSlaAvg="
				+ blockLoadFailureSlaAvg + ", blockLoadProfileSLA=" + blockLoadProfileSLA + ", blockLoadSuccessSlaAvg="
				+ blockLoadSuccessSlaAvg + ", firstBreath=" + firstBreath + ", lastGasp=" + lastGasp
				+ ", instantaneous=" + instantaneous + ", instantPushData=" + instantPushData + ", namePlate="
				+ namePlate + ", utility=" + utility + ", manufacturer=" + manufacturer + ", subdivision=" + subdivision
				+ ", substation=" + substation + ", feeder=" + feeder + ", dt=" + dt + "]";
	}
	
}
