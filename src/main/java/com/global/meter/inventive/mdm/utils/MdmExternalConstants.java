package com.global.meter.inventive.mdm.utils;

import com.global.meter.utils.Constants;

public class MdmExternalConstants {

	public static class Message{
		public static final String DEV_EXISTS = "Device Already Exists..";
		public static final String DEV_NOT_EXISTS = "Device Not Exists..";
		public static final String DEV_ADDED = "Device Added Successfully!!!";
		public static final String DEV_DELETED = "Device Deleted Successfully!!!";
		public static final String DEV_UPDATED = "Device Updated Successfully!!!";
		public static final String IP_EXISTS = "Ip Already Exists..";
		
		// Common Constant
		public static final String EXISTS = "Already Exists..";
		public static final String NOT_EXISTS = "Not Exists..";
		public static final String ADDED = "Added Successfully!!!";
		public static final String UPDATED = "Updated Successfully!!!";
		public static final String DELETED = "Deleted Successfully!!!";
		
		public static final String BATCH_ID_EXISTS = "Batch Id Already Exists..";
		public static final String BATCH_ADDED = "Batch Added Successfully!!!";
		public static final String BATCH_UPDATED = "Batch Updated Successfully!!!";
		public static final String BATCH_ID_NOT_EXISTS = "Batch Id Not Exists..";
		public static final String NO_PREVIOUS_PENDING_COMMAND = "No Previous Pending Command";
	}

	public static class Status{
		public static final String SUCCESS = "Success";
		public static final String VALID = "VALID";
		public static final String RELAY_STATUS = "UP";
		public static final String COMMISSIONED = "Commissioned";
		public static final String NON_COMMISSIONED = "Not-Commissioned";
		public static final String CONNECTED = "CONNECTED";	
		public static final String DISCONNECTED = "DISCONNECTED";	
		
		
	}
	
	
	
	public static String getLevelValue(String levelName) {
		String fieldName = "device_serial_number";
		
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) || "1".equalsIgnoreCase(levelName)) {
			fieldName = "owner_name";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName) || "2".equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)  || "3".equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)  || "4".equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)  || "5".equalsIgnoreCase(levelName) ) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)  || "6".equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.MANUFACTURER.equalsIgnoreCase(levelName)  || "8".equalsIgnoreCase(levelName)) {
			fieldName = "manufacturer_name";
		} 
		
		return fieldName;
	}
	
	public static String getUILevelValue(String levelName) {
		String fieldName = "device_serial_number";
		
		if ("1".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.ALL;
		} else if ("2".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.SUBDEVISION;
		} else if ("3".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.SUBSTATION;
		} else if ("4".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.FEEDER;
		} else if ("5".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.DTMETER;
		} else if ("6".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.DCU;
		} else if ("8".equalsIgnoreCase(levelName)) {
			fieldName = Constants.HierLevelName.MANUFACTURER;
		} 
		
		return fieldName;
	}
	
	public static String getUIBatchLevelName(String levelName) {
		String fieldName = Constants.BatchHierLevelName.METER;
		
		if ("1".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.ALL;
		} else if ("2".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.SUBDIVISION;
		} else if ("3".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.SUBSTATION;
		} else if ("4".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.FEEDER;
		} else if ("5".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.DTMETER;
		} else if ("6".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.DCU;
		} else if ("8".equalsIgnoreCase(levelName)) {
			fieldName = Constants.BatchHierLevelName.MANUFACTURER;
		}
		
		return fieldName;
	}
	
	public static String getGasLevelValue(String levelName) {
		String fieldName = "gasmeterno";
		
		if (Constants.HierLevelName.ALL.equalsIgnoreCase(levelName) || "1".equalsIgnoreCase(levelName)) {
			fieldName = "ownername";
		} else if (Constants.HierLevelName.SUBDEVISION.equalsIgnoreCase(levelName) || "2".equalsIgnoreCase(levelName)) {
			fieldName = "subdevision_name";
		} else if (Constants.HierLevelName.SUBSTATION.equalsIgnoreCase(levelName)  || "3".equalsIgnoreCase(levelName)) {
			fieldName = "substation_name";
		} else if (Constants.HierLevelName.FEEDER.equalsIgnoreCase(levelName)  || "4".equalsIgnoreCase(levelName)) {
			fieldName = "feeder_name";
		} else if (Constants.HierLevelName.DTMETER.equalsIgnoreCase(levelName)  || "5".equalsIgnoreCase(levelName) ) {
			fieldName = "dt_name";
		} else if (Constants.HierLevelName.DCU.equalsIgnoreCase(levelName)  || "6".equalsIgnoreCase(levelName)) {
			fieldName = "dcu_serial_number";
		} else if (Constants.HierLevelName.MANUFACTURER.equalsIgnoreCase(levelName)  || "8".equalsIgnoreCase(levelName)) {
			fieldName = "manufacturer_name";
		} 
		
		return fieldName;
	}
	
	public static class DeviceTypes {
		public static final String SINGLE_PHASE = "Single";
		public static final String THREE_PHASE = "Three";
		public static final String LT_METER = "LT";
		public static final String CT_METER = "CT";
		public static final String SINGLE_PHASE_DEV = "Single Phase";
		public static final String THREE_PHASE_DEV = "Three Phase";
	}
}
