package com.global.meter.business.model;

import java.util.Date;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "devices_commands_logs_one_per_day")
public class DeviceCommandLogsOnePerDay {

	@PrimaryKey
	private String device_serial_number;

	private Date command_datetime;
	private String tracking_id;
	private Map<String,String> command_name;
	private String status;
	private Date mdas_datetime;  
	private String dcu_serial_number;
	private String dt_name;
	private String feeder_name;
	private String owner_name;
	private String subdevision_name;
	private String substation_name;
	private String reason;
	private Date command_completion_datetime;
	private Integer tot_attempts;
	private Map<String,Integer> command_retry;
	private String cell_id;

	public DeviceCommandLogsOnePerDay() {
	}

	public DeviceCommandLogsOnePerDay(String device_serial_number, String tracking_id, Map<String,String> command_name, String status,
			Date mdas_datetime, String dcu_serial_number, String dt_name, String feeder_name, String owner_name,
			String subdevision_name, String substation_name, String reason, Date command_completion_datetime,
			Integer tot_attempts, Map<String,Integer> command_retry) {
		super();
		this.device_serial_number = device_serial_number;
		this.tracking_id = tracking_id;
		this.command_name = command_name;
		this.status = status;
		this.mdas_datetime = mdas_datetime;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.owner_name = owner_name;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.reason = reason;
		this.command_completion_datetime = command_completion_datetime;
		this.tot_attempts = tot_attempts;
		this.command_retry = command_retry;
	}

	public String getCell_id() {
		return cell_id;
	}

	public void setCell_id(String cell_id) {
		this.cell_id = cell_id;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
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

	public Map<String,String> getCommand_name() {
		return command_name;
	}

	public void setCommand_name(Map<String,String> command_name) {
		this.command_name = command_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Date getCommand_completion_datetime() {
		return command_completion_datetime;
	}

	public void setCommand_completion_datetime(Date command_completion_datetime) {
		this.command_completion_datetime = command_completion_datetime;
	}

	public Integer getTot_attempts() {
		return tot_attempts;
	}

	public void setTot_attempts(Integer tot_attempts) {
		this.tot_attempts = tot_attempts;
	}

	public Map<String, Integer> getCommand_retry() {
		return command_retry;
	}

	public void setCommand_retry(Map<String, Integer> command_retry) {
		this.command_retry = command_retry;
	}
	
	public Date getCommand_datetime() {
		return command_datetime;
	}

	public void setCommand_datetime(Date command_datetime) {
		this.command_datetime = command_datetime;
	}

	@Override
	public String toString() {
		return "DeviceCommandLogs [device_serial_number=" + device_serial_number + ", tracking_id=" + tracking_id
				+ ", command_name=" + command_name + ", status=" + status + ", mdas_datetime=" + mdas_datetime
				+ ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name + ", feeder_name=" + feeder_name
				+ ", owner_name=" + owner_name + ", subdevision_name=" + subdevision_name + ", substation_name="
				+ substation_name + ", reason=" + reason + ", command_completion_datetime="
				+ command_completion_datetime + ", tot_attempts=" + tot_attempts + ", command_retry=" + command_retry
				+ ", cell_id=" + cell_id + "]";
	}
	
}