package com.global.meter.inventive.dashboard.model;

import java.util.Date;

public class DailylpSla {
	private String device_serial_number;
	private Date mdas_datetime;
	private Date datetime;
	private String capture;
	public String getDevice_serial_number() {
		return device_serial_number;
	}
	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}
	public Date getMdas_datetime() {
		return mdas_datetime;
	}
	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getCapture() {
		return capture;
	}
	public void setCapture(String capture) {
		this.capture = capture;
	}
	
	@Override
	public String toString() {
		return "DailylpSla [device_serial_number=" + device_serial_number + ", mdas_datetime=" + mdas_datetime
				+ ", datetime=" + datetime + ", capture=" + capture + "]";
	}
}
