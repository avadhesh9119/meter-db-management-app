package com.global.meter.inventive.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.global.meter.business.model.DevicesCommand;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.MeterCommCount;
import com.global.meter.business.model.ProcessOnDemandCommandData;
import com.global.meter.data.repository.ProcessOnDemandCommandDataRepository;
import com.global.meter.inventive.enums.DataSet;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.DeviceCountRequest;
import com.global.meter.inventive.models.DevicesCountResponse;
import com.global.meter.inventive.models.MeterBarChartDataCountRequest;
import com.global.meter.inventive.models.MeterCommCountResponse;
import com.global.meter.inventive.models.MeterCommDrillDownRequest;
import com.global.meter.inventive.models.MeterCommDrillDownResponse;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class MeterCommCountCaster {
	private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountCaster.class);

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	ProcessOnDemandCommandDataRepository processOnDemandCommandRepo;

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

	public void prepareMeterBarChartCount(List<MeterBarChartDataCountRequest> meterCommCount, List<DevicesCountResponse> ispResponseList)
			throws JsonMappingException, JsonProcessingException, ParseException {

		Date date = null;
		Set<Date> dateSet = new HashSet<>();
		for(int i=0; i<meterCommCount.size(); i++) {
			boolean isFirstTime = true;
			boolean isSetDate = false;
		DevicesCountResponse isResponce = new DevicesCountResponse();
		for (MeterBarChartDataCountRequest ispData : meterCommCount) {
			 if((isFirstTime || ispData.getDate().equals(date)) && (!dateSet.contains(ispData.getDate()) || isSetDate)) {
			setCount(ispData, isResponce);
			isResponce.setDate(dateConverter.convertDateToHesDateString(ispData.getDate()));
			dateSet.add(ispData.getDate());
			date = ispData.getDate();
			isFirstTime = false;
			isSetDate = true;
			 }
		}
		if(isResponce.getDate() != null && !isResponce.getDate().isEmpty()) {
		setDefaultCount(isResponce);
		ispResponseList.add(isResponce);
		}
		}
	}

	private void setCount(MeterBarChartDataCountRequest ispData, DevicesCountResponse isResponse) {
		if ("Connect".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setConnect(ispData.getSuccess_percentage());
			isResponse.setConnectCount(ispData.getCount());
		} else if ("Disconnect".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setDisconnect(ispData.getSuccess_percentage());
			isResponse.setDisconnectCount(ispData.getCount());
		} else if ("DailyLoadProfile".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setDailyLoadProfile(ispData.getSuccess_percentage());
			isResponse.setDailyLoadProfileCount(ispData.getCount());
		}else if ("InstantaneousRead".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setInstantaneousRead(ispData.getSuccess_percentage());
			isResponse.setInstantaneousReadCount(ispData.getCount());
		} else if ("DeltaLoadProfile".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setDeltaLoadProfile(ispData.getSuccess_percentage());
			isResponse.setDeltaLoadProfileCount(ispData.getCount());
		} else if ("BillingData".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setBillingData(ispData.getSuccess_percentage());
			isResponse.setBillingDataCount(ispData.getCount());
		}else if ("PowerRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setPowerRelatedEvents(ispData.getSuccess_percentage());
			isResponse.setPowerRelatedEventsCount(ispData.getCount());
		} else if ("VoltageRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setVoltageRelatedEvents(ispData.getSuccess_percentage());
			isResponse.setVoltageRelatedEventsCount(ispData.getCount());
		} else if ("TransactionRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setTransactionRelatedEvents(ispData.getSuccess_percentage());
			isResponse.setTransactionRelatedEventsCount(ispData.getCount());
		}else if ("CurrentRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setCurrentRelatedEvents(ispData.getSuccess_percentage());
			isResponse.setCurrentRelatedEventsCount(ispData.getCount());
		} else if ("OtherRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setOtherRelatedEvents(ispData.getSuccess_percentage());
			isResponse.setOtherRelatedEventsCount(ispData.getCount());
		} else if ("ControlRelatedEvents".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setControlRelatedEvents(ispData.getSuccess_percentage());
			isResponse.setControlRelatedEventsCount(ispData.getCount());
		}else if("NamePlate".equalsIgnoreCase(ispData.getCommand_name())) {
			isResponse.setNamePlate(ispData.getSuccess_percentage());
			isResponse.setNamePlateCount(ispData.getCount());
		}

	}
    public void setDefaultCount(DevicesCountResponse isResponse) {
	
       	isResponse.setConnect(isResponse.getConnect() != null ? isResponse.getConnect() : "0");
    	isResponse.setConnectCount(isResponse.getConnectCount() != null ? isResponse.getConnectCount() : "0");
    	isResponse.setDisconnect(isResponse.getDisconnect() != null ? isResponse.getDisconnect() : "0");
    	isResponse.setDisconnectCount(isResponse.getDisconnectCount() != null ? isResponse.getDisconnectCount() : "0");
    	isResponse.setDailyLoadProfile(isResponse.getDailyLoadProfile() != null ? isResponse.getDailyLoadProfile() : "0");
    	isResponse.setDailyLoadProfileCount(isResponse.getDailyLoadProfileCount() != null ? isResponse.getDailyLoadProfileCount() : "0");
    	isResponse.setInstantaneousRead(isResponse.getInstantaneousRead() != null ? isResponse.getInstantaneousRead() : "0");
    	isResponse.setInstantaneousReadCount(isResponse.getInstantaneousReadCount() != null ? isResponse.getInstantaneousReadCount() : "0");
    	isResponse.setDeltaLoadProfile(isResponse.getDeltaLoadProfile() != null ? isResponse.getDeltaLoadProfile() : "0");
    	isResponse.setDeltaLoadProfileCount(isResponse.getDeltaLoadProfileCount() != null ? isResponse.getDeltaLoadProfileCount() : "0");
    	isResponse.setBillingData(isResponse.getBillingData() != null ? isResponse.getBillingData() : "0");
    	isResponse.setBillingDataCount(isResponse.getBillingDataCount() != null ? isResponse.getBillingDataCount() : "0");
    	isResponse.setPowerRelatedEvents(isResponse.getPowerRelatedEvents() != null ? isResponse.getPowerRelatedEvents() : "0");
    	isResponse.setPowerRelatedEventsCount(isResponse.getPowerRelatedEventsCount() != null ? isResponse.getPowerRelatedEventsCount() : "0");
    	isResponse.setVoltageRelatedEvents(isResponse.getVoltageRelatedEvents() != null ? isResponse.getVoltageRelatedEvents() : "0");
    	isResponse.setVoltageRelatedEventsCount(isResponse.getVoltageRelatedEventsCount() != null ? isResponse.getVoltageRelatedEventsCount() : "0");
    	isResponse.setTransactionRelatedEvents(isResponse.getTransactionRelatedEvents() != null ? isResponse.getTransactionRelatedEvents() : "0");
    	isResponse.setTransactionRelatedEventsCount(isResponse.getTransactionRelatedEventsCount() != null ? isResponse.getTransactionRelatedEventsCount() : "0");
    	isResponse.setCurrentRelatedEvents(isResponse.getCurrentRelatedEvents() != null ? isResponse.getCurrentRelatedEvents() : "0");
    	isResponse.setCurrentRelatedEventsCount(isResponse.getCurrentRelatedEventsCount() != null ? isResponse.getCurrentRelatedEventsCount() : "0");
    	isResponse.setOtherRelatedEvents(isResponse.getOtherRelatedEvents() != null ? isResponse.getOtherRelatedEvents() : "0");
    	isResponse.setOtherRelatedEventsCount(isResponse.getOtherRelatedEventsCount() != null ? isResponse.getOtherRelatedEventsCount() : "0");
    	isResponse.setControlRelatedEvents(isResponse.getControlRelatedEvents() != null ? isResponse.getControlRelatedEvents() : "0");
    	isResponse.setControlRelatedEventsCount(isResponse.getControlRelatedEventsCount() != null ? isResponse.getControlRelatedEventsCount() : "0");
    	isResponse.setNamePlate(isResponse.getNamePlate() != null ? isResponse.getNamePlate() : "0");
    	isResponse.setNamePlateCount(isResponse.getNamePlateCount() != null ? isResponse.getNamePlateCount() : "0");

   }
    
	public void prepareProcessOnDemandCommand(List<MeterBarChartDataCountRequest> meterCommCount)
			throws JsonMappingException, JsonProcessingException, ParseException {

		List<ProcessOnDemandCommandData> isResponseList = new ArrayList<ProcessOnDemandCommandData>();
		for(MeterBarChartDataCountRequest isData : meterCommCount) {
			
			ProcessOnDemandCommandData isresponse = new ProcessOnDemandCommandData();
			Double successPercentage = (Double.valueOf(isData.getSuccess_count())*100)/Double.valueOf(isData.getCount());
			
			isresponse.setCommand_date(isData.getDate());
			isresponse.setCommand_name(isData.getCommand_name());
			isresponse.setSuccess_percentage(successPercentage);
			
			isResponseList.add(isresponse);
		}
		processOnDemandCommandRepo.saveAll(isResponseList);
	}
	
	public void prepareProcessOnDemandCommandCombined(List<MeterBarChartDataCountRequest> singleCmdList, String outputListFull, List<MeterBarChartDataCountRequest> combinedList, String cmd, List<MeterBarChartDataCountRequest> fullCommandList, MeterDataVisualizationReq req, Set<String> cmdSet)
			throws JsonMappingException, JsonProcessingException, ParseException {
		
		List<MeterBarChartDataCountRequest> fullCmdList = new ArrayList<MeterBarChartDataCountRequest>();
		fullCmdList = CommonUtils.getMapper().readValue(outputListFull,
				new TypeReference<List<MeterBarChartDataCountRequest>>() {
		});
		fullCommandList.addAll(fullCmdList);
		boolean isNewData =false;
		for(MeterBarChartDataCountRequest isData : fullCmdList) {
			isNewData = false;
			cmdSet.add(cmd);
			for(MeterBarChartDataCountRequest data : singleCmdList) {
	            if (data.getCommand_name().equalsIgnoreCase(cmd) && data.getDate().equals(isData.getDate())) {
	            	
	            int successCount = Integer.valueOf(isData.getSuccess_count()) + Integer.valueOf(data.getSuccess_count());
	            int totalCount = Integer.valueOf(isData.getCount()) + Integer.valueOf(data.getCount());
	            MeterBarChartDataCountRequest newReq1 = new MeterBarChartDataCountRequest();
	            newReq1.setCommand_name(cmd);
	            newReq1.setSuccess_count(String.valueOf(successCount));
	            newReq1.setCount(String.valueOf(totalCount));
	            newReq1.setSuccess_percentage(String.valueOf(CommonUtils.decimalFormat((successCount*100)/totalCount)));
	            newReq1.setDate(isData.getDate());
	            
	            	combinedList.add(newReq1);
	            	isNewData = true;
	            }
		}
			if(!isNewData) {
				MeterBarChartDataCountRequest newReq = new MeterBarChartDataCountRequest();
				newReq.setCommand_name(cmd);
				newReq.setSuccess_count(isData.getSuccess_count());
				newReq.setCount(isData.getCount());
				newReq.setSuccess_percentage(String.valueOf(String.valueOf(CommonUtils.decimalFormat((Double.valueOf(isData.getSuccess_count())*100)/Double.valueOf(isData.getCount())))));
				newReq.setDate(isData.getDate());
				combinedList.add(newReq);
				
			}
		}
	}
	public void prepareMeterCountForSuccessCommand(List<MeterBarChartDataCountRequest> singleCmdList, String outputListFull)
			throws JsonMappingException, JsonProcessingException, ParseException {

		List<MeterBarChartDataCountRequest> fullCmdList = new ArrayList<MeterBarChartDataCountRequest>();
		fullCmdList = CommonUtils.getMapper().readValue(outputListFull,
				new TypeReference<List<MeterBarChartDataCountRequest>>() {
		});
		for(MeterBarChartDataCountRequest ispData : fullCmdList) {
			
		    for (MeterBarChartDataCountRequest data : singleCmdList) {
		    	
			 if(ispData.getDate().equals(data.getDate()) && ispData.getCommand_name().equalsIgnoreCase(data.getCommand_name())) {
			data.setDevice_count(String.valueOf(Integer.valueOf(ispData.getDevice_count()) + Integer.valueOf(data.getDevice_count())));
			 }
		  }
		}
		
	}

	public void prepareMeterCountForSuccessCommandRes(String outputList,
			List<DevicesCountResponse> ispResponseList) throws ParseException, JsonMappingException, JsonProcessingException {
		List<MeterBarChartDataCountRequest> dataList = new ArrayList<MeterBarChartDataCountRequest>();
		
		dataList = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<MeterBarChartDataCountRequest>>() {
		});

        Map<Date, Long> countByDateAndSerial = dataList.stream()
                .collect(Collectors.groupingBy(
                        MeterBarChartDataCountRequest::getDate,
                        Collectors.counting()
                ));

        // Print the result map
        countByDateAndSerial.forEach((date, count) -> {
        	DevicesCountResponse response = new DevicesCountResponse();
        	
				try {
					response.setDate(dateConverter.convertDateToHesDateString(date));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
        	response.setCount(String.valueOf(count));
        	ispResponseList.add(response);
        });
	}

	public void prepareDeviceCommandDrillDown(String outputList, List<DeviceCommandResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<DevicesCommand> deviceCommandData = new ArrayList<DevicesCommand>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesCommand>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");

		for (DevicesCommand ispData : deviceCommandData) {
			DeviceCommandResponse ispResponse = new DeviceCommandResponse();

			ispResponse.setDeviceSerialNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setCommandName(req.getCommand());
			ispResponse.setTrackingId(String.valueOf(ispData.getTracking_id()));
			ispResponse.setCommandCompletionDatetime(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: req.getReplaceBy());
			ispResponse.setMdasDatetime(
					ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
							: req.getReplaceBy());
			ispResponse.setStatus(req.getStatus().equalsIgnoreCase(Constants.SUCCESS) ? Constants.SUCCESS : ispData.getStatus());
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUserId(ispData.getUser_id());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Caster Added.");
	}

	public void prepareDeviceCommandSuccessDrillDown(String outputList, List<MeterDataRes> ispResponseList,
			MeterDataVisualizationReq req) throws JsonMappingException, JsonProcessingException {
		LOG.info("Device Command Data Caster called....");
		List<DevicesInfo> deviceData = new ArrayList<DevicesInfo>();
		deviceData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");

		for (DevicesInfo ispData : deviceData) {
			MeterDataRes ispResponse = new MeterDataRes();

			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setManufacturer(ispData.getMeter_type());
			ispResponse.setCommissioningStatus(ispData.getCommissioning_status());
			ispResponse.setDeviceType(ispData.getDevice_type());
			ispResponse.setDevMode(ispData.getDev_mode());
			ispResponse.setIpAddress(ispData.getIp_address_main());
			ispResponse.setPort(ispData.getIp_port_main());
			ispResponse.setConsumerName(ispData.getConsumer_name());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Caster Added.");
	}
}
