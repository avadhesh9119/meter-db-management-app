package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "firmware_config")
public class FirmwareConfig implements Serializable {

	private static final long serialVersionUID = 6721662591769989031L;

	@PrimaryKey
	private String device_serial_number;

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
	private String version;
	private String source;
	private String user_id;
	private Date created;
	private Date modified;
	private String updated_by;
	
	public FirmwareConfig(String device_serial_number, Date command_datetime,
			Date command_completion_datetime, String command_val, String dcu_serial_number,
			String dt_name, String feeder_name, Date mdas_datetime, String owner_name, String status,
			String subdevision_name, String substation_name, String tracking_id, String reason, Integer tot_attempts) {
		super();
		this.device_serial_number = device_serial_number;
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

	public FirmwareConfig() {
		super();
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	@Override
	public String toString() {
		return "FirmwareConfig [device_serial_number=" + device_serial_number + ", command_datetime=" + command_datetime
				+ ", command_completion_datetime=" + command_completion_datetime + ", command_val=" + command_val
				+ ", dcu_serial_number=" + dcu_serial_number + ", dt_name=" + dt_name + ", feeder_name=" + feeder_name
				+ ", mdas_datetime=" + mdas_datetime + ", owner_name=" + owner_name + ", status=" + status
				+ ", subdevision_name=" + subdevision_name + ", substation_name=" + substation_name + ", tracking_id="
				+ tracking_id + ", reason=" + reason + ", tot_attempts=" + tot_attempts + ", version=" + version
				+ ", source=" + source + ", user_id=" + user_id + ", created=" + created + ", modified=" + modified
				+ ", updated_by=" + updated_by + "]";
	}

}
