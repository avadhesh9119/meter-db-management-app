package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class PushInstantThreePhaseResponse {

	@JsonProperty("Meter No.")
	private String meterNumber;

	@JsonProperty("Meter Timestamp")
	private String meterDatetime;

	@JsonProperty("HES Timestamp")
	private String hesDatetime;
	
	@JsonProperty("KVah Import")
	private String kvahImport;
	
	@JsonProperty("KVah Export")
	private String kvahExport;

	@JsonProperty("kWh Import")
	private String kwhImport;
	
	@JsonProperty("kWh Export")
	private String kwhExport;

	@JsonProperty("Current R")
	private String currentL1;
	
	@JsonProperty("Current Y")
	private String currentL2;
	
	@JsonProperty("Current B")
	private String currentL3;

	@JsonProperty("Power Factor R")
	private String pfL1;

	@JsonProperty("Power Factor Y")
	private String pfL2;
	
	@JsonProperty("Power Factor B")
	private String pfL3;
	
	@JsonProperty("Voltage R")
	private String vL1;

	@JsonProperty("Voltage Y")
	private String vL2;
	
	@JsonProperty("Voltage B")
	private String vL3;
	
	@JsonProperty("Tamper Count")
	private String tamperCount;
	
	@JsonProperty("signed PF")
	private String signedPf;
	
	@JsonProperty("Subdivision")
	private String subdivision;

	@JsonProperty("Substation")
	private String substation;

	@JsonProperty("Feeder")
	private String feeder;

	@JsonProperty("DT")
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

	public String getKwhImport() {
		return kwhImport;
	}

	public void setKwhImport(String kwhImport) {
		this.kwhImport = kwhImport;
	}

	public String getKwhExport() {
		return kwhExport;
	}

	public void setKwhExport(String kwhExport) {
		this.kwhExport = kwhExport;
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

	public String getvL1() {
		return vL1;
	}

	public void setvL1(String vL1) {
		this.vL1 = vL1;
	}

	public String getvL2() {
		return vL2;
	}

	public void setvL2(String vL2) {
		this.vL2 = vL2;
	}

	public String getvL3() {
		return vL3;
	}

	public void setvL3(String vL3) {
		this.vL3 = vL3;
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
	
	public String getTamperCount() {
		return tamperCount;
	}

	public void setTamperCount(String tamperCount) {
		this.tamperCount = tamperCount;
	}

	public String getSignedPf() {
		return signedPf;
	}

	public void setSignedPf(String signedPf) {
		this.signedPf = signedPf;
	}

	@Override
	public String toString() {
		return "PushInstantThreePhaseResponse [meterNumber=" + meterNumber + ", meterDatetime=" + meterDatetime
				+ ", hesDatetime=" + hesDatetime + ", kvahImport=" + kvahImport + ", kvahExport=" + kvahExport
				+ ", kwhImport=" + kwhImport + ", kwhExport=" + kwhExport + ", currentL1=" + currentL1 + ", currentL2="
				+ currentL2 + ", currentL3=" + currentL3 + ", pfL1=" + pfL1 + ", pfL2=" + pfL2 + ", pfL3=" + pfL3
				+ ", vL1=" + vL1 + ", vL2=" + vL2 + ", vL3=" + vL3 + ", tamperCount=" + tamperCount + ", signedPf="
				+ signedPf + ", subdivision=" + subdivision + ", substation=" + substation + ", feeder=" + feeder
				+ ", dt=" + dt + "]";
	}
}