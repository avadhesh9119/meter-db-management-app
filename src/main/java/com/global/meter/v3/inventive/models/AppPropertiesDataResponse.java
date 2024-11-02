package com.global.meter.v3.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AppPropertiesDataResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7315852093936430845L;

	@JsonProperty("Tracking Id")
	private String trackingId;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("New Property Value")
	private String newPropertyValue;
	
	@JsonProperty("Old Property Value")
	private String oldPropertyValue;
	
	@JsonProperty("New Division Factor Value")
	private String newdivisionFactorValue;
	
	@JsonProperty("Old DivisionFactor Value")
	private String oldDivisionFactorValue;
	
	@JsonProperty("Source")
	private String source;
	
	@JsonProperty("Updated By")
	private String updatedBy;
	
	@JsonProperty("Updated On")
	private String updatedOn;
	
	@JsonProperty("User Comment")
	private String userComment;
	
	@JsonProperty("Created By")
	private String createdBy;
	
	@JsonProperty("Created On")
	private String createdOn;
	
	@JsonProperty("Division Factor Type")
	private String divisionFactorType;

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNewPropertyValue() {
		return newPropertyValue;
	}

	public void setNewPropertyValue(String newPropertyValue) {
		this.newPropertyValue = newPropertyValue;
	}

	public String getOldPropertyValue() {
		return oldPropertyValue;
	}

	public void setOldPropertyValue(String oldPropertyValue) {
		this.oldPropertyValue = oldPropertyValue;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getNewdivisionFactorValue() {
		return newdivisionFactorValue;
	}

	public void setNewdivisionFactorValue(String newdivisionFactorValue) {
		this.newdivisionFactorValue = newdivisionFactorValue;
	}

	public String getOldDivisionFactorValue() {
		return oldDivisionFactorValue;
	}

	public void setOldDivisionFactorValue(String oldDivisionFactorValue) {
		this.oldDivisionFactorValue = oldDivisionFactorValue;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getDivisionFactorType() {
		return divisionFactorType;
	}

	public void setDivisionFactorType(String divisionFactorType) {
		this.divisionFactorType = divisionFactorType;
	}

	@Override
	public String toString() {
		return "AppPropertiesDataResponse [trackingId=" + trackingId + ", description=" + description
				+ ", newPropertyValue=" + newPropertyValue + ", oldPropertyValue=" + oldPropertyValue
				+ ", newdivisionFactorValue=" + newdivisionFactorValue + ", oldDivisionFactorValue="
				+ oldDivisionFactorValue + ", source=" + source + ", updatedBy=" + updatedBy + ", updatedOn="
				+ updatedOn + ", userComment=" + userComment + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", divisionFactorType=" + divisionFactorType + "]";
	}

}
