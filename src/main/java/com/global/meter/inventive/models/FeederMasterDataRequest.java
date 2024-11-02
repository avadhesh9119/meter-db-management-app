package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FeederMasterDataRequest {

	private String iAmId;
	private String hesFeederId;
	private String access;
	private String created;
	private String feederName;
	private String hesSubdivisionId;
	private String hesSubstationId;
	private String latitude;
	private String longitude;
	private String modified;
	private String ownerId;
	private String ownerName;
	private String source;
	private String subdivisionName;
	private String substationName;
	private String updatedBy;
	private String userId;
	private String substationCode;

	public String getiAmId() {
		return iAmId;
	}

	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}

	public String getHesFeederId() {
		return hesFeederId;
	}

	public void setHesFeederId(String hesFeederId) {
		this.hesFeederId = hesFeederId;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
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

	public String getHesSubdivisionId() {
		return hesSubdivisionId;
	}

	public void setHesSubdivisionId(String hesSubdivisionId) {
		this.hesSubdivisionId = hesSubdivisionId;
	}

	public String getHesSubstationId() {
		return hesSubstationId;
	}

	public void setHesSubstationId(String hesSubstationId) {
		this.hesSubstationId = hesSubstationId;
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getSubstationCode() {
		return substationCode;
	}

	public void setSubstationCode(String substationCode) {
		this.substationCode = substationCode;
	}

	@Override
	public String toString() {
		return "FeederMasterDataRequest [iAmId=" + iAmId + ", hesFeederId=" + hesFeederId + ", access=" + access
				+ ", created=" + created + ", feederName=" + feederName + ", hesSubdivisionId=" + hesSubdivisionId
				+ ", hesSubstationId=" + hesSubstationId + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", modified=" + modified + ", ownerId=" + ownerId + ", ownerName=" + ownerName + ", source=" + source
				+ ", subdivisionName=" + subdivisionName + ", substationName=" + substationName + ", updatedBy="
				+ updatedBy + ", userId=" + userId + ", substationCode=" + substationCode + "]";
	}

}
