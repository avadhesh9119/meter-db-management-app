package com.global.meter.v3.inventive.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Nitin Sethi
 *
 */
public class SingleConnectionRes implements Serializable {

	private static final long serialVersionUID = 7373572130390682387L;

	private String message;
	private String status;
	private List<SingleConnectionCommandLogs> commandList;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SingleConnectionCommandLogs> getCommandList() {
		if(commandList == null) {
			commandList = new ArrayList<SingleConnectionCommandLogs>();
		}
		return commandList;
	}

	public void setCommandList(List<SingleConnectionCommandLogs> commandList) {
		this.commandList = commandList;
	}

}

