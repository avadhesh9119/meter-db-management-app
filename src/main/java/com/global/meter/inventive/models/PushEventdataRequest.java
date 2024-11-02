package com.global.meter.inventive.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class PushEventdataRequest implements Serializable{

	private static final long serialVersionUID = -1888735372262635883L;

 	private String device_serial_number;
	private String device_id;
	private String event_data_val;
	private List<String> event_data_desc;
	private String event_name;
    private Date meter_datetime;
    private Date mdas_datetime;
    private String dt_name;
    private String feeder_name;
    private String owner_name;
    private String subdivision_name;
    private String substation_name;
    private String count;
    private List<String> eventsList;
	private Date created;
    private Date modified;
    private String createdBy;
    private String updatedBy;
   
    
		public String getDevice_serial_number() {
		return device_serial_number;
	}


	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}


	public String getDevice_id() {
		return device_id;
	}


	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}


	public String getEvent_data_val() {
		return event_data_val;
	}


	public void setEvent_data_val(String event_data_val) {
		this.event_data_val = event_data_val;
	}


	public List<String> getEvent_data_desc() {
		return event_data_desc;
	}


	public void setEvent_data_desc(List<String> event_data_desc) {
		this.event_data_desc = event_data_desc;
	}


	public String getEvent_name() {
		return event_name;
	}


	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}


	public Date getMeter_datetime() {
		return meter_datetime;
	}


	public void setMeter_datetime(Date meter_datetime) {
		this.meter_datetime = meter_datetime;
	}


	public Date getMdas_datetime() {
		return mdas_datetime;
	}


	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}


	public String getDt_name() {
		return dt_name;
	}


	public void setDt_name(String dt_name) {
		this.dt_name = dt_name;
	}


	public String getFeeder_name() {
		return feeder_name;
	}


	public void setFeeder_name(String feeder_name) {
		this.feeder_name = feeder_name;
	}


	public String getOwner_name() {
		return owner_name;
	}


	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
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


	public String getCount() {
		return count;
	}


	public void setCount(String count) {
		this.count = count;
	}


	public List<String> getEventsList() {
		return eventsList;
	}


	public void setEventsList(List<String> eventsList) {
		this.eventsList = eventsList;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public Date getModified() {
		return modified;
	}


	public void setModified(Date modified) {
		this.modified = modified;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	@Override
	public String toString() {
		return "PushEventdataRequest [device_serial_number=" + device_serial_number + ", device_id=" + device_id
				+ ", event_data_val=" + event_data_val + ", event_data_desc=" + event_data_desc + ", event_name="
				+ event_name + ", meter_datetime=" + meter_datetime + ", mdas_datetime=" + mdas_datetime + ", dt_name="
				+ dt_name + ", feeder_name=" + feeder_name + ", owner_name=" + owner_name + ", subdivision_name="
				+ subdivision_name + ", substation_name=" + substation_name + ", count=" + count + ", eventsList="
				+ eventsList + ", created=" + created + ", modified=" + modified + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + "]";
	}
}
