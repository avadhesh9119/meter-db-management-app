package com.global.meter.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class DateConverter {

	private DateFormat dateFormat_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat timeFormat_hh_mm_aa = new SimpleDateFormat("hh:mm aa");
	private DateFormat date_yyyy_mm_dd = new SimpleDateFormat("yyyy-MM-dd");
	private String currentDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private DateFormat timeFormat_hh_mm_ss_aa = new SimpleDateFormat("HH:mm:ss aa");
	//private String convertedOutputDateFormat = "yyyy-MM-dd HH:mm:ss";
	private static DateFormat hes_ext_dateFormat_yyyy_mm_dd = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
	private static DateFormat hes_ext_dateFormat_yyyy_mm_dd_a = new SimpleDateFormat("M/d/yyyy hh:mm:ss a");
	
	private static DateFormat hes_dateFormat_yyyy_mm_dd = new SimpleDateFormat("dd-MMM-yyyy");
	
	private static DateFormat hes_gas_ext_dateFormat_yyyy_mm_dd = new SimpleDateFormat("dd-MMM-yyyy hh:mm:00 a");
	private static DateFormat xml_dateFormat_yyyy_mm_dd = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	
	private DateFormat dateFormat_currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	public static void main(String ars[]) {
		Date result = new Date(System.currentTimeMillis());
		synchronized (hes_ext_dateFormat_yyyy_mm_dd) {
			String resulta = hes_ext_dateFormat_yyyy_mm_dd.format(result);
			System.out.println(resulta);
		}
	}
	
	
	public Date convertStringToDate(String dateString) throws ParseException {
		Date result;
		synchronized (dateFormat_yyyy_mm_dd) {
			result = dateFormat_yyyy_mm_dd.parse(dateString);
		}
		return result;
	}
	
	public Date convertStringToDateXML(String dateString) throws ParseException {
		Date result;
		synchronized (hes_ext_dateFormat_yyyy_mm_dd_a) {
			result = hes_ext_dateFormat_yyyy_mm_dd_a.parse(dateString);
		}
		return result;
	}

	public String convertDateToString(Date date) throws ParseException {
		String result;
		synchronized (dateFormat_yyyy_mm_dd) {
			result = dateFormat_yyyy_mm_dd.format(date);
		}
		return result;
	}

	public String convertDateToHesString(Date date) throws ParseException {
		String result;
		synchronized (hes_ext_dateFormat_yyyy_mm_dd) {
			result = hes_ext_dateFormat_yyyy_mm_dd.format(date);
		}
		return result;
	}
	
	public String convertGASDateToHesString(Date date) throws ParseException {
		String result;
		synchronized (hes_gas_ext_dateFormat_yyyy_mm_dd) {
			result = hes_gas_ext_dateFormat_yyyy_mm_dd.format(date);
		}
		return result;
	}
	
	public String convertDateToHesDateString(Date date) throws ParseException {
		String result;
		synchronized (hes_dateFormat_yyyy_mm_dd) {
			result = hes_dateFormat_yyyy_mm_dd.format(date);
		}
		return result;
	}
	public String convertDateToTimeString(Date date) throws ParseException {
		String result;
		synchronized (timeFormat_hh_mm_aa) {
			result = timeFormat_hh_mm_aa.format(date);
		}
		return result;
	}
	public String convertDateToTime(Date date) throws ParseException {
		String result;
		synchronized (timeFormat_hh_mm_ss_aa) {
			result = timeFormat_hh_mm_ss_aa.format(date);
		}
		return result;
	}
	
	public Date convertStringToDay(String dateString) throws ParseException {
		Date result;
		synchronized (date_yyyy_mm_dd) {
			result = date_yyyy_mm_dd.parse(dateString);
		}
		return result;
	}

	public String convertDayToString(Date date) throws ParseException {
		String result;
		synchronized (date_yyyy_mm_dd) {
			result = date_yyyy_mm_dd.format(date);
		}
		return result;
	}

	public String convertStringDateToString(String dateString) throws ParseException {
		String result;
		synchronized (currentDateFormat) {
			DateTimeFormatter currentFormatter = DateTimeFormatter.ofPattern(currentDateFormat);
			Instant starting = LocalDateTime.parse(dateString, currentFormatter).toInstant(ZoneOffset.UTC);
			Date date = Date.from(starting);
			result = convertDateToHesString(date);
		}
		return result;

	}

	public Date convertStringToTime(String time) throws ParseException {
		Date result;
		synchronized (timeFormat) {
			result = timeFormat.parse(time);
		}
		return result;

	}
	
	public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	public Date convertStringDayToDay(String dateString) throws ParseException {
		Date result;
		synchronized (hes_dateFormat_yyyy_mm_dd) {
			result = hes_dateFormat_yyyy_mm_dd.parse(dateString);
		}
		return result;
	}
	public Date convertStringDayToDate(String dateString) throws ParseException {
		Date result;
		synchronized (dateFormat_currentDateFormat) {
			result = dateFormat_currentDateFormat.parse(dateString);
		}
		return result;
	}
	public LocalDate convertDateTimeToDateLocaldate (Date date) throws ParseException {
		
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());
        LocalDate localDate = zonedDateTime.toLocalDate();
        return localDate;

	}
	public String convertLocalDateToHesString(LocalDate date) throws ParseException {
		String result;
		synchronized (hes_ext_dateFormat_yyyy_mm_dd) {
			result = hes_ext_dateFormat_yyyy_mm_dd.format(date);
		}
		return result;
	}
	
	public static synchronized String addSecondsToDateString(String dateString, int secondsToAdd) {
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	        Date date = dateFormat.parse(dateString);

	        // Add the specified number of seconds
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.SECOND, secondsToAdd);

	        // Format the resulting date as a string
	        Date resultDate = calendar.getTime();
	        return dateFormat.format(resultDate);
	    } catch (ParseException e) {
	        // Handle parsing exceptions if the input date string is invalid
	        e.printStackTrace();
	        return null; // Return null or an error message as needed
	    }
	}
	
	public String convertDateToSkipTime(Date date) throws ParseException {
		String result;
		synchronized (timeFormat) {
			result = timeFormat.format(date);
		}
		return result;
	}
	public String convertLocalDateToString(LocalDate localDate) throws ParseException {
		 String result;
	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	     result = localDate.format(formatter);
		return result;
	}
	
	public Date convertBillingDateToDateTimestamp(String date) throws ParseException {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1; // Adding 1 because months are zero-based

    // Combine the current year and month with the day part from the input
    String dayPart = date.split(":")[0]; // Get the day part from the input
    String formattedDate = String.format("%d-%02d-%s", currentYear, currentMonth, dayPart);
    return convertStringToDay(formattedDate);
	}
	
	public Date convertStringToDateIAM(String dateString) throws ParseException {
		Date result;
		synchronized (hes_dateFormat_yyyy_mm_dd) {
			result = hes_dateFormat_yyyy_mm_dd.parse(dateString);
		}
		return result;
	}
	
	public Date convertStringToLocalDate(String dateString) throws ParseException {
		Date result = null;;
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
            Date date = inputFormatter.parse(dateString);

            SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            outputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

            result = convertStringDayToDate(outputFormatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return result;
    }

	public Date convertXmlDateToHesDate(String dateString) {
		try {
			synchronized (dateFormat_currentDateFormat) {
			Date date = xml_dateFormat_yyyy_mm_dd.parse(dateString);
			
			return  convertStringDayToDate(dateFormat_currentDateFormat.format(date));
			
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}

