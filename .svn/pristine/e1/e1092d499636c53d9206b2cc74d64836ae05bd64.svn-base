package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "devices_commands_batch_logs")
public class DevicesCommandsBatchLogs implements Serializable{

	private static final long serialVersionUID = -5581059776447864657L;
	
	@PrimaryKey
	private String batch_id;
	private String owner_name;
	private int in_progress;
	private int success;
	private int failure;
	private Date mdas_datetime;
	private String command_name;
	private String hier_name;
	private String hier_value;
	private String source;
	private String user_id;
	
	
	public DevicesCommandsBatchLogs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DevicesCommandsBatchLogs(String batch_id, String owner_name, int in_progress, int success, int failure,
			Date mdas_datetime, String command_name, String hier_name, String hier_value, String source,
			String user_id) {
		super();
		this.batch_id = batch_id;
		this.owner_name = owner_name;
		this.in_progress = in_progress;
		this.success = success;
		this.failure = failure;
		this.mdas_datetime = mdas_datetime;
		this.command_name = command_name;
		this.hier_name = hier_name;
		this.hier_value = hier_value;
		this.source = source;
		this.user_id = user_id;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public int getIn_progress() {
		return in_progress;
	}

	public void setIn_progress(int in_progress) {
		this.in_progress = in_progress;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getCommand_name() {
		return command_name;
	}

	public void setCommand_name(String command_name) {
		this.command_name = command_name;
	}

	public String getHier_name() {
		return hier_name;
	}

	public void setHier_name(String hier_name) {
		this.hier_name = hier_name;
	}

	public String getHier_value() {
		return hier_value;
	}

	public void setHier_value(String hier_value) {
		this.hier_value = hier_value;
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

	@Override
	public String toString() {
		return "DevicesCommandsBatchLogs [batch_id=" + batch_id + ", owner_name=" + owner_name + ", in_progress="
				+ in_progress + ", success=" + success + ", failure=" + failure + ", mdas_datetime=" + mdas_datetime
				+ ", command_name=" + command_name + ", hier_name=" + hier_name + ", hier_value=" + hier_value
				+ ", source=" + source + ", user_id=" + user_id + "]";
	}
	
}
