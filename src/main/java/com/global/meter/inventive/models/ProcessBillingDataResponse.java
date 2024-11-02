package com.global.meter.inventive.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class ProcessBillingDataResponse {

	@JsonProperty("On Time Meter")
	private List<String> onTimeMeter;
	
	@JsonProperty("Off Time Meter")
	private List<String> offTimeMeter;

	@JsonProperty("On Time")
	private String onTime;

	@JsonProperty("Off Time")
	private String offTime;
	
	@JsonProperty("On Time Count")
	private String onTimeCount;
	
	@JsonProperty("Off Time Count")
	private String offTimeCount;
	
	@JsonProperty("Total Count")
	private String totalCount;
	
	@JsonProperty("Recieved Count")
	private String recievedCount;
	
	@JsonProperty("Non-Recieved Count")
	private String nonRecievedCount;
	

	public List<String> getOnTimeMeter() {
		return onTimeMeter;
	}

	public void setOnTimeMeter(List<String> onTimeMeter) {
		this.onTimeMeter = onTimeMeter;
	}

	public List<String> getOffTimeMeter() {
		return offTimeMeter;
	}

	public void setOffTimeMeter(List<String> offTimeMeter) {
		this.offTimeMeter = offTimeMeter;
	}

	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

	public String getOffTime() {
		return offTime;
	}

	public void setOffTime(String offTime) {
		this.offTime = offTime;
	}

	public String getOnTimeCount() {
		return onTimeCount;
	}

	public void setOnTimeCount(String onTimeCount) {
		this.onTimeCount = onTimeCount;
	}

	public String getOffTimeCount() {
		return offTimeCount;
	}

	public void setOffTimeCount(String offTimeCount) {
		this.offTimeCount = offTimeCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getRecievedCount() {
		return recievedCount;
	}

	public void setRecievedCount(String recievedCount) {
		this.recievedCount = recievedCount;
	}

	public String getNonRecievedCount() {
		return nonRecievedCount;
	}

	public void setNonRecievedCount(String nonRecievedCount) {
		this.nonRecievedCount = nonRecievedCount;
	}
	
}
