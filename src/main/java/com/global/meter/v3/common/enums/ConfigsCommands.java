package com.global.meter.v3.common.enums;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.global.meter.common.enums.ConfigCommands;

public enum ConfigsCommands {

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
	FW_UPDATE("FwUpdate"),
	FULL_DATA_READ("FullData"),
	FULL_PREPAY_DATA_READ("FullPrepayData"),
	FULL_CONFIG_READ("FullConfigs"),
	RELAY_STATUS("RelayStatus"),
	AVERAGE_SIGNAL_STRENGTH("AverageSignalStrength"),
	METER_HEALTH_INDICATOR("MeterHealthIndicator"),
	IPV6_ADDRESS("IPv6Address"),
	IPV4_ADDRESS("IPv4Address"),
	APN("Apn");
	
	public String commandName;
	
	private ConfigsCommands(String commandName) {
		this.commandName = commandName;
	}

	public static final Map<Object, String> commands = Stream.of(
			new SimpleEntry<>(ConfigsCommands.PAYMENT_MODE,"13"),
			new SimpleEntry<>(ConfigsCommands.METERING_MODE,"14"),
			new SimpleEntry<>(ConfigsCommands.LAST_TOKEN_RECHARGE_AMOUNT,"15"),
			new SimpleEntry<>(ConfigsCommands.LAST_TOKEN_RECHARGE_TIME,"16"),
			new SimpleEntry<>(ConfigsCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE,"17"),
			new SimpleEntry<>(ConfigsCommands.CURRENT_BALANCE_AMOUNT,"18"),
			new SimpleEntry<>(ConfigsCommands.CURRENT_BALANCE_TIME,"19"),
			new SimpleEntry<>(ConfigsCommands.LOAD_LIMIT,"20"),
			new SimpleEntry<>(ConfigsCommands.ENABLE_DISABLE_DISCONNECT_CONTROL,"21"),
			new SimpleEntry<>(ConfigsCommands.RTC_ClOCK,"22"),
			new SimpleEntry<>(ConfigsCommands.ACTIVITY_CALENDER,"23"),
			new SimpleEntry<>(ConfigsCommands.DEMAND_INTEGRATION_PERIOD,"24"),
			new SimpleEntry<>(ConfigsCommands.PROFILE_CAPTURE_PERIOD,"25"),
			new SimpleEntry<>(ConfigsCommands.COVER_OPEN,"26"),
			new SimpleEntry<>(ConfigsCommands.MD_RESET,"27"),
			new SimpleEntry<>(ConfigsCommands.BILLING_DATES,"28"),
			new SimpleEntry<>(ConfigCommands.RELAY_STATUS.commandName,"29"),
			new SimpleEntry<>(ConfigCommands.AVERAGE_SIGNAL_STRENGTH,"30"),
			new SimpleEntry<>(ConfigCommands.METER_HEALTH_INDICATOR,"31"),
			new SimpleEntry<>(ConfigCommands.IPV6_ADDRESS,"32"),
			new SimpleEntry<>(ConfigCommands.IPV4_ADDRESS,"33"),
			new SimpleEntry<>(ConfigCommands.APN,"34")
			).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
	
	
	public static String getCommandId(String cmdName) {
		return commands.containsKey(ConfigsCommands.valueOf(cmdName))
				? (String) commands.get(ConfigsCommands.valueOf(cmdName)): "-1";
	}
	
	public static ConfigsCommands getCommandName(String commandName) {
		return Arrays.stream(values()).filter(val -> val.commandName.equals(commandName)).findFirst()
			.orElseThrow(() -> new IllegalArgumentException("Invalid ConfigCommand: " + commandName));
	}
	
	public synchronized static boolean contains(String commandName) {

	    try {
			for (ConfigsCommands c : ConfigsCommands.values()) {
			    if (c.commandName.equals(commandName)) {
			        return true;
			    }
			}
		} catch (Exception e) {
		}
	    return false;
	}
	
	public static void main(String args[]) {
		
		//String s = ConfigsCommands.getCommandId("ActivityCalendar");
		//System.out.println(s);
		System.out.println(ConfigsCommands.ACTIVITY_CALENDER.commandName);
		
		System.out.println(ConfigsCommands.getCommandId(ConfigsCommands.ACTIVITY_CALENDER.toString()));
		
	}
	
	
}
