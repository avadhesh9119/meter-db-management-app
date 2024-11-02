package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class DeltaLPSinglePhaseResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9014013672246527014L;

	@JsonProperty("Meter No.")
	private String meterNumber;

	@JsonProperty("Interval Timestamp")
	private String intervalDatetime;

	@JsonProperty("Average Current")
	private String avgCurrent;

	@JsonProperty("Average Voltage")
	private String avgVoltage;

	@JsonProperty("Frequency")
	private String frequency;

	@JsonProperty("Block Energy-kWh(Imp)")
	private String kWhImport;

	@JsonProperty("Block Energy-kWh(Exp)")
	private String kWhExport;

	@JsonProperty("Block Energy-kVAh(Imp)")
	private String kvahImport;

	@JsonProperty("Block Energy-kVAh(Exp)")
	private String kvahExport;

	@JsonProperty("HES Timestamp")
	private String hesDatetime;
	
	@JsonProperty("Meter Health Indicator")
	private String statusByte;

	@JsonProperty("Avg Signal Strength")
	private String avgSignalStrength;

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

	public String getAvgCurrent() {
		return avgCurrent;
	}

	public void setAvgCurrent(String avgCurrent) {
		this.avgCurrent = avgCurrent;
	}

	public String getAvgVoltage() {
		return avgVoltage;
	}

	public void setAvgVoltage(String avgVoltage) {
		this.avgVoltage = avgVoltage;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
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

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
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

}
