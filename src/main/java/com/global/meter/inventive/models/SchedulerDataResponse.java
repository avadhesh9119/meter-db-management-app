package com.global.meter.inventive.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SchedulerDataResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7315852093936430845L;

	private String description;
	private String scheduler;
	private String value;
	private String status;
	private String retry;
	private String trackingId;
	private String source;
	private String userId;
	private String data;
	private String hesTimestamp;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScheduler() {
		return scheduler;
	}
	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRetry() {
		return retry;
	}
	public void setRetry(String retry) {
		this.retry = retry;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHesTimestamp() {
		return hesTimestamp;
	}
	public void setHesTimestamp(String hesTimestamp) {
		this.hesTimestamp = hesTimestamp;
	}
	
}
