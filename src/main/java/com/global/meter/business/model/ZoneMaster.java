package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "zone_master")
public class ZoneMaster implements Serializable {

	private static final long serialVersionUID = -8613432154145015548L;

	@PrimaryKey
	private int i_am_id;
	private String hes_zone_id;
	private Set<String> access;
	private Date created;
	private String latitude;
	private String longitude;
	private Date modified;
	private String owner_id;
	private String source;
	private String zone_name;
	private String updated_by;
	private String user_id;
	private String owner_name;
	private String is_active;
	private String level2_id;
	private String zone_code;

	public ZoneMaster() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ZoneMaster(int i_am_id, String hes_zone_id, Set<String> access, Date created, String latitude,
			String longitude, Date modified, String owner_id, String source, String zone_name, String updated_by,
			String user_id, String owner_name, String is_active, String level2_id, String zone_code) {
		super();
		this.i_am_id = i_am_id;
		this.hes_zone_id = hes_zone_id;
		this.access = access;
		this.created = created;
		this.latitude = latitude;
		this.longitude = longitude;
		this.modified = modified;
		this.owner_id = owner_id;
		this.source = source;
		this.zone_name = zone_name;
		this.updated_by = updated_by;
		this.user_id = user_id;
		this.owner_name = owner_name;
		this.is_active = is_active;
		this.level2_id = level2_id;
		this.zone_code = zone_code;
	}

	public int getI_am_id() {
		return i_am_id;
	}

	public void setI_am_id(int i_am_id) {
		this.i_am_id = i_am_id;
	}

	public String getHes_zone_id() {
		return hes_zone_id;
	}

	public void setHes_zone_id(String hes_zone_id) {
		this.hes_zone_id = hes_zone_id;
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

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getZone_name() {
		return zone_name;
	}

	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getLevel2_id() {
		return level2_id;
	}


	public void setLevel2_id(String level2_id) {
		this.level2_id = level2_id;
	}

	public String getZone_code() {
		return zone_code;
	}


	public void setZone_code(String zone_code) {
		this.zone_code = zone_code;
	}


	@Override
	public String toString() {
		return "ZoneMaster [i_am_id=" + i_am_id + ", hes_zone_id=" + hes_zone_id + ", access=" + access + ", created="
				+ created + ", latitude=" + latitude + ", longitude=" + longitude + ", modified=" + modified
				+ ", owner_id=" + owner_id + ", source=" + source + ", zone_name=" + zone_name + ", updated_by="
				+ updated_by + ", user_id=" + user_id + ", owner_name=" + owner_name + ", is_active=" + is_active
				+ ", level2_id=" + level2_id + ", zone_code=" + zone_code + "]";
	}

	}
