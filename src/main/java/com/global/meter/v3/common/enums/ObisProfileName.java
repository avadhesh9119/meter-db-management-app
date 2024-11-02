package com.global.meter.v3.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ObisProfileName {

	DAILY_LP_1Ph("DailyLoadProfile_Single Phase"), 
	DAILY_LP_3Ph("DailyLoadProfile_Three Phase"), 
	DAILY_LP_CT("DailyLoadProfile_CT"),
	DELTA_LP_1Ph("DeltaLoadProfile_Single Phase"), 
	DELTA_LP_3Ph("DeltaLoadProfile_Three Phase"), 
	DELTA_LP_CT("DeltaLoadProfile CT"),
	INSTANT_1Ph("InstantaneousRead_Single Phase"), 
	INSTANT_3Ph("InstantaneousRead_Three Phase"), 
	INSTANT_CT("InstantaneousRead_CT"),
	BILLING_1Ph("BillingData_Single Phase"), 
	BILLING_3Ph("BillingData_Three Phase"), 
	BILLING_CT("BillingData_CT"),
	NAME_PLATE("NamePlate"),
	POWER_EVENTS_1Ph("PowerRelatedEvents_Single Phase"), 
	POWER_EVENTS_3Ph("PowerRelatedEvents_Three Phase"), 
	POWER_EVENTS_CT("PowerRelatedEvents_CT"),
	VOLTAGE_EVENTS_1Ph("VoltageRelatedEvents_Single Phase"), 
	VOLTAGE_EVENTS_3Ph("VoltageRelatedEvents_Three Phase"), 
	VOLTAGE_EVENTS_CT("VoltageRelatedEvents_CT"),
	TRANSACTION_EVENTS_1Ph("TransactionRelatedEvents_Single Phase"), 
	TRANSACTION_EVENTS_3Ph("TransactionRelatedEvents_Three Phase"), 
	TRANSACTION_EVENTS_CT("TransactionRelatedEvents_CT"),
	NON_ROLLOVER_EVENTS_1Ph("Non_RollOverEvents_Single Phase"), 
	NON_ROLLOVER_EVENTS_3Ph("Non_RollOverEvents_Three Phase"), 
	NON_ROLLOVER_EVENTS_CT("Non_RollOverEvents_CT"),
	CURRENT_EVENTS_1Ph("CurrentRelatedEvents_Single Phase"), 
	CURRENT_EVENTS_3Ph("CurrentRelatedEvents_Three Phase"), 
	CURRENT_EVENTS_CT("CurrentRelatedEvents_CT"),
	OTHER_EVENTS_1Ph("OtherRelatedEvents_Single Phase"), 
	OTHER_EVENTS_3Ph("OtherRelatedEvents_Three Phase"), 
	OTHER_EVENTS_CT("OtherRelatedEvents_CT"),
	CONTROL_EVENTS_1Ph("ControlRelatedEvents_Single Phase"), 
	CONTROL_EVENTS_3Ph("ControlRelatedEvents_Three Phase"), 
	CONTROL_EVENTS_CT("ControlRelatedEvents_CT");

	public String commandName;

	private ObisProfileName(String commandName) {
		this.commandName = commandName;
	}

	public static final Map<Object, String> commands = Stream.of(
			new SimpleEntry<>(ObisProfileName.DAILY_LP_1Ph.commandName, "DailyLoadProfile_1Ph"),
			new SimpleEntry<>(ObisProfileName.DAILY_LP_3Ph.commandName, "DailyLoadProfile_3Ph"),
			new SimpleEntry<>(ObisProfileName.DAILY_LP_CT.commandName, "DailyLoadProfile_CT"),
			new SimpleEntry<>(ObisProfileName.DELTA_LP_1Ph.commandName, "DeltaLoadProfile_1Ph"),
			new SimpleEntry<>(ObisProfileName.DELTA_LP_3Ph.commandName, "DeltaLoadProfile_3Ph"),
			new SimpleEntry<>(ObisProfileName.DELTA_LP_CT.commandName, "DeltaLoadProfile_CT"),
			new SimpleEntry<>(ObisProfileName.INSTANT_1Ph.commandName, "InstantaneousRead_1Ph"),
			new SimpleEntry<>(ObisProfileName.INSTANT_3Ph.commandName, "InstantaneousRead_3Ph"),
			new SimpleEntry<>(ObisProfileName.INSTANT_CT.commandName, "InstantaneousRead_CT"),
			new SimpleEntry<>(ObisProfileName.BILLING_1Ph.commandName, "BillingData_1P"),
			new SimpleEntry<>(ObisProfileName.BILLING_3Ph.commandName, "BillingData_3P"),
			new SimpleEntry<>(ObisProfileName.BILLING_CT.commandName, "BillingData_CT"),
			new SimpleEntry<>(ObisProfileName.NAME_PLATE.commandName, "NamePlate"),
			new SimpleEntry<>(ObisProfileName.POWER_EVENTS_1Ph.commandName, "PowerRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.POWER_EVENTS_3Ph.commandName, "PowerRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.POWER_EVENTS_CT.commandName, "PowerRelatedEvents_CT"),
			new SimpleEntry<>(ObisProfileName.VOLTAGE_EVENTS_1Ph.commandName, "VoltageRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.VOLTAGE_EVENTS_3Ph.commandName, "VoltageRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.VOLTAGE_EVENTS_CT.commandName, "VoltageRelatedEvents_CT"),
			new SimpleEntry<>(ObisProfileName.TRANSACTION_EVENTS_1Ph.commandName, "TransactionRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.TRANSACTION_EVENTS_3Ph.commandName, "TransactionRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.TRANSACTION_EVENTS_CT.commandName, "TransactionRelatedEvents_CT"),
			new SimpleEntry<>(ObisProfileName.NON_ROLLOVER_EVENTS_1Ph.commandName, "NonRolloverRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.NON_ROLLOVER_EVENTS_3Ph.commandName, "NonRolloverRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.NON_ROLLOVER_EVENTS_CT.commandName, "NonRolloverRelatedEvents_CT"),
			new SimpleEntry<>(ObisProfileName.CURRENT_EVENTS_1Ph.commandName, "CurrentRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.CURRENT_EVENTS_3Ph.commandName, "CurrentRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.CURRENT_EVENTS_CT.commandName, "CurrentRelatedEvents_CT"),
			new SimpleEntry<>(ObisProfileName.OTHER_EVENTS_1Ph.commandName, "OtherRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.OTHER_EVENTS_3Ph.commandName, "OtherRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.OTHER_EVENTS_CT.commandName, "OtherRelatedEvents_CT"),
			new SimpleEntry<>(ObisProfileName.CONTROL_EVENTS_1Ph.commandName, "ControlRelatedEvents_1Ph"),
			new SimpleEntry<>(ObisProfileName.CONTROL_EVENTS_3Ph.commandName, "ControlRelatedEvents_3Ph"),
			new SimpleEntry<>(ObisProfileName.CONTROL_EVENTS_CT.commandName, "ControlRelatedEvents_CT"))
			.collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

	public static String getCommandId(String cmdName) {
		return commands.containsKey(cmdName)? (String) commands.get(cmdName): "-1";
	}

}
