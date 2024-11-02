package com.global.meter.inventive.mdm.models;

/**
 * 
 * @author Satish Chauhan
 *
 */
public class InstantaneousSinglePhaseMdmResponse {

	private String meterNumber;	
	private String meterDatetime;
	private String hesDatetime;
	private String activePowerKW;	
	private String apparentPowerKva;
	private String kvahExport;
	private String kvahImport;
	private String kwhExport;
	private String kwhImport;
	private String mdKva;
	private String mdKvaDatetime;
	private String mdKw;
	private String mdKwDatetime;
	private String powerDuration;
	private String programCount;
	private String tamperCount;
	private String billCount;
	private String frequency;
	private String loadLimit;
	private String powerFactor;
	private String voltage;
	private String neutralCurrent;
	private String phaseCurrent;
	private String loadLimitStatus;

	
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getMeterDatetime() {
		return meterDatetime;
	}
	public void setMeterDatetime(String meterDatetime) {
		this.meterDatetime = meterDatetime;
	}
	public String getActivePowerKW() {
		return activePowerKW;
	}
	public void setActivePowerKW(String activePowerKW) {
		this.activePowerKW = activePowerKW;
	}
	public String getApparentPowerKva() {
		return apparentPowerKva;
	}
	public void setApparentPowerKva(String apparentPowerKva) {
		this.apparentPowerKva = apparentPowerKva;
	}
	public String getBillCount() {
		return billCount;
	}
	public void setBillCount(String billCount) {
		this.billCount = billCount;
	}
	public String getKvahExport() {
		return kvahExport;
	}
	public void setKvahExport(String kvahExport) {
		this.kvahExport = kvahExport;
	}
	public String getKvahImport() {
		return kvahImport;
	}
	public void setKvahImport(String kvahImport) {
		this.kvahImport = kvahImport;
	}
	public String getKwhExport() {
		return kwhExport;
	}
	public void setKwhExport(String kwhExport) {
		this.kwhExport = kwhExport;
	}
	public String getKwhImport() {
		return kwhImport;
	}
	public void setKwhImport(String kwhImport) {
		this.kwhImport = kwhImport;
	}
	public String getPowerDuration() {
		return powerDuration;
	}
	public void setPowerDuration(String powerDuration) {
		this.powerDuration = powerDuration;
	}
	public String getProgramCount() {
		return programCount;
	}
	public void setProgramCount(String programCount) {
		this.programCount = programCount;
	}
	public String getTamperCount() {
		return tamperCount;
	}
	public void setTamperCount(String tamperCount) {
		this.tamperCount = tamperCount;
	}
	public String getPowerFactor() {
		return powerFactor;
	}
	public void setPowerFactor(String powerFactor) {
		this.powerFactor = powerFactor;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getLoadLimit() {
		return loadLimit;
	}
	public void setLoadLimit(String loadLimit) {
		this.loadLimit = loadLimit;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getNeutralCurrent() {
		return neutralCurrent;
	}
	public void setNeutralCurrent(String neutralCurrent) {
		this.neutralCurrent = neutralCurrent;
	}
	public String getPhaseCurrent() {
		return phaseCurrent;
	}
	public void setPhaseCurrent(String phaseCurrent) {
		this.phaseCurrent = phaseCurrent;
	}
	public String getLoadLimitStatus() {
		return loadLimitStatus;
	}
	public void setLoadLimitStatus(String loadLimitStatus) {
		this.loadLimitStatus = loadLimitStatus;
	}
	public String getMdKva() {
		return mdKva;
	}
	public void setMdKva(String mdKva) {
		this.mdKva = mdKva;
	}
	public String getMdKvaDatetime() {
		return mdKvaDatetime;
	}
	public void setMdKvaDatetime(String mdKvaDatetime) {
		this.mdKvaDatetime = mdKvaDatetime;
	}
	public String getMdKw() {
		return mdKw;
	}
	public void setMdKw(String mdKw) {
		this.mdKw = mdKw;
	}
	public String getMdKwDatetime() {
		return mdKwDatetime;
	}
	public void setMdKwDatetime(String mdKwDatetime) {
		this.mdKwDatetime = mdKwDatetime;
	}
	public String getHesDatetime() {
		return hesDatetime;
	}
	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
	}
}
