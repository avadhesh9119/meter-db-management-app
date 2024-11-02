package com.global.meter.inventive.models;

import java.util.List;

import com.global.meter.business.model.DevicesInfo;

public class AllDevices {
	
	private String utility;
	private List<DevicesInfo> devicesInfo;
	
	public String getUtility() {
		return utility;
	}
	public void setUtility(String utility) {
		this.utility = utility;
	}
	public List<DevicesInfo> getDevicesInfo() {
		return devicesInfo;
	}
	public void setDevicesInfo(List<DevicesInfo> devicesInfo) {
		this.devicesInfo = devicesInfo;
	}
	
	
}
