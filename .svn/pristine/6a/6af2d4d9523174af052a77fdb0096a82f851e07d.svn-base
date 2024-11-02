package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "push_events_singlephase")
public class PushEventsSinglePhase implements Serializable{

	private static final long serialVersionUID = -1888735372262635883L;

	@PrimaryKey
 	private String device_serial_number;
	private String device_id;
	private String event_data_val;
	private List<String> event_data_desc;
    private Date meter_datetime;
    private Date mdas_datetime;
    private String dt_name;
    private String feeder_name;
    private String owner_name;
    private String subdivision_name;
    private String substation_name;
    private String device_type;
    
    public PushEventsSinglePhase() {
		super();
	}

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

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
}
