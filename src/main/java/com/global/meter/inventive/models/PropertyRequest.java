package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PropertyRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7315852093936430845L;

	private String name;
	private String value;
	private int index;
	private String oldPropertyValue;
	private String newPropertyValue;
	private String updatedBy;
	private String updatedOn;
	private String source;
	private String description;
	private String userComment;
	private String trackingId;
	private String fromDate;
	private String toDate;
	private String newDivisionFactorValue;
	private String oldDivisionFactorValue;
	private String createdBy;
	private String manufacturer;
	private String devType;
	private String profileType;
	private String modeType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getOldPropertyValue() {
		return oldPropertyValue;
	}

	public void setOldPropertyValue(String oldPropertyValue) {
		this.oldPropertyValue = oldPropertyValue;
	}

	public String getNewPropertyValue() {
		return newPropertyValue;
	}

	public void setNewPropertyValue(String newPropertyValue) {
		this.newPropertyValue = newPropertyValue;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getNewDivisionFactorValue() {
		return newDivisionFactorValue;
	}

	public void setNewDivisionFactorValue(String newDivisionFactorValue) {
		this.newDivisionFactorValue = newDivisionFactorValue;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public String getModeType() {
		return modeType;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	@Override
	public String toString() {
		return "PropertyRequest [name=" + name + ", value=" + value + ", index=" + index + ", oldPropertyValue="
				+ oldPropertyValue + ", newPropertyValue=" + newPropertyValue + ", updatedBy=" + updatedBy
				+ ", updatedOn=" + updatedOn + ", source=" + source + ", description=" + description + ", userComment="
				+ userComment + ", trackingId=" + trackingId + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ ", newDivisionFactorValue=" + newDivisionFactorValue + ", oldDivisionFactorValue="
				+ oldDivisionFactorValue + ", createdBy=" + createdBy + ", manufacturer=" + manufacturer + ", devType="
				+ devType + ", profileType=" + profileType + ", modeType=" + modeType + "]";
	}

}
