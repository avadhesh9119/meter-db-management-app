package com.global.meter.v3.inventive.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(value = "device_command_log")
public class DevicesCommandLog implements Serializable {

	private static final long serialVersionUID = -5464535167645380388L;

	private Date command_datetime;
	private String command_name;
	private String command_val;
	private Date command_completion_datetime;
	private Map<String,String> daily_range_date ;
	private Map<String,String> delta_range_date;
	private int retry;
	private int seqno;
	private String  status;
	private String tracking_id;
	private String ext_batch_id;
	private Map<String,String> commands;
	private String source;
	private String user_id;
	private String cancel_by_user;
	private String mode;
	private Date temp_installation_datetime;
	
	
	public DevicesCommandLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DevicesCommandLog(Date command_datetime, String command_name, String command_val,
			Date command_completion_datetime, Map<String, String> daily_range_date,
			Map<String, String> delta_range_date, int retry, int seqno, String status, String tracking_id,
			String ext_batch_id, Map<String, String> commands, String source, String user_id) {
		super();
		this.command_datetime = command_datetime;
		this.command_name = command_name;
		this.command_val = command_val;
		this.command_completion_datetime = command_completion_datetime;
		this.daily_range_date = daily_range_date;
		this.delta_range_date = delta_range_date;
		this.retry = retry;
		this.seqno = seqno;
		this.status = status;
		this.tracking_id = tracking_id;
		this.ext_batch_id = ext_batch_id;
		this.commands = commands;
		this.source = source;
		this.user_id = user_id;
	}

	public Date getCommand_datetime() {
		return command_datetime;
	}

	public void setCommand_datetime(Date command_datetime) {
		this.command_datetime = command_datetime;
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

	public Date getCommand_completion_datetime() {
		return command_completion_datetime;
	}

	public void setCommand_completion_datetime(Date command_completion_datetime) {
		this.command_completion_datetime = command_completion_datetime;
	}

	public Map<String, String> getDaily_range_date() {
		return daily_range_date;
	}

	public void setDaily_range_date(Map<String, String> daily_range_date) {
		this.daily_range_date = daily_range_date;
	}

	public Map<String, String> getDelta_range_date() {
		return delta_range_date;
	}

	public void setDelta_range_date(Map<String, String> delta_range_date) {
		this.delta_range_date = delta_range_date;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}
	
	public String getExt_batch_id() {
		return ext_batch_id;
	}

	public void setExt_batch_id(String ext_batch_id) {
		this.ext_batch_id = ext_batch_id;
	}

	public Map<String, String> getCommands() {
		return commands;
	}

	public void setCommands(Map<String, String> commands) {
		this.commands = commands;
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
	
	public String getCancel_by_user() {
		return cancel_by_user;
	}

	public void setCancel_by_user(String cancel_by_user) {
		this.cancel_by_user = cancel_by_user;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Date getTemp_installation_datetime() {
		return temp_installation_datetime;
	}

	public void setTemp_installation_datetime(Date temp_installation_datetime) {
		this.temp_installation_datetime = temp_installation_datetime;
	}

	@Override
	public String toString() {
		return "DevicesCommandLog [command_datetime=" + command_datetime + ", command_name=" + command_name
				+ ", command_val=" + command_val + ", command_completion_datetime=" + command_completion_datetime
				+ ", daily_range_date=" + daily_range_date + ", delta_range_date=" + delta_range_date + ", retry="
				+ retry + ", seqno=" + seqno + ", status=" + status + ", tracking_id=" + tracking_id + ", ext_batch_id="
				+ ext_batch_id + ", commands=" + commands + ", source=" + source + ", user_id=" + user_id
				+ ", cancel_by_user=" + cancel_by_user + ", mode=" + mode + ", temp_installation_datetime="
				+ temp_installation_datetime + "]";
	}

}
