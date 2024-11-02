package com.global.meter.inventive.mdm.models;

public class SingleConnectionCommandLogMdmResponse {

	private String tracking_id;

	private String device_serial_number;

	private String command_name;

	private String hes_date;

	private String mdas_datetime;

	private String command_completion_datetime;

	private String status;

	private String tot_attempts;

	private String batch_id;

	private String ext_batch_id;

	private String command_list;

	private String device_type;

	private String owner_name;

	private String reason;

	private String source;

	private String user_id;

	private String retry;

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
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

	public String getHes_date() {
		return hes_date;
	}

	public void setHes_date(String hes_date) {
		this.hes_date = hes_date;
	}

	public String getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(String mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getCommand_completion_datetime() {
		return command_completion_datetime;
	}

	public void setCommand_completion_datetime(String command_completion_datetime) {
		this.command_completion_datetime = command_completion_datetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getExt_batch_id() {
		return ext_batch_id;
	}

	public void setExt_batch_id(String ext_batch_id) {
		this.ext_batch_id = ext_batch_id;
	}

	public String getCommand_list() {
		return command_list;
	}

	public void setCommand_list(String command_list) {
		this.command_list = command_list;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRetry() {
		return retry;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}

}