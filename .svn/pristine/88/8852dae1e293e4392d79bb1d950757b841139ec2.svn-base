package com.global.meter.v3.inventive.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "division_factor_file_logs")
public class DivisionFactorFileLog implements Serializable {

	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String tracking_id;
	private String old_division_factor_value;
	private String new_division_factor_value;
	private String updated_by;
	private Date updated_on;
	private String source;
	private String description;
	private String user_comment;
	private Date mdas_datetime;
	private String created_by;
	private Date created_on;
	private String div_factor_type;

	public DivisionFactorFileLog() {
		super();
	}

	public DivisionFactorFileLog(String tracking_id, String old_division_factor_value, String new_division_factor_value,
			String updated_by, Date updated_on, String source, String description, String user_comment,
			Date mdas_datetime, String created_by, Date created_on, String div_factor_type) {
		super();
		this.tracking_id = tracking_id;
		this.old_division_factor_value = old_division_factor_value;
		this.new_division_factor_value = new_division_factor_value;
		this.updated_by = updated_by;
		this.updated_on = updated_on;
		this.source = source;
		this.description = description;
		this.user_comment = user_comment;
		this.mdas_datetime = mdas_datetime;
		this.created_by = created_by;
		this.created_on = created_on;
		this.div_factor_type = div_factor_type;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public String getOld_division_factor_value() {
		return old_division_factor_value;
	}

	public void setOld_division_factor_value(String old_division_factor_value) {
		this.old_division_factor_value = old_division_factor_value;
	}

	public String getNew_division_factor_value() {
		return new_division_factor_value;
	}

	public void setNew_division_factor_value(String new_division_factor_value) {
		this.new_division_factor_value = new_division_factor_value;
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}
	
	public String getDiv_factor_type() {
		return div_factor_type;
	}

	public void setDiv_factor_type(String div_factor_type) {
		this.div_factor_type = div_factor_type;
	}

	@Override
	public String toString() {
		return "DivisionFactorFileLog [tracking_id=" + tracking_id + ", old_division_factor_value="
				+ old_division_factor_value + ", new_division_factor_value=" + new_division_factor_value
				+ ", updated_by=" + updated_by + ", updated_on=" + updated_on + ", source=" + source + ", description="
				+ description + ", user_comment=" + user_comment + ", mdas_datetime=" + mdas_datetime + ", created_by="
				+ created_by + ", created_on=" + created_on + ", div_factor_type=" + div_factor_type + "]";
	}

}
