package com.global.meter.business.model;

import java.nio.ByteBuffer;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Table(value = "firmware_data")
@JsonInclude(Include.NON_EMPTY)
public class FirmwareData {
	
	
	@PrimaryKey
	@JsonProperty(value ="fileName",required = true)
	private String file_name;
	
    private String owner ;
    
    @JsonProperty(value ="fileUploadDatetime",required = false)
    private Date file_upload_datetime;
    
    @JsonProperty(value ="imageIdentifier",required = true)
    private String image_identifier ;
    private String status;
    private String version ;
    
    @JsonProperty(required = true)
    private String manufacturer;
    
    @JsonProperty(value ="source",required = true)
    private String source;
    
    @JsonProperty(value ="userId",required = true)
    private String user_id;
    
    @JsonProperty(value ="created",required = true)
    private Date created;
    
    @JsonProperty(value ="modified",required = true)
    private Date modified;
    
    @JsonProperty(value ="updatedBy",required = true)
    private String updated_by;
    
    @JsonProperty(value ="fileData")
    private ByteBuffer file_data ;
    
    @JsonProperty(value ="deviceType", required = true)
    private String device_type;
    
	public FirmwareData() {
		super();
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public ByteBuffer getFile_data() {
		return file_data;
	}
	public void setFile_data(ByteBuffer file_data) {
		this.file_data = file_data;
	}
	public Date getFile_upload_datetime() {
		return file_upload_datetime;
	}
	public void setFile_upload_datetime(Date file_upload_datetime) {
		this.file_upload_datetime = file_upload_datetime;
	}
	public String getImage_identifier() {
		return image_identifier;
	}
	public void setImage_identifier(String image_identifier) {
		this.image_identifier = image_identifier;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	@Override
	public String toString() {
		return "FirmwareData [file_name=" + file_name + ", owner=" + owner + ", file_upload_datetime="
				+ file_upload_datetime + ", image_identifier=" + image_identifier + ", status=" + status + ", version="
				+ version + ", manufacturer=" + manufacturer + ", source=" + source + ", user_id=" + user_id
				+ ", created=" + created + ", modified=" + modified + ", updated_by=" + updated_by + ", file_data="
				+ file_data + ", device_type=" + device_type + "]";
	}
	
}
