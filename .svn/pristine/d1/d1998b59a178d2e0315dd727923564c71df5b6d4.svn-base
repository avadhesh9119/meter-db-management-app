package com.global.meter.business.model;

import java.util.Date;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "subdevisions")
public class Subdivisions {
		
	@PrimaryKey
	private String subdevision_name;
	private Set<String> access;
	private Date created;
	private String latitude;
	private String longitude;
	private Date modified;
	private String owner_name;
	private String source;
	private String user_id;
	private String updated_by;
	
	
	public Subdivisions() {

	}

	public String getSubdivision_name() {
		return subdevision_name;
	}

	public void setSubdivision_name(String subdevision_name) {
		this.subdevision_name = subdevision_name;
	}

	public Set<String> getAccess() {
		return access;
	}

	public void setAccess(Set<String> access) {
		this.access = access;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
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

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Subdivisions(String subdevision_name, Set<String> access, Date created, String latitude, String longitude,
			Date modified, String owner_name, String source, String user_id, String updated_by) {
		super();
		this.subdevision_name = subdevision_name;
		this.access = access;
		this.created = created;
		this.latitude = latitude;
		this.longitude = longitude;
		this.modified = modified;
		this.owner_name = owner_name;
		this.source = source;
		this.user_id = user_id;
		this.updated_by = updated_by;
	}

	@Override
	public String toString() {
		return "Subdivisions [subdevision_name=" + subdevision_name + ", access=" + access + ", created=" + created
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", modified=" + modified + ", owner_name="
				+ owner_name + ", source=" + source + ", user_id=" + user_id + ", updated_by=" + updated_by + "]";
	}
		
		
	}
	

