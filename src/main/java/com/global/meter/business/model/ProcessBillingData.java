package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "process_billing_profile_data")
public class ProcessBillingData implements Serializable {

	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String device_serial_number;

	private String is_daily;
	private String is_monthly;
	private String is_weekly;
	private String device_type;
	private Date mdas_datetime;
	private String owner_name;
	private String zone;
	private String circle;
	private String division;
	private String subdevision_name;
	private String substation_name;
	private String feeder_name;
	private String dt_name;
	private String is_on_time_billing;
	private String is_received_billing;
	private String commissioning_status;

	public ProcessBillingData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcessBillingData(String device_serial_number, String is_daily, String is_monthly, String is_weekly,
			String device_type, Date mdas_datetime, String owner_name, String zone, String circle, String division,
			String subdevision_name, String substation_name, String feeder_name, String dt_name,
			String is_on_time_billing, String is_received_billing, String commissioning_status) {
		super();
		this.device_serial_number = device_serial_number;
		this.is_daily = is_daily;
		this.is_monthly = is_monthly;
		this.is_weekly = is_weekly;
		this.device_type = device_type;
		this.mdas_datetime = mdas_datetime;
		this.owner_name = owner_name;
		this.zone = zone;
		this.circle = circle;
		this.division = division;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.feeder_name = feeder_name;
		this.dt_name = dt_name;
		this.is_on_time_billing = is_on_time_billing;
		this.is_received_billing = is_received_billing;
		this.commissioning_status = commissioning_status;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getIs_daily() {
		return is_daily;
	}

	public void setIs_daily(String is_daily) {
		this.is_daily = is_daily;
	}

	public String getIs_monthly() {
		return is_monthly;
	}

	public void setIs_monthly(String is_monthly) {
		this.is_monthly = is_monthly;
	}

	public String getIs_weekly() {
		return is_weekly;
	}

	public void setIs_weekly(String is_weekly) {
		this.is_weekly = is_weekly;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getCircle() {
		return circle;
	}

	public void setCircle(String circle) {
		this.circle = circle;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
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

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}

	public String getDt_name() {
		return dt_name;
	}

	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}

	public String getIs_on_time_billing() {
		return is_on_time_billing;
	}

	public void setIs_on_time_billing(String is_on_time_billing) {
		this.is_on_time_billing = is_on_time_billing;
	}

	public String getIs_received_billing() {
		return is_received_billing;
	}

	public void setIs_received_billing(String is_received_billing) {
		this.is_received_billing = is_received_billing;
	}

	public String getCommissioning_status() {
		return commissioning_status;
	}

	public void setCommissioning_status(String commissioning_status) {
		this.commissioning_status = commissioning_status;
	}

	@Override
	public String toString() {
		return "ProcessBillingData [device_serial_number=" + device_serial_number + ", is_daily=" + is_daily
				+ ", is_monthly=" + is_monthly + ", is_weekly=" + is_weekly + ", device_type=" + device_type
				+ ", mdas_datetime=" + mdas_datetime + ", owner_name=" + owner_name + ", zone=" + zone + ", circle="
				+ circle + ", division=" + division + ", subdevision_name=" + subdevision_name + ", substation_name="
				+ substation_name + ", feeder_name=" + feeder_name + ", dt_name=" + dt_name + ", is_on_time_billing="
				+ is_on_time_billing + ", is_received_billing=" + is_received_billing + ", commissioning_status="
				+ commissioning_status + "]";
	}

}