package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DevicesCountResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9010058845710644512L;
	
	@JsonProperty("Total Meter")
	private String allDeviceCount;
	
	@JsonProperty("Active Meter")
	private String activeDeviceCount;
	
	@JsonProperty("Commissioned")
	private String commissinonedCount;
	
	@JsonProperty("Non-Commissioned")
	private String notCommissinonedCount;
	
	@JsonProperty("Communation Failed")
	private String communationFieldCount;
	
	@JsonProperty("Command")
	private String command_name;
	
	@JsonProperty("Date")
	private String date;
	
	@JsonProperty("Count")
	private String count;
	
	@JsonProperty("Connect")
	private String connect;
	
	@JsonProperty("Connect Count")
	private String connectCount;
	
	@JsonProperty("Disconnect")
	private String disconnect;
	
	@JsonProperty("Disconnect Count")
	private String disconnectCount;
	
	@JsonProperty("Daily Load Profile")
	private String dailyLoadProfile;
	
	@JsonProperty("Daily Load Profile Count")
	private String dailyLoadProfileCount;
	
	@JsonProperty("InstantaneousRead")
	private String instantaneousRead;
	
	@JsonProperty("InstantaneousRead Count")
	private String instantaneousReadCount;
	
	@JsonProperty("Delta Load Profile")
	private String deltaLoadProfile;
	
	@JsonProperty("Delta Load Profile Count")
	private String deltaLoadProfileCount;
	
	@JsonProperty("Billing Data")
	private String billingData;
	
	@JsonProperty("Billing Data Count")
	private String billingDataCount;
	
	@JsonProperty("Power Related Events")
	private String powerRelatedEvents;
	
	@JsonProperty("Power Related Events Count")
	private String powerRelatedEventsCount;
	
	@JsonProperty("Voltage Related Events")
	private String voltageRelatedEvents;
	
	@JsonProperty("Voltage Related Events Count")
	private String voltageRelatedEventsCount;
	
	@JsonProperty("Transaction Related Events")
	private String transactionRelatedEvents;
	
	@JsonProperty("Transaction Related Events Count")
	private String transactionRelatedEventsCount;
	
	@JsonProperty("Current Related Events")
	private String currentRelatedEvents;
	
	@JsonProperty("Current Related Events Count")
	private String currentRelatedEventsCount;
	
	@JsonProperty("Other Related Events")
	private String otherRelatedEvents;
	
	@JsonProperty("Other Related Events Count")
	private String otherRelatedEventsCount;
	
	@JsonProperty("Control Related Events")
	private String controlRelatedEvents;
	
	@JsonProperty("Control Related Events Count")
	private String controlRelatedEventsCount;
	
	@JsonProperty("Name Plate")
	private String namePlate;
	
	@JsonProperty("Name Plate Count")
	private String namePlateCount;
	
	@JsonProperty("Device Count")
	private String deviceCount;

	public String getAllDeviceCount() {
		return allDeviceCount;
	}

	public void setAllDeviceCount(String allDeviceCount) {
		this.allDeviceCount = allDeviceCount;
	}

	public String getActiveDeviceCount() {
		return activeDeviceCount;
	}

	public void setActiveDeviceCount(String activeDeviceCount) {
		this.activeDeviceCount = activeDeviceCount;
	}

	public String getCommissinonedCount() {
		return commissinonedCount;
	}

	public void setCommissinonedCount(String commissinonedCount) {
		this.commissinonedCount = commissinonedCount;
	}

	public String getNotCommissinonedCount() {
		return notCommissinonedCount;
	}

	public void setNotCommissinonedCount(String notCommissinonedCount) {
		this.notCommissinonedCount = notCommissinonedCount;
	}

	public String getCommunationFieldCount() {
		return communationFieldCount;
	}

	public void setCommunationFieldCount(String communationFieldCount) {
		this.communationFieldCount = communationFieldCount;
	}

	public String getCommand_name() {
		return command_name;
	}

	public void setCommand_name(String command_name) {
		this.command_name = command_name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getConnect() {
		return connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}

	public String getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(String connectCount) {
		this.connectCount = connectCount;
	}

	public String getDisconnect() {
		return disconnect;
	}

	public void setDisconnect(String disconnect) {
		this.disconnect = disconnect;
	}

	public String getDisconnectCount() {
		return disconnectCount;
	}

	public void setDisconnectCount(String disconnectCount) {
		this.disconnectCount = disconnectCount;
	}

	public String getDailyLoadProfile() {
		return dailyLoadProfile;
	}

	public void setDailyLoadProfile(String dailyLoadProfile) {
		this.dailyLoadProfile = dailyLoadProfile;
	}

	public String getDailyLoadProfileCount() {
		return dailyLoadProfileCount;
	}

	public void setDailyLoadProfileCount(String dailyLoadProfileCount) {
		this.dailyLoadProfileCount = dailyLoadProfileCount;
	}

	public String getInstantaneousRead() {
		return instantaneousRead;
	}

	public void setInstantaneousRead(String instantaneousRead) {
		this.instantaneousRead = instantaneousRead;
	}

	public String getInstantaneousReadCount() {
		return instantaneousReadCount;
	}

	public void setInstantaneousReadCount(String instantaneousReadCount) {
		this.instantaneousReadCount = instantaneousReadCount;
	}

	public String getDeltaLoadProfile() {
		return deltaLoadProfile;
	}

	public void setDeltaLoadProfile(String deltaLoadProfile) {
		this.deltaLoadProfile = deltaLoadProfile;
	}

	public String getDeltaLoadProfileCount() {
		return deltaLoadProfileCount;
	}

	public void setDeltaLoadProfileCount(String deltaLoadProfileCount) {
		this.deltaLoadProfileCount = deltaLoadProfileCount;
	}

	public String getBillingData() {
		return billingData;
	}

	public void setBillingData(String billingData) {
		this.billingData = billingData;
	}

	public String getBillingDataCount() {
		return billingDataCount;
	}

	public void setBillingDataCount(String billingDataCount) {
		this.billingDataCount = billingDataCount;
	}

	public String getPowerRelatedEvents() {
		return powerRelatedEvents;
	}

	public void setPowerRelatedEvents(String powerRelatedEvents) {
		this.powerRelatedEvents = powerRelatedEvents;
	}

	public String getPowerRelatedEventsCount() {
		return powerRelatedEventsCount;
	}

	public void setPowerRelatedEventsCount(String powerRelatedEventsCount) {
		this.powerRelatedEventsCount = powerRelatedEventsCount;
	}

	public String getVoltageRelatedEvents() {
		return voltageRelatedEvents;
	}

	public void setVoltageRelatedEvents(String voltageRelatedEvents) {
		this.voltageRelatedEvents = voltageRelatedEvents;
	}

	public String getVoltageRelatedEventsCount() {
		return voltageRelatedEventsCount;
	}

	public void setVoltageRelatedEventsCount(String voltageRelatedEventsCount) {
		this.voltageRelatedEventsCount = voltageRelatedEventsCount;
	}

	public String getTransactionRelatedEvents() {
		return transactionRelatedEvents;
	}

	public void setTransactionRelatedEvents(String transactionRelatedEvents) {
		this.transactionRelatedEvents = transactionRelatedEvents;
	}

	public String getTransactionRelatedEventsCount() {
		return transactionRelatedEventsCount;
	}

	public void setTransactionRelatedEventsCount(String transactionRelatedEventsCount) {
		this.transactionRelatedEventsCount = transactionRelatedEventsCount;
	}

	public String getCurrentRelatedEvents() {
		return currentRelatedEvents;
	}

	public void setCurrentRelatedEvents(String currentRelatedEvents) {
		this.currentRelatedEvents = currentRelatedEvents;
	}

	public String getCurrentRelatedEventsCount() {
		return currentRelatedEventsCount;
	}

	public void setCurrentRelatedEventsCount(String currentRelatedEventsCount) {
		this.currentRelatedEventsCount = currentRelatedEventsCount;
	}

	public String getOtherRelatedEvents() {
		return otherRelatedEvents;
	}

	public void setOtherRelatedEvents(String otherRelatedEvents) {
		this.otherRelatedEvents = otherRelatedEvents;
	}

	public String getOtherRelatedEventsCount() {
		return otherRelatedEventsCount;
	}

	public void setOtherRelatedEventsCount(String otherRelatedEventsCount) {
		this.otherRelatedEventsCount = otherRelatedEventsCount;
	}

	public String getControlRelatedEvents() {
		return controlRelatedEvents;
	}

	public void setControlRelatedEvents(String controlRelatedEvents) {
		this.controlRelatedEvents = controlRelatedEvents;
	}

	public String getControlRelatedEventsCount() {
		return controlRelatedEventsCount;
	}

	public void setControlRelatedEventsCount(String controlRelatedEventsCount) {
		this.controlRelatedEventsCount = controlRelatedEventsCount;
	}

	public String getNamePlate() {
		return namePlate;
	}

	public void setNamePlate(String namePlate) {
		this.namePlate = namePlate;
	}

	public String getNamePlateCount() {
		return namePlateCount;
	}

	public void setNamePlateCount(String namePlateCount) {
		this.namePlateCount = namePlateCount;
	}

	public String getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(String deviceCount) {
		this.deviceCount = deviceCount;
	}

}