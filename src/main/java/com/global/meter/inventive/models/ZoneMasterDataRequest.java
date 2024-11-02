package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ZoneMasterDataRequest implements Serializable {

	private static final long serialVersionUID = 8984081645412010645L;

	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String source;
	private String userId;
	private String updatedBy;
	private String isActive;
	private String zoneName;
	private String zoneCode;
	private String level1Id;
	private String level2Id;
	private String ownerCode;
	
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
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}
	public String getLevel1Id() {
		return level1Id;
	}
	public void setLevel1Id(String level1Id) {
		this.level1Id = level1Id;
	}
	public String getLevel2Id() {
		return level2Id;
	}
	public void setLevel2Id(String level2Id) {
		this.level2Id = level2Id;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	
	@Override
	public String toString() {
		return "ZoneMasterDataRequest [created=" + created + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", modified=" + modified + ", source=" + source + ", userId=" + userId + ", updatedBy=" + updatedBy
				+ ", isActive=" + isActive + ", zoneName=" + zoneName + ", zoneCode=" + zoneCode + ", level1Id="
				+ level1Id + ", level2Id=" + level2Id + ", ownerCode=" + ownerCode + "]";
	}
}
