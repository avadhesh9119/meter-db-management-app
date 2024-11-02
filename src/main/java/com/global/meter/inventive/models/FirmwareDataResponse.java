package com.global.meter.inventive.models;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FirmwareDataResponse implements Serializable {

	private static final long serialVersionUID = -5628037472352025497L;

	private String fileName;
	private String owner;
	private Date file_upload_datetime;
	private String imageIdentifier;
	private String status;
	private String version;
	private String manufacturer;
	private String source;
	private String userId;
	private Date created;
	private Date modified;
	private String updatedBy;
	private ByteBuffer fileData;
	private String deviceType;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getFile_upload_datetime() {
		return file_upload_datetime;
	}
	public void setFile_upload_datetime(Date file_upload_datetime) {
		this.file_upload_datetime = file_upload_datetime;
	}
	public String getImageIdentifier() {
		return imageIdentifier;
	}
	public void setImageIdentifier(String imageIdentifier) {
		this.imageIdentifier = imageIdentifier;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public ByteBuffer getFileData() {
		return fileData;
	}
	public void setFileData(ByteBuffer fileData) {
		this.fileData = fileData;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
}
