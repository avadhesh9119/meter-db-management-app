package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "devices_config")
public class DevicesConfig implements Serializable {

	private static final long serialVersionUID = 6721662591769989031L;

	@PrimaryKey
	private String device_serial_number;

	private String command_name;
	private Date command_datetime;
	private Date command_completion_datetime;
	private String command_val;
	private String dcu_serial_number;
	private String dt_name;
	private String feeder_name;
	private Date mdas_datetime;
	private String owner_name;
	private String status;
	private String subdevision_name;
	private String substation_name;
	private String tracking_id;
	private String reason;
	private Integer tot_attempts;
	private String source;
	private String user_id;
	private String batch_id;

	public DevicesConfig(String device_serial_number, String command_name, Date command_datetime,
			Date command_completion_datetime, String command_val, String dcu_serial_number,
			String dt_name, String feeder_name, Date mdas_datetime, String owner_name, String status,
			String subdevision_name, String substation_name, String tracking_id, String reason, Integer tot_attempts,String batch_id) {
		super();
		this.device_serial_number = device_serial_number;
		this.command_name = command_name;
		this.command_datetime = command_datetime;
		this.command_completion_datetime = command_completion_datetime;
		this.command_val = command_val;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.mdas_datetime = mdas_datetime;
		this.owner_name = owner_name;
		this.status = status;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.tracking_id = tracking_id;
		this.reason = reason;
		this.tot_attempts = tot_attempts;
		this.batch_id = batch_id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getTot_attempts() {
		return tot_attempts;
	}

	public void setTot_attempts(Integer tot_attempts) {
		this.tot_attempts = tot_attempts;
	}

	public DevicesConfig() {
		super();
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getCommand_name() {
		return command_name;
	}

	public void setCommand_name(String command_name) {
		this.command_name = command_name;
	}

	public Date getCommand_datetime() {
		return command_datetime;
	}

	public void setCommand_datetime(Date command_datetime) {
		this.command_datetime = command_datetime;
	}

	public Date getCommand_completion_datetime() {
		return command_completion_datetime;
	}

	public void setCommand_completion_datetime(Date command_completion_datetime) {
		this.command_completion_datetime = command_completion_datetime;
	}

	public String getCommand_val() {
		return command_val;
	}

	public void setCommand_val(String command_val) {
		this.command_val = command_val;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

}
