package com.global.meter.inventive.models;

public class DtTransMasterDataRequest {

	private String iAmId;
	private String hesDtTransId;
	private String hesFeederId;
	private String hesSubstationId;
	private String hesSubdivisionId;
	private String ownerId;
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
	private String ownerName;
	private String feederCode;
	
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
	public String getiAmId() {
		return iAmId;
	}
	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}
	public String getHesDtTransId() {
		return hesDtTransId;
	}
	public void setHesDtTransId(String hesDtTransId) {
		this.hesDtTransId = hesDtTransId;
	}
	public String getHesSubstationId() {
		return hesSubstationId;
	}
	public void setHesSubstationId(String hesSubstationId) {
		this.hesSubstationId = hesSubstationId;
	}
	public String getHesSubdivisionId() {
		return hesSubdivisionId;
	}
	public void setHesSubdivisionId(String hesSubdivisionId) {
		this.hesSubdivisionId = hesSubdivisionId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getHesFeederId() {
		return hesFeederId;
	}
	public void setHesFeederId(String hesFeederId) {
		this.hesFeederId = hesFeederId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getFeederCode() {
		return feederCode;
	}
	public void setFeederCode(String feederCode) {
		this.feederCode = feederCode;
	}
	
	@Override
	public String toString() {
		return "DtTransMasterDataRequest [iAmId=" + iAmId + ", hesDtTransId=" + hesDtTransId + ", hesFeederId="
				+ hesFeederId + ", hesSubstationId=" + hesSubstationId + ", hesSubdivisionId=" + hesSubdivisionId
				+ ", ownerId=" + ownerId + ", dtName=" + dtName + ", created=" + created + ", feederName=" + feederName
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", modified=" + modified + ", utility="
				+ utility + ", subdivisionName=" + subdivisionName + ", substationName=" + substationName + ", source="
				+ source + ", userId=" + userId + ", updatedBy=" + updatedBy + ", ownerName=" + ownerName
				+ ", feederCode=" + feederCode + "]";
	}

}
