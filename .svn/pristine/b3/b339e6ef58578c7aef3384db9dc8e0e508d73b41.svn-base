package com.global.meter.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ConfigCommands {

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
	PAYMENT_MODE("PaymentMode"),
	METERING_MODE("MeteringMode"),
	LAST_TOKEN_RECHARGE_AMOUNT("LastTokenRechargeAmount"),
	LAST_TOKEN_RECHARGE_TIME("LastTokenRechargeTime"),
	TOTAL_AMOUNT_AT_LAST_RECHARGE("TotalAmountAtLastRecharge"),
	CURRENT_BALANCE_AMOUNT("CurrentBalanceAmount"),
	CURRENT_BALANCE_TIME("CurrentBalanceTime"),
	LOAD_LIMIT("LoadLimit"),
	ENABLE_DISABLE_DISCONNECT_CONTROL("EnableDisableDisconnectControl"),
	RTC_ClOCK("RTCClock"),
	PING_METER("PingMeter"),
	ACTIVITY_CALENDER("ActivityCalendar"),
	DEMAND_INTEGRATION_PERIOD("DemandIntegrationPeriod"),
	PROFILE_CAPTURE_PERIOD("ProfileCapturePeriod"),
	COVER_OPEN("CoverOpen"),
	MD_RESET("MdReset"),
	INSTANT_IP_PUSH("InstantIPPush"),
	ALERT_IP_PUSH("AlertIPPush"),
	ACTIVITY_SCHEDULE_PUSH("ActivitySchedulePush"),
	BILLING_DATES("BillingDates"),
	NAME_PLATES("NamePlate"),
	FW_UPDATE("FwUpdate"),
	FULL_DATA_READ("FullData"),
	FULL_PREPAY_DATA_READ("FullPrepayData"),
	FULL_CONFIG_READ("FullConfigs"),
	RTC_SYNC("RTCSync"),
	LOAD_CONTROL_MODE("LoadControlMode"),
	ACTIVITY_CALENDAR_TOD("ActivityCalendar/TOD"),
	PUSH_SETUP_DURATION("PushSetupDuration"),
	RELAY_STATUS("RelayStatus"),
	AVERAGE_SIGNAL_STRENGTH("AverageSignalStrength"),
	METER_HEALTH_INDICATOR("MeterHealthIndicator"),
	IPV6_ADDRESS("IPv6Address"),
	IPV4_ADDRESS("IPv4Address"),
	APN("Apn");
	
	public String commandName;
	
	private ConfigCommands(String commandName) {
		this.commandName = commandName;
	}

	public static final Map<Object, String> commands = Stream.of(
			new SimpleEntry<>(ConfigCommands.PAYMENT_MODE.commandName,"13"),
			new SimpleEntry<>(ConfigCommands.METERING_MODE.commandName,"14"),
			new SimpleEntry<>(ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName,"15"),
			new SimpleEntry<>(ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName,"16"),
			new SimpleEntry<>(ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName,"17"),
			new SimpleEntry<>(ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName,"18"),
			new SimpleEntry<>(ConfigCommands.CURRENT_BALANCE_TIME.commandName,"19"),
			new SimpleEntry<>(ConfigCommands.LOAD_LIMIT.commandName,"20"),
			new SimpleEntry<>(ConfigCommands.ENABLE_DISABLE_DISCONNECT_CONTROL.commandName,"21"),
			new SimpleEntry<>(ConfigCommands.RTC_ClOCK.commandName,"22"),
			new SimpleEntry<>(ConfigCommands.ACTIVITY_CALENDER.commandName,"23"),
			new SimpleEntry<>(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName,"24"),
			new SimpleEntry<>(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName,"25"),
			new SimpleEntry<>(ConfigCommands.COVER_OPEN.commandName,"26"),
			new SimpleEntry<>(ConfigCommands.MD_RESET.commandName,"27"),
			new SimpleEntry<>(ConfigCommands.BILLING_DATES.commandName,"28"),
			new SimpleEntry<>(ConfigCommands.RELAY_STATUS.commandName,"29"),
			new SimpleEntry<>(ConfigCommands.AVERAGE_SIGNAL_STRENGTH,"30"),
			new SimpleEntry<>(ConfigCommands.METER_HEALTH_INDICATOR,"31"),
			new SimpleEntry<>(ConfigCommands.IPV6_ADDRESS,"32"),
			new SimpleEntry<>(ConfigCommands.IPV4_ADDRESS,"33"),
			new SimpleEntry<>(ConfigCommands.APN,"34")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	/*
	 * This map is used to hold key and value of meter configuration & commands for HES UI
	 */
	public static final Map<Object, String> uiCommandName = Stream.of(
			new SimpleEntry<>(ConfigCommands.INSTANTANEOUS_READ.commandName,ConfigCommands.INSTANTANEOUS_READ.commandName),
			new SimpleEntry<>(ConfigCommands.DAILY_LOAD_PROFILE.commandName,ConfigCommands.DAILY_LOAD_PROFILE.commandName),
			new SimpleEntry<>(ConfigCommands.DELTA_LOAD_PROFILE.commandName,ConfigCommands.DELTA_LOAD_PROFILE.commandName),
			new SimpleEntry<>(ConfigCommands.BILLING_DATA.commandName,ConfigCommands.BILLING_DATA.commandName),
			new SimpleEntry<>(ConfigCommands.POWER_RELATED_EVENTS.commandName,ConfigCommands.POWER_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.VOLTAGE_RELATED_EVENTS.commandName,ConfigCommands.VOLTAGE_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.TRANSACTION_RELATED_EVENTS.commandName,ConfigCommands.TRANSACTION_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.CURRENT_RELATED_EVENTS.commandName,ConfigCommands.CURRENT_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.OTHER_RELATED_EVENTS.commandName,ConfigCommands.OTHER_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.CONTROL_RELATED_EVENTS.commandName,ConfigCommands.CONTROL_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.CONNECT.commandName,ConfigCommands.CONNECT.commandName),
			new SimpleEntry<>(ConfigCommands.DISCONNECT.commandName,ConfigCommands.DISCONNECT.commandName),
			new SimpleEntry<>(ConfigCommands.PAYMENT_MODE.commandName,ConfigCommands.PAYMENT_MODE.commandName),
			new SimpleEntry<>(ConfigCommands.METERING_MODE.commandName,ConfigCommands.METERING_MODE.commandName),
			new SimpleEntry<>(ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName,ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName),
			new SimpleEntry<>(ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName,ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName),
			new SimpleEntry<>(ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName,ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName),
			new SimpleEntry<>(ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName,ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName),
			new SimpleEntry<>(ConfigCommands.CURRENT_BALANCE_TIME.commandName,ConfigCommands.CURRENT_BALANCE_TIME.commandName),
			new SimpleEntry<>(ConfigCommands.LOAD_LIMIT.commandName,ConfigCommands.LOAD_LIMIT.commandName),
			new SimpleEntry<>(ConfigCommands.ENABLE_DISABLE_DISCONNECT_CONTROL.commandName,ConfigCommands.LOAD_CONTROL_MODE.commandName),
			new SimpleEntry<>(ConfigCommands.RTC_ClOCK.commandName,ConfigCommands.RTC_SYNC.commandName),
			new SimpleEntry<>(ConfigCommands.ACTIVITY_CALENDER.commandName,ConfigCommands.ACTIVITY_CALENDAR_TOD.commandName),
			new SimpleEntry<>(ConfigCommands.ACTIVITY_CALENDAR_TOD.commandName,ConfigCommands.ACTIVITY_CALENDAR_TOD.commandName),
			new SimpleEntry<>(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName,ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName),
			new SimpleEntry<>(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName,ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName),
			new SimpleEntry<>(ConfigCommands.COVER_OPEN.commandName,ConfigCommands.COVER_OPEN.commandName),
			new SimpleEntry<>(ConfigCommands.MD_RESET.commandName,ConfigCommands.MD_RESET.commandName),
			new SimpleEntry<>(ConfigCommands.BILLING_DATES.commandName,ConfigCommands.BILLING_DATES.commandName),
			new SimpleEntry<>(ConfigCommands.ACTIVITY_SCHEDULE_PUSH.commandName,ConfigCommands.PUSH_SETUP_DURATION.commandName),
			new SimpleEntry<>(ConfigCommands.ALERT_IP_PUSH.commandName,ConfigCommands.ALERT_IP_PUSH.commandName),
			new SimpleEntry<>(ConfigCommands.INSTANT_IP_PUSH.commandName,ConfigCommands.INSTANT_IP_PUSH.commandName),
			new SimpleEntry<>(ConfigCommands.PING_METER.commandName,ConfigCommands.PING_METER.commandName),
			new SimpleEntry<>(ConfigCommands.NAME_PLATES.commandName,ConfigCommands.NAME_PLATES.commandName),
			new SimpleEntry<>(ConfigCommands.NON_ROLLOVER_RELATED_EVENTS.commandName,ConfigCommands.NON_ROLLOVER_RELATED_EVENTS.commandName),
			new SimpleEntry<>(ConfigCommands.FULL_DATA_READ.commandName,ConfigCommands.FULL_DATA_READ.commandName),
			new SimpleEntry<>(ConfigCommands.FULL_CONFIG_READ.commandName,ConfigCommands.FULL_CONFIG_READ.commandName),
			new SimpleEntry<>(ConfigCommands.FULL_PREPAY_DATA_READ.commandName,ConfigCommands.FULL_PREPAY_DATA_READ.commandName),
			new SimpleEntry<>(ConfigCommands.RELAY_STATUS.commandName,ConfigCommands.RELAY_STATUS.commandName),
			new SimpleEntry<>(ConfigCommands.IPV6_ADDRESS.commandName,ConfigCommands.IPV6_ADDRESS.commandName),
			new SimpleEntry<>(ConfigCommands.APN.commandName,ConfigCommands.APN.commandName)
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	/*
	 * GetUiCommandName is used to get command name of
	 * meter configuration & commands for HES UI
	 */
	public static String getUiCommandName(String cmdName) {
		return uiCommandName.containsKey(cmdName)
				? (String) uiCommandName.get(cmdName): "Invalid "+cmdName;
	}
	
	public static String getCommandId(String cmdName) {
		return commands.containsKey(ConfigCommands.valueOf(cmdName))
				? (String) commands.get(ConfigCommands.valueOf(cmdName)): "-1";
	}
	
	public static ConfigCommands getCommandName(String commandName) {
		return Arrays.stream(values()).filter(val -> val.commandName.equals(commandName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid ConfigCommand: " + commandName));
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
	
	public static void main(String args[]) {
		
		//String s = ConfigCommands.getCommandId("ActivityCalendar");
		//System.out.println(s);
		System.out.println(ConfigCommands.ACTIVITY_CALENDER.commandName);
		
		System.out.println(ConfigCommands.getCommandId(ConfigCommands.ACTIVITY_CALENDER.toString()));
		
	}
	
	
}
