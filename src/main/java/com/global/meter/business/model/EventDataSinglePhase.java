package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "event_data_singlephase")
public class EventDataSinglePhase implements Serializable{

	private static final long serialVersionUID = -2969968264897324923L;

	@PrimaryKey
	private String device_serial_number;

	private Integer event_code;
	private Date event_datetime;
	private Double cumulative_energy;
	private Double current;
	private String dcu_serial_number;
	private String dt_name;
	private String event_category;
	private String event_type;
	private String feeder_name;
	private Date mdas_datetime;
	private Date meter_datetime;
	private String owner_name;
	private Double power_factor;
	private String subdevision_name;
	private String substation_name;
	private Integer tamper_count;
	private Double voltage;

	public EventDataSinglePhase() {
		super();
	}

	public EventDataSinglePhase(String device_serial_number, Integer event_code, Date event_datetime,
			Double cumulative_energy, Double current, String dcu_serial_number, String dt_name, String event_category,
			String event_type, String feeder_name, Date mdas_datetime, Date meter_datetime, String owner_name,
			Double power_factor, String subdevision_name, String substation_name, Integer tamper_count,
			Double voltage) {
		super();
		this.device_serial_number = device_serial_number;
		this.event_code = event_code;
		this.event_datetime = event_datetime;
		this.cumulative_energy = cumulative_energy;
		this.current = current;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.event_category = event_category;
		this.event_type = event_type;
		this.feeder_name = feeder_name;
		this.mdas_datetime = mdas_datetime;
		this.meter_datetime = meter_datetime;
		this.owner_name = owner_name;
		this.power_factor = power_factor;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.tamper_count = tamper_count;
		this.voltage = voltage;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public Integer getEvent_code() {
		return event_code;
	}

	public void setEvent_code(Integer event_code) {
		this.event_code = event_code;
	}

	public Date getEvent_datetime() {
		return event_datetime;
	}

	public void setEvent_datetime(Date event_datetime) {
		this.event_datetime = event_datetime;
	}

	public Double getCumulative_energy() {
		return cumulative_energy;
	}

	public void setCumulative_energy(Double cumulative_energy) {
		this.cumulative_energy = cumulative_energy;
	}

	public Double getCurrent() {
		return current;
	}

	public void setCurrent(Double current) {
		this.current = current;
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

	public String getEvent_category() {
		return event_category;
	}

	public void setEvent_category(String event_category) {
		this.event_category = event_category;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
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

	public Double getPower_factor() {
		return power_factor;
	}

	public void setPower_factor(Double power_factor) {
		this.power_factor = power_factor;
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

	public Integer getTamper_count() {
		return tamper_count;
	}

	public void setTamper_count(Integer tamper_count) {
		this.tamper_count = tamper_count;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	@Override
	public String toString() {
		return "EventDataSinglePhase [device_serial_number=" + device_serial_number + ", event_code=" + event_code
				+ ", event_datetime=" + event_datetime + ", cumulative_energy=" + cumulative_energy + ", current="
				+ current + ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name + ", event_category="
				+ event_category + ", event_type=" + event_type + ", feeder_name=" + feeder_name + ", mdas_datetime="
				+ mdas_datetime + ", meter_datetime=" + meter_datetime + ", owner_name=" + owner_name
				+ ", power_factor=" + power_factor + ", subdevision_name=" + subdevision_name + ", substation_name="
				+ substation_name + ", tamper_count=" + tamper_count + ", voltage=" + voltage + "]";
	}

}
