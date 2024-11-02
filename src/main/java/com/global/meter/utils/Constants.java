package com.global.meter.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.global.meter.MeterDBAppStarter;

@Component
public class Constants {

	public static class Query {
		public static final String FETCH_DEVICE_SNO = "select device_serial_number from " + MeterDBAppStarter.keyspace
				+ ".devices_info" + " where commissioning_status = 'Up' ";
		public static final String FETCH_ALL_DEVICE_SNO = "select device_serial_number from "
				+ MeterDBAppStarter.keyspace + ".devices_info";
		public static final String COMMANDWISE_FETCH_DEVICE_SNO = "select device_serial_number, cell_id from "
				+ MeterDBAppStarter.keyspace + ".devices_info where (commissioning_status = 'Up' or (commissioning_status = 'Installed' and approved_datetime IS NULL))";
		public static final String OWNER_TABLE = "select * from " + MeterDBAppStarter.keyspace + ".owners";

		public static final String NAME_PLATE_DEVICES = "select device_serial_number from " + MeterDBAppStarter.keyspace
				+ ".devices_info where device_serial_number not in(select device_serial_number  from  "
				+ MeterDBAppStarter.keyspace + ".name_plate) "
				+ "union all select device_serial_number name_plate_dev from  " + MeterDBAppStarter.keyspace
				+ ".name_plate where status Is Null or status = 'INVALID'";
	}

	public static class MeterMake{
		public static final String HPL = "HPL";
		public static final String INISH = "INISH";
		public static final String IHM = "IHM";
	}
	
	public static class Status {
		public static final String BUSY = "BUSY";
		public static final String CONNECTED = "CONNECTED";
		public static final String DISCONNECTED = "DISCONNECTED";
		public static final String COMMISSIONED = "COMMISSIONED";
		public static final String NOT_COMMISSIONED = "NOT COMMISSIONED";
		public static final String INVALID = "INVALID";
		public static final String VALID = "VALID";
		public static final String UP = "Up";
		public static final String DOWN = "Down";
		public static final String INACTIVE = "Inactive";
		public static final String FAULTY = "Faulty";
		public static final String PARTIAL_SUCCESS = "Partial Succeeded";
		public static final String APPROVED = "APPROVED";
		public static final String CANCELLED = "CANCELLED";
		public static final String REPLACED = "REPLACED";
		public static final String FAULTY_METER_REPLACE = "REPLACEMENT OF FAULTY METER";
	}

	public static class DevMode {
		public static final String PREPAID = "Prepaid";
		public static final String POSTPAID = "Postpaid";
	}
	
	public static class Table {
		public static final String INSTANT_SP = "instantaneous_data_singlephase";
		public static final String LAST_BILLING_SP = "last_billing_data_singlephase";
		public static final String BILLING_SP = "billing_data_singlephase";
		public static final String DEVICES_INFO = "devices_info";
		public static final String DAILY_LP_SP = "daily_load_profile_singlephase";

	}

	public static class CmdConfigStatus {
		public static final String ADDED = "ADDED";
	}
	
	public static class DeviceCommandLogs {
		public static final String COMMAND_NAME = "command_name";
		public static final String COMMAND_RETRY = "command_retry";
		public static final String DEVICE_SERIAL_NUMBER = "device_serial_number";
		public static final String TRACKING_ID = "tracking_id";
		public static final String STATUS = "status";
		public static final String MDAS_DATETIME = "mdas_datetime";
		public static final String DCU_SERIAL_NUMBER = "dcu_serial_number";
		public static final String DT_NAME = "dt_name";
		public static final String FEEDER_NAME = "feeder_name";
		public static final String OWNER_NAME = "owner_name";
		public static final String SUBDEVISION_NAME = "subdevision_name";
		public static final String SUBSTATION_NAME = "substation_name";
		public static final String REASON = "reason";
		public static final String COMMAND_COMPLETION_DATETIME = "command_completion_datetime";
		public static final String TOT_ATTEMPTS = "tot_attempts";
		public static final String CELL_ID = "cell_id";
		public static final String MOD_COMMAND_NAME = "mod_command_name";
		public static final String BATCH_ID = "batch_id";
		public static final String HIER_NAME = "hier_name";
		public static final String HIER_VALUE = "hier_value";
		public static final String DATE_WISE_RANGE = "datewise_range";
		public static final String SOURCE = "source";
		public static final String USER_ID = "user_id";
	}
	
	public static class DeviceConfigLogs {
		public static final String DEVICE_SERIAL_NO = "device_serial_number";
		public static final String TRACKING_ID = "tracking_id";
		public static final String OWNER_NAME = "owner_name";
		public static final String SUBDEVISION_NAME = "subdevision_name";
		public static final String SUBSTATION_NAME = "substation_name";
		public static final String DT_NAME = "dt_name";
		public static final String ACTIVATE_ACTIVITY_CAL_DATETIME = "activate_activity_cal_datetime";
		public static final String CELL_ID = "cell_id";
		public static final String COMMAND_COMPLETION_DATETIME = "command_completion_datetime";
		public static final String COMMAND_NAME = "command_name";
		public static final String MOD_COMMAND_NAME = "mod_command_name";
		public static final String COMMAND_RETRY = "command_retry";
		public static final String COMMAND_STATUS = "command_status";
		public static final String MOD_COMMAND_STATUS = "mod_command_status";
		public static final String DCU_SERIAL_NO = "dcu_serial_no";
		public static final String FEEDER_NAME = "feeder_name";
		public static final String MDAS_DATETIME = "mdas_datetime";
		public static final String REASON = "reason";
		public static final String STATUS = "status";
		public static final String TOT_ATTEMPTS = "tot_attempts";
		public static final String BATCH_ID = "batch_id";
		public static final String HIER_NAME = "hier_name";
		public static final String HIER_VALUE = "hier_value";
		public static final String SOURCE = "source";
		public static final String USER_ID = "user_id";
	}

	public static class DeviceTypes {
		public static final String SINGLE_PHASE = "Single Phase";
		public static final String THREE_PHASE = "Three Phase";
		public static final String LT_METER = "LT Meter";
		public static final String CT_METER = "CT Meter";
		public static final String CT = "CT";
		public static final String HT = "HT";
		public static final String SINGLE_PHASE_ID = "6";
		public static final String THREE_PHASE_ID = "7";
		public static final String CT_METER_ID = "8";
	}

	public static class PhaseVal {
		public static final String _1PH = "1ph";
		public static final String _3PH = "3ph";
		public static final String _CT = "ct";
	}
	
	public static class Types {
		public static final String INSTALLED = "Installed";
		public static final String COMMISSIONING = "Commissioning";
	}

	public static class ObisCode {

