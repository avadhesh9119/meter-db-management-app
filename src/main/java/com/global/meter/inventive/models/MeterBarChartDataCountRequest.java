package com.global.meter.inventive.models;

import java.util.Date;

public class MeterBarChartDataCountRequest {
	
	private String success_percentage;
	private String count;
	private String success_count;
	private String command_name;
	private Date date;
	private String device_count;
	private String device;
	
	public String getSuccess_percentage() {
		return success_percentage;
	}
	public void setSuccess_percentage(String success_percentage) {
		this.success_percentage = success_percentage;
	}
	public String getCommand_name() {
		return command_name;
	}
	public void setCommand_name(String command_name) {
		this.command_name = command_name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSuccess_count() {
		return success_count;
	}
	public void setSuccess_count(String success_count) {
		this.success_count = success_count;
	}
	public String getDevice_count() {
		return device_count;
	}
	public void setDevice_count(String device_count) {
		this.device_count = device_count;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	
}
