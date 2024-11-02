package com.global.meter.v3.inventive.business.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "single_connection_meter_command_logs")
public class SingleConnectionMeterCommandLog implements Serializable {

	private static final long serialVersionUID = 1374428140715485919L;

	@PrimaryKey
	private Date hes_date;
	
	private String device_serial_number;
	private String batch_id;
	private List<DevicesCommandLog> command_list;
	private String dcu_serial_number;
	private String dt_name; 
	private String feeder_name;
	private Date mdas_datetime;
	private String owner_name;
	private String reason;
	private String source;
	private String status;
	private String subdivision_name;
	private String substation_name;
	private int tot_attempts;
	private String user_id;
	private String device_type;
	private String backend_status;
	private Map<String,String> cmd_res;
	
	public SingleConnectionMeterCommandLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SingleConnectionMeterCommandLog(Date hes_date, String device_serial_number, String batch_id,
			List<DevicesCommandLog> command_list, String dcu_serial_number, String dt_name, String feeder_name,
			Date mdas_datetime, String owner_name, String reason, String source, String status, String subdivision_name,
			String substation_name, int tot_attempts, String user_id, String device_type, String backend_status) {
		super();
		this.hes_date = hes_date;
		this.device_serial_number = device_serial_number;
		this.batch_id = batch_id;
		this.command_list = command_list;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.mdas_datetime = mdas_datetime;
		this.owner_name = owner_name;
		this.reason = reason;
		this.source = source;
		this.status = status;
		this.subdivision_name = subdivision_name;
		this.substation_name = substation_name;
		this.tot_attempts = tot_attempts;
		this.user_id = user_id;
		this.device_type = device_type;
		this.backend_status = backend_status;
	}


	public Date getHes_date() {
		return hes_date;
	}

	public void setHes_date(Date hes_date) {
		this.hes_date = hes_date;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public List<DevicesCommandLog> getCommand_list() {
		return command_list;
	}

	public void setCommand_list(List<DevicesCommandLog> command_list) {
		this.command_list = command_list;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubdivision_name() {
		return subdivision_name;
	}

	public void setSubdivision_name(String subdivision_name) {
		this.subdivision_name = subdivision_name;
	}

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	public int getTot_attempts() {
		return tot_attempts;
	}

	public void setTot_attempts(int tot_attempts) {
		this.tot_attempts = tot_attempts;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getBackend_status() {
		return backend_status;
	}

	public void setBackend_status(String backend_status) {
		this.backend_status = backend_status;
	}

	public Map<String, String> getCmd_res() {
		return cmd_res;
	}

	public void setCmd_res(Map<String, String> cmd_res) {
		this.cmd_res = cmd_res;
	}

	@Override
	public String toString() {
		return "SingleConnectionMeterCommandLog [hes_date=" + hes_date + ", device_serial_number="
				+ device_serial_number + ", batch_id=" + batch_id + ", command_list=" + command_list
				+ ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name + ", feeder_name=" + feeder_name
				+ ", mdas_datetime=" + mdas_datetime + ", owner_name=" + owner_name + ", reason=" + reason + ", source="
				+ source + ", status=" + status + ", subdivision_name=" + subdivision_name + ", substation_name="
				+ substation_name + ", tot_attempts=" + tot_attempts + ", user_id=" + user_id + ", device_type="
				+ device_type + ", backend_status=" + backend_status + ", cmd_res=" + cmd_res + "]";
	}

}

