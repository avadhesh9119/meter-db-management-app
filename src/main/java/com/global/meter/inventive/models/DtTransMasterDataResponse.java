package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DtTransMasterDataResponse implements Serializable {

	private static final long serialVersionUID = 871376901633296300L;
	
	private String iAmId;
	private String hesDtTransId;
	private String hesFeederId;
	private String hesSubstationId;
	private String hesSubdivisionId;
	private String hesOwnerId;
	private String created;
	private String latitude;
	private String longitude;
	private String modified;
	private String utility;
	private String subdivisionName;
	private String substationName;
	private String dtName;
	private String feederName;
	private String source;
	private String createdBy;
	private String lastUpdatedBy;
	private String dtCode;
	
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
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
	public String getHesFeederId() {
		return hesFeederId;
	}
	public void setHesFeederId(String hesFeederId) {
		this.hesFeederId = hesFeederId;
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
	public String getHesOwnerId() {
		return hesOwnerId;
	}
	public void setHesOwnerId(String hesOwnerId) {
		this.hesOwnerId = hesOwnerId;
	}
	public String getDtCode() {
		return dtCode;
	}
	public void setDtCode(String dtCode) {
		this.dtCode = dtCode;
	}
	@Override
	public String toString() {
		return "DtTransMasterDataResponse [iAmId=" + iAmId + ", hesDtTransId=" + hesDtTransId + ", hesFeederId="
				+ hesFeederId + ", hesSubstationId=" + hesSubstationId + ", hesSubdivisionId=" + hesSubdivisionId
				+ ", hesOwnerId=" + hesOwnerId + ", created=" + created + ", latitude=" + latitude + ", longitude="
				+ longitude + ", modified=" + modified + ", utility=" + utility + ", subdivisionName=" + subdivisionName
				+ ", substationName=" + substationName + ", dtName=" + dtName + ", feederName=" + feederName
				+ ", source=" + source + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", dtCode="
				+ dtCode + "]";
	}

}
