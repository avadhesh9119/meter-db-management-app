package com.global.meter.utils;

public class PropertyConstants {
	public static String getUiNameValue(String keyName) {
		String fieldName = "";

		if ("command.meter.retry".equalsIgnoreCase(keyName)) {
			fieldName = PropertyName.METER_RETRY;
		} else if ("command.firmware.upgrade.retry".equalsIgnoreCase(keyName)) {
			fieldName = PropertyName.FIRMWARE_RETRY;
		} else if ("command.meter.configRetry".equalsIgnoreCase(keyName)) {
			fieldName = PropertyName.CONFIG_RETRY;
		}

		return fieldName;
	}
	
	public static class PropertyName{
		public static final String METER_RETRY = "Meter Retry";
		public static final String FIRMWARE_RETRY = "Firmware Retry";
		public static final String CONFIG_RETRY = "Config Retry";

		public static final String ENABLE = "Y";
		public static final String DISABLE = "N";
		public static final String PROPERTY_UPDATE_KEY = "meter.applicationProperties.update";
		
	}

}
