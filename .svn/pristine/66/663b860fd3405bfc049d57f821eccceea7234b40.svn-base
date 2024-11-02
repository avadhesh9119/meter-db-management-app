package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.DtTrans;
import com.global.meter.business.model.Feeders;
import com.global.meter.business.model.MeterCommCount;
import com.global.meter.business.model.Subdivisions;
import com.global.meter.business.model.Substations;
import com.global.meter.inventive.models.AllDevices;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class DataUtils {

	private static final Logger LOG = LoggerFactory.getLogger(DataUtils.class);

	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	public DateConverter dateConverter;

	public static CopyOnWriteArrayList<AllDevices> allDeviceList;
	public static List<Feeders> feederList;
	public static List<DevicesInfo> devicesInfoList;
	public static List<DtTrans> dtList;
	public static List<Subdivisions> subdivisionsList;
	public static List<Substations> substationsList;

	public DataUtils() {

	}

	public DataUtils(JdbcTemplate prestoJdbcTemplate) {
		this.prestoJdbcTemplate = prestoJdbcTemplate;
	}

	public List<MeterCommCount> meterCommCountData(String owner_name) {

		List<MeterCommCount> meterCommCountList = new ArrayList<>();

		feederList = new ArrayList<>();
		devicesInfoList = new ArrayList<>();
		dtList = new ArrayList<>();
		subdivisionsList = new ArrayList<>();
		substationsList = new ArrayList<>();

		MeterCommCount commCountResponse = null;
		int singlephasecount = 0;
		int threephasecount = 0;
		int ltphasecount = 0;
		int ctphasecount = 0;

		// Billing
		int billingdayfailurecount = 0;
		int billingdaysuccesscount = 0;
		int billingyestfailurecount = 0;
		int billingyestsuccesscount = 0;
		int billingweekfailurecount = 0;
		int billingweeksuccesscount = 0;
		int billingmonthfailurecount = 0;
		int billingmonthsuccesscount = 0;

		// Communication
		int commdayfailurecount = 0;
		int commdaysuccesscount = 0;
		int commyestfailurecount = 0;
		int commyestsuccesscount = 0;
		int commweekfailurecount = 0;
		int commweeksuccesscount = 0;
		int commmonthfailurecount = 0;
		int commmonthsuccesscount = 0;

		// Daily
		int dailydayfailurecount = 0;
		int dailydaysuccesscount = 0;
		int dailyyestfailurecount = 0;
		int dailyyestsuccesscount = 0;
		int dailyweekfailurecount = 0;
		int dailyweeksuccesscount = 0;
		int dailymonthfailurecount = 0;
		int dailymonthsuccesscount = 0;

		// Delta
		int deltadayfailurecount = 0;
		int deltadaysuccesscount = 0;
		int deltayestfailurecount = 0;
		int deltayestsuccesscount = 0;
		int deltaweekfailurecount = 0;
		int deltaweeksuccesscount = 0;
		int deltamonthfailurecount = 0;
		int deltamonthsuccesscount = 0;

		// Event
		int eventdayfailurecount = 0;
		int eventdaysuccesscount = 0;
		int eventyestfailurecount = 0;
		int eventyestsuccesscount = 0;
		int eventweekfailurecount = 0;
		int eventweeksuccesscount = 0;
		int eventmonthfailurecount = 0;
		int eventmonthsuccesscount = 0;

		// Instance
		int instantdayfailurecount = 0;
		int instantdaysuccesscount = 0;
		int instantyestfailurecount = 0;
		int instantyestsuccesscount = 0;
		int instantweekfailurecount = 0;
		int instantweeksuccesscount = 0;
		int instantmonthfailurecount = 0;
		int instantmonthsuccesscount = 0;

		feederList = new CommonUtils(prestoJdbcTemplate).getFeederList();
		devicesInfoList = new CommonUtils(prestoJdbcTemplate).getDevicesInfo();
		dtList = new CommonUtils(prestoJdbcTemplate).getDtTrans();
		subdivisionsList = new CommonUtils(prestoJdbcTemplate).getSubdivision();
		substationsList = new CommonUtils(prestoJdbcTemplate).getSubstation();
		int dcuCount = new CommonUtils(prestoJdbcTemplate).getDcuCount();

		if (DataUtils.allDeviceList != null && DataUtils.allDeviceList.size() > 0) {

			for (AllDevices dev : DataUtils.allDeviceList) {
				if (dev.getUtility() != null) {
					if (dev.getUtility().equals(owner_name)) {
						if (dev.getDevicesInfo() != null && dev.getDevicesInfo().size() > 0) {

							LOG.info("Data add in MeterCommCount");
							try {

								for (DevicesInfo info : dev.getDevicesInfo()) {
									if (Constants.Status.DOWN.equalsIgnoreCase(info.getCommissioning_status())) {
										//do nothing here for now
									}
									else
										{
										/*
										 * Billing Count Start
										 */
	
										// Billing Today
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.DayWise,
												info.getLastbillingreaddatetime())) {
											billingdaysuccesscount++;
										} else {
											billingdayfailurecount++;
										}
	
										// Billing Yesterday
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.YesterdayWise,
												info.getLastbillingreaddatetime())) {
											billingyestsuccesscount++;
										} else {
											billingyestfailurecount++;
										}
	
										// Billing Week
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.WeekWise,
												info.getLastbillingreaddatetime())) {
											billingweeksuccesscount++;
										} else {
											billingweekfailurecount++;
										}
	
										// Billing Month
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.MonthWise,
												info.getLastbillingreaddatetime())) {
											billingmonthsuccesscount++;
										} else {
											billingmonthfailurecount++;
										}
	
										/*
										 * Billing Count End
										 */
	
										/*
										 * Communication Count Start
										 */
	
										// Communication Today
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.DayWise,
												info.getLastcommunicationdatetime())) {
											commdaysuccesscount++;
										} else {
											commdayfailurecount++;
										}
	
										// Communication Yesterday
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.YesterdayWise,
												info.getLastcommunicationdatetime())) {
											commyestsuccesscount++;
										} else {
											commyestfailurecount++;
										}
	
										// Communication Week
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.WeekWise,
												info.getLastcommunicationdatetime())) {
											commweeksuccesscount++;
										} else {
											commweekfailurecount++;
										}
	
										// Communication Month
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.MonthWise,
												info.getLastcommunicationdatetime())) {
											commmonthsuccesscount++;
										} else {
											commmonthfailurecount++;
										}
	
										/*
										 * Communication Count End
										 */
	
										/*
										 * Daily Count Start
										 */
	
										// Daily Today
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.DayWise,
												info.getLastdailylpreaddatetime())) {
											dailydaysuccesscount++;
										} else {
											dailydayfailurecount++;
										}
	
										// Daily Yesterday
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.YesterdayWise,
												info.getLastdailylpreaddatetime())) {
											dailyyestsuccesscount++;
										} else {
											dailyyestfailurecount++;
										}
	
										// Daily Week
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.WeekWise,
												info.getLastdailylpreaddatetime())) {
											dailyweeksuccesscount++;
										} else {
											dailyweekfailurecount++;
										}
	
										// Daily Month
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.MonthWise,
												info.getLastdailylpreaddatetime())) {
											dailymonthsuccesscount++;
										} else {
											dailymonthfailurecount++;
										}
	
										/*
										 * Daily Count End
										 */
	
										/*
										 * Delta Count Start
										 */
	
										// Delta Today
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.DayWise,
												info.getLastdeltalpreaddatetime())) {
											deltadaysuccesscount++;
										} else {
											deltadayfailurecount++;
										}
	
										// Delta Yesterday
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.YesterdayWise,
												info.getLastdeltalpreaddatetime())) {
											deltayestsuccesscount++;
										} else {
											deltayestfailurecount++;
										}
	
										// Delta Week
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.WeekWise,
												info.getLastdeltalpreaddatetime())) {
											deltaweeksuccesscount++;
										} else {
											deltaweekfailurecount++;
										}
	
										// Delta Month
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.MonthWise,
												info.getLastdeltalpreaddatetime())) {
											deltamonthsuccesscount++;
										} else {
											deltamonthfailurecount++;
										}
	
										/*
										 * Delta Count End
										 */
	
										/*
										 * Event Count Start
										 */
	
										// Event Today
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.DayWise,
												info.getLasteventsreaddatetime())) {
											eventdaysuccesscount++;
										} else {
											eventdayfailurecount++;
										}
	
										// Event Yesterday
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.YesterdayWise,
												info.getLasteventsreaddatetime())) {
											eventyestsuccesscount++;
										} else {
											eventyestfailurecount++;
										}
	
										// Event Week
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.WeekWise,
												info.getLasteventsreaddatetime())) {
											eventweeksuccesscount++;
										} else {
											eventweekfailurecount++;
										}
	
										// Event Month
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.MonthWise,
												info.getLasteventsreaddatetime())) {
											eventmonthsuccesscount++;
										} else {
											eventmonthfailurecount++;
										}
	
										/*
										 * Event Count End
										 */
	
										/*
										 * Instant Count Start
										 */
	
										// Instant Today
										if (info.getLastinstanteousreaddatetime() != null
												&& new CommonUtils().isGreaterFromMidNight(Constants.CommType.DayWise,
														info.getLastinstanteousreaddatetime())) {
											instantdaysuccesscount++;
										} else {
											instantdayfailurecount++;
										}
	
										// Instant Yesterday
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.YesterdayWise,
												info.getLastinstanteousreaddatetime())) {
											instantyestsuccesscount++;
										} else {
											instantyestfailurecount++;
										}
	
										// Instant Week
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.WeekWise,
												info.getLastinstanteousreaddatetime())) {
											instantweeksuccesscount++;
										} else {
											instantweekfailurecount++;
										}
	
										// Instant Month
										if (new CommonUtils().isGreaterFromMidNight(Constants.CommType.MonthWise,
												info.getLastinstanteousreaddatetime())) {
											instantmonthsuccesscount++;
										} else {
											instantmonthfailurecount++;
										}
	
										/*
										 * Instant Count End
										 */
	
										if (info.getDevice_type() != null && !info.getDevice_type().isEmpty()) {
	
											if (Constants.DeviceTypes.SINGLE_PHASE
													.equalsIgnoreCase(info.getDevice_type())) {
												singlephasecount++;
											}
											if (Constants.DeviceTypes.THREE_PHASE.equalsIgnoreCase(info.getDevice_type())) {
												threephasecount++;
											}
											if (Constants.DeviceTypes.CT_METER.equalsIgnoreCase(info.getDevice_type())) {
												ctphasecount++;
											}
											if (Constants.DeviceTypes.LT_METER.equalsIgnoreCase(info.getDevice_type())) {
												ltphasecount++;
											}
										}
	
										// Set Values in MeterCommCountResponse Model
										commCountResponse = new MeterCommCount();
										commCountResponse.setOwner_name(owner_name);
	
										commCountResponse.setBillingdaysuccesscount(billingdaysuccesscount);
										commCountResponse.setBillingdayfailurecount(billingdayfailurecount);
										commCountResponse.setBillingyestsuccesscount(billingyestsuccesscount);
										commCountResponse.setBillingyestfailurecount(billingyestfailurecount);
										commCountResponse.setBillingweeksuccesscount(billingweeksuccesscount);
										commCountResponse.setBillingweekfailurecount(billingweekfailurecount);
										commCountResponse.setBillingmonthsuccesscount(billingmonthsuccesscount);
										commCountResponse.setBillingmonthfailurecount(billingmonthfailurecount);
	
										commCountResponse.setCommdaysuccesscount(commdaysuccesscount);
										commCountResponse.setCommdayfailurecount(commdayfailurecount);
										commCountResponse.setCommyestsuccesscount(commyestsuccesscount);
										commCountResponse.setCommyestfailurecount(commyestfailurecount);
										commCountResponse.setCommweeksuccesscount(commweeksuccesscount);
										commCountResponse.setCommweekfailurecount(commweekfailurecount);
										commCountResponse.setCommmonthsuccesscount(commmonthsuccesscount);
										commCountResponse.setCommmonthfailurecount(commmonthfailurecount);
	
										commCountResponse.setDailydaysuccesscount(dailydaysuccesscount);
										commCountResponse.setDailydayfailurecount(dailydayfailurecount);
										commCountResponse.setDailyyestsuccesscount(dailyyestsuccesscount);
										commCountResponse.setDailyyestfailurecount(dailyyestfailurecount);
										commCountResponse.setDailyweeksuccesscount(dailyweeksuccesscount);
										commCountResponse.setDailyweekfailurecount(dailyweekfailurecount);
										commCountResponse.setDailymonthsuccesscount(dailymonthsuccesscount);
										commCountResponse.setDailymonthfailurecount(dailymonthfailurecount);
	
										commCountResponse.setDeltadaysuccesscount(deltadaysuccesscount);
										commCountResponse.setDeltadayfailurecount(deltadayfailurecount);
										commCountResponse.setDeltayestsuccesscount(deltayestsuccesscount);
										commCountResponse.setDeltayestfailurecount(deltayestfailurecount);
										commCountResponse.setDeltaweeksuccesscount(deltaweeksuccesscount);
										commCountResponse.setDeltaweekfailurecount(deltaweekfailurecount);
										commCountResponse.setDeltamonthsuccesscount(deltamonthsuccesscount);
										commCountResponse.setDeltamonthfailurecount(deltamonthfailurecount);
	
										commCountResponse.setEventdaysuccesscount(eventdaysuccesscount);
										commCountResponse.setEventdayfailurecount(eventdayfailurecount);
										commCountResponse.setEventyestsuccesscount(eventyestsuccesscount);
										commCountResponse.setEventyestfailurecount(eventyestfailurecount);
										commCountResponse.setEventweeksuccesscount(eventweeksuccesscount);
										commCountResponse.setEventweekfailurecount(eventweekfailurecount);
										commCountResponse.setEventmonthsuccesscount(eventmonthsuccesscount);
										commCountResponse.setEventmonthfailurecount(eventmonthfailurecount);
	
										commCountResponse.setInstantdaysuccesscount(instantdaysuccesscount);
										commCountResponse.setInstantdayfailurecount(instantdayfailurecount);
										commCountResponse.setInstantyestsuccesscount(instantyestsuccesscount);
										commCountResponse.setInstantyestfailurecount(instantyestfailurecount);
										commCountResponse.setInstantweeksuccesscount(instantweeksuccesscount);
										commCountResponse.setInstantweekfailurecount(instantweekfailurecount);
										commCountResponse.setInstantmonthsuccesscount(instantmonthsuccesscount);
										commCountResponse.setInstantmonthfailurecount(instantmonthfailurecount);
	
										commCountResponse.setLastupdatedtime(new Date(System.currentTimeMillis()));
	
										commCountResponse.setSinglephase(singlephasecount);
										commCountResponse.setThreephase(threephasecount);
										commCountResponse.setCtphase(ctphasecount);
										commCountResponse.setHtphase(ltphasecount);
	
										commCountResponse.setFeeder(feederList != null ? feederList.size() : 0);
										commCountResponse.setDevice(devicesInfoList != null ? devicesInfoList.size() : 0);
										commCountResponse.setDcu(dcuCount);
										commCountResponse.setDt(dtList != null ? dtList.size() : 0);
										commCountResponse
												.setSubdevision(subdivisionsList != null ? subdivisionsList.size() : 0);
										commCountResponse
												.setSubstation(substationsList != null ? substationsList.size() : 0);
	
										meterCommCountList.add(commCountResponse);
	
								}
								}

								LOG.info("Data add Success in MeterCommCount");

							} catch (Exception e) {
								LOG.error("Data add failure in MeterCommCount: " + e.getMessage());
							}

						}
					}
				}

			}
		}

		return meterCommCountList;
	}

	public static Object getTime(Object calObject) {
		String result = null;
		DateConverter dateConverter = new DateConverter();
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> dateMap = (Map<String, Object>) calObject;
			result = dateConverter.convertDateToTimeString(new Date((long) dateMap.get("value")));
		} catch (Exception e) {
			return calObject;
		}
		return result;

	}
}
