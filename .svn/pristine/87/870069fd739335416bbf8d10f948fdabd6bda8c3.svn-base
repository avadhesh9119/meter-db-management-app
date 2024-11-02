package com.global.meter.inventive.dashboard.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("communication_summary_logs")
public class CommunicationSummaryLogs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1406584087237413247L;

	@PrimaryKey
	private Date mdas_datetime;
	
	private String hier_name;
	private String hier_value;
	private String communcating;
	private String communcating_avg;
	private String non_communcating;
	private String non_communcating_avg;
	private String total_devices;
	
	public Date getMdas_datetime() {
		return mdas_datetime;
	}
	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}
	public String getHier_name() {
		return hier_name;
	}
	public void setHier_name(String hier_name) {
		this.hier_name = hier_name;
	}
	public String getHier_value() {
		return hier_value;
	}
	public void setHier_value(String hier_value) {
		this.hier_value = hier_value;
	}
	public String getCommuncating() {
		return communcating;
	}
	public void setCommuncating(String communcating) {
		this.communcating = communcating;
	}
	public String getCommuncating_avg() {
		return communcating_avg;
	}
	public void setCommuncating_avg(String communcating_avg) {
		this.communcating_avg = communcating_avg;
	}
	public String getNon_communcating() {
		return non_communcating;
	}
	public void setNon_communcating(String non_communcating) {
		this.non_communcating = non_communcating;
	}
	public String getNon_communcating_avg() {
		return non_communcating_avg;
	}
	public void setNon_communcating_avg(String non_communcating_avg) {
		this.non_communcating_avg = non_communcating_avg;
	}
	public String getTotal_devices() {
		return total_devices;
	}
	public void setTotal_devices(String total_devices) {
		this.total_devices = total_devices;
	}
	
	@Override
	public String toString() {
		return "CommunicationSummaryLogs [mdas_datetime=" + mdas_datetime + ", hier_name=" + hier_name + ", hier_value="
				+ hier_value + ", communcating=" + communcating + ", communcating_avg=" + communcating_avg
				+ ", non_communcating=" + non_communcating + ", non_communcating_avg=" + non_communcating_avg
				+ ", total_devices=" + total_devices + "]";
	}
	
}
