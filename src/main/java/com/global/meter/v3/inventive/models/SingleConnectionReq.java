package com.global.meter.v3.inventive.models;

import java.io.Serializable;
import java.util.List;

import com.global.meter.common.model.Devices;

/**
 * 
 * @author Nitin Sethi
 *
 */
public class SingleConnectionReq implements Serializable {

	private static final long serialVersionUID = 7373572130390682387L;

	private Devices devicesInfo;
	private String write;
	private List<SingleConnectionCommandLogs> commandList;

	public Devices getDevicesInfo() {
		return devicesInfo;
	}

	public void setDevicesInfo(Devices devicesInfo) {
		this.devicesInfo = devicesInfo;
	}

	public String getWrite() {
		return write;
	}

	public void setWrite(String write) {
		this.write = write;
	}

	public List<SingleConnectionCommandLogs> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<SingleConnectionCommandLogs> commandList) {
		this.commandList = commandList;
	}

	@Override
	public String toString() {
		return "SingleConnectionReq [devicesInfo=" + devicesInfo + ", write=" + write + ", commandList=" + commandList
				+ "]";
	}
	
	

}

