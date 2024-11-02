package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SubstationsMasterDataRequest {

	private String iAmId;
	private String hesSubstationId;
	private String hesSubdivisionId;
	private String ownerId;
	private String substationName;
	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String utility;
	private String subdivisionName;
	private String source;
	private String userId;
	private String updatedBy;
	private String address;
	private String ownerName;
	private String subdivisionCode;

	public String getiAmId() {
		return iAmId;
	}

	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
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

	public String getSubstationName() {
		return substationName;
	}

	public void setSubstationName(String substationName) {
		this.substationName = substationName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSubdivisionCode() {
		return subdivisionCode;
	}

	public void setSubdivisionCode(String subdivisionCode) {
		this.subdivisionCode = subdivisionCode;
	}

	@Override
	public String toString() {
		return "SubstationsMasterDataRequest [iAmId=" + iAmId + ", hesSubstationId=" + hesSubstationId
				+ ", hesSubdivisionId=" + hesSubdivisionId + ", ownerId=" + ownerId + ", substationName="
				+ substationName + ", created=" + created + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", modified=" + modified + ", utility=" + utility + ", subdivisionName=" + subdivisionName
				+ ", source=" + source + ", userId=" + userId + ", updatedBy=" + updatedBy + ", address=" + address
				+ ", ownerName=" + ownerName + ", subdivisionCode=" + subdivisionCode + "]";
	}

}