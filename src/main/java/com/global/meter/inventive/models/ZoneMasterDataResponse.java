package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ZoneMasterDataResponse implements Serializable {

	private static final long serialVersionUID = 6702781362986536106L;

	private String iAmId;
	private String hesZoneId;
	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String ownerId;
	private String source;
	private String userId;
	private String updatedBy;
	private String ZoneName;
	private String ZoneCode;

	public String getiAmId() {
		return iAmId;
	}

	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}

	public String getHesZoneId() {
		return hesZoneId;
	}

	public void setHesZoneId(String hesZoneId) {
		this.hesZoneId = hesZoneId;
	}

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

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	public String getZoneName() {
		return ZoneName;
	}

	public void setZoneName(String zoneName) {
		ZoneName = zoneName;
	}

	public String getZoneCode() {
		return ZoneCode;
	}

	public void setZoneCode(String zoneCode) {
		ZoneCode = zoneCode;
	}

	@Override
	public String toString() {
		return "ZoneMasterDataResponse [iAmId=" + iAmId + ", hesZoneId=" + hesZoneId + ", created=" + created
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", modified=" + modified + ", ownerId="
				+ ownerId + ", source=" + source + ", userId=" + userId + ", updatedBy=" + updatedBy + ", ZoneName="
				+ ZoneName + ", ZoneCode=" + ZoneCode + "]";
	}

}
