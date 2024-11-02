package com.global.meter.business.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "utility_master")
public class Owners {

	@PrimaryKey
	private int i_am_id;
	
	private String hes_owner_id;
	private String owner_name;
	private String email;
	private String is_active;
	private String company_id;
	private String created_by;
	private Date created_on;
	private String updated_by;
	private Date modified;
	private String level1_id;
	private String owner_code;

	public Owners() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Owners(int i_am_id, String hes_owner_id, String owner_name, String email, String is_active,
			String company_id, String created_by, Date created_on, String updated_by, Date modified, String level1_id,
			String owner_code) {
		super();
		this.i_am_id = i_am_id;
		this.hes_owner_id = hes_owner_id;
		this.owner_name = owner_name;
		this.email = email;
		this.is_active = is_active;
		this.company_id = company_id;
		this.created_by = created_by;
		this.created_on = created_on;
		this.updated_by = updated_by;
		this.modified = modified;
		this.level1_id = level1_id;
		this.owner_code = owner_code;
	}

	public int getI_am_id() {
		return i_am_id;
	}

	public void setI_am_id(int i_am_id) {
		this.i_am_id = i_am_id;
	}

	public String getHes_owner_id() {
		return hes_owner_id;
	}

	public void setHes_owner_id(String hes_owner_id) {
		this.hes_owner_id = hes_owner_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
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

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getLevel1_id() {
		return level1_id;
	}

	public void setLevel1_id(String level1_id) {
		this.level1_id = level1_id;
	}

	public String getOwner_code() {
		return owner_code;
	}

	public void setOwner_code(String owner_code) {
		this.owner_code = owner_code;
	}

	@Override
	public String toString() {
		return "Owners [i_am_id=" + i_am_id + ", hes_owner_id=" + hes_owner_id + ", owner_name=" + owner_name
				+ ", email=" + email + ", is_active=" + is_active + ", company_id=" + company_id + ", created_by="
				+ created_by + ", created_on=" + created_on + ", updated_by=" + updated_by + ", modified=" + modified
				+ ", level1_id=" + level1_id + ", owner_code=" + owner_code + "]";
	}

}