		public static class DAILY_LP_1P {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_IMPORT_T1 = "1.0.1.8.1.255";
			public static final String KWH_IMPORT_T2 = "1.0.1.8.2.255";
			public static final String KWH_IMPORT_T3 = "1.0.1.8.3.255";
			public static final String KWH_IMPORT_T4 = "1.0.1.8.4.255";
			public static final String KWH_IMPORT_T5 = "1.0.1.8.5.255";
			public static final String KWH_IMPORT_T6 = "1.0.1.8.6.255";
			public static final String KWH_IMPORT_T7 = "1.0.1.8.7.255";
			public static final String KWH_IMPORT_T8 = "1.0.1.8.8.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_IMPORT_T1 = "1.0.9.8.1.255";
			public static final String KVAH_IMPORT_T2 = "1.0.9.8.2.255";
			public static final String KVAH_IMPORT_T3 = "1.0.9.8.3.255";
			public static final String KVAH_IMPORT_T4 = "1.0.9.8.4.255";
			public static final String KVAH_IMPORT_T5 = "1.0.9.8.5.255";
			public static final String KVAH_IMPORT_T6 = "1.0.9.8.6.255";
			public static final String KVAH_IMPORT_T7 = "1.0.9.8.7.255";
			public static final String KVAH_IMPORT_T8 = "1.0.9.8.8.255";
			public static final String MD_KW = "1.0.1.6.0.255";
			public static final String MD_DATETIME_KW = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_KW_T1 = "1.0.1.6.1.255";
			public static final String MD_DATETIME_KW_T1 = "1.0.1.6.1.255"; // SAME INDEX
			public static final String MD_KW_T2 = "1.0.1.6.2.255";
			public static final String MD_DATETIME_KW_T2 = "1.0.1.6.2.255";// SAME INDEX
			public static final String MD_KW_T3 = "1.0.1.6.3.255";
			public static final String MD_DATETIME_KW_T3 = "1.0.1.6.3.255";// SAME INDEX
			public static final String MD_KW_T4 = "1.0.1.6.4.255";
			public static final String MD_DATETIME_KW_T4 = "1.0.1.6.4.255";// SAME INDEX
			public static final String MD_KW_T5 = "1.0.1.6.5.255";
			public static final String MD_DATETIME_KW_T5 = "1.0.1.6.5.255";// SAME INDEX
			public static final String MD_KW_T6 = "1.0.1.6.6.255";
			public static final String MD_DATETIME_KW_T6 = "1.0.1.6.6.255";// SAME INDEX
			public static final String MD_KW_T7 = "1.0.1.6.7.255";
			public static final String MD_DATETIME_KW_T7 = "1.0.1.6.7.255";// SAME INDEX
			public static final String MD_KW_T8 = "1.0.1.6.8.255";
			public static final String MD_DATETIME_KW_T8 = "1.0.1.6.8.255";// SAME INDEX
			public static final String MD_KVA = "1.0.9.6.0.255";
			public static final String MD_DATETIME_KVA = "1.0.9.6.0.255"; // SAME INDEX
			public static final String MD_KVA_T1 = "1.0.9.6.1.255";
			public static final String MD_DATETIME_KVA_T1 = "1.0.9.6.1.255";// SAME INDEX
			public static final String MD_KVA_T2 = "1.0.9.6.2.255";
			public static final String MD_DATETIME_KVA_T2 = "1.0.9.6.2.255";// SAME INDEX
			public static final String MD_KVA_T3 = "1.0.9.6.3.255";
			public static final String MD_DATETIME_KVA_T3 = "1.0.9.6.3.255";// SAME INDEX
			public static final String MD_KVA_T4 = "1.0.9.6.4.255";
			public static final String MD_DATETIME_KVA_T4 = "1.0.9.6.4.255";// SAME INDEX
			public static final String MD_KVA_T5 = "1.0.9.6.5.255";
			public static final String MD_DATETIME_KVA_T5 = "1.0.9.6.5.255";// SAME INDEX
			public static final String MD_KVA_T6 = "1.0.9.6.6.255";
			public static final String MD_DATETIME_KVA_T6 = "1.0.9.6.6.255";// SAME INDEX
			public static final String MD_KVA_T7 = "1.0.9.6.7.255";
			public static final String MD_DATETIME_KVA_T7 = "1.0.9.6.7.255";// SAME INDEX
			public static final String MD_KVA_T8 = "1.0.9.6.8.255";
			public static final String MD_DATETIME_KVA_T8 = "1.0.9.6.8.255";// SAME INDEX
			public static final String METER_SNO = "0.0.96.1.0.255";
		}

		public static class BILLING_1P {
			public static final String BILL_DATETIME = "0.0.0.1.2.255";
			public static final String AVG_PF = "1.0.13.0.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_IMPORT_T1 = "1.0.1.8.1.255";
			public static final String KWH_IMPORT_T2 = "1.0.1.8.2.255";
			public static final String KWH_IMPORT_T3 = "1.0.1.8.3.255";
			public static final String KWH_IMPORT_T4 = "1.0.1.8.4.255";
			public static final String KWH_IMPORT_T5 = "1.0.1.8.5.255";
			public static final String KWH_IMPORT_T6 = "1.0.1.8.6.255";
			public static final String KWH_IMPORT_T7 = "1.0.1.8.7.255";
			public static final String KWH_IMPORT_T8 = "1.0.1.8.8.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_IMPORT_T1 = "1.0.9.8.1.255";
			public static final String KVAH_IMPORT_T2 = "1.0.9.8.2.255";
			public static final String KVAH_IMPORT_T3 = "1.0.9.8.3.255";
			public static final String KVAH_IMPORT_T4 = "1.0.9.8.4.255";
			public static final String KVAH_IMPORT_T5 = "1.0.9.8.5.255";
			public static final String KVAH_IMPORT_T6 = "1.0.9.8.6.255";
			public static final String KVAH_IMPORT_T7 = "1.0.9.8.7.255";
			public static final String KVAH_IMPORT_T8 = "1.0.9.8.8.255";
			public static final String MD_KW = "1.0.1.6.0.255";
			public static final String MD_DATETIME_KW = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_KVA = "1.0.9.6.0.255";
			public static final String MD_DATETIME_KVA = "1.0.9.6.0.255"; // SAME INDEX
			public static final String MD_KW_T1 = "1.0.1.6.1.255";
			public static final String MD_DATETIME_KW_T1 = "1.0.1.6.1.255"; // SAME INDEX
			public static final String MD_KW_T2 = "1.0.1.6.2.255";
			public static final String MD_DATETIME_KW_T2 = "1.0.1.6.2.255";// SAME INDEX
			public static final String MD_KW_T3 = "1.0.1.6.3.255";
			public static final String MD_DATETIME_KW_T3 = "1.0.1.6.3.255";// SAME INDEX
			public static final String MD_KW_T4 = "1.0.1.6.4.255";
			public static final String MD_DATETIME_KW_T4 = "1.0.1.6.4.255";// SAME INDEX
			public static final String MD_KW_T5 = "1.0.1.6.5.255";
			public static final String MD_DATETIME_KW_T5 = "1.0.1.6.5.255";// SAME INDEX
			public static final String MD_KW_T6 = "1.0.1.6.6.255";
			public static final String MD_DATETIME_KW_T6 = "1.0.1.6.6.255";// SAME INDEX
			public static final String MD_KW_T7 = "1.0.1.6.7.255";
			public static final String MD_DATETIME_KW_T7 = "1.0.1.6.7.255";// SAME INDEX
			public static final String MD_KW_T8 = "1.0.1.6.8.255";
			public static final String MD_DATETIME_KW_T8 = "1.0.1.6.8.255";// SAME INDEX
			public static final String MD_KVA_T1 = "1.0.9.6.1.255";
			public static final String MD_DATETIME_KVA_T1 = "1.0.9.6.1.255";// SAME INDEX
			public static final String MD_KVA_T2 = "1.0.9.6.2.255";
			public static final String MD_DATETIME_KVA_T2 = "1.0.9.6.2.255";// SAME INDEX
			public static final String MD_KVA_T3 = "1.0.9.6.3.255";
			public static final String MD_DATETIME_KVA_T3 = "1.0.9.6.3.255";// SAME INDEX
			public static final String MD_KVA_T4 = "1.0.9.6.4.255";
			public static final String MD_DATETIME_KVA_T4 = "1.0.9.6.4.255";// SAME INDEX
			public static final String MD_KVA_T5 = "1.0.9.6.5.255";
			public static final String MD_DATETIME_KVA_T5 = "1.0.9.6.5.255";// SAME INDEX
			public static final String MD_KVA_T6 = "1.0.9.6.6.255";
			public static final String MD_DATETIME_KVA_T6 = "1.0.9.6.6.255";// SAME INDEX
			public static final String MD_KVA_T7 = "1.0.9.6.7.255";
			public static final String MD_DATETIME_KVA_T7 = "1.0.9.6.7.255";// SAME INDEX
			public static final String MD_KVA_T8 = "1.0.9.6.8.255";
			public static final String MD_DATETIME_KVA_T8 = "1.0.9.6.8.255";// SAME INDEX
			public static final String BILL_PON = "0.0.94.91.13.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			
		}

