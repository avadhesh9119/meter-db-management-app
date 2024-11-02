package com.global.meter.inventive.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_NULL)
public class CommonResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3212327225693380122L;

	private String status;
	private boolean isError = false;
	private List<ErrorData> errorMessage;
	private int code = 200;
	private String message;
	private Object data;
	private String traceId;
	private String batchId;
	private String commandType;
	private Set<String> configCmdVals;
	private String commandDateTime;
	private String iAmId;
	private String autoId;
	private String mappingId;
	private String extBatchId;
	private Object commandStatus;
	private String hesId;
	
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public List<ErrorData> getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(List<ErrorData> errorMessage) {
		if(errorMessage == null) {
			errorMessage = new ArrayList<>();
		}
		
		this.errorMessage = errorMessage;
	}
	public void addErrorMessage(ErrorData errorData) {
		if(errorMessage == null) {
			errorMessage = new ArrayList<>();
		}
		this.getErrorMessage().add(errorData);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getCommandType() {
		return commandType;
	}
	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}
	public Set<String> getConfigCmdVals() {
		return configCmdVals;
	}
	public void setConfigCmdVals(Set<String> configCmdVals) {
		this.configCmdVals = configCmdVals;
	}
	public String getCommandDateTime() {
		return commandDateTime;
	}
	public void setCommandDateTime(String commandDateTime) {
		this.commandDateTime = commandDateTime;
	}
	public String getiAmId() {
		return iAmId;
	}
	public void setiAmId(String iAmId) {
		this.iAmId = iAmId;
	}
	public String getAutoId() {
		return autoId;
	}
	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}
	public String getMappingId() {
		return mappingId;
	}
	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}
	public String getExtBatchId() {
		return extBatchId;
	}
	public void setExtBatchId(String extBatchId) {
		this.extBatchId = extBatchId;
	}
	public Object getCommandStatus() {
		return commandStatus;
	}
	public void setCommandStatus(Object commandStatus) {
		this.commandStatus = commandStatus;
	}
	public String getHesId() {
		return hesId;
	}
	public void setHesId(String hesId) {
		this.hesId = hesId;
	}
	
}
