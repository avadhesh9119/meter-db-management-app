package com.global.meter.inventive.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SchedulerDataSet {
	DAY("Day"),
	SEC("Second"),
	MIN("Minute"),
	HOUR("Hour"),
	EVERY("Every"),
	FIXED("Fixed"),
	FIXED_AT("FixedAt"),
	RANGE("Range"),
	RANGE_AT("RangeAt");
	
	public String schedulerData;
	
	private SchedulerDataSet(String schedulerData) {
		this.schedulerData = schedulerData;
	}
	
	
	public static final Map<Object, String> property = Stream.of(
			new SimpleEntry<>(SchedulerDataSet.DAY.schedulerData,"day"),
			new SimpleEntry<>(SchedulerDataSet.SEC.schedulerData,"second"),
			new SimpleEntry<>(SchedulerDataSet.MIN.schedulerData,"minute"),
			new SimpleEntry<>(SchedulerDataSet.HOUR.schedulerData,"hour"),
			new SimpleEntry<>(SchedulerDataSet.EVERY.schedulerData,"every"),
			new SimpleEntry<>(SchedulerDataSet.FIXED.schedulerData,"fixed"),
			new SimpleEntry<>(SchedulerDataSet.FIXED_AT.schedulerData,"fixedAt"),
			new SimpleEntry<>(SchedulerDataSet.RANGE.schedulerData,"range"),
			new SimpleEntry<>(SchedulerDataSet.RANGE_AT.schedulerData,"rangeAt")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	
	public static String getSchedulerData(String cmdName) {
		return property.containsKey(SchedulerDataSet.valueOf(cmdName))
				? (String) property.get(SchedulerDataSet.valueOf(cmdName)): "Invalid Schedule";
	}
	
	
	public static SchedulerDataSet getSchedulerName(String commandName) {
		return Arrays.stream(values()).filter(val -> val.schedulerData.equals(commandName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid ConfigCommand: " + commandName));
	}
	
	public static String getScheduleByUI(String fieldName) throws IllegalArgumentException {

		if(property.containsKey(fieldName)) {

		return property.get(fieldName);

		}else {

		throw new IllegalArgumentException("Invalid PagingCommand: " + fieldName);

		}

		 

	}
	
	public synchronized static boolean contains(String commandName) {

	    try {
			for (SchedulerDataSet c : SchedulerDataSet.values()) {
			    if (c.schedulerData.equals(commandName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
	public static void main(String args[]) {
		
		System.out.println(SchedulerDataSet.EVERY.schedulerData);
		
		System.out.println(SchedulerDataSet.getSchedulerName(SchedulerDataSet.EVERY.toString()));
		
	}
}