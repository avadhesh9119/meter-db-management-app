package com.global.meter.business.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DcuMasterResponse implements Serializable {

	private static final long serialVersionUID = 5658221707297317329L;
	
	private String replaceBy;
	private String userId;
	private String dcuSerialNo;	
	private String dcuMacNicId;
	private String frequency;
	private String ipv6Address;
	private String latitude;
	private String longitude;
	private String manufacturer;
	private String network;
	private String owner;
	private String remarks;
	private String simNo;
	private String UpdatedDatetime;
	private String updatedBy;
	private String createdBy;	
	private String createdDatetime;
	private String deletedBY;
	private String deletedDatetime;
	public String getReplaceBy() {
		return replaceBy;
	}
	public void setReplaceBy(String replaceBy) {
		this.replaceBy = replaceBy;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDcuSerialNo() {
		return dcuSerialNo;
	}
	public void setDcuSerialNo(String dcuSerialNo) {
		this.dcuSerialNo = dcuSerialNo;
	}
	public String getDcuMacNicId() {
		return dcuMacNicId;
	}
	public void setDcuMacNicId(String dcuMacNicId) {
		this.dcuMacNicId = dcuMacNicId;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getIpv6Address() {
		return ipv6Address;
	}
	public void setIpv6Address(String ipv6Address) {
		this.ipv6Address = ipv6Address;
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
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public String getUpdatedDatetime() {
		return UpdatedDatetime;
	}
	public void setUpdatedDatetime(String updatedDatetime) {
		UpdatedDatetime = updatedDatetime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDatetime() {
		return createdDatetime;
	}
	public void setCreatedDatetime(String createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public String getDeletedBY() {
		return deletedBY;
	}
	public void setDeletedBY(String deletedBY) {
		this.deletedBY = deletedBY;
	}
	public String getDeletedDatetime() {
		return deletedDatetime;
	}
	public void setDeletedDatetime(String deletedDatetime) {
		this.deletedDatetime = deletedDatetime;
	}
	@Override
	public String toString() {
		return "DcuMasterResponse [replaceBy=" + replaceBy + ", userId=" + userId + ", dcuSerialNo=" + dcuSerialNo
				+ ", dcuMacNicId=" + dcuMacNicId + ", frequency=" + frequency + ", ipv6Address=" + ipv6Address
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", manufacturer=" + manufacturer
				+ ", network=" + network + ", owner=" + owner + ", remarks=" + remarks + ", simNo=" + simNo
				+ ", UpdatedDatetime=" + UpdatedDatetime + ", updatedBy=" + updatedBy + ", createdBy=" + createdBy
				+ ", createdDatetime=" + createdDatetime + ", deletedBY=" + deletedBY + ", deletedDatetime="
				+ deletedDatetime + "]";
	}
	
}
