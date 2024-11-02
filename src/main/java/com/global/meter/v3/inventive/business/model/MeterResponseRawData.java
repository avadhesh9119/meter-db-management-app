package com.global.meter.v3.inventive.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "raw_data")
public class MeterResponseRawData implements Serializable {

	private static final long serialVersionUID = -5290816761455278114L;

	@PrimaryKey
	private String device_serial_number;
	private String tracking_id;
	private String data;
	private Date mdas_datetime;
	private String profile_type;

	public MeterResponseRawData() {
		super();
	}

	public MeterResponseRawData(String device_serial_number, String tracking_id, String data, Date mdas_datetime,
			String profileType) {
		super();
		this.device_serial_number = device_serial_number;
		this.tracking_id = tracking_id;
		this.data = data;
		this.mdas_datetime = mdas_datetime;
	}

	public String getDevice_serial_number() {
		return device_serial_number;
	}

	public void setDevice_serial_number(String device_serial_number) {
		this.device_serial_number = device_serial_number;
	}

	public String getTracking_id() {
		return tracking_id;
	}

	public void setTracking_id(String tracking_id) {
		this.tracking_id = tracking_id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getProfile_type() {
		return profile_type;
	}

	public void setProfile_type(String profile_type) {
		this.profile_type = profile_type;
	}

	@Override
	public String toString() {
		return "RawData [device_serial_number=" + device_serial_number + ", tracking_id=" + tracking_id + ", data="
				+ data + ", mdas_datetime=" + mdas_datetime + ", profile_type=" + profile_type + "]";
	}

}
