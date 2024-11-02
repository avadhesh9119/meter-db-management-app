package com.global.meter.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum CommandStatus {

	SUCCESS("SUCCESS"),
	IN_PROGRESS("IN_PROGRESS"),
	FAILURE("FAILURE"),
	ADDED("ADDED");
	
	public String status;
	
	private CommandStatus(String status) {
		this.status = status;
	}

	public static final Map<Object, String> stat = Stream.of(
			new SimpleEntry<>(CommandStatus.SUCCESS,"SUCCESS"),
			new SimpleEntry<>(CommandStatus.IN_PROGRESS,"IN_PROGRESS"),
			new SimpleEntry<>(CommandStatus.FAILURE,"FAILURE"),
			new SimpleEntry<>(CommandStatus.ADDED,"ADDED")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	
	public static String getCommandStatusId(String statusName) {
		return stat.containsKey(CommandStatus.valueOf(statusName))
				? (String) stat.get(CommandStatus.valueOf(statusName)): "-1";
	}
	
	public static CommandStatus getStatusName(String statusName) {
		return Arrays.stream(values()).filter(val -> val.status.equals(statusName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid Command Status: " + statusName));
	}
	
	public synchronized static boolean contains(String statusName) {

	    try {
			for (CommandStatus c : CommandStatus.values()) {
			    if (c.status.equalsIgnoreCase(statusName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
}
