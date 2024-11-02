package com.global.meter.v3.inventive.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "properties_file_logs")
public class AppPropertiesFileLog implements Serializable {

	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String tracking_id;
	private String old_property_value;
	private String new_property_value;
	private String updated_by;
	private Date updated_on;
	private String source;
	private String description;
	private String user_comment;
	private Date mdas_datetime;

	public AppPropertiesFileLog() {
		super();
	}

	public AppPropertiesFileLog(String tracking_id, String old_property_value, String new_property_value,
			String updated_by, Date updated_on, String source, String description, String user_comment,
			Date mdas_datetime) {
		super();
		this.tracking_id = tracking_id;
		this.old_property_value = old_property_value;
		this.new_property_value = new_property_value;
		this.updated_by = updated_by;
		this.updated_on = updated_on;
		this.source = source;
		this.description = description;
		this.user_comment = user_comment;
		this.mdas_datetime = mdas_datetime;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public String getOld_property_value() {
		return old_property_value;
	}

	public void setOld_property_value(String old_property_value) {
		this.old_property_value = old_property_value;
	}

	public String getNew_property_value() {
		return new_property_value;
	}

	public void setNew_property_value(String new_property_value) {
		this.new_property_value = new_property_value;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser_comment() {
		return user_comment;
	}

	public void setUser_comment(String user_comment) {
		this.user_comment = user_comment;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	@Override
	public String toString() {
		return "AppPropertiesFileLog [tracking_id=" + tracking_id + ", old_property_value=" + old_property_value
				+ ", new_property_value=" + new_property_value + ", updated_by=" + updated_by + ", updated_on="
				+ updated_on + ", source=" + source + ", description=" + description + ", user_comment=" + user_comment
				+ ", mdas_datetime=" + mdas_datetime + "]";
	}

}
