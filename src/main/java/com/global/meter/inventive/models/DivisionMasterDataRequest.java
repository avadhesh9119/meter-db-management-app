package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DivisionMasterDataRequest {

	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String source;
	private String zoneName;
	private String updatedBy;
	private String userId;
	private String isActive;
	private String level3Id;
	private String level4Id;
	private String divisionName;
	private String divisionCode;
	private String circleCode;
	
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
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
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getLevel3Id() {
		return level3Id;
	}
	public void setLevel3Id(String level3Id) {
		this.level3Id = level3Id;
	}
	public String getLevel4Id() {
		return level4Id;
	}
	public void setLevel4Id(String level4Id) {
		this.level4Id = level4Id;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	
	@Override
	public String toString() {
		return "DivisionMasterDataRequest [created=" + created + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", modified=" + modified + ", source=" + source + ", zoneName=" + zoneName + ", updatedBy="
				+ updatedBy + ", userId=" + userId + ", isActive=" + isActive + ", level3Id=" + level3Id + ", level4Id="
				+ level4Id + ", divisionName=" + divisionName + ", divisionCode=" + divisionCode + ", circleCode="
				+ circleCode + "]";
	}
	
}
