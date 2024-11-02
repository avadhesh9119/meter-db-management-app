package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GetDeviceListsRequest extends MeterDataVisualizationReq {
	private String startLatitude;
	private String endLatitude;
	private String startLongitude;
	private String endLongitude;
	private String range;
	private String showCount;
	private String latitude;
	private String longitude;
	private String deviceCount;
	private String meterNumber;

	public String getStartLatitude() {
		return startLatitude;
	}

	public void setStartLatitude(String startLatitude) {
		this.startLatitude = startLatitude;
	}

	public String getEndLatitude() {
		return endLatitude;
	}

	public void setEndLatitude(String endLatitude) {
		this.endLatitude = endLatitude;
	}

	public String getStartLongitude() {
		return startLongitude;
	}

	public void setStartLongitude(String startLongitude) {
		this.startLongitude = startLongitude;
	}

	public String getEndLongitude() {
		return endLongitude;
	}

	public void setEndLongitude(String endLongitude) {
		this.endLongitude = endLongitude;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}
	
	

	public String getShowCount() {
		return showCount;
	}

	public void setShowCount(String showCount) {
		this.showCount = showCount;
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

	public String getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(String deviceCount) {
		this.deviceCount = deviceCount;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	@Override
	public String toString() {
		return "GetDeviceListsRequest [startLatitude=" + startLatitude + ", endLatitude=" + endLatitude
				+ ", startLongitude=" + startLongitude + ", endLongitude=" + endLongitude + ", range=" + range + "]";
	}

}
