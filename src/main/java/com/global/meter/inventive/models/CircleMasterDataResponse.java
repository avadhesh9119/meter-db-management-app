package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CircleMasterDataResponse implements Serializable {

	private static final long serialVersionUID = 2770286484075380547L;

	private String iAmId;
	private String hesCircleId;
	private String circleName;
	private String circleCode;
	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String source;
	private String createdBy;
	private String lastUpdatedBy;
	
	public String getiAmId() {
		return iAmId;
	}
	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}
	public String getHesCircleId() {
		return hesCircleId;
	}
	public void setHesCircleId(String hesCircleId) {
		this.hesCircleId = hesCircleId;
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
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	@Override
	public String toString() {
		return "CircleMasterDataResponse [iAmId=" + iAmId + ", hesCircleId=" + hesCircleId + ", circleName="
				+ circleName + ", circleCode=" + circleCode + ", created=" + created + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", modified=" + modified + ", source=" + source + ", createdBy="
				+ createdBy + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}
	
}