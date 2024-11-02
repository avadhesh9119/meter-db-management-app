package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DeltaLPThreePhaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8210609004639776880L;

	@JsonProperty("Meter No.")
	private String meterNumber;

	@JsonProperty("Interval Timestamp")
	private String intervalDatetime;
	
	@JsonProperty("HES Timestamp")
	private String hesDatetime;
	
	@JsonProperty("Block Energy-kVAh(Imp)")
	private String kvahImport;

	@JsonProperty("Block Energy-kVAh(Exp)")
	private String kvahExport;

	@JsonProperty("Block Energy-kWh(Imp)")
	private String kWhImport;

	@JsonProperty("Block Energy-kWh(Exp)")
	private String kWhExport;

	@JsonProperty("L1 Current Avg")
	private String phaseCurrentL1;

	@JsonProperty("L2 Current Avg")
	private String phaseCurrentL2;

	@JsonProperty("L3 Current Avg")
	private String phaseCurrentL3;

	@JsonProperty("L1 Voltage Avg")
	private String phaseVoltageL1;

	@JsonProperty("L2 Voltage Avg")
	private String phaseVoltageL2;

	@JsonProperty("L3 Voltage Avg")
	private String phaseVoltageL3;

	@JsonProperty("Block Energy-kVarh,Q1")
	private String kvarhQ1;

	@JsonProperty("Block Energy-kVarh,Q2")
	private String kvarhQ2;

	@JsonProperty("Block Energy-kVarh,Q3")
	private String kvarhQ3;

	@JsonProperty("Block Energy-kVarh,Q4")
	private String kvarhQ4;

	@JsonProperty("Meter Health Indicator")
	private String statusByte;

	@JsonProperty("Avg Signal Strength")
	private String avgSignalStrength;

	@JsonProperty("Power Downtime In Mins")
	private String powerDowntimeInMins;

	@JsonProperty("R Phase Active Power (kw)")
	private String rPhaseActivePowerKw;

	@JsonProperty("Y Phase Active Power (kw)")
	private String yPhaseActivePowerKw;

	@JsonProperty("B Phase Active Power (kw)")
	private String bPhaseActivePowerKw;

	@JsonProperty("Lead Block Energy (kvarh)")
	private String leadBlockEnergyKvarh;

	@JsonProperty("Cum. Energy-kWh(Imp)")
	private String cumulativeEnergyKwh;

	@JsonProperty("Cum. Energy-kVAh(Imp)")
	private String cumulativeEnergyKvah;

	@JsonProperty("MD Type")
	private String mdType;

	@JsonProperty("MD Block Sliding Type")
	private String mdBlockSlidingType;

	@JsonProperty("Dlms_Object_Neutral_Current_3Ph")
	private String neutralCurrent;

	@JsonProperty("Mobile No")
	private String mobileNo;

	@JsonProperty("Instant avgpf")
	private String instantAvgpf;

	@JsonProperty("Module rtc")
	private String moduleRtc;
	
	@JsonProperty("kVA Max")
	private String kVAMax;
	
	@JsonProperty("Max Current")
	private String maxCurrent;
	
	@JsonProperty("Max Voltage")
	private String maxVoltage;


	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getIntervalDatetime() {
		return intervalDatetime;
	}

	public void setIntervalDatetime(String intervalDatetime) {
		this.intervalDatetime = intervalDatetime;
	}

	public String getKvahImport() {
		return kvahImport;
	}

	public void setKvahImport(String kvahImport) {
		this.kvahImport = kvahImport;
	}

	public String getKvahExport() {
		return kvahExport;
	}

	public void setKvahExport(String kvahExport) {
		this.kvahExport = kvahExport;
	}

	public String getkWhImport() {
		return kWhImport;
	}

	public void setkWhImport(String kWhImport) {
		this.kWhImport = kWhImport;
	}

	public String getkWhExport() {
		return kWhExport;
	}

	public void setkWhExport(String kWhExport) {
		this.kWhExport = kWhExport;
	}

	public String getPhaseCurrentL1() {
		return phaseCurrentL1;
	}

	public void setPhaseCurrentL1(String phaseCurrentL1) {
		this.phaseCurrentL1 = phaseCurrentL1;
	}

	public String getPhaseCurrentL2() {
		return phaseCurrentL2;
	}

	public void setPhaseCurrentL2(String phaseCurrentL2) {
		this.phaseCurrentL2 = phaseCurrentL2;
	}

	public String getPhaseCurrentL3() {
		return phaseCurrentL3;
	}

	public void setPhaseCurrentL3(String phaseCurrentL3) {
		this.phaseCurrentL3 = phaseCurrentL3;
	}

	public String getPhaseVoltageL1() {
		return phaseVoltageL1;
	}

	public void setPhaseVoltageL1(String phaseVoltageL1) {
		this.phaseVoltageL1 = phaseVoltageL1;
	}

	public String getPhaseVoltageL2() {
		return phaseVoltageL2;
	}

	public void setPhaseVoltageL2(String phaseVoltageL2) {
		this.phaseVoltageL2 = phaseVoltageL2;
	}

	public String getPhaseVoltageL3() {
		return phaseVoltageL3;
	}

	public void setPhaseVoltageL3(String phaseVoltageL3) {
		this.phaseVoltageL3 = phaseVoltageL3;
	}

	public String getKvarhQ1() {
		return kvarhQ1;
	}

	public void setKvarhQ1(String kvarhQ1) {
		this.kvarhQ1 = kvarhQ1;
	}

	public String getKvarhQ2() {
		return kvarhQ2;
	}

	public void setKvarhQ2(String kvarhQ2) {
		this.kvarhQ2 = kvarhQ2;
	}

	public String getKvarhQ3() {
		return kvarhQ3;
	}

	public void setKvarhQ3(String kvarhQ3) {
		this.kvarhQ3 = kvarhQ3;
	}

	public String getKvarhQ4() {
		return kvarhQ4;
	}

	public void setKvarhQ4(String kvarhQ4) {
		this.kvarhQ4 = kvarhQ4;
	}

	public String getStatusByte() {
		return statusByte;
	}

	public void setStatusByte(String statusByte) {
		this.statusByte = statusByte;
	}

	public String getAvgSignalStrength() {
		return avgSignalStrength;
	}

	public void setAvgSignalStrength(String avgSignalStrength) {
		this.avgSignalStrength = avgSignalStrength;
	}

	public String getPowerDowntimeInMins() {
		return powerDowntimeInMins;
	}

	public void setPowerDowntimeInMins(String powerDowntimeInMins) {
		this.powerDowntimeInMins = powerDowntimeInMins;
	}

	public String getrPhaseActivePowerKw() {
		return rPhaseActivePowerKw;
	}

	public void setrPhaseActivePowerKw(String rPhaseActivePowerKw) {
		this.rPhaseActivePowerKw = rPhaseActivePowerKw;
	}

	public String getyPhaseActivePowerKw() {
		return yPhaseActivePowerKw;
	}

	public void setyPhaseActivePowerKw(String yPhaseActivePowerKw) {
		this.yPhaseActivePowerKw = yPhaseActivePowerKw;
	}

	public String getbPhaseActivePowerKw() {
		return bPhaseActivePowerKw;
	}

	public void setbPhaseActivePowerKw(String bPhaseActivePowerKw) {
		this.bPhaseActivePowerKw = bPhaseActivePowerKw;
	}

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
	}

	public String getLeadBlockEnergyKvarh() {
		return leadBlockEnergyKvarh;
	}

	public void setLeadBlockEnergyKvarh(String leadBlockEnergyKvarh) {
		this.leadBlockEnergyKvarh = leadBlockEnergyKvarh;
	}

	public String getCumulativeEnergyKwh() {
		return cumulativeEnergyKwh;
	}

	public void setCumulativeEnergyKwh(String cumulativeEnergyKwh) {
		this.cumulativeEnergyKwh = cumulativeEnergyKwh;
	}

	public String getCumulativeEnergyKvah() {
		return cumulativeEnergyKvah;
	}

	public void setCumulativeEnergyKvah(String cumulativeEnergyKvah) {
		this.cumulativeEnergyKvah = cumulativeEnergyKvah;
	}

	public String getMdType() {
		return mdType;
	}

	public void setMdType(String mdType) {
		this.mdType = mdType;
	}

	public String getMdBlockSlidingType() {
		return mdBlockSlidingType;
	}

	public void setMdBlockSlidingType(String mdBlockSlidingType) {
		this.mdBlockSlidingType = mdBlockSlidingType;
	}

	public String getNeutralCurrent() {
		return neutralCurrent;
	}

	public void setNeutralCurrent(String neutralCurrent) {
		this.neutralCurrent = neutralCurrent;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getInstantAvgpf() {
		return instantAvgpf;
	}

	public void setInstantAvgpf(String instantAvgpf) {
		this.instantAvgpf = instantAvgpf;
	}

	public String getModuleRtc() {
		return moduleRtc;
	}

	public void setModuleRtc(String moduleRtc) {
		this.moduleRtc = moduleRtc;
	}

	public String getkVAMax() {
		return kVAMax;
	}

	public void setkVAMax(String kVAMax) {
		this.kVAMax = kVAMax;
	}

	public String getMaxCurrent() {
		return maxCurrent;
	}

	public void setMaxCurrent(String maxCurrent) {
		this.maxCurrent = maxCurrent;
	}

	public String getMaxVoltage() {
		return maxVoltage;
	}

	public void setMaxVoltage(String maxVoltage) {
		this.maxVoltage = maxVoltage;
	}
	

}