		public static class INSTANT_1P {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String VOLTAGE = "1.0.12.7.0.255";
			public static final String PHASE_CURRENT = "1.0.11.7.0.255";
			public static final String NUTRAL_CURRENT =  "1.0.91.7.0.255"; 
			public static final String SIGNED_PF = "1.0.13.7.0.255";
			public static final String FREQ = "1.0.14.7.0.255";
			public static final String APPARENT_POWER_KVA =  "1.0.9.7.0.255";
			public static final String ACTIVE_POWER_KW = "1.0.1.7.0.255";
			public static final String CUMULATIVE_IMPORT_KWH = "1.0.1.8.0.255";
			public static final String CUMULATIVE_IMPORT_KVAH = "1.0.9.8.0.255";
			public static final String MAX_DEMAND_KW = "1.0.1.6.0.255";
			public static final String MAX_DEMAND_KW_DATETIME = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MAX_DEMAND_KVA = "1.0.9.6.0.255";
			public static final String MAX_DEMAND_KVA_DATETIME = "1.0.9.6.0.255"; // SAME INDEX
			public static final String CUMULATIVE_POWER_ON_DURATION = "0.0.94.91.14.255";
			public static final String TAMPER_COUNT = "0.0.94.91.0.255";
			public static final String BILLING_COUNT = "0.0.0.1.0.255";
			public static final String PROGRAMMING_COUNT = "0.0.96.2.0.255";
			public static final String CUMULATIVE_EXPORT_KWH ="1.0.2.8.0.255";
			public static final String CUMULATIVE_EXPORT_KVAH =  "1.0.10.8.0.255";
			public static final String LOAD_LIMIT_VAL = "0.0.17.0.0.255";
			public static final String CONNECT_DISCONNECT_STATUS_LOAD_LIMIT = "0.0.96.3.10.255";
		}
		
		public static class INSTANT_3P {

			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String CURR_L1 = "1.0.31.7.0.255";
			public static final String CURR_L2 = "1.0.51.7.0.255";
			public static final String CURR_L3 = "1.0.71.7.0.255";
			public static final String VOL_L1 = "1.0.32.7.0.255";
			public static final String VOL_L2 = "1.0.52.7.0.255";
			public static final String VOL_L3 = "1.0.72.7.0.255";
			public static final String PF_L1 = "1.0.33.7.0.255";
			public static final String PF_L2 = "1.0.53.7.0.255";
			public static final String PF_L3 = "1.0.53.7.0.255";
			public static final String PF_3P = "1.0.13.7.0.255";
			public static final String FREQ = "1.0.14.7.0.255";
			public static final String APP_POW_KVA = "1.0.9.7.0.255";
			public static final String ACTIVE_POWER_KW = "1.0.1.7.0.255";
			public static final String REACTIVE_POWER_KVAR = "1.0.3.7.0.255";
			public static final String NO_POWER_FAILURE = "0.0.96.7.0.255";
			public static final String POW_OFF_DURATION_MINS = "0.0.94.91.8.255";
			public static final String TAMPER_COUNT = "0.0.94.91.0.255";
			public static final String BILLING_COUNT = "0.0.0.1.0.255";
			public static final String PROGRAMMING_COUNT = "0.0.96.2.0.255";
			public static final String BILLING_DATE = "0.0.0.1.2.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KAVH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String MD_ACTIVE_IMPORT_KW = "1.0.1.6.0.255";
			public static final String MD_ACTIVE_IMPORT_DATETIME_KW = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_ACTIVE_IMPORT_KVA = "1.0.9.6.0.255";
			public static final String MD_ACTIVE_IMPORT_DATETIME_KVA = "1.0.9.6.0.255"; // SAME INDEX
			public static final String LOAD_LIMIT_FUNCTION_STATUS = "0.0.96.3.10.255";
			public static final String LOAD_LIMIT_KW = "0.0.17.0.0.255";
			public static final String KVARH_Q1 = "1.0.5.8.0.255";
			public static final String KVARH_Q2 = "1.0.6.8.0.255";
			public static final String KVARH_Q3 = "1.0.7.8.0.255";
			public static final String KVARH_Q4 = "1.0.8.8.0.255";
			public static final String SIGNED_PF_BP = "1.0.73.7.0.255";
			public static final String CUMULATIVE_POWER_ON_DURATION = "0.0.94.91.14.255";
		}
		
		public static class INSTANT_CT {

			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String CURR_L1 = "1.0.31.7.0.255";
			public static final String CURR_L2 = "1.0.51.7.0.255";
			public static final String CURR_L3 = "1.0.71.7.0.255";
			public static final String VOL_L1 = "1.0.32.7.0.255";
			public static final String VOL_L2 = "1.0.52.7.0.255";
			public static final String VOL_L3 = "1.0.72.7.0.255";
			public static final String PF_L1 = "1.0.33.7.0.255";
			public static final String PF_L2 = "1.0.53.7.0.255";
			public static final String PF_L3 = "1.0.53.7.0.255";
			public static final String PF_3P = "1.0.13.7.0.255";
			public static final String FREQ = "1.0.14.7.0.255";
			public static final String APP_POW_KVA = "1.0.9.7.0.255";
			public static final String ACTIVE_POWER_KW = "1.0.1.7.0.255";
			public static final String REACTIVE_POWER_KVAR = "1.0.3.7.0.255";
			public static final String NO_POWER_FAILURE = "0.0.96.7.0.255";
			public static final String POW_OFF_DURATION_MINS = "0.0.94.91.8.255";
			public static final String TAMPER_COUNT = "0.0.94.91.0.255";
			public static final String BILLING_COUNT = "0.0.0.1.0.255";
			public static final String PROGRAMMING_COUNT = "0.0.96.2.0.255";
			public static final String BILLING_DATE = "0.0.0.1.2.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KAVH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String MD_ACTIVE_IMPORT_KW = "1.0.1.6.0.255";
			public static final String MD_ACTIVE_IMPORT_DATETIME_KW = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_ACTIVE_IMPORT_KVA = "1.0.9.6.0.255";
			public static final String MD_ACTIVE_IMPORT_DATETIME_KVA = "1.0.9.6.0.255"; // SAME INDEX
			public static final String LOAD_LIMIT_FUNCTION_STATUS = "0.0.96.3.10.255";
			public static final String LOAD_LIMIT_KW = "0.0.17.0.0.255";
			public static final String KVARH_Q1 = "1.0.5.8.0.255";
			public static final String KVARH_Q2 = "1.0.6.8.0.255";
			public static final String KVARH_Q3 = "1.0.7.8.0.255";
			public static final String KVARH_Q4 = "1.0.8.8.0.255";
			public static final String SIGNED_PF_BP = "1.0.73.7.0.255";
			public static final String MD_EXPORT_W = "1.0.2.6.0.255";
			public static final String MD_EXPORT_DATETIME_W = "1.0.2.6.0.255";
			public static final String MD_EXPORT_VA = "1.0.10.6.0.255";
			public static final String MD_EXPORT_DATETIME_VA = "1.0.10.6.0.255";
			public static final String ANGLE_PHASE_VOLT_AB = "1.0.81.7.1.255";
			public static final String ANGLE_PHASE_VOLT_BC = "1.0.81.7.12.255";
			public static final String ANGLE_PHASE_VOLT_AC = "1.0.81.7.2.255";
			public static final String CUMULATIVE_POWER_ON_DURATION = "0.0.94.91.14.255";
		}

