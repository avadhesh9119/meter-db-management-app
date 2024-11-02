package com.global.meter.v3.utils;

public class AppPropertyConstants {
	public static String getUiNameValue(String keyName) {
		String fieldName = "";

		if ("server.contextPath".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SERVER_CONTEXTPATH;
		} else if ("server.port".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SERVER_PORT;
		} else if ("spring.data.cassandra.keyspace-name".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CASSANDRA_KEYSPACE_NAME;
		} else if ("spring.data.cassandra.contact-points".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CASSANDRA_CONTACT_POINTS;	
		} else if ("spring.data.cassandra.port".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CASSANDRA_PORT;
		} else if ("spring.data.cassandra.user".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CASSANDRA_USER;
		} else if ("spring.data.cassandra.password".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CASSANDRA_PASSWORD;
		} else if ("spring.data.cassandra.protocol-version".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CASSANDRA_PROTOCOL_VERSION;
		} else if ("spring.datasource.presto.name".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PRESTO_NAME;
		} else if ("spring.datasource.presto.driver-class-name".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PRESTO_DRIVER_CLASS_NAME;
		} else if ("spring.datasource.presto.url".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PRESTO_URL;
		} else if ("spring.datasource.presto.username".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PRESTO_USERNAME;
		} else if ("spring.datasource.presto.testWhileIdle".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PRESTO_TEST_WHILE_IDLE;
		} else if ("spring.datasource.presto.validationQuery".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PRESTO_VALIDATION_QUERY;
		} else if ("schedule.process.metercommcount.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.METER_COMM_COUNT_CRON;
		} else if ("command.meter.retry".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.METER_RETRY;
		} else if ("command.firmware.upgrade.retry".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.FIRMWARE_UPDATE_RETRY;
		} else if ("command.meter.configRetry".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_RETRY;
		} else if ("command.meter.lastLPRead".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.LAST_LP_READ;
		} else if ("meter.dlms.app.address".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.DLMS_APP_ADDRESS;
		} else if ("meter.fullData.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.FULLDATA_READ;
		} else if ("meter.fullPrepayData.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.FULL_PREPAY_READ;
		} else if ("meter.firmware.upgrade".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.FIRMWARE_RETRY;
		} else if ("meter.fullData.config.write".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.FULLDATA_CONFIG_WRITE;
		} else if ("meter.instantaneous.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.INSTANTANEOUS_READ;
		} else if ("meter.dailyLP.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.DAILY_LP_READ;
		} else if ("meter.deltaLP.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.DELTA_LP_READ;
		} else if ("meter.billingData.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.BILLING_DATA_READ;
		} else if ("meter.powerRelatedEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.POWER_REALATED_EVENTS_READ;
		} else if ("meter.voltageRelatedEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.VOLTAGE_REALATED_EVENTS_READ;
		} else if ("meter.transactionRelatedEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.TRANSACTION_RELATED_EVENTS_READ;
		} else if ("meter.currentRelatedEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CURRENT_RELATED_EVENTS_READ;
		} else if ("meter.otherRelatedEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.OTHER_RELATED_EVENTS_READ;
		} else if ("meter.nonRolloverEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.NON_ROLL_OVER_RELATED_EVENTS_READ;
		} else if ("meter.controlRelatedEvents.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONTROL_RELATED_EVENTS_READ;
		} else if ("meter.namePlate.read".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.NAME_PLATE_READ;
		} else if ("meter.connect.write".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONNECT_WRITE;
		} else if ("meter.disconnect.write".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.DISCONNECT_WRITE;
		} else if ("meter.config.enablePaymentMode".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_ENABLE_PAYMENT_MODE;
		} else if ("meter.config.enableMeteringMode".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_ENABLE_METERING_MODE;
		} else if ("meter.config.lastTokenRechargeAmount".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_LAST_TOKEN_RECHARGE_AMOUNT;
		} else if ("meter.config.lastTokenRechargeTime".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_LAST_TOKEN_RECHARGE_TIME;
		} else if ("meter.config.totalAmountAtLastRecharge".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_TOTAL_AMOUNT_AT_LAST_RECHARGE;
		} else if ("meter.config.currentBalanceAmount".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_CURRENT_BALANCE_AMOUNT;
		} else if ("meter.config.currentBalanceTime".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_CURRENT_BALANCE_TIME;
		} else if ("meter.config.loadLimitKw".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_LOAD_LIMIT_KW;
		} else if ("meter.config.enableDisableDisControl".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.ENABLE_DISABLE_DIS_CONTROL;
		} else if ("meter.config.rtcClock".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_RTC_CLOCK;
		} else if ("meter.config.activityCalendar".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_ACTIVITY_CALENDAR;
		} else if ("meter.config.demandIntegrationPeriod".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_DEMAND_INTEGRATION_PERIOD;
		} else if ("meter.config.profileCapturePeriod".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_PROFILE_CAPTURE_PERIOD;
		} else if ("meter.config.coverOpen".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_COVER_OPEN;
		} else if ("meter.config.mdReset".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_MD_RESET;
		} else if ("meter.config.instantIPPush".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_INSTANT_IP_PUSH;
		} else if ("meter.config.alertIPPush".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_ALERT_IP_PUSH;
		} else if ("meter.config.activitySchedulePush".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_ACTIVITY_SCHEDULE_PUSH;
		} else if ("meter.config.billingDates".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.CONFIG_BILLING_DATES;
		} else if ("meter.eventTypeList".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.EVENT_TYPE_LIST;
		} else if ("meter.eventTypeCategoryList".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.EVENT_TYPE_CATEGORY_LIST;
		} else if ("meter.divFactor".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.DIV_FACTOR;
		} else if ("meter.applicationProperty".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.APPLICATION_PROPERTY;
		} else if ("processBatch.deviceCommand.backDays".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PROCESS_BATCH_DEVICE_COMMAND_BACKDAYS;
		} else if ("happyhour.start.time".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.HAPPY_HOUR_START_TIME;
		} else if ("happyhour.end.time".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.HAPPY_HOUR_END_TIME;
		} else if ("schedule.process.deviceConfigBatchLog.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_DEVICE_CONFIG_BATCH_LOG_CRON;
		} else if ("schedule.process.deviceCommandBatchLog.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_DEVICE_COMMAND_BATCH_LOG_CRON;
		} else if ("schedule.process.deviceConfigReadBatchLog.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_DEVICE_CONFIG_READ_BATCH_LOG_CRON;
		} else if ("meter.applicationProperties.update".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.APPLICATION_PROPERTY_UPDATE;
		} else if ("meter.applicationProperties.read.update".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.APPLICATION_PROPERTY_READ_UPDATE;
		} else if ("meter.pushEventsList".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PUSH_EVENTS_LIST;
		}else if ("schedule.process.nonDLMSGasMeterCommandLogs.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_NON_DLMS_GAS_METER_COMMAND_LOG_CRON;
		} else if ("meter.singleConnection.command".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SINGLE_CONNECTION_COMMAND;
		} else if ("singleConnection.dependent.connect.commands".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SINGLE_CONNECTION_DEPENDENT_CONNECT_COMMAND;
		} else if ("singleConnection.dependent.disconnect.commands".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SINGLE_CONNECTION_DEPENDENT_DISCONNECT_COMMAND;
		} else if ("schedule.process.billingData.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_BILLING_DATA_CRON;
		} else if ("meter.process.BillingData.enable".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PROCESS_BILLING_DATA_ENABLE;
		} else if ("processSla.blockLoad.backDays".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PROCESS_SLA_BLOCKLOAD_BACKDAYS;
		} else if ("processSla.blockLoad.RangeTime".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PROCESS_SLA_BLOCKLOAD_RANGETIME;
		} else if ("processSla.dailyLoad.RangeTime".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PROCESS_SLA_DAILYLOAD_RANGETIME;
		} else if ("schedule.process.dailySummary.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_DAILY_SUMMARY_CRON;
		} else if ("schedule.process.dailySummary.enable".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_DAILY_SUMMARY_ENABLE;
		} else if ("schedule.process.communicationSummary.cron".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_COMMUNICATION_SUMMARY_CRON;
		} else if ("schedule.process.communicationSummary.enable".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.SCHEDULE_PROCESS_COMMUNICATION_SUMMARY_ENABLE;
		} else if ("meter.push.daily.billing".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PUSH_DAILY_BILLING;
		} else if ("meter.push.external.enable".equalsIgnoreCase(keyName)) {
			fieldName = AppPropertyName.PUSH_EXTERNAL_ENABLE;
		} 
		
		return fieldName;
	}
	
