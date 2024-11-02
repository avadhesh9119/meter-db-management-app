package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class UsersHierMappingDataResponse {

	@JsonProperty("Mapping Id")
	private int mappingId;

	@JsonProperty("Company Id")
	private int companyId;

	@JsonProperty("Created By")
	private int createdBy;

	@JsonProperty("Created On")
	private String createdOn;

	@JsonProperty("IP Address")
	private String ipAddress;

	@JsonProperty("Is Active")
	private Boolean isActive;

	@JsonProperty("Is Default")
	private Boolean isDefault;

	@JsonProperty("Last Updated By")
	private int lastUpdatedBy;

	@JsonProperty("Last Updated On")
	private String lastUpdatedOn;

	@JsonProperty("Level1 Id")
	private int level1Id;

	@JsonProperty("Level2 Id")
	private int level2Id;

	@JsonProperty("Level3 Id")
	private int level3Id;

	@JsonProperty("Level4 Id")
	private int level4Id;

	@JsonProperty("Level5 Id")
	private int level5Id;

	@JsonProperty("Level6 Id")
	private int level6Id;

	@JsonProperty("User Id")
	private int userId;

	public int getMappingId() {
		return mappingId;
	}

	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public int getLevel1Id() {
		return level1Id;
	}

	public void setLevel1Id(int level1Id) {
		this.level1Id = level1Id;
	}

	public int getLevel2Id() {
		return level2Id;
	}

	public void setLevel2Id(int level2Id) {
		this.level2Id = level2Id;
	}

	public int getLevel3Id() {
		return level3Id;
	}

	public void setLevel3Id(int level3Id) {
		this.level3Id = level3Id;
	}

	public int getLevel4Id() {
		return level4Id;
	}

	public void setLevel4Id(int level4Id) {
		this.level4Id = level4Id;
	}

	public int getLevel5Id() {
		return level5Id;
	}

	public void setLevel5Id(int level5Id) {
		this.level5Id = level5Id;
	}

	public int getLevel6Id() {
		return level6Id;
	}

	public void setLevel6Id(int level6Id) {
		this.level6Id = level6Id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UsersHierMappingDataResponse [mappingId=" + mappingId + ", companyId=" + companyId + ", createdBy="
				+ createdBy + ", createdOn=" + createdOn + ", ipAddress=" + ipAddress + ", isActive=" + isActive
				+ ", isDefault=" + isDefault + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedOn=" + lastUpdatedOn
				+ ", level1Id=" + level1Id + ", level2Id=" + level2Id + ", level3Id=" + level3Id + ", level4Id="
				+ level4Id + ", level5Id=" + level5Id + ", level6Id=" + level6Id + ", userId=" + userId + "]";
	}

}