		public static class DAILY_LP_3P {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_IMPORT_T1 = "1.0.1.8.1.255";
			public static final String KWH_IMPORT_T2 = "1.0.1.8.2.255";
			public static final String KWH_IMPORT_T3 = "1.0.1.8.3.255";
			public static final String KWH_IMPORT_T4 = "1.0.1.8.4.255";
			public static final String KWH_IMPORT_T5 = "1.0.1.8.5.255";
			public static final String KWH_IMPORT_T6 = "1.0.1.8.6.255";
			public static final String KWH_IMPORT_T7 = "1.0.1.8.7.255";
			public static final String KWH_IMPORT_T8 = "1.0.1.8.8.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_IMPORT_T1 = "1.0.9.8.1.255";
			public static final String KVAH_IMPORT_T2 = "1.0.9.8.2.255";
			public static final String KVAH_IMPORT_T3 = "1.0.9.8.3.255";
			public static final String KVAH_IMPORT_T4 = "1.0.9.8.4.255";
			public static final String KVAH_IMPORT_T5 = "1.0.9.8.5.255";
			public static final String KVAH_IMPORT_T6 = "1.0.9.8.6.255";
			public static final String KVAH_IMPORT_T7 = "1.0.9.8.7.255";
			public static final String KVAH_IMPORT_T8 = "1.0.9.8.8.255";
			public static final String MD_KW = "1.0.1.6.0.255";
			public static final String MD_KW_DATETIME = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_KW_T1 = "1.0.1.6.1.255";
			public static final String MD_DATETIME_KW_T1 = "1.0.1.6.1.255"; // SAME INDEX
			public static final String MD_KW_T2 = "1.0.1.6.2.255";
			public static final String MD_DATETIME_KW_T2 = "1.0.1.6.2.255";// SAME INDEX
			public static final String MD_KW_T3 = "1.0.1.6.3.255";
			public static final String MD_DATETIME_KW_T3 = "1.0.1.6.3.255";// SAME INDEX
			public static final String MD_KW_T4 = "1.0.1.6.4.255";
			public static final String MD_DATETIME_KW_T4 = "1.0.1.6.4.255";// SAME INDEX
			public static final String MD_KW_T5 = "1.0.1.6.5.255";
			public static final String MD_DATETIME_KW_T5 = "1.0.1.6.5.255";// SAME INDEX
			public static final String MD_KW_T6 = "1.0.1.6.6.255";
			public static final String MD_DATETIME_KW_T6 = "1.0.1.6.6.255";// SAME INDEX
			public static final String MD_KW_T7 = "1.0.1.6.7.255";
			public static final String MD_DATETIME_KW_T7 = "1.0.1.6.7.255";// SAME INDEX
			public static final String MD_KW_T8 = "1.0.1.6.8.255";
			public static final String MD_DATETIME_KW_T8 = "1.0.1.6.8.255";// SAME INDEX
			public static final String MD_KVA = "1.0.9.6.0.255";
			public static final String MD_KVA_DATETIME = "1.0.9.6.0.255"; // SAME INDEX
			public static final String MD_KVA_T1 = "1.0.9.6.1.255";
			public static final String MD_DATETIME_KVA_T1 = "1.0.9.6.1.255";// SAME INDEX
			public static final String MD_KVA_T2 = "1.0.9.6.2.255";
			public static final String MD_DATETIME_KVA_T2 = "1.0.9.6.2.255";// SAME INDEX
			public static final String MD_KVA_T3 = "1.0.9.6.3.255";
			public static final String MD_DATETIME_KVA_T3 = "1.0.9.6.3.255";// SAME INDEX
			public static final String MD_KVA_T4 = "1.0.9.6.4.255";
			public static final String MD_DATETIME_KVA_T4 = "1.0.9.6.4.255";// SAME INDEX
			public static final String MD_KVA_T5 = "1.0.9.6.5.255";
			public static final String MD_DATETIME_KVA_T5 = "1.0.9.6.5.255";// SAME INDEX
			public static final String MD_KVA_T6 = "1.0.9.6.6.255";
			public static final String MD_DATETIME_KVA_T6 = "1.0.9.6.6.255";// SAME INDEX
			public static final String MD_KVA_T7 = "1.0.9.6.7.255";
			public static final String MD_DATETIME_KVA_T7 = "1.0.9.6.7.255";// SAME INDEX
			public static final String MD_KVA_T8 = "1.0.9.6.8.255";
			public static final String MD_DATETIME_KVA_T8 = "1.0.9.6.8.255";// SAME INDEX
			public static final String KVARH_Q1 = "1.0.5.8.0.255";
			public static final String KVARH_Q2 = "1.0.6.8.0.255";
			public static final String KVARH_Q3 = "1.0.7.8.0.255";
			public static final String KVARH_Q4 = "1.0.8.8.0.255";
			public static final String MD_EXPORT_W = "1.0.2.6.0.255";
			public static final String MD_EXPORT_DATETIME_W = "1.0.2.6.0.255";
			public static final String MD_EXPORT_VA = "1.0.10.6.0.255";
			public static final String MD_EXPORT_DATETIME_VA = "1.0.10.6.0.255";
			public static String METER_SNO = "0.0.96.1.0.255";
		}
		
		public static class DELTA_LP_1P {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String KWH_IMPORT = "1.0.1.29.0.255";
			public static final String KWH_EXPORT = "1.0.2.29.0.255";
			public static final String KVAH_IMPORT = "1.0.9.29.0.255";
			public static final String KVAH_EXPORT = "1.0.10.29.0.255";
			public static final String AVG_VOLTAGE = "1.0.12.27.0.255";
			public static final String AVG_CURRENT = "1.0.11.27.0.255";
			public static final String STATUS_BYTE = "0.0.96.10.1.255";
			public static final String AVG_SIGNAL_STRENGTH = "0.1.96.12.5.255";
		}
		
		public static class DELTA_LP_3P {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String CURR_L1 = "1.0.31.27.0.255";
			public static final String CURR_L2 = "1.0.51.27.0.255";
			public static final String CURR_L3 = "1.0.71.27.0.255";
			public static final String VOL_L1 = "1.0.32.27.0.255";
			public static final String VOL_L2 = "1.0.52.27.0.255";
			public static final String VOL_L3 = "1.0.72.27.0.255";
			public static final String KWH_IMPORT = "1.0.1.29.0.255";
			public static final String KWH_EXPORT = "1.0.2.29.0.255";
			public static final String KVAH_IMPORT = "1.0.9.29.0.255";
			public static final String KVAH_EXPORT = "1.0.10.29.0.255";
			public static final String KVARH_Q1 = "1.0.5.29.0.255";
			public static final String KVARH_Q2 = "1.0.6.29.0.255";
			public static final String KVARH_Q3 = "1.0.7.29.0.255";
			public static final String KVARH_Q4 = "1.0.8.29.0.255";
			public static final String STATUS_BYTE = "0.0.96.10.1.255";
			public static final String AVG_SIGNAL_STRENGTH = "0.1.96.12.5.255";
			public static final String POWER_DOWNTIME_IN_MINS = "0.0.94.7.8.255";
			public static final String R_PHASE_ACTIVE_POWER_KW = "1.0.35.27.0.255";
			public static final String Y_PHASE_ACTIVE_POWER_KW = "1.0.55.27.0.255";
			public static final String B_PHASE_ACTIVE_POWER_KW = "1.0.75.27.0.255";
			public static final String LEAD_BLOCK_ENERGY_KVARH = "1.0.8.29.0.255";
			public static final String CUMULATIVE_ENERGY_KWH = "1.0.1.8.0.255";
			public static final String CUMULATIVE_ENERGY_KVAH = "1.0.9.8.0.255";
			public static final String MD_TYPE = "0.128.162.41.128.255";
			public static final String MD_BLOCK_SLIDING_TYPE = "0.128.162.42.128.255";
			public static final String NEUTRAL_CURRENT_3P = "0.128.162.27.128.255";
			public static final String MOBILE_NO = "0.128.162.46.128.255";
			public static final String INSTANT_AVGPF = "0.128.162.26.128.255";
			public static final String MODULE_RTC = "0.128.162.47.128.255";
			public static final String KVA_MAX = "0.128.162.43.128.255";
			public static final String MAX_CURRENT = "0.128.162.44.128.255";
			public static final String MAX_VOLTAGE = "0.128.162.45.128.255";
			
		}

		public static class NAME_PLATE {
			public static final String METER_SERIAL_NUMBER = "0.0.96.1.0.255";
			public static final String DEVICE_ID = "0.0.96.1.2.255";
			public static final String MANUFACTURER_NAME = "0.0.96.1.1.255";
			public static final String FIRMWARE_VERSION = "1.0.0.2.0.255";
			public static final String METER_TYPE = "0.0.94.91.9.255";
			public static final String METER_CATEGORY = "0.0.94.91.11.255";
			public static final String CURRENT_RATING = "0.0.94.91.12.255";
			public static final String YEARS_OF_MANUFACT = "0.0.96.1.4.255";
			public static final String INTERNAL_CT_RATIO_STRING = "1.0.0.4.2.255";
			public static final String INTERNAL_PT_RATIO_STRING = "1.0.0.4.3.255";
		}
		
