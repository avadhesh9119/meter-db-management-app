package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DivisionDataResponse implements Serializable {

	private static final long serialVersionUID = -935949441732876838L;

	private String iAmId;
	private String hesDivisionId;
	private String divisionName;
	private String divisionCode;
	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String utility;
	private String source;
	private String createdBy;
	private String lastupdatedBy;

	
	public String getiAmId() {
		return iAmId;
	}


	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}


	public String getHesDivisionId() {
		return hesDivisionId;
	}


	public void setHesDivisionId(String hesDivisionId) {
		this.hesDivisionId = hesDivisionId;
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


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getLastupdatedBy() {
		return lastupdatedBy;
	}


	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}


	@Override
	public String toString() {
		return "DivisionDataResponse [iAmId=" + iAmId + ", hesDivisionId=" + hesDivisionId + ", divisionName="
				+ divisionName + ", divisionCode=" + divisionCode + ", created=" + created + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", modified=" + modified + ", utility=" + utility + ", source=" + source
				+ ", createdBy=" + createdBy + ", lastupdatedBy=" + lastupdatedBy + "]";
	}

}
