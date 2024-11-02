package com.global.meter.inventive.mdm.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class InstantaneousCtMdmResponse {

	private String meterNumber;
	private String meterDatetime;
	private String hesDatetime;
	private String activePowerKW;
	private String apparentPowerKva;
	private String reactivePowerKvar;
	private String powerOffDuration;
	private String kvahExport;
	private String kvahImport;
	private String kwhExport;
	private String kwhImport;
	private String mdKva;
	private String mdKvaDatetime;
	private String mdKw;
	private String mdKwDatetime;
	private String programCount;
	private String tamperCount;
	private String billCount;
	private String powerFailureCount;
	private String frequency;
	private String loadLimit;
	private String loadLimitStatus;
	private String kvahQ1;
	private String kvahQ2;
	private String kvahQ3;
	private String kvahQ4;
	private String voltageL1;
	private String voltageL2;
	private String voltageL3;
	private String currentL1;
	private String currentL2;
	private String currentL3;
	private String pfL1;
	private String pfL2;
	private String pfL3;
	private String pf3Ph;
	private String mdWExport;
	private String mdWExportDatetime;
	private String mdVAExport;
	private String mdVAExportDatetime;
	private String aPVoltAB;
	private String aPVoltBC;
	private String aPVoltAC;
	private String datetime;
	private String lastBillingDatetime;
	private String subdivisionName;
	private String substationName;
	private String feeder;
	private String dt;

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

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
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

	public String getReactivePowerKvar() {
		return reactivePowerKvar;
	}

	public void setReactivePowerKvar(String reactivePowerKvar) {
		this.reactivePowerKvar = reactivePowerKvar;
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

	public String getBillCount() {
		return billCount;
	}

	public void setBillCount(String billCount) {
		this.billCount = billCount;
	}

	public String getPowerFailureCount() {
		return powerFailureCount;
	}

	public void setPowerFailureCount(String powerFailureCount) {
		this.powerFailureCount = powerFailureCount;
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

	public String getLoadLimitStatus() {
		return loadLimitStatus;
	}

	public void setLoadLimitStatus(String loadLimitStatus) {
		this.loadLimitStatus = loadLimitStatus;
	}

	public String getKvahQ1() {
		return kvahQ1;
	}

	public void setKvahQ1(String kvahQ1) {
		this.kvahQ1 = kvahQ1;
	}

	public String getKvahQ2() {
		return kvahQ2;
	}

	public void setKvahQ2(String kvahQ2) {
		this.kvahQ2 = kvahQ2;
	}

	public String getKvahQ3() {
		return kvahQ3;
	}

	public void setKvahQ3(String kvahQ3) {
		this.kvahQ3 = kvahQ3;
	}

	public String getKvahQ4() {
		return kvahQ4;
	}

	public void setKvahQ4(String kvahQ4) {
		this.kvahQ4 = kvahQ4;
	}

	public String getVoltageL1() {
		return voltageL1;
	}

	public void setVoltageL1(String voltageL1) {
		this.voltageL1 = voltageL1;
	}

	public String getVoltageL2() {
		return voltageL2;
	}

	public void setVoltageL2(String voltageL2) {
		this.voltageL2 = voltageL2;
	}

	public String getVoltageL3() {
		return voltageL3;
	}

	public void setVoltageL3(String voltageL3) {
		this.voltageL3 = voltageL3;
	}

	public String getCurrentL1() {
		return currentL1;
	}

	public void setCurrentL1(String currentL1) {
		this.currentL1 = currentL1;
	}

	public String getCurrentL2() {
		return currentL2;
	}

	public void setCurrentL2(String currentL2) {
		this.currentL2 = currentL2;
	}

	public String getCurrentL3() {
		return currentL3;
	}

	public void setCurrentL3(String currentL3) {
		this.currentL3 = currentL3;
	}

	public String getPfL1() {
		return pfL1;
	}

	public void setPfL1(String pfL1) {
		this.pfL1 = pfL1;
	}

	public String getPfL2() {
		return pfL2;
	}

	public void setPfL2(String pfL2) {
		this.pfL2 = pfL2;
	}

	public String getPfL3() {
		return pfL3;
	}

	public void setPfL3(String pfL3) {
		this.pfL3 = pfL3;
	}

	public String getPf3Ph() {
		return pf3Ph;
	}

	public void setPf3Ph(String pf3Ph) {
		this.pf3Ph = pf3Ph;
	}

	public String getMdWExport() {
		return mdWExport;
	}

	public void setMdWExport(String mdWExport) {
		this.mdWExport = mdWExport;
	}

	public String getMdWExportDatetime() {
		return mdWExportDatetime;
	}

	public void setMdWExportDatetime(String mdWExportDatetime) {
		this.mdWExportDatetime = mdWExportDatetime;
	}

	public String getMdVAExport() {
		return mdVAExport;
	}

	public void setMdVAExport(String mdVAExport) {
		this.mdVAExport = mdVAExport;
	}

	public String getMdVAExportDatetime() {
		return mdVAExportDatetime;
	}

	public void setMdVAExportDatetime(String mdVAExportDatetime) {
		this.mdVAExportDatetime = mdVAExportDatetime;
	}

	public String getaPVoltAB() {
		return aPVoltAB;
	}

	public void setaPVoltAB(String aPVoltAB) {
		this.aPVoltAB = aPVoltAB;
	}

	public String getaPVoltBC() {
		return aPVoltBC;
	}

	public void setaPVoltBC(String aPVoltBC) {
		this.aPVoltBC = aPVoltBC;
	}

	public String getaPVoltAC() {
		return aPVoltAC;
	}

	public void setaPVoltAC(String aPVoltAC) {
		this.aPVoltAC = aPVoltAC;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getLastBillingDatetime() {
		return lastBillingDatetime;
	}

	public void setLastBillingDatetime(String lastBillingDatetime) {
		this.lastBillingDatetime = lastBillingDatetime;
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

	public String getPowerOffDuration() {
		return powerOffDuration;
	}

	public void setPowerOffDuration(String powerOffDuration) {
		this.powerOffDuration = powerOffDuration;
	}

	
}
