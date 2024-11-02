package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "process_on_demand_command")
public class ProcessOnDemandCommandData implements Serializable {

	private static final long serialVersionUID = -8613432154145015548L;

	@PrimaryKey
	private Date command_date;

	private String command_name;
	private Double success_percentage;

	public ProcessOnDemandCommandData() {
		super();
	}

	public ProcessOnDemandCommandData(Date command_date, String command_name, Double success_percentage) {
		super();
		this.command_date = command_date;
		this.command_name = command_name;
		this.success_percentage = success_percentage;
	}

	public Date getCommand_date() {
		return command_date;
	}

	public void setCommand_date(Date command_date) {
		this.command_date = command_date;
	}

	public String getCommand_name() {
		return command_name;
	}

	public void setCommand_name(String command_name) {
		this.command_name = command_name;
	}

	public Double getSuccess_percentage() {
		return success_percentage;
	}

	public void setSuccess_percentage(Double success_percentage) {
		this.success_percentage = success_percentage;
	}

	@Override
	public String toString() {
		return "ProcessOnDemandCommandData [command_date=" + command_date + ", command_name=" + command_name
				+ ", success_percentage=" + success_percentage + "]";
	}

}
