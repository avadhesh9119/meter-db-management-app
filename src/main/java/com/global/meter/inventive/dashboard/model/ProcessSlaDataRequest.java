package com.global.meter.inventive.dashboard.model;

import com.global.meter.inventive.models.MeterDataHierarchy;
import com.global.meter.inventive.models.MeterDataVisualizationReq;

public class ProcessSlaDataRequest extends MeterDataVisualizationReq {
	
	private String network;
	private String fromDate;
	private String toDate;
	private MeterDataHierarchy hier;
	private String devType;
	private String manufacturer;
	private String command;

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
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

	public MeterDataHierarchy getHier() {
		return hier;
	}

	public void setHier(MeterDataHierarchy hier) {
		this.hier = hier;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
}

