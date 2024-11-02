package com.global.meter.business.model;

import java.util.Date;
import java.util.Map;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "devices_commands_logs")
public class DeviceCommandLogs {

	@PrimaryKey
	private String device_serial_number;

	private String tracking_id;
	private Map<String,String> command_name;
	private String status;
	private Date mdas_datetime;  
	private String dcu_serial_number;
	private String dt_name;
	private String feeder_name;
	private String owner_name;
	private String subdevision_name;
	private String substation_name;
	private String reason;
	private Date command_completion_datetime;
	private Integer tot_attempts;
	private Map<String,Integer> command_retry;
	private String cell_id;
	private String batch_id;
	private String source;
	private String user_id;
	private Map<String,String> daily_range_date;
	private Map<String,String> delta_range_date;

	public DeviceCommandLogs() {
	}

	public DeviceCommandLogs(String device_serial_number, String tracking_id, Map<String,String> command_name, String status,
			Date mdas_datetime, String dcu_serial_number, String dt_name, String feeder_name, String owner_name,
			String subdevision_name, String substation_name, String reason, Date command_completion_datetime,
			Integer tot_attempts, Map<String,Integer> command_retry) {
		super();
		this.device_serial_number = device_serial_number;
		this.tracking_id = tracking_id;
		this.command_name = command_name;
		this.status = status;
		this.mdas_datetime = mdas_datetime;
		this.dcu_serial_number = dcu_serial_number;
		this.dt_name = dt_name;
		this.feeder_name = feeder_name;
		this.owner_name = owner_name;
		this.subdevision_name = subdevision_name;
		this.substation_name = substation_name;
		this.reason = reason;
		this.command_completion_datetime = command_completion_datetime;
		this.tot_attempts = tot_attempts;
		this.command_retry = command_retry;
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

	public Map<String, String> getCommand_name() {
		return command_name;
	}

	public void setCommand_name(Map<String, String> command_name) {
		this.command_name = command_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getDcu_serial_number() {
		return dcu_serial_number;
	}

	public void setDcu_serial_number(String dcu_serial_number) {
		this.dcu_serial_number = dcu_serial_number;
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

	public String getSubdevision_name() {
		return subdevision_name;
	}

	public void setSubdevision_name(String subdevision_name) {
		this.subdevision_name = subdevision_name;
	}

	public String getSubstation_name() {
		return substation_name;
	}

	public void setSubstation_name(String substation_name) {
		this.substation_name = substation_name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCommand_completion_datetime() {
		return command_completion_datetime;
	}

	public void setCommand_completion_datetime(Date command_completion_datetime) {
		this.command_completion_datetime = command_completion_datetime;
	}

	public Integer getTot_attempts() {
		return tot_attempts;
	}

	public void setTot_attempts(Integer tot_attempts) {
		this.tot_attempts = tot_attempts;
	}

	public Map<String, Integer> getCommand_retry() {
		return command_retry;
	}

	public void setCommand_retry(Map<String, Integer> command_retry) {
		this.command_retry = command_retry;
	}

	public String getCell_id() {
		return cell_id;
	}

	public void setCell_id(String cell_id) {
		this.cell_id = cell_id;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public Map<String, String> getDaily_range_date() {
		return daily_range_date;
	}

	public void setDaily_range_date(Map<String, String> daily_range_date) {
		this.daily_range_date = daily_range_date;
	}

	public Map<String, String> getDelta_range_date() {
		return delta_range_date;
	}

	public void setDelta_range_date(Map<String, String> delta_range_date) {
		this.delta_range_date = delta_range_date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceCommandLogs [device_serial_number=");
		builder.append(device_serial_number);
		builder.append(", tracking_id=");
		builder.append(tracking_id);
		builder.append(", command_name=");
		builder.append(command_name);
		builder.append(", status=");
		builder.append(status);
		builder.append(", mdas_datetime=");
		builder.append(mdas_datetime);
		builder.append(", dcu_serial_number=");
		builder.append(dcu_serial_number);
		builder.append(", dt_name=");
		builder.append(dt_name);
		builder.append(", feeder_name=");
		builder.append(feeder_name);
		builder.append(", owner_name=");
		builder.append(owner_name);
		builder.append(", subdevision_name=");
		builder.append(subdevision_name);
		builder.append(", substation_name=");
		builder.append(substation_name);
		builder.append(", reason=");
		builder.append(reason);
		builder.append(", command_completion_datetime=");
		builder.append(command_completion_datetime);
		builder.append(", tot_attempts=");
		builder.append(tot_attempts);
		builder.append(", command_retry=");
		builder.append(command_retry);
		builder.append(", cell_id=");
		builder.append(cell_id);
		builder.append(", batch_id=");
		builder.append(batch_id);
		builder.append(", source=");
		builder.append(source);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", daily_range_date=");
		builder.append(daily_range_date);
		builder.append(", delta_range_date=");
		builder.append(delta_range_date);
		builder.append("]");
		return builder.toString();
	}

}
