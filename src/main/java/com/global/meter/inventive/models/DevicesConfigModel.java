package com.global.meter.inventive.models;

import com.global.meter.business.model.DevicesConfig;

public class DevicesConfigModel extends DevicesConfig {
	
	private static final long serialVersionUID = -1729743602807650339L;
	
	private String mod_command_name;

	public String getMod_command_name() {
		return mod_command_name;
	}

	public void setMod_command_name(String mod_command_name) {
		this.mod_command_name = mod_command_name;
	}
	
	
}
