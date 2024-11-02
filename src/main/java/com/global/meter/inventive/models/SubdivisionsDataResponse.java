package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SubdivisionsDataResponse implements Serializable {

	private static final long serialVersionUID = 6702781362986536106L;

	private String subdivisionName;

	private String created;

	private String latitude;

	private String longitude;

	private String modified;

	private String utility;
	
	private String source;
	
	private String createdBy;
	
	private String lastUpdatedBy;

	public String getSubdivisionName() {
		return subdivisionName;
	}

	public void setSubdivisionName(String subdivisionName) {
		this.subdivisionName = subdivisionName;
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

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public String toString() {
		return "SubdivisionsDataResponse [subdivisionName=" + subdivisionName + ", created=" + created + ", latitude="
				+ latitude + ", longitude=" + longitude + ", modified=" + modified + ", utility=" + utility
				+ ", source=" + source + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}

}
