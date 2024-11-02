package com.global.meter.request.model;

import java.io.Serializable;

public class DevicesCommandReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8805659401721543542L;
	
	private String device_serial_number;
	private String tracking_id;
	private String command_name;
	private String command_val;
	private String cell_id;
	
	public String getDevice_serial_number() {
		return device_serial_number;
	}
	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}
	public String getTracking_id() {
		return tracking_id;
	}
	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}
	public String getCommand_name() {
		return command_name;
	}
	public void setCommand_name(String command_name) {
		this.command_name = command_name;
	}
	public String getCommand_val() {
		return command_val;
	}
	public void setCommand_val(String command_val) {
		this.command_val = command_val;
	}
	public String getCell_id() {
		return cell_id;
	}
	public void setCell_id(String cell_id) {
		this.cell_id = cell_id;
	}
	
}
