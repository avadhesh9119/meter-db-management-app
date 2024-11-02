package com.global.meter.inventive.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.global.meter.common.enums.ConfigCommands;


public enum DataSet {
	COMMUNICATION_DATA("CommunicationData"),
	INSTANTANEOUS_READ("InstantaneousRead"),
	DAILY_LOAD_PROFILE("DailyLoadProfile"),
	DELTA_LOAD_PROFILE("DeltaLoadProfile"),
	BILLING_DATA("BillingData"),
	EVENT("EventData");
	
	public String dataSet;
	
	private DataSet(String dataSet) {
		this.dataSet = dataSet;
	}
	
	
	
	public static final Map<Object, String> data = Stream.of(
			new SimpleEntry<>(DataSet.COMMUNICATION_DATA,"1"),
			new SimpleEntry<>(DataSet.INSTANTANEOUS_READ,"2"),
			new SimpleEntry<>(DataSet.DAILY_LOAD_PROFILE,"3"),
			new SimpleEntry<>(DataSet.DELTA_LOAD_PROFILE,"4"),
			new SimpleEntry<>(DataSet.BILLING_DATA,"5"),
			new SimpleEntry<>(DataSet.EVENT,"6")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	
	public static String getDataSetId(String dataSetName) {
		return data.containsKey(ConfigCommands.valueOf(dataSetName))
				? (String) data.get(ConfigCommands.valueOf(dataSetName)): "-1";
	}
	
	public static DataSet getDataSetName(String dataSetName) {
		return Arrays.stream(values()).filter(val -> val.dataSet.equals(dataSetName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid ConfigCommand: " + dataSetName));
	}
	
	public synchronized static boolean contains(String commandName) {

	    try {
			for (ConfigCommands c : ConfigCommands.values()) {
			    if (c.commandName.equals(commandName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
}
