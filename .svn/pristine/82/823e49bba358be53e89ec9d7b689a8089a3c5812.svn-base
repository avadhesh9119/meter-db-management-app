package com.global.meter.common.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.global.meter.business.model.FirmwareData;

@Component
@JsonInclude(Include.NON_NULL)
public class MeterResponse {
	
	private String obisCmd;
	private List<String> obisCode;
	private Object[] data;
	private int status;
	private List<String> error;
	private String message;
	private String outputResponse;
	private List<FirmwareData> firmwareList;
	private List<DeviceTrackingIDs> trackingIds;
	private String trackingId;
	private String reason;
	private String batchId;
	
	public List<FirmwareData> getFirmwareList() {
		return firmwareList;
	}
	public void setFirmwareList(List<FirmwareData> firmwareList) {
		this.firmwareList = firmwareList;
	}
	public String getObisCmd() {
		return obisCmd;
	}
	public void setObisCmd(String obisCmd) {
		this.obisCmd = obisCmd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<String> getObisCode() {
		return obisCode;
	}
	public void setObisCode(List<String> obisCode) {
		this.obisCode = obisCode;
	}
	public Object[] getData() {
		return data;
	}
	public void setData(Object[] data) {
		this.data = data;
	}
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOutputResponse() {
		return outputResponse;
	}
	public void setOutputResponse(String outputResponse) {
		this.outputResponse = outputResponse;
	}
	public List<DeviceTrackingIDs> getTrackingIds() {
		return trackingIds;
	}
	public void setTrackingIds(List<DeviceTrackingIDs> trackingIds) {
		this.trackingIds = trackingIds;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	@Override
	public String toString() {
		return "MeterResponse [obisCmd=" + obisCmd + ", obisCode=" + obisCode + ", data=" + Arrays.toString(data)
				+ ", status=" + status + ", error=" + error + ", message=" + message + ", outputResponse="
				+ outputResponse + ", firmwareList=" + firmwareList + ", trackingIds=" + trackingIds + ", trackingId="
				+ trackingId + ", reason=" + reason + ", batchId=" + batchId + "]";
	}
	
}
