package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DeviceCountRequest{

	private String totDevCount;
	private String activeCount;
	private String commissionedCount;
	private String notCommissionedCount;
	private String communationFieldCount;
	private String devicesinfoSet;
	private String devicesNamePlateSet;
	
	public String getTotDevCount() {
		return totDevCount;
	}
	public void setTotDevCount(String totDevCount) {
		this.totDevCount = totDevCount;
	}
	public String getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(String activeCount) {
		this.activeCount = activeCount;
	}
	public String getCommissionedCount() {
		return commissionedCount;
	}
	public void setCommissionedCount(String commissionedCount) {
		this.commissionedCount = commissionedCount;
	}
	public String getNotCommissionedCount() {
		return notCommissionedCount;
	}
	public void setNotCommissionedCount(String notCommissionedCount) {
		this.notCommissionedCount = notCommissionedCount;
	}
	public String getCommunationFieldCount() {
		return communationFieldCount;
	}
	public void setCommunationFieldCount(String communationFieldCount) {
		this.communationFieldCount = communationFieldCount;
	}
	public String getDevicesinfoSet() {
		return devicesinfoSet;
	}
	public void setDevicesinfoSet(String devicesinfoSet) {
		this.devicesinfoSet = devicesinfoSet;
	}
	public String getDevicesNamePlateSet() {
		return devicesNamePlateSet;
	}
	public void setDevicesNamePlateSet(String devicesNamePlateSet) {
		this.devicesNamePlateSet = devicesNamePlateSet;
	}

}