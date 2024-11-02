package com.global.meter.inventive.dashboard.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("meter_commissioning_logs")
public class MeterCommissioningLogs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7290563902754656319L;
	
	@PrimaryKey
	private Date datetime;
	private String commissioning_count;
	private String devices_count;
	private String installation_count;
	private Date mdas_datetime;
	private String non_commissioning_count;
	private String non_installation_count;
	
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getCommissioning_count() {
		return commissioning_count;
	}
	public void setCommissioning_count(String commissioning_count) {
		this.commissioning_count = commissioning_count;
	}
	public String getDevices_count() {
		return devices_count;
	}
	public void setDevices_count(String devices_count) {
		this.devices_count = devices_count;
	}
	public String getInstallation_count() {
		return installation_count;
	}
	public void setInstallation_count(String installation_count) {
		this.installation_count = installation_count;
	}
	public Date getMdas_datetime() {
		return mdas_datetime;
	}
	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}
	public String getNon_commissioning_count() {
		return non_commissioning_count;
	}
	public void setNon_commissioning_count(String non_commissioning_count) {
		this.non_commissioning_count = non_commissioning_count;
	}
	public String getNon_installation_count() {
		return non_installation_count;
	}
	public void setNon_installation_count(String non_installation_count) {
		this.non_installation_count = non_installation_count;
	}
	
	@Override
	public String toString() {
		return "MeterCommissioningLogs [datetime=" + datetime + ", commissioning_count=" + commissioning_count
				+ ", devices_count=" + devices_count + ", installation_count=" + installation_count + ", mdas_datetime="
				+ mdas_datetime + ", non_commissioning_count=" + non_commissioning_count + ", non_installation_count="
				+ non_installation_count + "]";
	}
	
}
