package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "scheduler_config_logs")
public class SchedulerConfigurationLogs implements Serializable{

	private static final long serialVersionUID = -5290816761455278114L;
	
	@PrimaryKey
	private String tracking_id;

	private Date mdas_datetime;
	private String description;
	private String source;
	private String user_id;
	private String data;

	public SchedulerConfigurationLogs() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SchedulerConfigurationLogs(String tracking_id, Date mdas_datetime, String description, String source,
			String user_id, String data) {
		super();
		this.tracking_id = tracking_id;
		this.mdas_datetime = mdas_datetime;
		this.description = description;
		this.source = source;
		this.user_id = user_id;
		this.data = data;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "SchedulerConfigurationLogs [tracking_id=" + tracking_id + ", mdas_datetime=" + mdas_datetime
				+ ", description=" + description + ", source=" + source + ", user_id=" + user_id + ", data=" + data
				+ "]";
	}

}
