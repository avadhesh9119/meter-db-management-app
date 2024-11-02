package com.global.meter.business.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(value = "device_batch_logs")
public class DeviceBatchLogs implements Serializable {

	private static final long serialVersionUID = 2360486597143497020L;

	@PrimaryKey
	private String batch_id;

	private Date completion_datetime;
	private Integer failure_records;
	private String file_name;
	private Date mdas_datetime;
	private String status;
	private Integer success_records;
	private Integer tot_records;
	private String user_name;
	private String bulk_type;

	public DeviceBatchLogs() {
		
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public Date getCompletion_datetime() {
		return completion_datetime;
	}

	public void setCompletion_datetime(Date completion_datetime) {
		this.completion_datetime = completion_datetime;
	}

	public Integer getFailure_records() {
		return failure_records;
	}

	public void setFailure_records(Integer failure_records) {
		this.failure_records = failure_records;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Date getMdas_datetime() {
		return mdas_datetime;
	}

	public void setMdas_datetime(Date mdas_datetime) {
		this.mdas_datetime = mdas_datetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSuccess_records() {
		return success_records;
	}

	public void setSuccess_records(Integer success_records) {
		this.success_records = success_records;
	}

	public Integer getTot_records() {
		return tot_records;
	}

	public void setTot_records(Integer tot_records) {
		this.tot_records = tot_records;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getBulk_type() {
		return bulk_type;
	}

	public void setBulk_type(String bulk_type) {
		this.bulk_type = bulk_type;
	}
}
