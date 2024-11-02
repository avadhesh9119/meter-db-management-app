package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(value = "firmware_deleted_logs")
public class FirmwareDeleteLogs implements Serializable{
	
	private static final long serialVersionUID = 6721662591769989031L;
	
	@PrimaryKey
	@JsonProperty(value ="fileName",required = true)
	private String file_name;
	
	@JsonProperty(value ="created",required = true)
	private Date created;
	
	@JsonProperty(value ="deviceType",required = false)
	private String device_type;
	private Date modified;
	private String remarks;
	private String source;
	private String status;
	
	@JsonProperty(value ="udpatedBy",required = false)
	private String updated_by;
	
	@JsonProperty(value ="userId",required = false)
	private String user_id;
	private String version;
	private String deleted_by;
	private Date deleted_on;
	private String manufacturer;
	
	@JsonProperty(value ="owner",required = false)
	private String owner;
	
	public FirmwareDeleteLogs() {		
		
	}
	public FirmwareDeleteLogs(String file_name, Date created, String device_type, Date modified, String owner,
			String remarks, String source, String status, String updated_by, String user_id, String version,
			String deleted_by, Date deleted_on, String manufacturer) {
		super();
		this.file_name = file_name;
		this.created = created;
		this.device_type = device_type;
		this.modified = modified;
		this.owner = owner;
		this.remarks = remarks;
		this.source = source;
		this.status = status;
		this.updated_by = updated_by;
		this.user_id = user_id;
		this.version = version;
		this.deleted_by = deleted_by;
		this.deleted_on = deleted_on;
		this.manufacturer = manufacturer;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getOwner_name() {
		return owner;
	}
	public void setOwner_name(String owner_name) {
		this.owner = owner_name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String reason) {
		this.remarks = reason;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDeleted_by() {
		return deleted_by;
	}
	public void setDeleted_by(String deleted_by) {
		this.deleted_by = deleted_by;
	}
	public Date getDeleted_on() {
		return deleted_on;
	}
	public void setDeleted_on(Date deleted_on) {
		this.deleted_on = deleted_on;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	@Override
	public String toString() {
		return "FirmwareDeleteLogs [file_name=" + file_name + ", created=" + created + ", device_type=" + device_type
				+ ", modified=" + modified + ", owner=" + owner + ", remarks=" + remarks + ", source=" + source
				+ ", status=" + status + ", updated_by=" + updated_by + ", user_id=" + user_id + ", version=" + version
				+ ", deleted_by=" + deleted_by + ", deleted_on=" + deleted_on + ", manufacturer=" + manufacturer + "]";
	}
}
