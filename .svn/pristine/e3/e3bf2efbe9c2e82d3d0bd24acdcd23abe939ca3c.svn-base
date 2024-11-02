package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "load_profile_data_singlephase")
public class DeltaLoadProfileSinglePhase implements Serializable {

	private static final long serialVersionUID = -6945666684895475516L;

	@PrimaryKey
	private String device_serial_number;

	private Date interval_datetime;
	private Double average_current;
	private Double average_voltage;
	private Double block_energy_kvah_export;
	private Double block_energy_kvah_import;
	private Double block_energy_kwh_export;
	private Double block_energy_kwh_import;
	private String dcu_serial_number;
	private String dt_name;
	private String feeder_name;
	private Double frequency;
	private Date mdas_datetime;
	private Date meter_datetime;
	private String owner_name;
	private String subdevision_name;
	private String substation_name;
	private Double status_byte;
    private Double avg_signal_strength;

	public DeltaLoadProfileSinglePhase() {
		super();
	}

	public DeltaLoadProfileSinglePhase(String device_serial_number, Date interval_datetime, Double average_current,
			Double average_voltage, Double block_energy_kvah_export, Double block_energy_kvah_import,
			Double block_energy_kwh_export, Double block_energy_kwh_import, String dcu_serial_number, String dt_name,
			String feeder_name, Double frequency, Date mdas_datetime, Date meter_datetime, String owner_name,
			String subdevision_name, String substation_name) {
		super();
		this.device_serial_number = device_serial_number;
		this.interval_datetime = interval_datetime;
		this.average_current = average_current;
		this.average_voltage = average_voltage;
		this.block_energy_kvah_export = block_energy_kvah_export;
		this.block_energy_kvah_import = block_energy_kvah_import;
		this.block_energy_kwh_export = block_energy_kwh_export;
		this.block_energy_kwh_import = block_energy_kwh_import;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.frequency = frequency;
		this.mdas_datetime = mdas_datetime;
		this.meter_datetime = meter_datetime;
		this.owner_name = owner_name;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
	}
	
	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public Date getInterval_datetime() {
		return interval_datetime;
	}

	public void setInterval_datetime(Date interval_datetime) {
		this.interval_datetime = interval_datetime;
	}

	public Double getAverage_current() {
		return average_current;
	}

	public void setAverage_current(Double average_current) {
		this.average_current = average_current;
	}

	public Double getAverage_voltage() {
		return average_voltage;
	}

	public void setAverage_voltage(Double average_voltage) {
		this.average_voltage = average_voltage;
	}

	public Double getBlock_energy_kvah_export() {
		return block_energy_kvah_export;
	}

	public void setBlock_energy_kvah_export(Double block_energy_kvah_export) {
		this.block_energy_kvah_export = block_energy_kvah_export;
	}

	public Double getBlock_energy_kvah_import() {
		return block_energy_kvah_import;
	}

	public void setBlock_energy_kvah_import(Double block_energy_kvah_import) {
		this.block_energy_kvah_import = block_energy_kvah_import;
	}

	public Double getBlock_energy_kwh_export() {
		return block_energy_kwh_export;
	}

	public void setBlock_energy_kwh_export(Double block_energy_kwh_export) {
		this.block_energy_kwh_export = block_energy_kwh_export;
	}

	public Double getBlock_energy_kwh_import() {
		return block_energy_kwh_import;
	}

	public void setBlock_energy_kwh_import(Double block_energy_kwh_import) {
		this.block_energy_kwh_import = block_energy_kwh_import;
	}

	public String getDcu_serial_number() {
		return dcu_serial_number;
	}

	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
	}

	public String getDt_name() {
		return dt_name;
	}

	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public Date getMeter_datetime() {
		return meter_datetime;
	}

	public void setMeter_datetime(Date meter_datetime) {
		this.meter_datetime = meter_datetime;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getSubdevision_name() {
		return subdevision_name;
	}

	public void setSubdevision_name(String subdevision_name) {
		this.subdevision_name = subdevision_name;
	}

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	public Double getStatus_byte() {
		return status_byte;
	}

	public void setStatus_byte(Double status_byte) {
		this.status_byte = status_byte;
	}

	public Double getAvg_signal_strength() {
		return avg_signal_strength;
	}

	public void setAvg_signal_strength(Double avg_signal_strength) {
		this.avg_signal_strength = avg_signal_strength;
	}

	@Override
	public String toString() {
		return "DeltaLoadProfileSinglePhase [device_serial_number=" + device_serial_number + ", interval_datetime="
				+ interval_datetime + ", average_current=" + average_current + ", average_voltage=" + average_voltage
				+ ", block_energy_kvah_export=" + block_energy_kvah_export + ", block_energy_kvah_import="
				+ block_energy_kvah_import + ", block_energy_kwh_export=" + block_energy_kwh_export
				+ ", block_energy_kwh_import=" + block_energy_kwh_import + ", dcu_serial_number=" + dcu_serial_number
				+ ", dt_name=" + dt_name + ", feeder_name=" + feeder_name + ", frequency=" + frequency
				+ ", mdas_datetime=" + mdas_datetime + ", meter_datetime=" + meter_datetime + ", owner_name="
				+ owner_name + ", subdevision_name=" + subdevision_name + ", substation_name=" + substation_name
				+ ", status_byte=" + status_byte + ", avg_signal_strength=" + avg_signal_strength + "]";
	}
}
