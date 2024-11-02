package com.global.meter.v3.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum OnDemandCommands {

	INSTANTANEOUS_READ("InstantaneousRead"),
	DAILY_LOAD_PROFILE("DailyLoadProfile"),
	DELTA_LOAD_PROFILE("DeltaLoadProfile"),
	BILLING_DATA("BillingData"),
	POWER_RELATED_EVENTS("PowerRelatedEvents"),
	VOLTAGE_RELATED_EVENTS("VoltageRelatedEvents"),
	TRANSACTION_RELATED_EVENTS("TransactionRelatedEvents"),
	NON_ROLLOVER_RELATED_EVENTS("NonRolloverRelatedEvents"),
	CURRENT_RELATED_EVENTS("CurrentRelatedEvents"),
	OTHER_RELATED_EVENTS("OtherRelatedEvents"),
	CONTROL_RELATED_EVENTS("ControlRelatedEvents"),
	CONNECT("Connect"),
	DISCONNECT("Disconnect"),
	NAME_PLATES("NamePlate");
	
	public String commandName;
	
	private OnDemandCommands(String commandName) {
		this.commandName = commandName;
	}

	public static final Map<Object, String> commands = Stream.of(
			new SimpleEntry<>(OnDemandCommands.INSTANTANEOUS_READ,"1"),
			new SimpleEntry<>(OnDemandCommands.DAILY_LOAD_PROFILE,"2"),
			new SimpleEntry<>(OnDemandCommands.DELTA_LOAD_PROFILE,"3"),
			new SimpleEntry<>(OnDemandCommands.BILLING_DATA,"4"),
			new SimpleEntry<>(OnDemandCommands.POWER_RELATED_EVENTS,"5"),
			new SimpleEntry<>(OnDemandCommands.VOLTAGE_RELATED_EVENTS,"6"),
			new SimpleEntry<>(OnDemandCommands.TRANSACTION_RELATED_EVENTS,"7"),
			new SimpleEntry<>(OnDemandCommands.CURRENT_RELATED_EVENTS,"8"),
			new SimpleEntry<>(OnDemandCommands.OTHER_RELATED_EVENTS,"9"),
			new SimpleEntry<>(OnDemandCommands.CONTROL_RELATED_EVENTS,"10"),
			new SimpleEntry<>(OnDemandCommands.CONNECT,"11"),
			new SimpleEntry<>(OnDemandCommands.DISCONNECT,"12"),
			new SimpleEntry<>(OnDemandCommands.NAME_PLATES,"13")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	
	public static String getCommandId(String cmdName) {
		return commands.containsKey(OnDemandCommands.valueOf(cmdName))
				? (String) commands.get(OnDemandCommands.valueOf(cmdName)): "-1";
	}
	
	public static OnDemandCommands getCommandName(String commandName) {
		return Arrays.stream(values()).filter(val -> val.commandName.equals(commandName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid ConfigCommand: " + commandName));
	}
	
	public synchronized static boolean contains(String commandName) {

	    try {
			for (OnDemandCommands c : OnDemandCommands.values()) {
			    if (c.commandName.equals(commandName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
	public static void main(String args[]) {
		
	}
	
	
}