		public static class BILLING_3P{
			public static final String BILL_DATETIME = "0.0.0.1.2.255";
			public static final String AVG_PF = "1.0.13.0.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_IMPORT_T1 = "1.0.1.8.1.255";
			public static final String KWH_IMPORT_T2 = "1.0.1.8.2.255";
			public static final String KWH_IMPORT_T3 = "1.0.1.8.3.255";
			public static final String KWH_IMPORT_T4 = "1.0.1.8.4.255";
			public static final String KWH_IMPORT_T5 = "1.0.1.8.5.255";
			public static final String KWH_IMPORT_T6 = "1.0.1.8.6.255";
			public static final String KWH_IMPORT_T7 = "1.0.1.8.7.255";
			public static final String KWH_IMPORT_T8 = "1.0.1.8.8.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_IMPORT_T1 = "1.0.9.8.1.255";
			public static final String KVAH_IMPORT_T2 = "1.0.9.8.2.255";
			public static final String KVAH_IMPORT_T3 = "1.0.9.8.3.255";
			public static final String KVAH_IMPORT_T4 = "1.0.9.8.4.255";
			public static final String KVAH_IMPORT_T5 = "1.0.9.8.5.255";
			public static final String KVAH_IMPORT_T6 = "1.0.9.8.6.255";
			public static final String KVAH_IMPORT_T7 = "1.0.9.8.7.255";
			public static final String KVAH_IMPORT_T8 = "1.0.9.8.8.255";
			public static final String MD_KW = "1.0.1.6.0.255";
			public static final String MD_DATETIME_KW = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_KW_T1 = "1.0.1.6.1.255";
			public static final String MD_DATETIME_KW_T1 = "1.0.1.6.1.255"; // SAME INDEX
			public static final String MD_KW_T2 = "1.0.1.6.2.255";
			public static final String MD_DATETIME_KW_T2 = "1.0.1.6.2.255";// SAME INDEX
			public static final String MD_KW_T3 = "1.0.1.6.3.255";
			public static final String MD_DATETIME_KW_T3 = "1.0.1.6.3.255";// SAME INDEX
			public static final String MD_KW_T4 = "1.0.1.6.4.255";
			public static final String MD_DATETIME_KW_T4 = "1.0.1.6.4.255";// SAME INDEX
			public static final String MD_KW_T5 = "1.0.1.6.5.255";
			public static final String MD_DATETIME_KW_T5 = "1.0.1.6.5.255";// SAME INDEX
			public static final String MD_KW_T6 = "1.0.1.6.6.255";
			public static final String MD_DATETIME_KW_T6 = "1.0.1.6.6.255";// SAME INDEX
			public static final String MD_KW_T7 = "1.0.1.6.7.255";
			public static final String MD_DATETIME_KW_T7 = "1.0.1.6.7.255";// SAME INDEX
			public static final String MD_KW_T8 = "1.0.1.6.8.255";
			public static final String MD_DATETIME_KW_T8 = "1.0.1.6.8.255";// SAME INDEX
			public static final String MD_KVA = "1.0.9.6.0.255";
			public static final String MD_DATETIME_KVA = "1.0.9.6.0.255"; // SAME INDEX
			public static final String MD_KVA_T1 = "1.0.9.6.1.255";
			public static final String MD_DATETIME_KVA_T1 = "1.0.9.6.1.255";// SAME INDEX
			public static final String MD_KVA_T2 = "1.0.9.6.2.255";
			public static final String MD_DATETIME_KVA_T2 = "1.0.9.6.2.255";// SAME INDEX
			public static final String MD_KVA_T3 = "1.0.9.6.3.255";
			public static final String MD_DATETIME_KVA_T3 = "1.0.9.6.3.255";// SAME INDEX
			public static final String MD_KVA_T4 = "1.0.9.6.4.255";
			public static final String MD_DATETIME_KVA_T4 = "1.0.9.6.4.255";// SAME INDEX
			public static final String MD_KVA_T5 = "1.0.9.6.5.255";
			public static final String MD_DATETIME_KVA_T5 = "1.0.9.6.5.255";// SAME INDEX
			public static final String MD_KVA_T6 = "1.0.9.6.6.255";
			public static final String MD_DATETIME_KVA_T6 = "1.0.9.6.6.255";// SAME INDEX
			public static final String MD_KVA_T7 = "1.0.9.6.7.255";
			public static final String MD_DATETIME_KVA_T7 = "1.0.9.6.7.255";// SAME INDEX
			public static final String MD_KVA_T8 = "1.0.9.6.8.255";
			public static final String MD_DATETIME_KVA_T8 = "1.0.9.6.8.255";// SAME INDEX
			public static final String BILL_PON = "0.0.94.91.13.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String KVARH_Q1 = "1.0.5.8.0.255";
			public static final String KVARH_Q2 = "1.0.6.8.0.255";
			public static final String KVARH_Q3 = "1.0.7.8.0.255";
			public static final String KVARH_Q4 = "1.0.8.8.0.255";
		}
		
		 public static class EVENTS_1P {
				public static final String RTC_DATETIME = "0.0.1.0.0.255";
				public static final String EVENT_VOLTAGE_RELATED = "0.0.96.11.0.255";
				public static final String CURRENT = "1.0.94.91.14.255";
				public static final String VOLTAGE = "1.0.12.7.0.255";
				public static final String POWER_FACTOR = "1.0.13.7.0.255";
				public static final String KWH_IMPORT = "1.0.1.8.0.255";
				public static final String TAMPER_COUNT = "0.0.94.91.0.255";
			}
		
        public static class EVENTS_3P {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String CURR_L1 = "1.0.31.7.0.255";
			public static final String CURR_L2 = "1.0.51.7.0.255";
			public static final String CURR_L3 = "1.0.71.7.0.255";
			public static final String VOL_L1 = "1.0.32.7.0.255";
			public static final String VOL_L2 = "1.0.52.7.0.255";
			public static final String VOL_L3 = "1.0.72.7.0.255";
			public static final String PF_L1 = "1.0.33.7.0.255";
			public static final String PF_L2 = "1.0.53.7.0.255";
			public static final String PF_L3 = "1.0.73.7.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String TAMPER_COUNT = "0.0.94.91.0.255";
			public static final String NEUTRAL_CURRENT =  "1.0.91.7.0.255";
		}
        
        public static class EVENTS_CT {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String CURR_L1 = "1.0.31.7.0.255";
			public static final String CURR_L2 = "1.0.51.7.0.255";
			public static final String CURR_L3 = "1.0.71.7.0.255";
			public static final String VOL_L1 = "1.0.32.7.0.255";
			public static final String VOL_L2 = "1.0.52.7.0.255";
			public static final String VOL_L3 = "1.0.72.7.0.255";
			public static final String PF_L1 = "1.0.33.7.0.255";
			public static final String PF_L2 = "1.0.53.7.0.255";
			public static final String PF_L3 = "1.0.73.7.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String TAMPER_COUNT = "0.0.94.91.0.255";
			public static final String NEUTRAL_CURRENT =  "1.0.91.7.0.255";
		}
        
        public static class DAILY_LP_CT {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String KVARH_Q1 = "1.0.5.8.0.255";
			public static final String KVARH_Q2 = "1.0.6.8.0.255";
			public static final String KVARH_Q3 = "1.0.7.8.0.255";
			public static final String KVARH_Q4 = "1.0.8.8.0.255";
			public static final String MD_KW = "1.0.1.6.0.255";
			public static final String MD_KW_DATETIME = "1.0.1.6.0.255";
			public static final String MD_KW_EXPORT = "1.0.1.6.0.255";
			public static final String MD_KW_EXPORT_DATETIME = "1.0.1.6.0.255";
			public static final String MD_KVA = "1.0.9.6.0.255";
			public static final String MD_KVA_DATETIME = "1.0.9.6.0.255";
			public static final String NO_SUPPLY = "0.128.162.6.128.255";
			public static final String NO_LOAD = "0.128.162.7.128.255";
		}
        
        public static class DELTA_LP_CT {
			public static final String RTC_DATETIME = "0.0.1.0.0.255";
			public static final String CURR_L1 = "1.0.31.27.0.255";
			public static final String CURR_L2 = "1.0.51.27.0.255";
			public static final String CURR_L3 = "1.0.71.27.0.255";
			public static final String VOL_L1 = "1.0.32.27.0.255";
			public static final String VOL_L2 = "1.0.52.27.0.255";
			public static final String VOL_L3 = "1.0.72.27.0.255";
			public static final String KWH_IMPORT = "1.0.1.29.0.255";
			public static final String KWH_EXPORT = "1.0.2.29.0.255";
			public static final String KVAH_IMPORT = "1.0.9.29.0.255";
			public static final String KVAH_EXPORT = "1.0.10.29.0.255";
			public static final String KVARH_Q1 = "1.0.5.29.0.255";
			public static final String KVARH_Q2 = "1.0.6.29.0.255";
			public static final String KVARH_Q3 = "1.0.7.29.0.255";
			public static final String KVARH_Q4 = "1.0.7.29.0.255";
			public static final String STATUS_BYTE = "0.0.96.10.1.255";
			public static final String AVG_SIGNAL_STRENGTH = "0.1.96.12.5.255";
			public static final String LEAD_BLOCK_ENERGY_kVARH = "1.0.8.29.0.255";
			public static final String AVG_5_NEUTRAL_CURRENT = "1.0.91.27.0.255";
			
		}
        
