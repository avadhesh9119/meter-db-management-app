package com.global.meter.inventive.mdm.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.MeterCommCount;
import com.global.meter.inventive.enums.DataSet;
import com.global.meter.inventive.models.DeviceCountRequest;
import com.global.meter.inventive.models.DevicesCountResponse;
import com.global.meter.inventive.models.MeterBarChartDataCountRequest;
import com.global.meter.inventive.models.MeterCommCountResponse;
import com.global.meter.inventive.models.MeterCommDrillDownRequest;
import com.global.meter.inventive.models.MeterCommDrillDownResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class MeterCommCountMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareMeterCommCount(String outputList, List<MeterCommCountResponse> ispResponseList)
			throws Exception {
		LOG.info("Meter Comm Count Caster called....");
		List<MeterCommCount> meterCommCount = new ArrayList<MeterCommCount>();
		meterCommCount = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<MeterCommCount>>() {
		});
		LOG.info("Meter Comm Count Caster Response Adding");
		for (MeterCommCount ispData : meterCommCount) {
			MeterCommCountResponse ispResponse = new MeterCommCountResponse();

			ispResponse.setUtility(String.valueOf(ispData.getOwner_name()));
			ispResponse.setBillingDayFailureCount(ispData.getBillingdayfailurecount());
			ispResponse.setBillingDaySuccessCount(ispData.getBillingdaysuccesscount());
			ispResponse.setBillingMonthFailureCount(ispData.getBillingmonthfailurecount());
			ispResponse.setBillingMonthSuccessCount(ispData.getBillingmonthsuccesscount());
			ispResponse.setBillingWeekFailureCount(ispData.getBillingweekfailurecount());
			ispResponse.setBillingWeekSuccessCount(ispData.getBillingweeksuccesscount());
			ispResponse.setBillingYesFailureCount(ispData.getBillingyestfailurecount());
			ispResponse.setBillingYesSuccessCount(ispData.getBillingyestsuccesscount());
			ispResponse.setCommDayFailureCount(ispData.getCommdayfailurecount());
			ispResponse.setCommDaySuccessCount(ispData.getCommdaysuccesscount());
			ispResponse.setComMonthFailureCount(ispData.getCommmonthfailurecount());
			ispResponse.setComMonthSuccessCount(ispData.getCommmonthsuccesscount());
			ispResponse.setCommWeekFailureCount(ispData.getCommweekfailurecount());
			ispResponse.setCommWeekSuccessCount(ispData.getCommweeksuccesscount());
			ispResponse.setCommYestFailureCount(ispData.getCommyestfailurecount());
			ispResponse.setCommYestSuccessCount(ispData.getCommyestsuccesscount());
			ispResponse.setCtPhase(ispData.getCtphase());
			ispResponse.setDailyDayFailureCount(ispData.getDailydayfailurecount());
			ispResponse.setDailyDaySuccessCount(ispData.getDailydaysuccesscount());
			ispResponse.setDailyMonthFailureCount(ispData.getDailymonthfailurecount());
			ispResponse.setCommYestFailureCount(ispData.getCommyestfailurecount());
			ispResponse.setDailyMonthSuccessCount(ispData.getDailymonthsuccesscount());
			ispResponse.setDailyWeekFailureCount(ispData.getDailyweekfailurecount());
			ispResponse.setDailyWeekSuccessCount(ispData.getDailyweeksuccesscount());
			ispResponse.setDailyYesFailureCount(ispData.getDailyyestfailurecount());
			ispResponse.setDailyYesSuccessCount(ispData.getDailyyestsuccesscount());
			ispResponse.setDcu(ispData.getDcu());
			ispResponse.setDeltaDayFailureCount(ispData.getDeltadayfailurecount());
			ispResponse.setDeltaDaySuccessCount(ispData.getDeltadaysuccesscount());
			ispResponse.setDeltaMonthFailureCount(ispData.getDeltamonthfailurecount());
			ispResponse.setDeltaMonthSuccessCount(ispData.getDeltamonthsuccesscount());
			ispResponse.setDeltaWeekFailureCount(ispData.getDeltaweekfailurecount());
			ispResponse.setDeltaWeekSuccessCount(ispData.getDeltayestsuccesscount());
			ispResponse.setDeltaYesFailureCount(ispData.getDeltayestfailurecount());
			ispResponse.setDeltaYesSuccessCount(ispData.getDeltayestsuccesscount());
			ispResponse.setDt(ispData.getDt());
			ispResponse.setEventDayFailureCount(ispData.getEventdayfailurecount());
			ispResponse.setEventDaySuccessCount(ispData.getEventdaysuccesscount());
			ispResponse.setEventMonthFailureCount(ispData.getEventmonthfailurecount());
			ispResponse.setEventMonthSuccessCount(ispData.getEventmonthsuccesscount());
			ispResponse.setEventWeekFailureCount(ispData.getEventweekfailurecount());
			ispResponse.setEventWeekSuccessCount(ispData.getEventweeksuccesscount());
			ispResponse.setEventYesFailureCount(ispData.getEventyestfailurecount());
			ispResponse.setEventYesSuccessCount(ispData.getEventyestsuccesscount());
			ispResponse.setLastUpdatedTime(dateConverter.convertDateToHesString(ispData.getLastupdatedtime()));
			ispResponse.setManualActiveDev(ispData.getManual_active_dev());
			ispResponse.setSinglePhase(ispData.getSinglephase());
			ispResponse.setSubDevision(ispData.getSubdevision());
			ispResponse.setSubstation(ispData.getSubstation());
			ispResponse.setThreePhase(ispData.getThreephase());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Meter Comm Count Caster Response Added Success");
	}

	public void prepareMeterCommDrillDown(List<DevicesInfo> devicesList, List<MeterCommDrillDownResponse> drillDownResponseList,
			MeterCommDrillDownRequest req) throws Exception {

		LOG.info("Meter Comm Count Caster Drill Down Called....");

		

		if (devicesList != null && devicesList.size() > 0) {
			LOG.info("Meter Comm Count Caster Drill Down Adding Data....");
			for (DevicesInfo deviceInfo : devicesList) {
				if(Constants.Status.DOWN.equalsIgnoreCase(deviceInfo.getCommissioning_status())) {
					//do nothing here for now
				}
				else {

					MeterCommDrillDownResponse meterCommDrillDownResponse = null;

					if (req != null) {

						if (req.getDataset() != null) {
							if (DataSet.COMMUNICATION_DATA.dataSet.equalsIgnoreCase(req.getDataset())) {
								if (new CommonUtils().isGreaterFromMidNight(req.getTimeFrame(),deviceInfo.getLastcommunicationdatetime())) {
									if(Constants.SUCCESS.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}
								} else {
									if(Constants.FAILURE.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}								
								}
							}
							
							if (DataSet.INSTANTANEOUS_READ.dataSet.equalsIgnoreCase(req.getDataset())) {
								if (new CommonUtils().isGreaterFromMidNight(req.getTimeFrame(),deviceInfo.getLastinstanteousreaddatetime())) {
									if(Constants.SUCCESS.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}
								} else {
									if(Constants.FAILURE.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}								
								}
							}

							if (DataSet.DAILY_LOAD_PROFILE.dataSet.equalsIgnoreCase(req.getDataset())) {
								if (new CommonUtils().isGreaterFromMidNight(req.getTimeFrame(),deviceInfo.getLastdailylpreaddatetime())) {
									if(Constants.SUCCESS.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}
								} else {
									if(Constants.FAILURE.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}								
								}
							}

							if (DataSet.DELTA_LOAD_PROFILE.dataSet.equalsIgnoreCase(req.getDataset())) {
								if (new CommonUtils().isGreaterFromMidNight(req.getTimeFrame(),deviceInfo.getLastdeltalpreaddatetime())) {
									if(Constants.SUCCESS.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}
								} else {
									if(Constants.FAILURE.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}								
								}
								
							}
							
							if (DataSet.EVENT.dataSet.equalsIgnoreCase(req.getDataset())) {
									if (new CommonUtils().isGreaterFromMidNight(req.getTimeFrame(),deviceInfo.getLasteventsreaddatetime())) {
										if(Constants.SUCCESS.equalsIgnoreCase(req.getCommStatus())){
											meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
										}
									} else {
										if(Constants.FAILURE.equalsIgnoreCase(req.getCommStatus())){
											meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
										}								
									}
							}

							if (DataSet.BILLING_DATA.dataSet.equalsIgnoreCase(req.getDataset())) {
								if (new CommonUtils().isGreaterFromMidNight(req.getTimeFrame(),deviceInfo.getLastbillingreaddatetime())) {
									if(Constants.SUCCESS.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}
								} else {
									if(Constants.FAILURE.equalsIgnoreCase(req.getCommStatus())){
										meterCommDrillDownResponse = setMeterCommDrillDown(deviceInfo,req);
									}								
								}
							}
						}
					}else {
						LOG.info("Meter Comm Count Caster Drill Down Request getting null.");
					}

					if(meterCommDrillDownResponse != null) {
						drillDownResponseList.add(meterCommDrillDownResponse);
					}
				}
			}
			LOG.info("Meter Comm Count Caster Drill Down Data Added Success.");
		}
	}

	private MeterCommDrillDownResponse setMeterCommDrillDown(DevicesInfo deviceInfo,MeterCommDrillDownRequest req) throws ParseException {
		MeterCommDrillDownResponse meterCommDrillDownResponse = new MeterCommDrillDownResponse();
		meterCommDrillDownResponse.setCrnNo(deviceInfo.getCrn());
		meterCommDrillDownResponse.setDeviceMode(deviceInfo.getDev_mode());
		meterCommDrillDownResponse.setMeterNo(deviceInfo.getDevice_serial_number());
		meterCommDrillDownResponse.setIpAddress(deviceInfo.getIp_address_main());
		meterCommDrillDownResponse.setLatitude(deviceInfo.getLatitude());
		meterCommDrillDownResponse.setLongitude(deviceInfo.getLongitude());
		meterCommDrillDownResponse.setLastCommTimestamp(deviceInfo.getLastcommunicationdatetime() != null ?
				dateConverter.convertDateToHesString(deviceInfo.getLastcommunicationdatetime()) : "-");
		// Set Last Communication Time-stamp from data set wise
		if (DataSet.INSTANTANEOUS_READ.dataSet.equalsIgnoreCase(req.getDataset())) {
			meterCommDrillDownResponse.setLastInstantCommTimestamp(deviceInfo.getLastinstanteousreaddatetime() != null ?
					dateConverter.convertDateToHesString(deviceInfo.getLastinstanteousreaddatetime()) : "-");
		}else if (DataSet.DAILY_LOAD_PROFILE.dataSet.equalsIgnoreCase(req.getDataset())) {
			meterCommDrillDownResponse.setLastDailyCommTimestamp(deviceInfo.getLastdailylpreaddatetime() != null ?
					dateConverter.convertDateToHesString(deviceInfo.getLastdailylpreaddatetime()) : "-");
		}else if (DataSet.DELTA_LOAD_PROFILE.dataSet.equalsIgnoreCase(req.getDataset())) {
			meterCommDrillDownResponse.setLastDeltaCommTimestamp(deviceInfo.getLastdeltalpreaddatetime() != null ?
					dateConverter.convertDateToHesString(deviceInfo.getLastdeltalpreaddatetime()) : "-");
		}else if (DataSet.EVENT.dataSet.equalsIgnoreCase(req.getDataset())) {
			meterCommDrillDownResponse.setLastEventCommTimestamp(deviceInfo.getLasteventsreaddatetime() != null ?
					dateConverter.convertDateToHesString(deviceInfo.getLasteventsreaddatetime()) : "-");
		}else if (DataSet.BILLING_DATA.dataSet.equalsIgnoreCase(req.getDataset())) {
			meterCommDrillDownResponse.setLastBillingCommTimestamp(deviceInfo.getLastbillingreaddatetime() != null ?
					dateConverter.convertDateToHesString(deviceInfo.getLastbillingreaddatetime()) : "-");
		}
		return meterCommDrillDownResponse;
	}

	public void prepareMeterDataCount(String outputList, String outputListInfo, String outputListName, List<DevicesCountResponse> ispResponseList) throws com.fasterxml.jackson.databind.JsonMappingException, JsonProcessingException{
		
		LOG.info("Meter Data Count Caster Called...");
		List<DeviceCountRequest> meterCommCount = new ArrayList<DeviceCountRequest>();
		List<DeviceCountRequest> devInfoSet = new ArrayList<DeviceCountRequest>();
		List<DeviceCountRequest> devNamePlateSet = new ArrayList<DeviceCountRequest>();
		Set<String> deviceInfoSet = new HashSet<>();
		Set<String> deviceNamePlateSet = new HashSet<>();
		meterCommCount = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DeviceCountRequest>>() {
		});
		devInfoSet = CommonUtils.getMapper().readValue(outputListInfo, new TypeReference<List<DeviceCountRequest>>() {
		});
		devNamePlateSet = CommonUtils.getMapper().readValue(outputListName, new TypeReference<List<DeviceCountRequest>>() {
		});
         for(DeviceCountRequest infoSet:devInfoSet)
         {
        	 deviceInfoSet.add(infoSet.getDevicesinfoSet());
         }
         for(DeviceCountRequest namePlateSet:devNamePlateSet)
         {
        	 if(deviceInfoSet.contains(namePlateSet.getDevicesNamePlateSet())) {
        	 deviceNamePlateSet.add(namePlateSet.getDevicesNamePlateSet());
        	 }
         }
		for(DeviceCountRequest isData:meterCommCount)
		{		DevicesCountResponse isResponce = new DevicesCountResponse();
			isResponce.setAllDeviceCount(String.valueOf(deviceInfoSet.size()));
			isResponce.setActiveDeviceCount(isData.getActiveCount());
			isResponce.setCommissinonedCount(String.valueOf(deviceNamePlateSet.size()));
			isResponce.setNotCommissinonedCount(Integer.toString((Integer.valueOf(deviceInfoSet.size())-Integer.valueOf(deviceNamePlateSet.size()))));
			isResponce.setCommunationFieldCount(isData.getCommunationFieldCount());
			ispResponseList.add(isResponce);
			
		}
		LOG.info("Meter Data Count Caster Data Added Sucess.");
	}

	public void prepareMeterBarChartCount(String outputList, List<DevicesCountResponse> ispResponseList)
			throws JsonMappingException, JsonProcessingException, ParseException {

		List<MeterBarChartDataCountRequest> meterCommCount = new ArrayList<MeterBarChartDataCountRequest>();
		meterCommCount = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<MeterBarChartDataCountRequest>>() {
				});
		Date date = null;
		boolean isFirstTime = true;
		boolean isLastTime = false;
		DevicesCountResponse isResponce = new DevicesCountResponse();
		for (MeterBarChartDataCountRequest ispData : meterCommCount) {
			isLastTime = true;
			if (!ispData.getDate().equals(date)) {
				if (!isFirstTime) {
                    setDefaultCount(isResponce);
					ispResponseList.add(isResponce);
					isResponce = new DevicesCountResponse();
				}
				isResponce.setDate(dateConverter.convertDateToHesDateString(ispData.getDate()));
				setCount(ispData, isResponce);
				date = ispData.getDate();
				isFirstTime = false;

				if (meterCommCount.size() == 1) {
					isResponce.setDate(dateConverter.convertDateToHesDateString(ispData.getDate()));
					isResponce.setCommand_name(ispData.getCommand_name());
					ispResponseList.add(isResponce);
				}
			} else {
				setCount(ispData, isResponce);
				isFirstTime = false;
			}
		}
		if(isLastTime) {
		setDefaultCount(isResponce);
		ispResponseList.add(isResponce);
		isLastTime = false;
		}
	}

	private void setCount(MeterBarChartDataCountRequest ispData, DevicesCountResponse isResponse) {
		if ("Connect".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setConnect(ispData.getSuccess_percentage());
		} else if ("Disconnect".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setDisconnect(ispData.getSuccess_percentage());
		} else if ("DailyLoadProfile".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setDailyLoadProfile(ispData.getSuccess_percentage());
		}else if ("InstantaneousRead".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setInstantaneousRead(ispData.getSuccess_percentage());
		} else if ("DeltaLoadProfile".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setDeltaLoadProfile(ispData.getSuccess_percentage());
		} else if ("BillingData".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setBillingData(ispData.getSuccess_percentage());
		}else if ("PowerRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setPowerRelatedEvents(ispData.getSuccess_percentage());
		} else if ("VoltageRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setVoltageRelatedEvents(ispData.getSuccess_percentage());
		} else if ("TransactionRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setTransactionRelatedEvents(ispData.getSuccess_percentage());
		}else if ("CurrentRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setCurrentRelatedEvents(ispData.getSuccess_percentage());
		} else if ("OtherRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setOtherRelatedEvents(ispData.getSuccess_percentage());
		} else if ("ControlRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setControlRelatedEvents(ispData.getSuccess_percentage());
		}else if("NamePlate".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setNamePlate(ispData.getSuccess_percentage());
		}
	}
    public void setDefaultCount(DevicesCountResponse isResponse) {
	
	isResponse.setConnect(isResponse.getConnect() != null ? isResponse.getConnect() : "0");
	isResponse.setDisconnect(isResponse.getDisconnect() != null ? isResponse.getDisconnect() : "0");
	isResponse.setDailyLoadProfile(isResponse.getDailyLoadProfile() != null ? isResponse.getDailyLoadProfile() : "0");
	isResponse.setInstantaneousRead(isResponse.getInstantaneousRead() != null ? isResponse.getInstantaneousRead() : "0");
	isResponse.setDeltaLoadProfile(isResponse.getDeltaLoadProfile() != null ? isResponse.getDeltaLoadProfile() : "0");
	isResponse.setBillingData(isResponse.getBillingData() != null ? isResponse.getBillingData() : "0");
	isResponse.setPowerRelatedEvents(isResponse.getPowerRelatedEvents() != null ? isResponse.getPowerRelatedEvents() : "0");
	isResponse.setVoltageRelatedEvents(isResponse.getVoltageRelatedEvents() != null ? isResponse.getVoltageRelatedEvents() : "0");
	isResponse.setTransactionRelatedEvents(isResponse.getTransactionRelatedEvents() != null ? isResponse.getTransactionRelatedEvents() : "0");
	isResponse.setCurrentRelatedEvents(isResponse.getCurrentRelatedEvents() != null ? isResponse.getCurrentRelatedEvents() : "0");
	isResponse.setOtherRelatedEvents(isResponse.getOtherRelatedEvents() != null ? isResponse.getOtherRelatedEvents() : "0");
	isResponse.setControlRelatedEvents(isResponse.getControlRelatedEvents() != null ? isResponse.getControlRelatedEvents() : "0");
	isResponse.setNamePlate(isResponse.getNamePlate() != null ? isResponse.getNamePlate() : "0");

   }
}
