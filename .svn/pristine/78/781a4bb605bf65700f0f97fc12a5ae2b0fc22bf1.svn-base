package com.global.meter.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EventCommands {

	OVER_VOLTAGE("Over Voltage"),
	LOW_VOLTAGE("Low Voltage"),
	OVER_CURRENT_IN_ANY_PHASE("Over current in any phase"),
	EARTH_LOADING("Earth Loading"),
	INFLUENCE_OF_PARMANENT_MAGNET("Influence of permanent magnet or AC/DC Electromagnet"),
	NATURAL_DISTURBANCE("Neutral disturbance - HF, DC or Alternate Method"),
	METER_COVER_OPEN("Meter cover open"),
	METER_LOAD_CONNECT_DISCONNECT("Meter load disconnected/Meter load connected"),
	LAST_GASP_OCCURRENCE("Last Gasp - Occurrence"),
	FIRST_BREATH_RESTORATION("First Breath - Restoration"),
	INCREMENT_IN_BILLING_COUNTER("Increment in Billing counter (Manual/MRI reset)");
	
	public String commandName;

	private EventCommands(String commandName) {
		this.commandName = commandName;
	}

	public static final Map<Object, String> commands = Stream.of(
			new SimpleEntry<>(EventCommands.OVER_VOLTAGE,"1"),
			new SimpleEntry<>(EventCommands.LOW_VOLTAGE,"2"),
			new SimpleEntry<>(EventCommands.OVER_CURRENT_IN_ANY_PHASE,"3"),
			new SimpleEntry<>(EventCommands.EARTH_LOADING,"4"),
			new SimpleEntry<>(EventCommands.INFLUENCE_OF_PARMANENT_MAGNET,"5"),
			new SimpleEntry<>(EventCommands.NATURAL_DISTURBANCE,"6"),
			new SimpleEntry<>(EventCommands.METER_COVER_OPEN,"7"),
			new SimpleEntry<>(EventCommands.METER_LOAD_CONNECT_DISCONNECT,"8"),
			new SimpleEntry<>(EventCommands.LAST_GASP_OCCURRENCE,"9"),
			new SimpleEntry<>(EventCommands.FIRST_BREATH_RESTORATION,"10"),
			new SimpleEntry<>(EventCommands.INCREMENT_IN_BILLING_COUNTER,"11")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	
	public static String getCommandId(String cmdName) {
		return commands.containsKey(EventCommands.valueOf(cmdName))
				? (String) commands.get(EventCommands.valueOf(cmdName)): "-1";
	}
	
	public static EventCommands getCommandName(String commandName) {
		return Arrays.stream(values()).filter(val -> val.commandName.equals(commandName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid ConfigCommand: " + commandName));
	}
	
	public synchronized static boolean contains(String commandName) {

	    try {
			for (EventCommands c : EventCommands.values()) {
			    if (c.commandName.equals(commandName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
}
