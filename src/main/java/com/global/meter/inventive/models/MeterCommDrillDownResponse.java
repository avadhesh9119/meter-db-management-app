package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MeterCommDrillDownResponse {

	@JsonProperty("Meter No")
	private String meterNo;
	
	@JsonProperty("Last Comm. Timestamp")
	private String lastCommTimestamp;
	
	@JsonProperty("Last Instant Timestamp")
	private String lastInstantCommTimestamp;
	
	@JsonProperty("Last Daily Timestamp")
	private String lastDailyCommTimestamp;
	
	@JsonProperty("Last Delta Timestamp")
	private String lastDeltaCommTimestamp;
	
	@JsonProperty("Last Billing Timestamp")
	private String lastBillingCommTimestamp;
	
	@JsonProperty("Last Event Timestamp")
	private String lastEventCommTimestamp;
	
	@JsonProperty("Mode")
	private String deviceMode;
	
	@JsonProperty("IP")
	private String ipAddress;
	
	@JsonProperty("CRN")
	private String crnNo;
	
	@JsonProperty("Latitude")
	private String latitude;
	
	@JsonProperty("Longitude")
	private String longitude;
	
	public String getLastCommTimestamp() {
		return lastCommTimestamp;
	}
	public void setLastCommTimestamp(String lastCommTimestamp) {
		this.lastCommTimestamp = lastCommTimestamp;
	}
	public String getMeterNo() {
		return meterNo;
	}
	public void setMeterNo(String meterNo) {
		this.meterNo = meterNo;
	}
	public String getDeviceMode() {
		return deviceMode;
	}
	public void setDeviceMode(String deviceMode) {
		this.deviceMode = deviceMode;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCrnNo() {
		return crnNo;
	}
	public void setCrnNo(String crnNo) {
		this.crnNo = crnNo;
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
	
	public String getLastInstantCommTimestamp() {
		return lastInstantCommTimestamp;
	}
	public void setLastInstantCommTimestamp(String lastInstantCommTimestamp) {
		this.lastInstantCommTimestamp = lastInstantCommTimestamp;
	}
	public String getLastDailyCommTimestamp() {
		return lastDailyCommTimestamp;
	}
	public void setLastDailyCommTimestamp(String lastDailyCommTimestamp) {
		this.lastDailyCommTimestamp = lastDailyCommTimestamp;
	}
	public String getLastDeltaCommTimestamp() {
		return lastDeltaCommTimestamp;
	}
	public void setLastDeltaCommTimestamp(String lastDeltaCommTimestamp) {
		this.lastDeltaCommTimestamp = lastDeltaCommTimestamp;
	}
	public String getLastBillingCommTimestamp() {
		return lastBillingCommTimestamp;
	}
	public void setLastBillingCommTimestamp(String lastBillingCommTimestamp) {
		this.lastBillingCommTimestamp = lastBillingCommTimestamp;
	}
	public String getLastEventCommTimestamp() {
		return lastEventCommTimestamp;
	}
	public void setLastEventCommTimestamp(String lastEventCommTimestamp) {
		this.lastEventCommTimestamp = lastEventCommTimestamp;
	}
	
	@Override
	public String toString() {
		return "MeterCommDrillDownResponse [meterNo=" + meterNo + ", lastCommTimestamp=" + lastCommTimestamp
				+ ", lastInstantCommTimestamp=" + lastInstantCommTimestamp + ", lastDailyCommTimestamp="
				+ lastDailyCommTimestamp + ", lastDeltaCommTimestamp=" + lastDeltaCommTimestamp
				+ ", lastBillingCommTimestamp=" + lastBillingCommTimestamp + ", lastEventCommTimestamp="
				+ lastEventCommTimestamp + ", deviceMode=" + deviceMode + ", ipAddress=" + ipAddress + ", crnNo="
				+ crnNo + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}	
}
