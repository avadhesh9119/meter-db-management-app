package com.global.meter.utils;

import org.springframework.util.StringUtils;

public class PropertyUtils {
	
	public static String replaceString(String data, String key, String value) {

		String replaceBy = "";
		String updatedString = "";
		if (!StringUtils.isEmpty(data) && data.contains(key)) {
			if (data.contains(":")) {
				replaceBy = ":";
			}
			if (data.contains("=")) {
				replaceBy = "=";
			}
			String replacString = key.concat(" ").concat(replaceBy).concat(" ").concat(value);
			updatedString = data.replace(data, replacString);
		}

		return updatedString;
	}
	

	

	
	public static String getValueFromProperty(String data, String key) {

		String splitBy = "";
		String cron = "";
		if (!StringUtils.isEmpty(data) && data.contains(key)) {
			if (data.contains(":")) {
				splitBy = ":";
			}
			if (data.contains("=")) {
				splitBy = "=";
			}
			String[] cronProperty = data.split(splitBy);
			if (cronProperty.length < 3) {
			 cron =	cronProperty[1];
			}
		
		}

		return cron.replaceAll("\\s", "");
	}
	
	public static String getKeyFromProperty(String data) {

		String splitBy = "";
		String cron = "";
		if (!StringUtils.isEmpty(data)) {
			if (data.contains(":")) {
				splitBy = ":";
			}
			if (data.contains("=")) {
				splitBy = "=";
			}
			String[] cronProperty = data.split(splitBy);
			if (cronProperty.length < 3) {
			 cron =	cronProperty[0];
			}
		
		}

		return cron.replaceAll("\\s", "");
	}
	
	public static String getValueFromProperty(String data) {

		String splitBy = "";
		String cron = "";
		if (!StringUtils.isEmpty(data)) {
			if (data.contains(":")) {
				splitBy = ":";
			}
			if (data.contains("=")) {
				splitBy = "=";
			}
			String[] cronProperty = data.split(splitBy);
			if (cronProperty.length < 3) {
			 cron =	cronProperty[1];
			}
		
		}

		return cron.replaceAll("\\s", "");
	}
	
	public static String getValuesFromProperty(String data) {

		String splitBy = "";
		String cron = "";
		if (!StringUtils.isEmpty(data)) {
			if (data.contains(":")) {
				splitBy = ":";
			}
			if (data.contains("=")) {
				splitBy = "=";
			}
			String[] cronProperty = data.split(splitBy);
			if (cronProperty.length < 3) {
			 cron =	cronProperty[1];
			}
		
		}

		return cron;
	}
	
	public static String setNewDivFactor(String data, String key, String value, int index) {

		String replaceBy = "=";
		String updatedString = "";
		String newDivFactor = "";
		
		StringBuilder newValue = new StringBuilder();
		
		String [] str = data.split("=");
		
		String pairData = str[1];
		
		for(String ind : pairData.split("-")) {
			
			
			if(Integer.parseInt((ind.split(":")[0]).trim()) ==index) {
		 		ind = index +":"+value;
				newValue.append(ind).append("-");
			}else {
				newValue.append(ind).append("-");
			}
		}
		 newDivFactor =  newValue.substring(0, newValue.length()-1);
		String replacString = key.concat(" ").concat(replaceBy).concat(" ").concat(newDivFactor.trim());
		updatedString = data.replace(data, replacString);

		return updatedString;
	}
}
