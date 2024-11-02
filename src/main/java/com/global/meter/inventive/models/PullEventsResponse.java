package com.global.meter.inventive.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Nitin Sethi
 *
 */
@JsonInclude(Include.NON_NULL)
public class PullEventsResponse{

	@JsonProperty("Meter No.")
	private String meterNumber;
	
	@JsonProperty("Meter Timestamp")
	private String meterDatetime;
	
	@JsonProperty("HES Timestamp")
	private String hesDatetime;
	
	@JsonProperty("Event Name")
	private Object eventDesc;
	
	@JsonProperty("Event Value")
	private String eventVal;
	
	@JsonProperty("count")
	private String count;
	
	@JsonProperty("event")
	private String eventName;

	@JsonProperty("Subdivision")
	private String subdivision_name;
	
	@JsonProperty("Substation")
	private String substation_name;
	
	@JsonProperty("Feeder")
	private String feeder;
	
	@JsonProperty("DT")
	private String dt;
	
	@JsonProperty("Critical Events")
	private String critical;	
	
	@JsonProperty("High Priority Events List")
	private Object highPriorityEvents;
	
	@JsonProperty("Critical Events List")
	private Object criticalEvents;
	
	@JsonProperty("Non-Critical Events")
	private Object nonCritical;
	
	@JsonProperty("Push Events List")
	private Object pushEventsList;
	
	@JsonProperty("Pull Events List")
	private Object pullEventsList;
	
	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}

	public String getMeterDatetime() {
		return meterDatetime;
	}

	public void setMeterDatetime(String meterDatetime) {
		this.meterDatetime = meterDatetime;
	}

	public String getHesDatetime() {
		return hesDatetime;
	}

	public void setHesDatetime(String hesDatetime) {
		this.hesDatetime = hesDatetime;
	}

	public Object getEventDesc() {
		return eventDesc;
	}

	public void setEventDesc(Object eventDesc) {
		this.eventDesc = eventDesc;
	}

	public String getEventVal() {
		return eventVal;
	}

	public void setEventVal(String eventVal) {
		this.eventVal = eventVal;
	}

	public String getSubdivision_name() {
		return subdivision_name;
	}

	public void setSubdivision_name(String subdivision_name) {
		this.subdivision_name = subdivision_name;
	}

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	public String getFeeder() {
		return feeder;
	}

	public void setFeeder(String feeder) {
		this.feeder = feeder;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getCritical() {
		return critical;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public Object getNonCritical() {
		return nonCritical;
	}

	public void setNonCritical(Object nonCritical) {
		this.nonCritical = nonCritical;
	}

	public Object getHighPriorityEvents() {
		return highPriorityEvents;
	}

	public void setHighPriorityEvents(Object highPriorityEvents) {
		this.highPriorityEvents = highPriorityEvents;
	}

	public Object getCriticalEvents() {
		return criticalEvents;
	}

	public void setCriticalEvents(Object criticalEvents) {
		this.criticalEvents = criticalEvents;
	}

	public Object getPushEventsList() {
		return pushEventsList;
	}

	public void setPushEventsList(Object pushEventsList) {
		this.pushEventsList = pushEventsList;
	}

	public Object getPullEventsList() {
		return pullEventsList;
	}

	public void setPullEventsList(Object pullEventsList) {
		this.pullEventsList = pullEventsList;
	}
	
}
