package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SubdivisionMasterDataResponse implements Serializable {

	private static final long serialVersionUID = 6702781362986536106L;

	private String iAmId;
	private String hesSubdivisionId;
	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String source;
	private String userId;
	private String updatedBy;
	private String subdivisionName;
	private String subdivisionCode;
	
	public String getiAmId() {
		return iAmId;
	}
	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}
	public String getHesSubdivisionId() {
		return hesSubdivisionId;
	}
	public void setHesSubdivisionId(String hesSubdivisionId) {
		this.hesSubdivisionId = hesSubdivisionId;
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
	public String getSubdivisionName() {
		return subdivisionName;
	}
	public void setSubdivisionName(String subdivisionName) {
		this.subdivisionName = subdivisionName;
	}
	public String getSubdivisionCode() {
		return subdivisionCode;
	}
	public void setSubdivisionCode(String subdivisionCode) {
		this.subdivisionCode = subdivisionCode;
	}
	
	@Override
	public String toString() {
		return "SubdivisionMasterDataResponse [iAmId=" + iAmId + ", hesSubdivisionId=" + hesSubdivisionId + ", created="
				+ created + ", latitude=" + latitude + ", longitude=" + longitude + ", modified=" + modified
				+ ", source=" + source + ", userId=" + userId + ", updatedBy=" + updatedBy + ", subdivisionName="
				+ subdivisionName + ", subdivisionCode=" + subdivisionCode + "]";
	}

}
