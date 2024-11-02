package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class DailyLPSinglePhaseResponse {

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
	
	@JsonProperty("Cum. Energy-kWh(Imp)-TZ1")
	private String kwhTier1;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ2")
	private String kwhTier2;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ3")
	private String kwhTier3;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ4")
	private String kwhTier4;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ5")
	private String kwhTier5;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ6")
	private String kwhTier6;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ7")
	private String kwhTier7;

	@JsonProperty("Cum. Energy-kWh(Imp)-TZ8")
	private String kwhTier8;
	
	@JsonProperty("Cum. Energy-kVAh(Imp)")
	private String kvahImport;
	
	@JsonProperty("Cum. Energy-kVAh(Exp)")
	private String kvahExport;
	
	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ1")
	private String kvahTier1;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ2")
	private String kvahTier2;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ3")
	private String kvahTier3;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ4")
	private String kvahTier4;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ5")
	private String kvahTier5;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ6")
	private String kvahTier6;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ7")
	private String kvahTier7;

	@JsonProperty("Cum. Energy-kVAh(Imp)-TZ8")
	private String kvahTier8;

	@JsonProperty("MD-kW(Imp)")
	private String  mdkw;
	
	@JsonProperty("MD-Timestamp kW(Imp)")
	private String mdkwDatetime;
	
	@JsonProperty("MD-kW(Imp)-TZ1")
	private String mdkwTier1;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ1")
	private String mdkwTier1Datetime;

	@JsonProperty("MD-kW(Imp)-TZ2")
	private String mdkwTier2;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ2")
	private String mdkwTier2Datetime;

	@JsonProperty("MD-kW(Imp)-TZ3")
	private String mdkwTier3;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ3")
	private String mdkwTier3Datetime;

	@JsonProperty("MD-kW(Imp)-TZ4")
	private String mdkwTier4;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ4")
	private String mdkwTier4Datetime;

	@JsonProperty("MD-kW(Imp)-TZ5")
	private String mdkwTier5;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ5")
	private String mdkwTier5Datetime;

	@JsonProperty("MD-kW(Imp)-TZ6")
	private String mdkwTier6;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ6")
	private String mdkwTier6Datetime;

	@JsonProperty("MD-kW(Imp)-TZ7")
	private String mdkwTier7;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ7")
	private String mdkwTier7Datetime;

	@JsonProperty("MD-kW(Imp)-TZ8")
	private String mdkwTier8;

	@JsonProperty("MD Timestamp-kW(Imp)-TZ8")
	private String mdkwTier8Datetime;

	@JsonProperty("MD-kVA(Imp)")
	private String mdkva;
	
	@JsonProperty("MD-Timestamp kVA(Imp)")
	private String  mdkvaDatetime;
	
	@JsonProperty("MD-kVA(Imp)-TZ1")
	private String mdkvaTier1;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ1")
	private String mdkvaTier1Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ2")
	private String mdkvaTier2;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ2")
	private String mdkvaTier2Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ3")
	private String mdkvaTier3;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ3")
	private String mdkvaTier3Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ4")
	private String mdkvaTier4;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ4")
	private String mdkvaTier4Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ5")
	private String mdkvaTier5;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ5")
	private String mdkvaTier5Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ6")
	private String mdkvaTier6;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ6")
	private String mdkvaTier6Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ7")
	private String mdkvaTier7;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ7")
	private String mdkvaTier7Datetime;

	@JsonProperty("MD-kVA(Imp)-TZ8")
	private String mdkvaTier8;

	@JsonProperty("MD Timestamp-kVA(Imp)-TZ8")
	private String mdkvaTier8Datetime;

	
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

	public String getkWhExport() {
		return kWhExport;
	}

	public void setkWhExport(String kWhExport) {
		this.kWhExport = kWhExport;
	}

	public String getkWhImport() {
		return kWhImport;
	}

	public void setkWhImport(String kWhImport) {
		this.kWhImport = kWhImport;
	}

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
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

	public String getKwhTier1() {
		return kwhTier1;
	}

	public void setKwhTier1(String kwhTier1) {
		this.kwhTier1 = kwhTier1;
	}

	public String getKwhTier2() {
		return kwhTier2;
	}

	public void setKwhTier2(String kwhTier2) {
		this.kwhTier2 = kwhTier2;
	}

	public String getKwhTier3() {
		return kwhTier3;
	}

	public void setKwhTier3(String kwhTier3) {
		this.kwhTier3 = kwhTier3;
	}

	public String getKwhTier4() {
		return kwhTier4;
	}

	public void setKwhTier4(String kwhTier4) {
		this.kwhTier4 = kwhTier4;
	}

	public String getKwhTier5() {
		return kwhTier5;
	}

	public void setKwhTier5(String kwhTier5) {
		this.kwhTier5 = kwhTier5;
	}

	public String getKwhTier6() {
		return kwhTier6;
	}

	public void setKwhTier6(String kwhTier6) {
		this.kwhTier6 = kwhTier6;
	}

	public String getKwhTier7() {
		return kwhTier7;
	}

	public void setKwhTier7(String kwhTier7) {
		this.kwhTier7 = kwhTier7;
	}

	public String getKwhTier8() {
		return kwhTier8;
	}

	public void setKwhTier8(String kwhTier8) {
		this.kwhTier8 = kwhTier8;
	}

	public String getKvahTier1() {
		return kvahTier1;
	}

	public void setKvahTier1(String kvahTier1) {
		this.kvahTier1 = kvahTier1;
	}

	public String getKvahTier2() {
		return kvahTier2;
	}

	public void setKvahTier2(String kvahTier2) {
		this.kvahTier2 = kvahTier2;
	}

	public String getKvahTier3() {
		return kvahTier3;
	}

	public void setKvahTier3(String kvahTier3) {
		this.kvahTier3 = kvahTier3;
	}

	public String getKvahTier4() {
		return kvahTier4;
	}

	public void setKvahTier4(String kvahTier4) {
		this.kvahTier4 = kvahTier4;
	}

	public String getKvahTier5() {
		return kvahTier5;
	}

	public void setKvahTier5(String kvahTier5) {
		this.kvahTier5 = kvahTier5;
	}

	public String getKvahTier6() {
		return kvahTier6;
	}

	public void setKvahTier6(String kvahTier6) {
		this.kvahTier6 = kvahTier6;
	}

	public String getKvahTier7() {
		return kvahTier7;
	}

	public void setKvahTier7(String kvahTier7) {
		this.kvahTier7 = kvahTier7;
	}

	public String getKvahTier8() {
		return kvahTier8;
	}

	public void setKvahTier8(String kvahTier8) {
		this.kvahTier8 = kvahTier8;
	}

	public String getMdkwTier1() {
		return mdkwTier1;
	}

	public void setMdkwTier1(String mdkwTier1) {
		this.mdkwTier1 = mdkwTier1;
	}

	public String getMdkwTier1Datetime() {
		return mdkwTier1Datetime;
	}

	public void setMdkwTier1Datetime(String mdkwTier1Datetime) {
		this.mdkwTier1Datetime = mdkwTier1Datetime;
	}

	public String getMdkwTier2() {
		return mdkwTier2;
	}

	public void setMdkwTier2(String mdkwTier2) {
		this.mdkwTier2 = mdkwTier2;
	}

	public String getMdkwTier2Datetime() {
		return mdkwTier2Datetime;
	}

	public void setMdkwTier2Datetime(String mdkwTier2Datetime) {
		this.mdkwTier2Datetime = mdkwTier2Datetime;
	}

	public String getMdkwTier3() {
		return mdkwTier3;
	}

	public void setMdkwTier3(String mdkwTier3) {
		this.mdkwTier3 = mdkwTier3;
	}

	public String getMdkwTier3Datetime() {
		return mdkwTier3Datetime;
	}

	public void setMdkwTier3Datetime(String mdkwTier3Datetime) {
		this.mdkwTier3Datetime = mdkwTier3Datetime;
	}

	public String getMdkwTier4() {
		return mdkwTier4;
	}

	public void setMdkwTier4(String mdkwTier4) {
		this.mdkwTier4 = mdkwTier4;
	}

	public String getMdkwTier4Datetime() {
		return mdkwTier4Datetime;
	}

	public void setMdkwTier4Datetime(String mdkwTier4Datetime) {
		this.mdkwTier4Datetime = mdkwTier4Datetime;
	}

	public String getMdkwTier5() {
		return mdkwTier5;
	}

	public void setMdkwTier5(String mdkwTier5) {
		this.mdkwTier5 = mdkwTier5;
	}

	public String getMdkwTier5Datetime() {
		return mdkwTier5Datetime;
	}

	public void setMdkwTier5Datetime(String mdkwTier5Datetime) {
		this.mdkwTier5Datetime = mdkwTier5Datetime;
	}

	public String getMdkwTier6() {
		return mdkwTier6;
	}

	public void setMdkwTier6(String mdkwTier6) {
		this.mdkwTier6 = mdkwTier6;
	}

	public String getMdkwTier6Datetime() {
		return mdkwTier6Datetime;
	}

	public void setMdkwTier6Datetime(String mdkwTier6Datetime) {
		this.mdkwTier6Datetime = mdkwTier6Datetime;
	}

	public String getMdkwTier7() {
		return mdkwTier7;
	}

	public void setMdkwTier7(String mdkwTier7) {
		this.mdkwTier7 = mdkwTier7;
	}

	public String getMdkwTier7Datetime() {
		return mdkwTier7Datetime;
	}

	public void setMdkwTier7Datetime(String mdkwTier7Datetime) {
		this.mdkwTier7Datetime = mdkwTier7Datetime;
	}

	public String getMdkwTier8() {
		return mdkwTier8;
	}

	public void setMdkwTier8(String mdkwTier8) {
		this.mdkwTier8 = mdkwTier8;
	}

	public String getMdkwTier8Datetime() {
		return mdkwTier8Datetime;
	}

	public void setMdkwTier8Datetime(String mdkwTier8Datetime) {
		this.mdkwTier8Datetime = mdkwTier8Datetime;
	}

	public String getMdkvaTier1() {
		return mdkvaTier1;
	}

	public void setMdkvaTier1(String mdkvaTier1) {
		this.mdkvaTier1 = mdkvaTier1;
	}

	public String getMdkvaTier1Datetime() {
		return mdkvaTier1Datetime;
	}

	public void setMdkvaTier1Datetime(String mdkvaTier1Datetime) {
		this.mdkvaTier1Datetime = mdkvaTier1Datetime;
	}

	public String getMdkvaTier2() {
		return mdkvaTier2;
	}

	public void setMdkvaTier2(String mdkvaTier2) {
		this.mdkvaTier2 = mdkvaTier2;
	}

	public String getMdkvaTier2Datetime() {
		return mdkvaTier2Datetime;
	}

	public void setMdkvaTier2Datetime(String mdkvaTier2Datetime) {
		this.mdkvaTier2Datetime = mdkvaTier2Datetime;
	}

	public String getMdkvaTier3() {
		return mdkvaTier3;
	}

	public void setMdkvaTier3(String mdkvaTier3) {
		this.mdkvaTier3 = mdkvaTier3;
	}

	public String getMdkvaTier3Datetime() {
		return mdkvaTier3Datetime;
	}

	public void setMdkvaTier3Datetime(String mdkvaTier3Datetime) {
		this.mdkvaTier3Datetime = mdkvaTier3Datetime;
	}

	public String getMdkvaTier4() {
		return mdkvaTier4;
	}

	public void setMdkvaTier4(String mdkvaTier4) {
		this.mdkvaTier4 = mdkvaTier4;
	}

	public String getMdkvaTier4Datetime() {
		return mdkvaTier4Datetime;
	}

	public void setMdkvaTier4Datetime(String mdkvaTier4Datetime) {
		this.mdkvaTier4Datetime = mdkvaTier4Datetime;
	}

	public String getMdkvaTier5() {
		return mdkvaTier5;
	}

	public void setMdkvaTier5(String mdkvaTier5) {
		this.mdkvaTier5 = mdkvaTier5;
	}

	public String getMdkvaTier5Datetime() {
		return mdkvaTier5Datetime;
	}

	public void setMdkvaTier5Datetime(String mdkvaTier5Datetime) {
		this.mdkvaTier5Datetime = mdkvaTier5Datetime;
	}

	public String getMdkvaTier6() {
		return mdkvaTier6;
	}

	public void setMdkvaTier6(String mdkvaTier6) {
		this.mdkvaTier6 = mdkvaTier6;
	}

	public String getMdkvaTier6Datetime() {
		return mdkvaTier6Datetime;
	}

	public void setMdkvaTier6Datetime(String mdkvaTier6Datetime) {
		this.mdkvaTier6Datetime = mdkvaTier6Datetime;
	}

	public String getMdkvaTier7() {
		return mdkvaTier7;
	}

	public void setMdkvaTier7(String mdkvaTier7) {
		this.mdkvaTier7 = mdkvaTier7;
	}

	public String getMdkvaTier7Datetime() {
		return mdkvaTier7Datetime;
	}

	public void setMdkvaTier7Datetime(String mdkvaTier7Datetime) {
		this.mdkvaTier7Datetime = mdkvaTier7Datetime;
	}

	public String getMdkvaTier8() {
		return mdkvaTier8;
	}

	public void setMdkvaTier8(String mdkvaTier8) {
		this.mdkvaTier8 = mdkvaTier8;
	}

	public String getMdkvaTier8Datetime() {
		return mdkvaTier8Datetime;
	}

	public void setMdkvaTier8Datetime(String mdkvaTier8Datetime) {
		this.mdkvaTier8Datetime = mdkvaTier8Datetime;
	}
	
}
