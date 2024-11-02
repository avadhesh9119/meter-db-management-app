package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DailyLPCTResponse {
	
	@JsonProperty("Meter No.")
	private String meterNumber;
	
	@JsonProperty("Read Timestamp")
	private String datetime;
	
	@JsonProperty("HES Timestamp")
	private String hesDatetime;
	
	@JsonProperty("Cum. Energy-kWh(Imp)")
	private String kWhImport;
	
	@JsonProperty("Cum. Energy-kWh(Exp)")
	private String kWhExport;
	
	@JsonProperty("Cum. Energy-kVAh(Imp)")
	private String kvahImport;
	
	@JsonProperty("Cum. Energy-kVAh(Exp)")
	private String kvahExport;

	@JsonProperty("Cum. Energy-kVarh,Q1")
	private String kvarh_q1;
	
	@JsonProperty("Cum. Energy-kVarh,Q2")
	private String kvarh_q2;
	
	@JsonProperty("Cum. Energy-kVarh,Q3")
	private String kvarh_q3;
	
	@JsonProperty("Cum. Energy-kVarh,Q4")
	private String kvarh_q4;
	
	@JsonProperty("MD-kVA(Imp)")
	private String mdkva;
	
	@JsonProperty("MD-Timestamp kVA(Imp)")
	private String  mdkvaDatetime;
	
	@JsonProperty("MD-kW(Imp)")
	private String  mdkw;
	
	@JsonProperty("MD-Timestamp kW(Imp)")
	private String mdkwDatetime;
	
	@JsonProperty("MD-kW(exp)")
	private String  mdkwExport;
	
	@JsonProperty("MD-Timestamp kW(exp)")
	private String mdkwExportDatetime;

	@JsonProperty("No Supply")
	private String  noSupply;
	
	@JsonProperty("No Load")
	private String noLoad;

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
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

	public String getKvarh_q1() {
		return kvarh_q1;
	}

	public void setKvarh_q1(String kvarh_q1) {
		this.kvarh_q1 = kvarh_q1;
	}

	public String getKvarh_q2() {
		return kvarh_q2;
	}

	public void setKvarh_q2(String kvarh_q2) {
		this.kvarh_q2 = kvarh_q2;
	}

	public String getKvarh_q3() {
		return kvarh_q3;
	}

	public void setKvarh_q3(String kvarh_q3) {
		this.kvarh_q3 = kvarh_q3;
	}

	public String getKvarh_q4() {
		return kvarh_q4;
	}

	public void setKvarh_q4(String kvarh_q4) {
		this.kvarh_q4 = kvarh_q4;
	}

	public String getMdkva() {
		return mdkva;
	}

	public void setMdkva(String mdkva) {
		this.mdkva = mdkva;
	}

	public String getMdkvaDatetime() {
		return mdkvaDatetime;
	}

	public void setMdkvaDatetime(String mdkvaDatetime) {
		this.mdkvaDatetime = mdkvaDatetime;
	}

	public String getMdkw() {
		return mdkw;
	}

	public void setMdkw(String mdkw) {
		this.mdkw = mdkw;
	}

	public String getMdkwDatetime() {
		return mdkwDatetime;
	}

	public void setMdkwDatetime(String mdkwDatetime) {
		this.mdkwDatetime = mdkwDatetime;
	}

	public String getMdkwExport() {
		return mdkwExport;
	}

	public void setMdkwExport(String mdkwExport) {
		this.mdkwExport = mdkwExport;
	}

	public String getMdkwExportDatetime() {
		return mdkwExportDatetime;
	}

	public void setMdkwExportDatetime(String mdkwExportDatetime) {
		this.mdkwExportDatetime = mdkwExportDatetime;
	}

	public String getNoSupply() {
		return noSupply;
	}

	public void setNoSupply(String noSupply) {
		this.noSupply = noSupply;
	}

	public String getNoLoad() {
		return noLoad;
	}

	public void setNoLoad(String noLoad) {
		this.noLoad = noLoad;
	}

}
