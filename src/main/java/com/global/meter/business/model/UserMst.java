package com.global.meter.business.model;

import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "user_mst")
public class UserMst {

	@PrimaryKey
	private int auto_id;
	private int user_id;
	private String user_name;
	private String user_code;
	private String login_id;
	private String father_name;
	private String user_type_name;
	private String mobile_no;
	private String email_id;
	private String department_code;
	private String department_name;
	private String designation_code;
	private String designation_name;
	private String photo;
	private Boolean change_password_flag;
	private String identity_type_name;
	private String identity_no;
	private int user_remark_id;
	private Boolean is_active;
	private int created_by;
	private Date created_on;
	private int last_updated_by;
	private Date last_updated_on;
	private String ip_address;
	private Date start_date;
	private Date end_date;
	private String attribute1;
	private String attribute2;
	private String attribute3;
	private String attribute4;
	private String attribute5;
	private int reporting_user_id;

	public int getUser_id() {
		return user_id;
	}

	public int getAuto_id() {
		return auto_id;
	}

	public void setAuto_id(int auto_id) {
		this.auto_id = auto_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	public String getUser_type_name() {
		return user_type_name;
	}

	public void setUser_type_name(String user_type_name) {
		this.user_type_name = user_type_name;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getDepartment_code() {
		return department_code;
	}

	public void setDepartment_code(String department_code) {
		this.department_code = department_code;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDesignation_code() {
		return designation_code;
	}

	public void setDesignation_code(String designation_code) {
		this.designation_code = designation_code;
	}

	public String getDesignation_name() {
		return designation_name;
	}

	public void setDesignation_name(String designation_name) {
		this.designation_name = designation_name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Boolean getChange_password_flag() {
		return change_password_flag;
	}

	public void setChange_password_flag(Boolean change_password_flag) {
		this.change_password_flag = change_password_flag;
	}

	public String getIdentity_type_name() {
		return identity_type_name;
	}

	public void setIdentity_type_name(String identity_type_name) {
		this.identity_type_name = identity_type_name;
	}

	public String getIdentity_no() {
		return identity_no;
	}

	public void setIdentity_no(String identity_no) {
		this.identity_no = identity_no;
	}

	public int getUser_remark_id() {
		return user_remark_id;
	}

	public void setUser_remark_id(int user_remark_id) {
		this.user_remark_id = user_remark_id;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
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

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public int getReporting_user_id() {
		return reporting_user_id;
	}

	public void setReporting_user_id(int reporting_user_id) {
		this.reporting_user_id = reporting_user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public UserMst(int auto_id, int user_id, String user_name, String user_code, String login_id, String father_name,
			String user_type_name, String mobile_no, String email_id, String department_code, String department_name,
			String designation_code, String designation_name, String photo, Boolean change_password_flag,
			String identity_type_name, String identity_no, int user_remark_id, Boolean is_active, int created_by,
			Date created_on, int last_updated_by, Date last_updated_on, String ip_address, Date start_date,
			Date end_date, String attribute1, String attribute2, String attribute3, String attribute4,
			String attribute5, int reporting_user_id) {
		super();
		this.auto_id = auto_id;
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_code = user_code;
		this.login_id = login_id;
		this.father_name = father_name;
		this.user_type_name = user_type_name;
		this.mobile_no = mobile_no;
		this.email_id = email_id;
		this.department_code = department_code;
		this.department_name = department_name;
		this.designation_code = designation_code;
		this.designation_name = designation_name;
		this.photo = photo;
		this.change_password_flag = change_password_flag;
		this.identity_type_name = identity_type_name;
		this.identity_no = identity_no;
		this.user_remark_id = user_remark_id;
		this.is_active = is_active;
		this.created_by = created_by;
		this.created_on = created_on;
		this.last_updated_by = last_updated_by;
		this.last_updated_on = last_updated_on;
		this.ip_address = ip_address;
		this.start_date = start_date;
		this.end_date = end_date;
		this.attribute1 = attribute1;
		this.attribute2 = attribute2;
		this.attribute3 = attribute3;
		this.attribute4 = attribute4;
		this.attribute5 = attribute5;
		this.reporting_user_id = reporting_user_id;
	}

	public UserMst() {
		super();
	}

	@Override
	public String toString() {
		return "Users [auto_id=" + auto_id + ", user_id=" + user_id + ", user_name=" + user_name + ", user_code="
				+ user_code + ", login_id=" + login_id + ", father_name=" + father_name + ", user_type_name="
				+ user_type_name + ", mobile_no=" + mobile_no + ", email_id=" + email_id + ", department_code="
				+ department_code + ", department_name=" + department_name + ", designation_code=" + designation_code
				+ ", designation_name=" + designation_name + ", photo=" + photo + ", change_password_flag="
				+ change_password_flag + ", identity_type_name=" + identity_type_name + ", identity_no=" + identity_no
				+ ", user_remark_id=" + user_remark_id + ", is_active=" + is_active + ", created_by=" + created_by
				+ ", created_on=" + created_on + ", last_updated_by=" + last_updated_by + ", last_updated_on="
				+ last_updated_on + ", ip_address=" + ip_address + ", start_date=" + start_date + ", end_date="
				+ end_date + ", attribute1=" + attribute1 + ", attribute2=" + attribute2 + ", attribute3=" + attribute3
				+ ", attribute4=" + attribute4 + ", attribute5=" + attribute5 + ", reporting_user_id="
				+ reporting_user_id + "]";
	}

}
