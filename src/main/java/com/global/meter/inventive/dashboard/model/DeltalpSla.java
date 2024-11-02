package com.global.meter.inventive.dashboard.model;

public class DeltalpSla {
	
	private String device_serial_number;
	private int present;
	private int notPresent;
	private int totalCapturedSlots;
	
	public String getDevice_serial_number() {
		return device_serial_number;
	}
	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}
	public int getPresent() {
		return present;
	}
	public void setPresent(int present) {
		this.present = present;
	}
	public int getNotPresent() {
		return notPresent;
	}
	public void setNotPresent(int notPresent) {
		this.notPresent = notPresent;
	}
	public int getTotalCapturedSlots() {
		return totalCapturedSlots;
	}
	public void setTotalCapturedSlots(int totalCapturedSlots) {
		this.totalCapturedSlots = totalCapturedSlots;
	}
	
	@Override
	public String toString() {
		return "DeltalpSLA [device_serial_number=" + device_serial_number + ", present=" + present + ", notPresent="
				+ notPresent + ", totalCapturedSlots=" + totalCapturedSlots + "]";
	}
	
}
