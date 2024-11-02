package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "name_plate")
public class NamePlate implements Serializable {

	private static final long serialVersionUID = -2969968264897324923L;

	@PrimaryKey
	private String device_serial_number;

	private String category;
	private String current_ratings;
	private String device_id;
	private String firmware_version;
	private String manufacturer_name;
	private String manufacturer_year;
	private Date mdas_datetime;
	private String meter_type;
	private String feeder_name;
	private String subdevision_name;
	private String substation_name;
	private String meter_serial_number;
	private String dcu_serial_number;
	private String dt_name;
	private String status;
	private String owner_name;
	private String ct_ratio;
	private String pt_ratio;
	
	
	public NamePlate() {
		super();
	}

	public NamePlate(String device_serial_number, String category, String current_ratings, String device_id,
			String firmware_version, String manufacturer_name, String manufacturer_year, Date mdas_datetime,
			String meter_type) {
		super();
		this.device_serial_number = device_serial_number;
		this.category = category;
		this.current_ratings = current_ratings;
		this.device_id = device_id;
		this.firmware_version = firmware_version;
		this.manufacturer_name = manufacturer_name;
		this.manufacturer_year = manufacturer_year;
		this.mdas_datetime = mdas_datetime;
		this.meter_type = meter_type;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getCategory() {
		return category;
	}

	public String getFeeder_name() {
		return feeder_name;
	}

	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
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

	public String getMeter_serial_number() {
		return meter_serial_number;
	}

	public void setMeter_serial_number(String meter_serial_number) {
		this.meter_serial_number = meter_serial_number;
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

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCurrent_ratings() {
		return current_ratings;
	}

	public void setCurrent_ratings(String current_ratings) {
		this.current_ratings = current_ratings;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getFirmware_version() {
		return firmware_version;
	}

	public void setFirmware_version(String firmware_version) {
		this.firmware_version = firmware_version;
	}

	public String getManufacturer_name() {
		return manufacturer_name;
	}

	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}

	public String getManufacturer_year() {
		return manufacturer_year;
	}

	public void setManufacturer_year(String manufacturer_year) {
		this.manufacturer_year = manufacturer_year;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getMeter_type() {
		return meter_type;
	}

	public void setMeter_type(String meter_type) {
		this.meter_type = meter_type;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getCt_ratio() {
		return ct_ratio;
	}

	public void setCt_ratio(String ct_ratio) {
		this.ct_ratio = ct_ratio;
	}

	public String getPt_ratio() {
		return pt_ratio;
	}

	public void setPt_ratio(String pt_ratio) {
		this.pt_ratio = pt_ratio;
	}

	@Override
	public String toString() {
		return "NamePlate [device_serial_number=" + device_serial_number + ", category=" + category
				+ ", current_ratings=" + current_ratings + ", device_id=" + device_id + ", firmware_version="
				+ firmware_version + ", manufacturer_name=" + manufacturer_name + ", manufacturer_year="
				+ manufacturer_year + ", mdas_datetime=" + mdas_datetime + ", meter_type=" + meter_type
				+ ", feeder_name=" + feeder_name + ", subdevision_name=" + subdevision_name + ", substation_name="
				+ substation_name + ", meter_serial_number=" + meter_serial_number + ", dcu_serial_number="
				+ dcu_serial_number + ", dt_name=" + dt_name + ", status=" + status + ", owner_name=" + owner_name
				+ ", ct_ratio=" + ct_ratio + ", pt_ratio=" + pt_ratio + "]";
	}

}