        public static class BILLING_CT{
			public static final String BILL_DATETIME = "0.0.0.1.2.255";
			public static final String AVG_PF = "1.0.13.0.0.255";
			public static final String KWH_IMPORT = "1.0.1.8.0.255";
			public static final String KWH_IMPORT_T1 = "1.0.1.8.1.255";
			public static final String KWH_IMPORT_T2 = "1.0.1.8.2.255";
			public static final String KWH_IMPORT_T3 = "1.0.1.8.3.255";
			public static final String KWH_IMPORT_T4 = "1.0.1.8.4.255";
			public static final String KWH_IMPORT_T5 = "1.0.1.8.5.255";
			public static final String KWH_IMPORT_T6 = "1.0.1.8.6.255";
			public static final String KWH_IMPORT_T7 = "1.0.1.8.7.255";
			public static final String KWH_IMPORT_T8 = "1.0.1.8.8.255";
			public static final String KVAH_IMPORT = "1.0.9.8.0.255";
			public static final String KVAH_IMPORT_T1 = "1.0.9.8.1.255";
			public static final String KVAH_IMPORT_T2 = "1.0.9.8.2.255";
			public static final String KVAH_IMPORT_T3 = "1.0.9.8.3.255";
			public static final String KVAH_IMPORT_T4 = "1.0.9.8.4.255";
			public static final String KVAH_IMPORT_T5 = "1.0.9.8.5.255";
			public static final String KVAH_IMPORT_T6 = "1.0.9.8.6.255";
			public static final String KVAH_IMPORT_T7 = "1.0.9.8.7.255";
			public static final String KVAH_IMPORT_T8 = "1.0.9.8.8.255";
			public static final String MD_KW = "1.0.1.6.0.255";
			public static final String MD_DATETIME_KW = "1.0.1.6.0.255"; // SAME INDEX
			public static final String MD_KW_T1 = "1.0.1.6.1.255";
			public static final String MD_DATETIME_KW_T1 = "1.0.1.6.1.255"; // SAME INDEX
			public static final String MD_KW_T2 = "1.0.1.6.2.255";
			public static final String MD_DATETIME_KW_T2 = "1.0.1.6.2.255";// SAME INDEX
			public static final String MD_KW_T3 = "1.0.1.6.3.255";
			public static final String MD_DATETIME_KW_T3 = "1.0.1.6.3.255";// SAME INDEX
			public static final String MD_KW_T4 = "1.0.1.6.4.255";
			public static final String MD_DATETIME_KW_T4 = "1.0.1.6.4.255";// SAME INDEX
			public static final String MD_KW_T5 = "1.0.1.6.5.255";
			public static final String MD_DATETIME_KW_T5 = "1.0.1.6.5.255";// SAME INDEX
			public static final String MD_KW_T6 = "1.0.1.6.6.255";
			public static final String MD_DATETIME_KW_T6 = "1.0.1.6.6.255";// SAME INDEX
			public static final String MD_KW_T7 = "1.0.1.6.7.255";
			public static final String MD_DATETIME_KW_T7 = "1.0.1.6.7.255";// SAME INDEX
			public static final String MD_KW_T8 = "1.0.1.6.8.255";
			public static final String MD_DATETIME_KW_T8 = "1.0.1.6.8.255";// SAME INDEX
			public static final String MD_KVA = "1.0.9.6.0.255";
			public static final String MD_DATETIME_KVA = "1.0.9.6.0.255"; // SAME INDEX
			public static final String MD_KVA_T1 = "1.0.9.6.1.255";
			public static final String MD_DATETIME_KVA_T1 = "1.0.9.6.1.255";// SAME INDEX
			public static final String MD_KVA_T2 = "1.0.9.6.2.255";
			public static final String MD_DATETIME_KVA_T2 = "1.0.9.6.2.255";// SAME INDEX
			public static final String MD_KVA_T3 = "1.0.9.6.3.255";
			public static final String MD_DATETIME_KVA_T3 = "1.0.9.6.3.255";// SAME INDEX
			public static final String MD_KVA_T4 = "1.0.9.6.4.255";
			public static final String MD_DATETIME_KVA_T4 = "1.0.9.6.4.255";// SAME INDEX
			public static final String MD_KVA_T5 = "1.0.9.6.5.255";
			public static final String MD_DATETIME_KVA_T5 = "1.0.9.6.5.255";// SAME INDEX
			public static final String MD_KVA_T6 = "1.0.9.6.6.255";
			public static final String MD_DATETIME_KVA_T6 = "1.0.9.6.6.255";// SAME INDEX
			public static final String MD_KVA_T7 = "1.0.9.6.7.255";
			public static final String MD_DATETIME_KVA_T7 = "1.0.9.6.7.255";// SAME INDEX
			public static final String MD_KVA_T8 = "1.0.9.6.8.255";
			public static final String MD_DATETIME_KVA_T8 = "1.0.9.6.8.255";// SAME INDEX
			public static final String BILL_POWER_ON = "0.0.94.91.13.255";
			public static final String KWH_EXPORT = "1.0.2.8.0.255";
			public static final String KVAH_EXPORT = "1.0.10.8.0.255";
			public static final String CUMULATIVE_ENERGY_KVARH_LAG = "1.0.5.8.0.255";
			public static final String KWH_EXPORT_T1 = "1.0.2.8.1.255";
			public static final String KWH_EXPORT_T2 = "1.0.2.8.2.255";
			public static final String KWH_EXPORT_T3 = "1.0.2.8.3.255";
			public static final String KWH_EXPORT_T4 = "1.0.2.8.4.255";
			public static final String KWH_EXPORT_T5 = "1.0.2.8.5.255";
			public static final String KWH_EXPORT_T6 = "1.0.2.8.6.255";
			public static final String KWH_EXPORT_T7 = "1.0.2.8.7.255";
			public static final String KWH_EXPORT_T8 = "1.0.2.8.8.255";
			public static final String KVAH_EXPORT_T1 = "1.0.10.8.1.255";
			public static final String KVAH_EXPORT_T2 = "1.0.10.8.2.255";
			public static final String KVAH_EXPORT_T3 = "1.0.10.8.3.255";
			public static final String KVAH_EXPORT_T4 = "1.0.10.8.4.255";
			public static final String KVAH_EXPORT_T5 = "1.0.10.8.5.255";
			public static final String KVAH_EXPORT_T6 = "1.0.10.8.6.255";
			public static final String KVAH_EXPORT_T7 = "1.0.10.8.7.255";
			public static final String KVAH_EXPORT_T8 = "1.0.10.8.8.255";
			public static final String MD_KW_EXPORT = "1.0.2.6.0.255";
			public static final String MD_DATETIME_KW_EXPORT = "1.0.2.6.0.255";
			public static final String MD_KW_EXPORT_T1 = "1.0.2.6.1.255";
			public static final String MD_DATETIME_KW_EXPORT_T1 = "1.0.2.6.1.255";
			public static final String MD_KW_EXPORT_T2 = "1.0.2.6.2.255";
			public static final String MD_DATETIME_KW_EXPORT_T2 = "1.0.2.6.2.255";
			public static final String MD_KW_EXPORT_T3 = "1.0.2.6.3.255";
			public static final String MD_DATETIME_KW_EXPORT_T3 = "1.0.2.6.3.255";
			public static final String MD_KW_EXPORT_T4 = "1.0.2.6.4.255";
			public static final String MD_DATETIME_KW_EXPORT_T4 = "1.0.2.6.4.255";
			public static final String MD_KW_EXPORT_T5 = "1.0.2.6.5.255";
			public static final String MD_DATETIME_KW_EXPORT_T5 = "1.0.2.6.5.255";
			public static final String MD_KW_EXPORT_T6 = "1.0.2.6.6.255";
			public static final String MD_DATETIME_KW_EXPORT_T6 = "1.0.2.6.6.255";
			public static final String MD_KW_EXPORT_T7 = "1.0.2.6.7.255";
			public static final String MD_DATETIME_KW_EXPORT_T7 = "1.0.2.6.7.255";
			public static final String MD_KW_EXPORT_T8 = "1.0.2.6.8.255";
			public static final String MD_DATETIME_KW_EXPORT_T8 = "1.0.2.6.8.255";
			public static final String MD_KVA_EXPORT = "1.0.10.6.0.255";
			public static final String MD_DATETIME_KVA_EXPORT = "1.0.10.6.0.255";
			public static final String MD_KVA_EXPORT_T1 = "1.0.10.6.1.255";
			public static final String MD_DATETIME_KVA_EXPORT_T1 = "1.0.10.6.1.255";
			public static final String MD_KVA_EXPORT_T2 = "1.0.10.6.2.255";
			public static final String MD_DATETIME_KVA_EXPORT_T2 = "1.0.10.6.2.255";
			public static final String MD_KVA_EXPORT_T3 = "1.0.10.6.3.255";
			public static final String MD_DATETIME_KVA_EXPORT_T3 = "1.0.10.6.3.255";
			public static final String MD_KVA_EXPORT_T4 = "1.0.10.6.4.255";
			public static final String MD_DATETIME_KVA_EXPORT_T4 = "1.0.10.6.4.255";
			public static final String MD_KVA_EXPORT_T5 = "1.0.10.6.5.255";
			public static final String MD_DATETIME_KVA_EXPORT_T5 = "1.0.10.6.5.255";
			public static final String MD_KVA_EXPORT_T6 = "1.0.10.6.6.255";
			public static final String MD_DATETIME_KVA_EXPORT_T6 = "1.0.10.6.6.255";
			public static final String MD_KVA_EXPORT_T7 = "1.0.10.6.7.255";
			public static final String MD_DATETIME_KVA_EXPORT_T7 = "1.0.10.6.7.255";
			public static final String MD_KVA_EXPORT_T8 = "1.0.10.6.8.255";
			public static final String MD_DATETIME_KVA_EXPORT_T8 = "1.0.10.6.8.255";
			public static final String TAMPER_COUNT = "0.0.96.15.128.255";
			public static final String KVARH_Q1 = "1.0.5.8.0.255";
			public static final String KVARH_Q2 = "1.0.6.8.0.255";
			public static final String KVARH_Q3 = "1.0.7.8.0.255";
			public static final String KVARH_Q4 = "1.0.8.8.0.255";
		}
	}

