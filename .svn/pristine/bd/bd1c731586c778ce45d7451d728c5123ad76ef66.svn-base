package com.global.meter.inventive.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.global.meter.utils.PropertyConstants;

public enum PropertyDataSet {
	
	METER_RETRY(PropertyConstants.PropertyName.METER_RETRY),
	FIRMWARE_RETRY(PropertyConstants.PropertyName.FIRMWARE_RETRY),
	CONFIG_RETRY(PropertyConstants.PropertyName.CONFIG_RETRY);

	public String propertyKey;

	private PropertyDataSet(String propertyKey) {
		this.propertyKey = propertyKey;
	}
	
	public static final Map<Object, String> Property = Stream.of(
			new SimpleEntry<>(PropertyDataSet.METER_RETRY,"command.meter.retry"),
			new SimpleEntry<>(PropertyDataSet.FIRMWARE_RETRY,"command.firmware.upgrade.retry"),
			new SimpleEntry<>(PropertyDataSet.CONFIG_RETRY,"command.meter.configRetry")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
		
	
	public synchronized static boolean contains(String commandName) {

	    try {
			for (PropertyDataSet c : PropertyDataSet.values()) {
			    if (c.propertyKey.equals(commandName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
	
	public static final Map<Object, String> PropertyName = Stream.of(
			new SimpleEntry<>(PropertyDataSet.METER_RETRY.propertyKey,"command.meter.retry"),
			new SimpleEntry<>(PropertyDataSet.FIRMWARE_RETRY.propertyKey,"command.firmware.upgrade.retry"),
			new SimpleEntry<>(PropertyDataSet.CONFIG_RETRY.propertyKey,"command.meter.configRetry")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	

	
	public static String getPropertyKeyByUiName(String fieldName) throws IllegalArgumentException {

		if(PropertyName.containsKey(fieldName)) {

		return PropertyName.get(fieldName);

		}else {

		throw new IllegalArgumentException("Invalid Property Name: " + fieldName);

		}

	}

}
