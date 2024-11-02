package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CircleMasterDataRequest {

	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String utility;
	private String source;
	private String userId;
	private String updatedBy;
	private String isActive;
	private String level2Id;
	private String level3Id;
	private String circleName;
	private String circleCode;
	private String zoneCode;
	
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
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getLevel2Id() {
		return level2Id;
	}
	public void setLevel2Id(String level2Id) {
		this.level2Id = level2Id;
	}
	public String getLevel3Id() {
		return level3Id;
	}
	public void setLevel3Id(String level3Id) {
		this.level3Id = level3Id;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	
	@Override
	public String toString() {
		return "CircleMasterDataRequest [created=" + created + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", modified=" + modified + ", utility=" + utility + ", source=" + source + ", userId=" + userId
				+ ", updatedBy=" + updatedBy + ", isActive=" + isActive + ", level2Id=" + level2Id + ", level3Id="
				+ level3Id + ", circleName=" + circleName + ", circleCode=" + circleCode + ", zoneCode=" + zoneCode
				+ "]";
	}

}
