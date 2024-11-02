package com.global.meter.business.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "user_hierarchy_mapping")
public class UserHierMapping {

	@PrimaryKey
	private int user_id;
	private int mapping_id;
	private int company_id;
	private int created_by;
	private Date created_on;
	private String ip_address;
	private Boolean is_active;
	private Boolean is_default;
	private int last_updated_by;
	private Date last_updated_on;
	private int level1_id;
	private int level2_id;
	private int level3_id;
	private int level4_id;
	private int level5_id;
	private int level6_id;

	public UserHierMapping(int mapping_id, int company_id, int created_by, Date created_on, String ip_address,
			Boolean is_active, Boolean is_default, int last_updated_by, Date last_updated_on, int level1_id,
			int level2_id, int level3_id, int level4_id, int level5_id, int level6_id, int user_id) {
		super();
		this.mapping_id = mapping_id;
		this.company_id = company_id;
		this.created_by = created_by;
		this.created_on = created_on;
		this.ip_address = ip_address;
		this.is_active = is_active;
		this.is_default = is_default;
		this.last_updated_by = last_updated_by;
		this.last_updated_on = last_updated_on;
		this.level1_id = level1_id;
		this.level2_id = level2_id;
		this.level3_id = level3_id;
		this.level4_id = level4_id;
		this.level5_id = level5_id;
		this.level6_id = level6_id;
		this.user_id = user_id;
	}

	public UserHierMapping() {
		super();
	}

	public int getMapping_id() {
		return mapping_id;
	}

	public void setMapping_id(int mapping_id) {
		this.mapping_id = mapping_id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public Boolean getIs_default() {
		return is_default;
	}

	public void setIs_default(Boolean is_default) {
		this.is_default = is_default;
	}

	public int getLast_updated_by() {
		return last_updated_by;
	}

	public void setLast_updated_by(int last_updated_by) {
		this.last_updated_by = last_updated_by;
	}

	public Date getLast_updated_on() {
		return last_updated_on;
	}

	public void setLast_updated_on(Date last_updated_on) {
		this.last_updated_on = last_updated_on;
	}

	public int getLevel1_id() {
		return level1_id;
	}

	public void setLevel1_id(int level1_id) {
		this.level1_id = level1_id;
	}

	public int getLevel2_id() {
		return level2_id;
	}

	public void setLevel2_id(int level2_id) {
		this.level2_id = level2_id;
	}

	public int getLevel3_id() {
		return level3_id;
	}

	public void setLevel3_id(int level3_id) {
		this.level3_id = level3_id;
	}

	public int getLevel4_id() {
		return level4_id;
	}

	public void setLevel4_id(int level4_id) {
		this.level4_id = level4_id;
	}

	public int getLevel5_id() {
		return level5_id;
	}

	public void setLevel5_id(int level5_id) {
		this.level5_id = level5_id;
	}

	public int getLevel6_id() {
		return level6_id;
	}

	public void setLevel6_id(int level6_id) {
		this.level6_id = level6_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "UserHierMapping [mapping_id=" + mapping_id + ", company_id=" + company_id + ", created_by=" + created_by
				+ ", created_on=" + created_on + ", ip_address=" + ip_address + ", is_active=" + is_active
				+ ", is_default=" + is_default + ", last_updated_by=" + last_updated_by + ", last_updated_on="
				+ last_updated_on + ", level1_id=" + level1_id + ", level2_id=" + level2_id + ", level3_id=" + level3_id
				+ ", level4_id=" + level4_id + ", level5_id=" + level5_id + ", level6_id=" + level6_id + ", user_id="
				+ user_id + "]";
	}

}
