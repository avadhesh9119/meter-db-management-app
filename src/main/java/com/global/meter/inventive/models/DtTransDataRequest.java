package com.global.meter.inventive.models;

public class DtTransDataRequest {

	private String dtName;
	private String created;
	private String feederName;
	private String latitude;
	private String longitude;
	private String modified;
	private String utility;
	private String subdivisionName;
	private String substationName;
	private String source;
	private String userId;
	private String updatedBy;
	
	
	public String getDtName() {
		return dtName;
	}
	public void setDtName(String dtName) {
		this.dtName = dtName;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getFeederName() {
		return feederName;
	}
	public void setFeederName(String feederName) {
		this.feederName = feederName;
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
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
	}
	public String getSubdivisionName() {
		return subdivisionName;
	}
	public void setSubdivisionName(String subdivisionName) {
		this.subdivisionName = subdivisionName;
	}
	public String getSubstationName() {
		return substationName;
	}
	public void setSubstationName(String substationName) {
		this.substationName = substationName;
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
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		return "DtTransDataRequest [dtName=" + dtName + ", created=" + created + ", feederName=" + feederName
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", modified=" + modified + ", utility="
				+ utility + ", subdivisionName=" + subdivisionName + ", substationName=" + substationName + ", source="
				+ source + ", userId=" + userId + ", updatedBy=" + updatedBy + "]";
	}
	
	
	

	

}
