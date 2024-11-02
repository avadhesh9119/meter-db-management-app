package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "circle_master")
public class CircleMaster implements Serializable {

	private static final long serialVersionUID = -8613432154145015548L;
	
	@PrimaryKey
	private int i_am_id;
	
	private String hes_circle_id;
	private String hes_zone_id;
	private String hes_owner_id;
	private String circle_name;
	private Set<String> access;
	private Date created;
	private String latitude;
	private String longitude;
	private Date modified;
	private String owner_name;
	private String zone_name;
	private String source;
	private String user_id;
	private String updated_by;
	private String address;
	private String is_active;
	private String level3_id;
	private String circle_code;
	
	public CircleMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CircleMaster(int i_am_id, String hes_circle_id, String hes_zone_id, String hes_owner_id, String circle_name,
			Set<String> access, Date created, String latitude, String longitude, Date modified, String owner_name,
			String zone_name, String source, String user_id, String updated_by, String address, String is_active,
			String level3_id, String circle_code) {
		super();
		this.i_am_id = i_am_id;
		this.hes_circle_id = hes_circle_id;
		this.hes_zone_id = hes_zone_id;
		this.hes_owner_id = hes_owner_id;
		this.circle_name = circle_name;
		this.access = access;
		this.created = created;
		this.latitude = latitude;
		this.longitude = longitude;
		this.modified = modified;
		this.owner_name = owner_name;
		this.zone_name = zone_name;
		this.source = source;
		this.user_id = user_id;
		this.updated_by = updated_by;
		this.address = address;
		this.is_active = is_active;
		this.level3_id = level3_id;
		this.circle_code = circle_code;
	}

	public int getI_am_id() {
		return i_am_id;
	}

	public void setI_am_id(int i_am_id) {
		this.i_am_id = i_am_id;
	}

	public String getHes_circle_id() {
		return hes_circle_id;
	}

	public void setHes_circle_id(String hes_circle_id) {
		this.hes_circle_id = hes_circle_id;
	}

	public String getHes_zone_id() {
		return hes_zone_id;
	}

	public void setHes_zone_id(String hes_zone_id) {
		this.hes_zone_id = hes_zone_id;
	}

	public String getHes_owner_id() {
		return hes_owner_id;
	}

	public void setHes_owner_id(String hes_owner_id) {
		this.hes_owner_id = hes_owner_id;
	}

	public String getCircle_name() {
		return circle_name;
	}

	public void setCircle_name(String circle_name) {
		this.circle_name = circle_name;
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

	public String getZone_name() {
		return zone_name;
	}

	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getLevel3_id() {
		return level3_id;
	}

	public void setLevel3_id(String level3_id) {
		this.level3_id = level3_id;
	}

	public String getCircle_code() {
		return circle_code;
	}

	public void setCircle_code(String circle_code) {
		this.circle_code = circle_code;
	}

	@Override
	public String toString() {
		return "CircleMaster [i_am_id=" + i_am_id + ", hes_circle_id=" + hes_circle_id + ", hes_zone_id=" + hes_zone_id
				+ ", hes_owner_id=" + hes_owner_id + ", circle_name=" + circle_name + ", access=" + access
				+ ", created=" + created + ", latitude=" + latitude + ", longitude=" + longitude + ", modified="
				+ modified + ", owner_name=" + owner_name + ", zone_name=" + zone_name + ", source=" + source
				+ ", user_id=" + user_id + ", updated_by=" + updated_by + ", address=" + address + ", is_active="
				+ is_active + ", level3_id=" + level3_id + ", circle_code=" + circle_code + "]";
	}
}