	public final class HierLevelName {
		public static final String SUBDEVISION = "SUBDEVISION";
		public static final String FEEDER = "FEEDER";
		public static final String SUBSTATION = "SUBSTATION";
		public static final String DTMETER = "DT";
		public static final String DCU = "DCU";
		public static final String METER = "METER";
		public static final String ALL = "ALL";
		public static final String MANUFACTURER = "manufacturer_name";

	}
	
	public final class BatchHierLevelName {
		public static final String SUBDIVISION = "Sub-Division";
		public static final String FEEDER = "Feeder";
		public static final String SUBSTATION = "Sub-Station";
		public static final String DTMETER = "DT";
		public static final String DCU = "DCU";
		public static final String METER = "Meter No";
		public static final String ALL = "All";
		public static final String MANUFACTURER = "Manufacture";
		public static final String UTILITY = "Utility";
	}

	public static final String BILL_ALREADY_EXIST_CUURENT_MONTH = "BILL_ALREADY_EXIST_CURRENT_MONTH";
	public static final String ALREADY_EXIST= "Data already exist";
	public static final String SUCCESS = "SUCCESS";
	public static final String UNSUCCESS = "UNSUCCESS";
	public static final String BUSY = "BUSY";
	public static final String FAILURE = "FAILURE";
	public static final String FAILURE_NA = "FAILURE:NO_DATA_AVAILABLE";
	public static final String IN_PROGRESS = "IN_PROGRESS";
	public static final String ADDED = "ADDED";
	public static final String WRONG_DEVICE_TYPE = "Invalid Device type";
	public static final String CONTROL_DEVICE_TYPE = "CT does not support Control related events";
	public static final String WRONG_COMMAND_TYPE = "Invalid Command type";
	public static final String INVALID_STATUS_TYPE = "Invalid Status type";
	public static final String WRONG_COMMAND_SENT = "Wrong Command Sent";
	public static final String MOVED = "MOVED";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String COMMAND_READ = "CMD_READ";
	public static final String COMMAND_WRITE = "CMD_WRITE";
	public static final String CONFIG_READ = "CFG_READ";
	public static final String CONFIG_WRITE = "CFG_WRITE";
	public static final String ON_DEMAND = "ON_DEMAND";

	public static final boolean FLAG_SUCCESS = true;
	public static final boolean FLAG_FAILURE = false;

	public static final String INSTANTANEOUS_READ = "InstantaneousRead";
	public static final String DAILY_LOAD_PROFILE = "DailyLoadProfile";
	public static final String DELTA_LOAD_PROFILE = "DeltaLoadProfile";
	public static final String BILLING_DATA = "BillingData";
	public static final String POWER_RELATED_EVENTS = "PowerRelatedEvents";
	public static final String VOLTAGE_RELATED_EVENTS = "VoltageRelatedEvents";
	public static final String TRANSACTION_RELATED_EVENTS = "TransactionRelatedEvents";
	public static final String CURRENT_RELATED_EVENTS = "CurrentRelatedEvents";
	public static final String OTHER_RELATED_EVENTS = "OtherRelatedEvents";
	public static final String CONTROL_RELATED_EVENTS = "ControlRelatedEvents";
	public static final String CONNECT = "Connect";
	public static final String DISCONNECT = "Disconnect";
	public static final String PAYMENT_MODE = "PaymentMode";
	public static final String METERING_MODE = "MeteringMode";
	public static final String LAST_TOKEN_RECHARGE_AMOUNT = "LastTokenRechargeAmount";
	public static final String LAST_TOKEN_RECHARGE_TIME = "LastTokenRechargeTime";
	public static final String TOTAL_AMOUNT_AT_lAST_RECHARGE = "TotalAmountAtLastRecharge";
	public static final String CURRENT_BALANCE_AMOUNT = "CurrentBalanceAmount";
	public static final String CURRENT_BALANCE_TIME = "CurrentBalanceTime";
	public static final String LOAD_CONTROL_MODE = "LoadControlMode";
	public static final String PUSH_SETUP_DURATION = "PushSetupDuration";
	public static final String ACTIVITY_CALENDER_TOD = "ActivityCalendar/TOD";
	public static final String RTC_SYNC = "RTCSync";

	public static final String POWER_RELATED = "Power Related";
	public static final String VOLTAGE_RELATED = "Voltage Related";
	public static final String NON_ROLLOVER__RELATED = "Non-Rollover Related";
	public static final String TRANSACTION_RELATED = "Transaction Related";
	public static final String CURRENT_RELATED = "Current Related";
	public static final String OTHERS_RELATED = "Others Related";
	public static final String CONTROL_RELATED = "Control Related";
	
	
	public static final String NO_DATA_FOUND = "No data found.";
	public static final String ALL = "All";

	public static final int LIMIT = 2000;

	public static final String nullKey = "NULL";
	
	public static final String BATCH_PREFIX = "B";
	public static final String IMAGE_VERIFICATION_FAILED = "IMAGE_VERIFICATION_FAILED";
	public static final String ENABLE = "Y";
	public static final String DISABLE = "N";
	public static final String NOT_FOUND = "Not Found.";
	public static final String NOT_RECEIVED = "Not Received";
	public static final String RECEIVED = "Received";
	public static final String ENTER_TRACKING_ID = "Enter tracking id";
	

	public static final class FullDataList {

		public static List<String> getList() {
			// TBD - This should be picked from config file.
			List<String> obisCodeList = new ArrayList<String>();
			//obisCodeList.add("InstantaneousRead");
			obisCodeList.add("DailyLoadProfile");
			//obisCodeList.add("DeltaLoadProfile");
			obisCodeList.add("BillingData");
			//obisCodeList.add("PowerRelatedEvents");
			//obisCodeList.add("VoltageRelatedEvents");
			//obisCodeList.add("TransactionRelatedEvents");
			//obisCodeList.add("CurrentRelatedEvents");
			//obisCodeList.add("OtherRelatedEvents");
			//obisCodeList.add("ControlRelatedEvents");
			//obisCodeList.add("NonRolloverRelatedEvents");

			return obisCodeList;
		}
		
