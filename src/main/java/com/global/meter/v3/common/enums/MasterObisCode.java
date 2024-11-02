package com.global.meter.v3.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MasterObisCode {

	NAME_PLATE_OBIS("0.0.94.91.10.255"), 
	DELTA_LOAD_OBIS("1.0.99.1.0.255"), 
	INSTANT_DATA_OBIS("1.0.94.91.0.255"),
	BILLING_DATA_OBIS("1.0.98.1.0.255"), 
	DAILY_LOAD_OBIS("1.0.99.2.0.255"), 
	EVENT_DATA_OBIS("0.0.99.98.e.255"),
	VOLTAGE_RELATED_EVENTS_OBIS("0.0.99.98.0.255"), 
	CURRENT_RELATED_EVENTS_OBIS("0.0.99.98.1.255"), 
	POWER_RELATED_EVENTS_OBIS("0.0.99.98.2.255"), 
	TRANSACTION_RELATED_EVENTS_OBIS("0.0.99.98.3.255"), 
	OTHER_RELATED_EVENTS_OBIS("0.0.99.98.4.255"), 
	NON_ROLL_OVER_RELATED_EVENTS_OBIS("0.0.99.98.5.255"), 
	CONTROL_RELATED_EVENTS_OBIS("0.0.99.98.6.255");

	public String commandName;

	private MasterObisCode(String commandName) {
		this.commandName = commandName;
	}

	public static final Map<Object, String> commands = Stream.of(
			new SimpleEntry<>(MasterObisCode.NAME_PLATE_OBIS.commandName, "NamePlate"),
			new SimpleEntry<>(MasterObisCode.DELTA_LOAD_OBIS.commandName, "DeltaLoadProfile"),
			new SimpleEntry<>(MasterObisCode.INSTANT_DATA_OBIS.commandName, "InstantaneousRead"),
			new SimpleEntry<>(MasterObisCode.BILLING_DATA_OBIS.commandName, "BillingData"),
			new SimpleEntry<>(MasterObisCode.DAILY_LOAD_OBIS.commandName, "DailyLoadProfile"),
			new SimpleEntry<>(MasterObisCode.EVENT_DATA_OBIS.commandName, "EventData"),
			new SimpleEntry<>(MasterObisCode.VOLTAGE_RELATED_EVENTS_OBIS.commandName, "VoltageRelatedEvents"),
			new SimpleEntry<>(MasterObisCode.CURRENT_RELATED_EVENTS_OBIS.commandName, "CurrentRelatedEvents"),
			new SimpleEntry<>(MasterObisCode.POWER_RELATED_EVENTS_OBIS.commandName, "PowerRelatedEvents"),
			new SimpleEntry<>(MasterObisCode.TRANSACTION_RELATED_EVENTS_OBIS.commandName, "TransactionRelatedEvents"),
			new SimpleEntry<>(MasterObisCode.OTHER_RELATED_EVENTS_OBIS.commandName, "OtherRelatedEvents"),
			new SimpleEntry<>(MasterObisCode.NON_ROLL_OVER_RELATED_EVENTS_OBIS.commandName, "NonRollOverRelatedEvents"),
			new SimpleEntry<>(MasterObisCode.CONTROL_RELATED_EVENTS_OBIS.commandName, "ControlRelatedEvents"))
			.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

	public static String getCommandName(String cmdName) {
		return commands.containsKey(cmdName)? (String) commands.get(cmdName): "Invalid ObisCode";
	}

}
