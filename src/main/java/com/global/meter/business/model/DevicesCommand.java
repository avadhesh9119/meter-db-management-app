package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "devices_commands")
public class DevicesCommand implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4831170519376890079L;

	@PrimaryKey
	private String device_serial_number;

	private String command_name;

	private String tracking_id;
	private Date command_completion_datetime;
	private String dcu_serial_number;
	private String dt_name;
	private String feeder_name;
	private Date mdas_datetime;
	private String owner_name;
	private String reason;
	private String status;
	private String subdevision_name;
	private String substation_name;
	private String tot_attempts;
	private String source;
	private String user_id;
	private String batch_id;


	public DevicesCommand() {
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


	public String getTracking_id() {
		return tracking_id;
	}


	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}


	public Date getCommand_completion_datetime() {
		return command_completion_datetime;
	}


	public void setCommand_completion_datetime(Date command_completion_datetime) {
		this.command_completion_datetime = command_completion_datetime;
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


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
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


	public String getTot_attempts() {
		return tot_attempts;
	}


	public void setTot_attempts(String tot_attempts) {
		this.tot_attempts = tot_attempts;
	}
		
	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
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


	public DevicesCommand(String device_serial_number, String command_name, String tracking_id,
			Date command_completion_datetime, String dcu_serial_number, String dt_name, String feeder_name,
			Date mdas_datetime, String owner_name, String reason, String status, String subdevision_name,
			String substation_name, String tot_attempts, String source, String user_id) {
		super();
		this.device_serial_number = device_serial_number;
		this.command_name = command_name;
		this.tracking_id = tracking_id;
		this.command_completion_datetime = command_completion_datetime;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.mdas_datetime = mdas_datetime;
		this.owner_name = owner_name;
		this.reason = reason;
		this.status = status;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.tot_attempts = tot_attempts;
		this.source = source;
		this.user_id = user_id;
	}


	
}