	public static class AppPropertyName{
		public static final String SERVER_CONTEXTPATH = "Server Contextpath";
		public static final String SERVER_PORT = "Server Port";
		public static final String CASSANDRA_KEYSPACE_NAME = "Cassandra Keyspace Name";
		public static final String CASSANDRA_CONTACT_POINTS = "Cassandra Contact Points";
		public static final String CASSANDRA_PORT = "Cassandra Port";
		public static final String CASSANDRA_USER = "Cassandra user";
		public static final String CASSANDRA_PASSWORD = "Cassandra Password";
		public static final String CASSANDRA_PROTOCOL_VERSION = "Cassandra Protocol Version";
		public static final String PRESTO_NAME = "Presto Name";
		public static final String PRESTO_DRIVER_CLASS_NAME = "Presto Driver class Name";
		public static final String PRESTO_URL = "Presto Url";
		public static final String PRESTO_USERNAME = "Presto Username";
		public static final String PRESTO_TEST_WHILE_IDLE = "Presto Test While Idle";
		public static final String PRESTO_VALIDATION_QUERY = "Presto Validation Query";
		public static final String METER_COMM_COUNT_CRON = "Meter Comm Count Cron";
		public static final String METER_RETRY = "Meter Retry";
		public static final String FIRMWARE_UPDATE_RETRY = "Firmware Update Retry";
		public static final String CONFIG_RETRY = "Config Retry";
		public static final String LAST_LP_READ = "Last Lp Read";
		public static final String DLMS_APP_ADDRESS = "DLMS App Address";
		public static final String FULLDATA_READ = "Full Data Read";
		public static final String FULL_PREPAY_READ = "Full Prepay Read";
		public static final String FIRMWARE_RETRY = "Firmware Upgrade";
		public static final String FULLDATA_CONFIG_WRITE = "Full Data Config Write";
		public static final String INSTANTANEOUS_READ = "Instantaneous Read";
		public static final String DAILY_LP_READ = "Daily Lp Read";
		public static final String DELTA_LP_READ = "Delta Lp Read";
		public static final String BILLING_DATA_READ = "Billing Data Read";
		public static final String POWER_REALATED_EVENTS_READ = "Power Related Events Read";
		public static final String VOLTAGE_REALATED_EVENTS_READ = "Voltage Related Events Read";
		public static final String TRANSACTION_RELATED_EVENTS_READ = "Transaction Related Events Read";
		public static final String CURRENT_RELATED_EVENTS_READ = "Current Related Events Read";
		public static final String OTHER_RELATED_EVENTS_READ = "Other Related Events Read";
		public static final String NON_ROLL_OVER_RELATED_EVENTS_READ = "Non Roll Over Related Events Read";
		public static final String CONTROL_RELATED_EVENTS_READ = "Control Related Events Read";
		public static final String NAME_PLATE_READ = "Name Plate Read";
		public static final String CONNECT_WRITE = "Connect Write";
		public static final String DISCONNECT_WRITE = "Disconnect Write";
		public static final String CONFIG_ENABLE_PAYMENT_MODE = "Config Enable Payment Mode";
		public static final String CONFIG_ENABLE_METERING_MODE = "Config Enable Metering Mode";
		public static final String CONFIG_LAST_TOKEN_RECHARGE_AMOUNT = "Config Last Token Recharge Amount";
		public static final String CONFIG_LAST_TOKEN_RECHARGE_TIME = "Config Last Token Recharge Time";
		public static final String CONFIG_TOTAL_AMOUNT_AT_LAST_RECHARGE = "Config Total Amount At Last Recharge";
		public static final String CONFIG_CURRENT_BALANCE_AMOUNT = "Config Current Balance Amount";
		public static final String CONFIG_CURRENT_BALANCE_TIME = "Config Current Balance Time";
		public static final String CONFIG_LOAD_LIMIT_KW = "Config Load Limit KW";
		public static final String ENABLE_DISABLE_DIS_CONTROL = "Enable Disable Dis Control";
		public static final String CONFIG_RTC_CLOCK = "Config RTC Clock";
		public static final String CONFIG_ACTIVITY_CALENDAR = "Config Activity Calender";
		public static final String CONFIG_DEMAND_INTEGRATION_PERIOD = "Config Demand Integration Period";
		public static final String CONFIG_PROFILE_CAPTURE_PERIOD = "Config Profile Capture Period";
		public static final String CONFIG_COVER_OPEN = "Config Cover Open";
		public static final String CONFIG_MD_RESET = "Config Md Reset";
		public static final String CONFIG_INSTANT_IP_PUSH = "Config instant Ip Push";
		public static final String CONFIG_ALERT_IP_PUSH = "Config Alert Ip Push";
		public static final String CONFIG_ACTIVITY_SCHEDULE_PUSH = "Config Activity Schedule Push";
		public static final String CONFIG_BILLING_DATES = "Config Billing dates";
		public static final String EVENT_TYPE_LIST = "Event Type List";
		public static final String EVENT_TYPE_CATEGORY_LIST = "Event Type Category list";
		public static final String DIV_FACTOR = "Div Factor";
		public static final String APPLICATION_PROPERTY = "Application Property";
		public static final String PROCESS_BATCH_DEVICE_COMMAND_BACKDAYS = "Process Batch Device Command Back Days";
		public static final String HAPPY_HOUR_START_TIME = "Happy Hour Start Time";
		public static final String HAPPY_HOUR_END_TIME = "Happy Hour End Time";
		public static final String SCHEDULE_PROCESS_DEVICE_CONFIG_BATCH_LOG_CRON = "Schedule Process Device Config Batch Log Cron";
		public static final String SCHEDULE_PROCESS_DEVICE_COMMAND_BATCH_LOG_CRON = "Schedule Process Device Command Batch Log Cron";
		public static final String SCHEDULE_PROCESS_DEVICE_CONFIG_READ_BATCH_LOG_CRON = "Schedule Process Device Config Read Batch Log Cron";
		public static final String APPLICATION_PROPERTY_UPDATE = "Application Property Update";
		public static final String APPLICATION_PROPERTY_READ_UPDATE = "Application Property Read Update";
		public static final String PUSH_EVENTS_LIST = "Push Events List";
		public static final String GAS_METER_COMMAND_LOG_BACKDAYS = "Gas Meter Command Log Backdays";
		public static final String SCHEDULE_PROCESS_NON_DLMS_GAS_METER_COMMAND_LOG_CRON = "Schedule Process Non Dlms Gas Meter Command Log cron";
		public static final String SINGLE_CONNECTION_COMMAND = "Single Connection Command";
		public static final String SINGLE_CONNECTION_DEPENDENT_CONNECT_COMMAND = "Single Connection Connect Dependent Command";
		public static final String SINGLE_CONNECTION_DEPENDENT_DISCONNECT_COMMAND = "Single Connection Disconnect Dependent Command";
		public static final String SCHEDULE_PROCESS_BILLING_DATA_CRON = "Schedule Process Billing Data Cron";
		public static final String PROCESS_BILLING_DATA_ENABLE = "Process Billing Data Enable";
		public static final String PROCESS_SLA_BLOCKLOAD_BACKDAYS = "Process Sla Block Load Backdays";
		public static final String PROCESS_SLA_BLOCKLOAD_RANGETIME = "Process Sla Block Load RangeTime";
		public static final String PROCESS_SLA_DAILYLOAD_RANGETIME = "Process Sla Daily Load RangeTime";
		public static final String SCHEDULE_PROCESS_DAILY_SUMMARY_CRON = "Schedule Process Daily Summary Cron";
		public static final String SCHEDULE_PROCESS_DAILY_SUMMARY_ENABLE = "Schedule Process Daily Summary Enable";
		public static final String SCHEDULE_PROCESS_COMMUNICATION_SUMMARY_CRON = "Schedule Process Communication Summary Cron";
		public static final String SCHEDULE_PROCESS_COMMUNICATION_SUMMARY_ENABLE = "Schedule Process Communication Summary Enable";
		public static final String PUSH_DAILY_BILLING = "Push Daily Billing";
		public static final String PUSH_EXTERNAL_ENABLE = "Push External Enable";

		public static final String ENABLE = "Y";
		public static final String DISABLE = "N";
		public static final String PROPERTY_UPDATE_KEY = "meter.applicationProperties.update";
		
	}

}
