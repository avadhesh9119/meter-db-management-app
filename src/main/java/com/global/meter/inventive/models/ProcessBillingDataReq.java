package com.global.meter.inventive.models;

import java.util.Date;

public class ProcessBillingDataReq {

	private String device_serial_number;
	private Date billing_datetime;
	private Date mdas_datetime;
	private String owner_name;
	private String zone;
	private String circle;
	private String division;
	private String subdevision_name;
	private String substation_name;
	private String feeder_name;
	private String dt_name;
	private String recivedCount;
	private String onTimeCount;
	private String devCount;
	
	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public Date getBilling_datetime() {
		return billing_datetime;
	}

	public void setBilling_datetime(Date billing_datetime) {
		this.billing_datetime = billing_datetime;
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

	public String getRecivedCount() {
		return recivedCount;
	}

	public void setRecivedCount(String recivedCount) {
		this.recivedCount = recivedCount;
	}

	public String getOnTimeCount() {
		return onTimeCount;
	}

	public void setOnTimeCount(String onTimeCount) {
		this.onTimeCount = onTimeCount;
	}

	public String getDevCount() {
		return devCount;
	}

	public void setDevCount(String devCount) {
		this.devCount = devCount;
	}

}
