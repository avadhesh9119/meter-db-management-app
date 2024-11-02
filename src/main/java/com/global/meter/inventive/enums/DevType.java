package com.global.meter.inventive.enums;

public enum DevType {
	SINGLE_PHASE("Single"),
	THREE_PHASE("Three"),
	CT("CT"),
	HT("HT");
	
	public String devType;
	
	private DevType(String devType) {
		this.devType = devType;
	}
}