		public static List<String> onDemandObisCodeList() {
			List<String> obisCodeList = new ArrayList<String>();
			obisCodeList.add("InstantaneousRead");
			obisCodeList.add("DailyLoadProfile");
			obisCodeList.add("DeltaLoadProfile");
			obisCodeList.add("BillingData");
			obisCodeList.add("PowerRelatedEvents");
			obisCodeList.add("VoltageRelatedEvents");
			obisCodeList.add("TransactionRelatedEvents");
			obisCodeList.add("CurrentRelatedEvents");
			obisCodeList.add("OtherRelatedEvents");
			obisCodeList.add("ControlRelatedEvents");
			obisCodeList.add("NonRolloverRelatedEvents");
			obisCodeList.add("NamePlate");
			
			return obisCodeList;
		}
	}
	
	public final class CommandName{
		public static final String Instantaneous = "Instantaneous";
		public static final String Connect = "Connect";
		public static final String Disconnect = "DisConnect";
		public static final String FullData = "FullData";
		public static final String FullConfig = "FullConfig";
		public static final String Disconnect_Cmd = "Disconnect";
		public static final String Daily_Load_Profile = "DailyLoadProfile";
		public static final String Delta_Load_Profile = "DeltaLoadProfile";
		public static final String Instantaneous_Read = "InstantaneousRead";
		public static final String Billing_Data = "BillingData";
		public static final String Power_Related_Events = "PowerRelatedEvents";
		public static final String Voltage_Related_Events = "VoltageRelatedEvents";
		public static final String Transaction_Related_Events = "TransactionRelatedEvents";
		public static final String Current_Related_Events = "CurrentRelatedEvents";
		public static final String Other_Related_Events = "OtherRelatedEvents";
		public static final String Control_Related_Events = "ControlRelatedEvents";
		public static final String Name_Plate = "NamePlate";
	}
	
	public final class CommType{
		public static final String DayWise = "Today";
		public static final String YesterdayWise = "Yesterday";
		public static final String WeekWise = "Week";
		public static final String MonthWise = "Month";
	}
	
	public static class DataSet {
		public static final String Comm_Status = "Communication";
		public static final String Instant_Data = "InstantData";
		public static final String Daily_Load_Profile = "DailyLoadProfile";
		public static final String Delta_Profile = "DeltaProfile";
		public static final String Event_Data = "EventData";
		public static final String Billing_Data = "BillingData";
		public static final String Full_Prepay_Data = "FullPrepayData";
	}
	
	public static class ConfigurationReadDataLogs {
		public static final String DEVICE_SERIAL_NUMBER = "device_serial_number";
		public static final String TRACKING_ID = "tracking_id";
		public static final String CELL_ID = "cell_id";
		public static final String COMMAND_COMPLETION_DATETIME = "command_completion_datetime";
		public static final String COMMAND_NAME = "command_name";
		public static final String MOD_COMMAND_NAME = "mod_command_name";
		public static final String DCU_SERIAL_NUMBER = "dcu_serial_number";
		public static final String DT_NAME = "dt_name";
		public static final String FEEDER_NAME = "feeder_name";
		public static final String MDAS_DATETIME = "mdas_datetime";
		public static final String OWNER_NAME = "owner_name";
		public static final String REASON = "reason";
		public static final String STATUS = "status";
		public static final String SUBDEVISION_NAME = "subdevision_name";
		public static final String SUBSTATION_NAME = "substation_name";
		public static final String TOT_ATTEMPTS = "tot_attempts";
		public static final String SOURCE = "source";
		public static final String USER_ID = "user_id";
		public static final String BATCH_ID = "batch_id";
}
	public static class CreateBatch{
		public static final String BATCH = "B";
		public static final String EXT_BATCH = "ExB";
	}

	// version v2 start
	
	public static final String FROM_MID_NIGHT = " 00:00:00";
	public static final String TO_MID_NIGHT = " 23:59:59";
	
	public static final String CRITICAL_EVENTS = "Critical Events";
	public static final String NON_CRITICAL_EVENTS = "Non-Critical Events";
	public static final String PRIORITY_EVENTS = "Priority Events";
	public static final String RESERVED = "Reserved";
	public static final String CATEGORIZED_EVENTS = "Categorized Events";
	public static final String CATEGORIZED_EVENTS_LIST = "Categorized Events List";

	public static class DateRange{
		public static final String STARTDATE = "startDate";
		public static final String ENDDATE = "endDate";
	}
	
	public static final String CANT_USE_GREATER_DATE_TIME = "Recharge/Balance date time must not be greater than current date time.";
	
	public static String createBatchId() {
		return "S"+ String.valueOf(System.currentTimeMillis());
	}
	public static final String USER_ID = "SCHEDULER";
	public static final String SOURCE = "SCHEDULER";
	
	public static class SkipDate {
		public static final String MILLISECOND = "MILLISECOND";
		public static final String SECOND = "SECOND";
		public static final String YEAR = "YEAR";
		public static final String HOUR = "HOUR";
		public static final String DAY_OF_WEEK = "DAY_OF_WEEK";
		public static final String MONTH = "MONTH";
		public static final String MINUTE = "MINUTE";
		public static final String DAY = "DAY";
	}
	public static final String DISCARD = "DISCARD";
	public static final String ALREADY_IN_QUEUE = "Already in queue";
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String CANCELLED = "CANCELLED";
	public static final String EXT = "ext";
	public static final String HES = "hes";
	public static final String HIRE_NAME = "7";
	public static final String INVALID_HIER = "Invalid Hierarchy";
	public static final String ALREADY_CANCELLED = "ALREADY CANCELLED";
	public static final String MISSING = "missing";
	public static final String MISSING_FILE = "Missing firmware file";
	
	
	public static class ManufacturerName {
		public static final String ZEN = "ZEN";
		public static final String HPL = "HPL";
	}
	public static class BillingType {
		public static final String DAILY = "daily";
		public static final String WEEKLY = "weekly";
		public static final String MONTHLY = "monthly";
	}
	
	public final class HierLevelKey {
		public static final String SUBDEVISION = "2";
		public static final String FEEDER = "4";
		public static final String SUBSTATION = "3";
		public static final String DTMETER = "5";
		public static final String DCU = "6";
		public static final String METER = "7";
		public static final String ALL = "1";
		public static final String MANUFACTURER = "8";

	}
	public static class BackendStatus {
		public static final String Y = "Y";
		public static final String N = "N";
	}
	public static class Subdivision {
		public static final String SUBDIVISION = "subdivision_name";
	}
	public static final String EVENT_DATA = "EventData";
	public static final String MONTHLY_BILLING_DATA = "MonthlyBillingData";
	public static final String ALARM_DATA = "AlarmData";
	public static final String User_Desc = "User Desc";
	public static final String Property = "Property";
	public static final String Value = "Value";
	public static final String Status_ = "Status"; // Status_ (Here extra underscore used due to manage confliction)
	public static final String Retry = "Retry";
	public static final String Active = "Active";
	public static final String Inactive = "Inactive";
	public static final String Scheduler = "Scheduler";
	
	public static class ModeComm {
		public static final String RF = "RF";
		public static final String CELLULAR = "Cellular";
		public static final String LORA = "LoRa";
		public static final String NBIOT = "NBioT";
		public static final String LORAWAN = "LORAWAN";
		public static final String NBGAS = "NBGAS";
	}
	public static class Source {
		public static final String HES = "HES";
	}
	public static class HierLevel {
		public static final String LEVEL1 = "L1";
		public static final String LEVEL2 = "L2";
		public static final String LEVEL3 = "L3";
		public static final String LEVEL4 = "L4";
		public static final String LEVEL5 = "L5";
		public static final String LEVEL6 = "L6";
	}
	public static final String CATEGORY_LEVEL = "Category Level";
	public static class DivFactorType {
		public static final String PULL = "PULL";
	}
	public static class DataRequest {
		public static final String PUSH = "PUSH";
	}
	
	public static final String DEVICE_NOT_EXISTS = "Device Not Exists.";
	public static final String ERROR_MESSAGE = "Resources are busy.";
	public static final String DEVICE_NOT_FOUND = "Device Not Found.";

}