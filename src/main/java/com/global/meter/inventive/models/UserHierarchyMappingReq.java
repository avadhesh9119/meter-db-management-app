package com.global.meter.inventive.models;

import java.util.List;

public class UserHierarchyMappingReq {

	public int userId;
	public int createdBy;
	public int updatedBy;
	public List<IamUserHierarchy> hierarchyList;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public List<IamUserHierarchy> getHierarchyList() {
		return hierarchyList;
	}
	public void setHierarchyList(List<IamUserHierarchy> hierarchyList) {
		this.hierarchyList = hierarchyList;
	}
	
}
