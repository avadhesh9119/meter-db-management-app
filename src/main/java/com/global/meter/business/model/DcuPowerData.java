package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "dcu_power_data")
public class DcuPowerData implements Serializable {
	
	private static final long serialVersionUID = -6692567871177252257L;
	
	@PrimaryKey
	private String dcu_serial_number;	
	
	private String dcu_mac_nic_id;
	private Date mdas_datetime;
	private String message_type ;
	private String request_id;
	private String battery_charging_status;
	private String power_good_status;
	private String battery_raw_value;
	private String battery_mv_value;
	
	public String getDcu_serial_number() {
		return dcu_serial_number;
	}
	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
	}
	public String getDcu_mac_nic_id() {
		return dcu_mac_nic_id;
	}
	public void setDcu_mac_nic_id(String dcu_mac_nic_id) {
		this.dcu_mac_nic_id = dcu_mac_nic_id;
	}
	public Date getMdas_datetime() {
		return mdas_datetime;
	}
	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getBattery_charging_status() {
		return battery_charging_status;
	}
	public void setBattery_charging_status(String battery_charging_status) {
		this.battery_charging_status = battery_charging_status;
	}
	public String getPower_good_status() {
		return power_good_status;
	}
	public void setPower_good_status(String power_good_status) {
		this.power_good_status = power_good_status;
	}
	public String getBattery_raw_value() {
		return battery_raw_value;
	}
	public void setBattery_raw_value(String battery_raw_value) {
		this.battery_raw_value = battery_raw_value;
	}
	public String getBattery_mv_value() {
		return battery_mv_value;
	}
	public void setBattery_mv_value(String battery_mv_value) {
		this.battery_mv_value = battery_mv_value;
	}
	@Override
	public String toString() {
		return "DcuPowerData [dcu_serial_number=" + dcu_serial_number + ", dcu_mac_nic_id=" + dcu_mac_nic_id
				+ ", mdas_datetime=" + mdas_datetime + ", message_type=" + message_type + ", request_id=" + request_id
				+ ", battery_charging_status=" + battery_charging_status + ", power_good_status=" + power_good_status
				+ ", battery_raw_value=" + battery_raw_value + ", battery_mv_value=" + battery_mv_value + "]";
	}
	
}
