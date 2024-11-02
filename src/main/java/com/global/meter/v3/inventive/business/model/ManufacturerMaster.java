package com.global.meter.v3.inventive.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "manufacturer_masters")
public class ManufacturerMaster implements Serializable {

	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String code;
	private boolean is_active;
	private String name;
	private String created_by;
	private String updated_by;
	private Date created_on;
	private Date updated_on;

	public ManufacturerMaster() {
		super();
	}

	public ManufacturerMaster(String code, boolean is_active, String name, String created_by, String updated_by,
			Date created_on, Date updated_on) {
		super();
		this.code = code;
		this.is_active = is_active;
		this.name = name;
		this.created_by = created_by;
		this.updated_by = updated_by;
		this.created_on = created_on;
		this.updated_on = updated_on;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	@Override
	public String toString() {
		return "ManufacturerMaster [code=" + code + ", is_active=" + is_active + ", name=" + name + ", created_by="
				+ created_by + ", updated_by=" + updated_by + ", created_on=" + created_on + ", updated_on="
				+ updated_on + "]";
	}

}
