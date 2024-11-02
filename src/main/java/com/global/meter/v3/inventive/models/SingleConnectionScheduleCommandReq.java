package com.global.meter.v3.inventive.models;

import java.io.Serializable;

import com.global.meter.request.model.Device;

public class SingleConnectionScheduleCommandReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8511886307423678597L;
	
	private Device device;
	private String batchId;

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
 }