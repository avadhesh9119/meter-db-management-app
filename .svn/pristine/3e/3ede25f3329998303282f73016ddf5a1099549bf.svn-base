package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("mdm_master_logs")
public class MdmMasterLogs implements Serializable {
	
	private static final long serialVersionUID = -5290816761455278114L;
	@PrimaryKey
	private String mdm_id;

	private String ftp_address;
	private String ftp_login;
	private String ftp_password;
	private boolean is_active;
	private String login_id;
	private String mdm_auth_url;
	private String mdm_name;
	private String login_password;
	private String created_by;
	private Date created_on;
	private String updated_by;
	private Date updated_on;
	
	public MdmMasterLogs() {
		
	}

	public MdmMasterLogs(String mdm_id, String ftp_address, String ftp_login, String ftp_password, boolean is_active,
			String login_id, String mdm_auth_url, String mdm_name, String login_password, String created_by,
			Date created_on, String updated_by, Date updated_on) {
		super();
		this.mdm_id = mdm_id;
		this.ftp_address = ftp_address;
		this.ftp_login = ftp_login;
		this.ftp_password = ftp_password;
		this.is_active = is_active;
		this.login_id = login_id;
		this.mdm_auth_url = mdm_auth_url;
		this.mdm_name = mdm_name;
		this.login_password = login_password;
		this.created_by = created_by;
		this.created_on = created_on;
		this.updated_by = updated_by;
		this.updated_on = updated_on;
	}

	public String getMdm_id() {
		return mdm_id;
	}

	public void setMdm_id(String mdm_id) {
		this.mdm_id = mdm_id;
	}

	public String getFtp_address() {
		return ftp_address;
	}

	public void setFtp_address(String ftp_address) {
		this.ftp_address = ftp_address;
	}

	public String getFtp_login() {
		return ftp_login;
	}

	public void setFtp_login(String ftp_login) {
		this.ftp_login = ftp_login;
	}

	public String getFtp_password() {
		return ftp_password;
	}

	public void setFtp_password(String ftp_password) {
		this.ftp_password = ftp_password;
	}

	public boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getMdm_auth_url() {
		return mdm_auth_url;
	}

	public void setMdm_auth_url(String mdm_auth_url) {
		this.mdm_auth_url = mdm_auth_url;
	}

	public String getMdm_name() {
		return mdm_name;
	}

	public void setMdm_name(String mdm_name) {
		this.mdm_name = mdm_name;
	}

	public String getLogin_password() {
		return login_password;
	}

	public void setLogin_password(String login_password) {
		this.login_password = login_password;
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

	public Date getUpdated_on() {
		return updated_on;
	}

	public void setUpdated_on(Date updated_on) {
		this.updated_on = updated_on;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "MdmLogMaster [mdm_id=" + mdm_id + ", ftp_address=" + ftp_address + ", ftp_login=" + ftp_login
				+ ", ftp_password=" + ftp_password + ", is_active=" + is_active + ", login_id=" + login_id
				+ ", mdm_auth_url=" + mdm_auth_url + ", mdm_name=" + mdm_name + ", login_password=" + login_password
				+ ", created_by=" + created_by + ", created_on=" + created_on + ", updated_by=" + updated_by
				+ ", updated_on=" + updated_on + "]";
	}
	
	

}
