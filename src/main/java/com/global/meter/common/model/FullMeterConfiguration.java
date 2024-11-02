package com.global.meter.common.model;

import java.util.Map;

public class FullMeterConfiguration {

	private Devices devicesInfo;
	private String commandsName;
	private Map<String, String> configVals;
	private String activateActivityCalDatetime;

	public Devices getDevicesInfo() {
		return devicesInfo;
	}

	public void setDevicesInfo(Devices devicesInfo) {
		this.devicesInfo = devicesInfo;
	}

	public String getCommandsName() {
		return commandsName;
	}

	public void setCommandsName(String commandsName) {
		this.commandsName = commandsName;
	}

	public Map<String, String> getConfigVals() {
		return configVals;
	}

	public void setConfigVals(Map<String, String> configVals) {
		this.configVals = configVals;
	}

	public String getActivateActivityCalDatetime() {
		return activateActivityCalDatetime;
	}

	public void setActivateActivityCalDatetime(String activateActivityCalDatetime) {
		this.activateActivityCalDatetime = activateActivityCalDatetime;
	}

}
