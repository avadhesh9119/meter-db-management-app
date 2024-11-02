package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.BillingCTResponse;
import com.global.meter.inventive.models.BillingSinglePhaseResponse;
import com.global.meter.inventive.models.BillingThreePhaseResponse;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ConfigurationReadDataLogsResponse;
import com.global.meter.inventive.models.ConfigurationReadDataResponse;
import com.global.meter.inventive.models.DailyLPCTResponse;
import com.global.meter.inventive.models.DailyLPSinglePhaseResponse;
import com.global.meter.inventive.models.DailyLPThreePhaseResponse;
import com.global.meter.inventive.models.DeltaLPCTResponse;
import com.global.meter.inventive.models.DeltaLPSinglePhaseResponse;
import com.global.meter.inventive.models.DeltaLPThreePhaseResponse;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.DeviceConfigLogsResponse;
import com.global.meter.inventive.models.EventsCTResponse;
import com.global.meter.inventive.models.EventsSinglePhaseResponse;
import com.global.meter.inventive.models.EventsThreePhaseResponse;
import com.global.meter.inventive.models.InstantaneousCtResponse;
import com.global.meter.inventive.models.InstantaneousSinglePhaseResponse;
import com.global.meter.inventive.models.InstantaneousThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.MonthlyBillingResponse;
import com.global.meter.inventive.models.MonthlyCTBillingResponse;
import com.global.meter.inventive.models.MonthlyThreePhaseBillingResponse;
import com.global.meter.inventive.models.NamePlateDeviceResponse;
import com.global.meter.inventive.models.NamePlateResponse;
import com.global.meter.inventive.models.PushEventsResponse;
import com.global.meter.inventive.models.PushInstantResponse;
import com.global.meter.inventive.models.PushInstantThreePhaseResponse;
import com.global.meter.inventive.service.MeterDataVisualizationService;
import com.global.meter.inventive.utils.BillingDataCaster;
import com.global.meter.inventive.utils.ConfigurationReadDataCaster;
import com.global.meter.inventive.utils.ConfigurationReadDataLogsCaster;
import com.global.meter.inventive.utils.DailyLPDataCaster;
import com.global.meter.inventive.utils.DeltaLPDataCaster;
import com.global.meter.inventive.utils.DeviceConfigLogsCaster;
import com.global.meter.inventive.utils.DevicesCommandCaster;
import com.global.meter.inventive.utils.EventDataCaster;
import com.global.meter.inventive.utils.ExternalConstants;

import com.global.meter.inventive.utils.InstantaneousDataCaster;
import com.global.meter.inventive.utils.MonthlyBillingCaster;
import com.global.meter.inventive.utils.NamePlateDataCaster;
import com.global.meter.inventive.utils.PushEventsDataCaster;
import com.global.meter.inventive.utils.PushInstantDataCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class MeterDataVisualizationServiceImpl implements MeterDataVisualizationService {

	private static final Logger LOG = LoggerFactory.getLogger(MeterDataVisualizationServiceImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	InstantaneousDataCaster instantCaster;

	@Autowired
	DailyLPDataCaster dailyLPCaster;
	
	@Autowired
	ConfigurationReadDataCaster configurationReadDataCaster;
	
	@Autowired
	ConfigurationReadDataLogsCaster configurationReadDataLogsCaster;

	@Autowired
	DeltaLPDataCaster deltaLPCaster;

	@Autowired
	EventDataCaster eventCaster;

	@Autowired
	NamePlateDataCaster namePlateCaster;
	
	@Autowired
	DeviceConfigLogsCaster deviceConfigLogsCaster;

	@Autowired
	PushEventsDataCaster pushEventsDataCaster;
	
	@Autowired
	PushInstantDataCaster pushInstantDataCaster;
	
	@Autowired
	BillingDataCaster billingCaster;
	
	@Autowired
	MonthlyBillingCaster monthlyBillingCaster;
	
	@Autowired
	DevicesCommandCaster devicesCommandCaster;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	DevicesInfoService devicesInfoService;

	@Override
	public CommonResponse getInstantData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Instant Data from DB:--> ");
		try {

			String table = "";
			if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.INSTANT_SP;
  			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){
				table = meterConfiguration.getKeyspace() + "." + Tables.INSTANT_TP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)){
				table = meterConfiguration.getKeyspace() + "." + Tables.INSTANT_CT;
			}
			
			if(table == "") {
				response.setError(true);
				response.setMessage(Constants.WRONG_DEVICE_TYPE);
				response.setCode(404);
			}
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" meter_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and meter_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<InstantaneousSinglePhaseResponse> ispResponseList = new ArrayList<>();
				instantCaster.prepareSinglePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Instant Single Phase Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<InstantaneousThreePhaseResponse> ispResponseList = new ArrayList<>();
				instantCaster.prepareThreePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Instant Three Phase Data Service Response by Hier  Success.");
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<InstantaneousCtResponse> ispResponseList = new ArrayList<>();
				instantCaster.prepareCt(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Instant CT Data Service Response by Hier  Success.");
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDailyLPData(MeterDataVisualizationReq req) {

		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Daily LP Data from DB:--> ");
		try {

		    String table = "";
            
		if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())) {				
				table=meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_SP;
			}
			else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(req.getDevType())) {	
			    table=	meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_TP;
			}
			else if (ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
				table=	meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_CT;
			}
			else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
		if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
		    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
		    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
			response.setMessage("Device Not Found");
			response.setCode(404);
			return response;
		   }
		}
		
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<DailyLPSinglePhaseResponse> ispResponseList = new ArrayList<>();
				dailyLPCaster.prepareSinglePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Single Phase: Daily LP Data Service Response by Hier  Success.");
			} 
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<DailyLPThreePhaseResponse> ispResponseList = new ArrayList<>();
				dailyLPCaster.prepareThreePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Three Phase: Daily LP Data Service Response by Hier  Success.");
			}
			else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<DailyLPCTResponse> ispResponseList = new ArrayList<>();
				dailyLPCaster.prepareCT(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("CT: Daily LP Data Service Response by Hier  Success.");
			
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getDeltaLPData(MeterDataVisualizationReq req) {

		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Delta LP Data from DB:--> ");
		try {

			String table = "";
			if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_SP;
			}
			else if(ExternalConstants.DeviceTypes.THREE_PHASE.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_TP;
			}
			else if(ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_CT;
			}
			else {
				response.setMessage(Constants.WRONG_DEVICE_TYPE);
				response.setCode(404);
				response.setError(true);
				return response;
				}
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" interval_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and interval_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<DeltaLPSinglePhaseResponse> ispResponseList = new ArrayList<>();
				deltaLPCaster.prepareSinglePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Delta LP Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){
				List<DeltaLPThreePhaseResponse> ispResponseList = new ArrayList<>();
				deltaLPCaster.prepareThreePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Delta LP Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)){
				List<DeltaLPCTResponse> ispResponseList = new ArrayList<>();
				deltaLPCaster.prepareCT(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Delta LP Data Service Response by Hier  Success.");
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getNamePlateData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Name Plate Data from DB:--> ");
		try {

			String deviceType = "";
 
            if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
            	deviceType = Constants.DeviceTypes.SINGLE_PHASE_ID;
            }
            else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
            	deviceType = Constants.DeviceTypes.THREE_PHASE_ID;
			}
            else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				deviceType = Constants.DeviceTypes.CT_METER_ID;
			}
            
            if(deviceType.isEmpty()) {
            	response.setError(true);
            	response.setCode(404);
            	response.setMessage("Device Type Not Found");
            	return response;
            }
 
			String table = meterConfiguration.getKeyspace() + "." + Tables.NAME_PLATE;
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ");
					if(!req.getDevType().contains(Constants.ALL)) {
						queryBuilder.append("meter_type = '").append(deviceType).append("' and ");
					}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<NamePlateResponse> ispResponseList = new ArrayList<>();
			namePlateCaster.prepareNamePlate(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("NamePlate Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
	
	@Override
	public CommonResponse getNamePlateHistoryData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Name Plate History Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.NAME_PLATE_LOGS;
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<NamePlateResponse> ispResponseList = new ArrayList<>();
			namePlateCaster.prepareNamePlate(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("NamePlate Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getBillingData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Billing Data from DB:--> ");
		try {
			
			String table = "";
			if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_SP;
			}
			else if(ExternalConstants.DeviceTypes.THREE_PHASE.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_TP;
			}
			else if(ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_CT;
			}
			else {
				response.setMessage("Wrong Device type sent");
				response.setCode(404);
				response.setError(true);
				return response;
				}
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" billing_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and billing_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<BillingSinglePhaseResponse> ispResponseList = new ArrayList<>();
				billingCaster.prepareSinglePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Single Phase Billing Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){
				List<BillingThreePhaseResponse> ispResponseList = new ArrayList<>();
				billingCaster.prepareThreePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Three Phase Billing Data Service Response by Hier  Success.");
			}else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)){
				List<BillingCTResponse> ispResponseList = new ArrayList<>();
				billingCaster.prepareCT(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("CT Meter Billing Data Service Response by Hier  Success.");
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getEventData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting event Data from DB:--> ");
		try {

            String table = "";
            if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())) {
		
		         table=meterConfiguration.getKeyspace() + "." + Tables.EVENTS_LP_SP;
	           }
	           else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(req.getDevType())) {
		
	             table=	meterConfiguration.getKeyspace() + "." + Tables.EVENTS_LP_TP;
	           }
	           else if (ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
		
		         table=	meterConfiguration.getKeyspace() + "." + Tables.EVENTS_LP_CT;
	           }
	           else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
            
            if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
            
	          String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
	          String[] levels = req.getHier().getValue().split(",");

	          StringBuilder queryBuilder = new StringBuilder();
	          queryBuilder.append("select * from ").append(table).append(" where ").append(" event_datetime >= cast('")
			  .append(req.getFromDate()).append("' as timestamp) ").append(" and event_datetime <= cast('")
			  .append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
	         for (String level : levels) {
	        	queryBuilder.append("'").append(level).append("',");
	         }
	        String query = queryBuilder.substring(0, queryBuilder.length() - 1);
	        query = query.concat(") ");
	        if (!StringUtils.isEmpty(req.getEventType())) {
		    if(!Constants.ALL.equalsIgnoreCase(req.getEventType())) {
			   query = query.concat(" and event_category = '").concat(req.getEventType()).concat(" Related' order by mdas_datetime desc");	
		    }else {
			  query = query.concat(" order by mdas_datetime desc");	
		    }
		
	      }

	      String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

         	if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
		        List<EventsSinglePhaseResponse> ispResponseList = new ArrayList<>();
	         	eventCaster.prepareSinglePhase(outputList, ispResponseList, req);
		        response.setData(ispResponseList);
		        LOG.info("Single Phase: Event Data Service Response by Hier  Success.");
	         } 
	         else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
	        	List<EventsThreePhaseResponse> ispResponseList = new ArrayList<>();
		        eventCaster.prepareThreePhase(outputList, ispResponseList, req);
		        response.setData(ispResponseList);
		        LOG.info("Three Phase: Event Data Service Response by Hier  Success.");
	         }
	         else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
	        	List<EventsCTResponse> ispResponseList = new ArrayList<>();
		        eventCaster.prepareCT(outputList, ispResponseList, req);
	        	response.setData(ispResponseList);
        		LOG.info("CT: Event Data Service Response by Hier  Success.");
         	}
         	
		    } catch (Exception e) {
			  LOG.error("Issue in fetching data due to : " + e.getMessage());
			  response = ExceptionHandlerConfig.setErrorData(e);
		   }
		
		     return response;
	      }

	@Override
	public CommonResponse getDeviceConfigLog(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Device Config Logs Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG_LOGS;
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] meterTypes = req.getManufacturer().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				queryBuilder.append("select * from ");
			}else {
				queryBuilder.append("select *, concat(cast('{\"").append(req.getCommand())
				.append("\" : \"' as varchar) , concat(cast(json_extract(command_name,'$.").append(req.getCommand())
				.append("') as varchar), '\"}')) mod_command_name , ");
				
				// append command status
				queryBuilder.append("concat(cast('{\"").append(req.getCommand())
				.append("\" : \"' as varchar) , concat(cast(json_extract(command_status,'$.").append(req.getCommand())
				.append("') as varchar), '\"}')) mod_command_status from ");
			}
			queryBuilder.append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("'");
			}
			queryBuilder.append(") and  ");
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				if (Constants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())){							
					queryBuilder.append("device_type = '").append(Constants.DeviceTypes.SINGLE_PHASE);
				}
				else if (Constants.DeviceTypes.THREE_PHASE.contains(req.getDevType())){							
					queryBuilder.append("device_type = '").append(Constants.DeviceTypes.THREE_PHASE);
				}
				else if (Constants.DeviceTypes.CT.contains(req.getDevType())){							
					queryBuilder.append("device_type = '").append(Constants.DeviceTypes.CT);
				}
				else if (Constants.DeviceTypes.LT_METER.contains(req.getDevType())){							
					queryBuilder.append("device_type = '").append(Constants.DeviceTypes.LT_METER);
				}
				queryBuilder.append("'");
			}
			
			queryBuilder.replace(queryBuilder.length(), queryBuilder.length(), "");
			if(!req.getManufacturer().isEmpty())
			{
			queryBuilder.append(" and lower(meter_type) in (");
			for (String meterType : meterTypes) {
				queryBuilder.append("'").append(meterType.trim().toLowerCase()).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
			}

			if(req.getStatus() != null && !req.getStatus().isEmpty()) {
				String[] status = req.getStatus().split(",");
			queryBuilder.append(" and lower(status) in (");
			for (String stat : status) {
				queryBuilder.append("'").append(stat.trim().toLowerCase()).append("',");
			    }
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
			}
			String query = queryBuilder.substring(0, queryBuilder.length());
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(" order by mdas_datetime desc");
			}else {
			query = query.concat(" and command_name like '%").concat(req.getCommand()).concat("%' order by mdas_datetime desc");
			}

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceConfigLogsResponse> ispResponseList = new ArrayList<>();
			deviceConfigLogsCaster.prepareDeviceConfigLogs(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Device Config Log Service Data Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getConfigurationReadDataByHier(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Configuration Read Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PREPAY_DATA;
			

			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] meterTypes = req.getManufacturer().split(",");
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append("and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("'");
			}
			queryBuilder.append(") and  ");
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			if(req.getDevType() != null) {
				if (Constants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.SINGLE_PHASE);
				}
				else if (Constants.DeviceTypes.THREE_PHASE.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.THREE_PHASE);
				}
				else if (Constants.DeviceTypes.CT.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.CT);
				}
				else if (Constants.DeviceTypes.LT_METER.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.LT_METER);
				}
				queryBuilder.append("'");
			}
			queryBuilder.replace(queryBuilder.length(), queryBuilder.length(), "");
			if(!req.getManufacturer().isEmpty())
			{
			queryBuilder.append(") and lower(meter_type) in (");
			for (String meterType : meterTypes) {
				queryBuilder.append("'").append(meterType.trim().toLowerCase()).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");

			}

			String query = queryBuilder.substring(0, queryBuilder.length());
			if(ConfigCommands.BILLING_DATES.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and billing_dates is not null order by mdas_datetime desc");
			}else if(ConfigCommands.RTC_ClOCK.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and rtc_clock is not null order by mdas_datetime desc");
			}else if(ConfigCommands.PING_METER.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and ping_meter is not null order by mdas_datetime desc");
			}else if(ConfigCommands.ACTIVITY_CALENDER.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and tod is not null order by mdas_datetime desc");
			}else if(ConfigCommands.LAST_TOKEN_RECHARGE_AMOUNT.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and last_token_recharge_amount is not null order by mdas_datetime desc");
			}else if(ConfigCommands.TOTAL_AMOUNT_AT_LAST_RECHARGE.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and total_amount_at_last_recharge is not null order by mdas_datetime desc");
			}else if(ConfigCommands.LAST_TOKEN_RECHARGE_TIME.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and last_token_recharge_time is not null order by mdas_datetime desc");
			}else if(ConfigCommands.CURRENT_BALANCE_AMOUNT.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and current_balance_amount is not null order by mdas_datetime desc");
			}else if(ConfigCommands.CURRENT_BALANCE_TIME.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and current_balance_time is not null order by mdas_datetime desc");
			}else if(ConfigCommands.PAYMENT_MODE.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and payment_mode is not null order by mdas_datetime desc");
			}else if(ConfigCommands.DEMAND_INTEGRATION_PERIOD.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and demand_integration_period is not null order by mdas_datetime desc");
			}else if(ConfigCommands.PROFILE_CAPTURE_PERIOD.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and profile_capture_period is not null order by mdas_datetime desc");
			}else if(ConfigCommands.LOAD_LIMIT.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and load_limit is not null order by mdas_datetime desc");
			}else if(ConfigCommands.INSTANT_IP_PUSH.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and instant_push_ip is not null order by mdas_datetime desc");
			}else if(ConfigCommands.ALERT_IP_PUSH.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and alert_push_ip is not null order by mdas_datetime desc");
			}else if(ConfigCommands.ACTIVITY_SCHEDULE_PUSH.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and push_setup_duration is not null order by mdas_datetime desc");
			}else if(ConfigCommands.METERING_MODE.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and metering_mode is not null order by mdas_datetime desc");
			}else if(ConfigCommands.BILLING_DATES.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and billing_dates is not null order by mdas_datetime desc");
			}else if(ConfigCommands.ENABLE_DISABLE_DISCONNECT_CONTROL.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and enable_disable_control_modes is not null order by mdas_datetime desc");
			}
			else if(ConfigCommands.RELAY_STATUS.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and relay_status is not null order by mdas_datetime desc");
			}
			else if(ConfigCommands.APN.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and apn is not null order by mdas_datetime desc");
			}
			else if(ConfigCommands.IPV6_ADDRESS.commandName.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") and ipv6_address is not null order by mdas_datetime desc");
			}
			else if(Constants.ALL.equalsIgnoreCase(req.getCommand())){
				query = query.concat(") order by mdas_datetime desc");
			}else {
				 response.setMessage(Constants.WRONG_COMMAND_TYPE);
				 response.setCode(404);
				 response.setError(true);
				 return response;
			}

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<ConfigurationReadDataResponse> ispResponseList = new ArrayList<>();
			configurationReadDataCaster.prepareConfigurationReadData(outputList, ispResponseList,req);
			response.setData(ispResponseList);
			LOG.info("Configuration Read Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getConfigurationReadDataLogsByHier(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Configuration Read Data Logs from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS_PREPAY_LOGS;
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] meterTypes = req.getManufacturer().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				queryBuilder.append("select * from ");
			}else {
				queryBuilder.append("select *, concat(cast('{\"").append(req.getCommand())
				.append("\" : \"' as varchar) , concat(cast(json_extract(command_name,'$.").append(req.getCommand())
				.append("') as varchar), '\"}')) mod_command_name from ");
			}
			
			queryBuilder.append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("') and ");
			}
			
			
			if(req.getDevType() != null) {
				if (Constants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.SINGLE_PHASE);
				}
				else if (Constants.DeviceTypes.THREE_PHASE.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.THREE_PHASE);
				}
				else if (Constants.DeviceTypes.CT.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.CT);
				}
				else if (Constants.DeviceTypes.LT_METER.contains(req.getDevType())){							
					queryBuilder.append("device_type = ('").append(Constants.DeviceTypes.LT_METER);
				}
				queryBuilder.append("')");
			}
			
			if(!req.getManufacturer().isEmpty())
			{
			queryBuilder.append(" and lower(meter_type) in (");
			for (String meterType : meterTypes) {
				queryBuilder.append("'").append(meterType.trim().toLowerCase()).append("',");
			}
			
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");

			}
			if(req.getStatus() != null && !req.getStatus().isEmpty()) {
				String[] status = req.getStatus().split(",");
			queryBuilder.append(" and lower(status) in (");
			for (String stat : status) {
				queryBuilder.append("'").append(stat.trim().toLowerCase()).append("',");
			    }
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
			}
			
			String query = queryBuilder.substring(0, queryBuilder.length());
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(" order by mdas_datetime desc");
			}else {
			query = query.concat(" and command_name like '%").concat(req.getCommand()).concat("%' order by mdas_datetime desc");
			}

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<ConfigurationReadDataLogsResponse> ispResponseList = new ArrayList<>();
			configurationReadDataLogsCaster.prepareConfigurationReadDataLogs(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Configuration Read Data Logs Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getMonthlyBillingProfile(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Monthly Data from DB:--> ");
		try {

			String table = "";
			if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.LAST_BILLING_DATA_SP;
			}
			else if(ExternalConstants.DeviceTypes.THREE_PHASE.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.LAST_BILLING_DATA_TP;
			}
			else if(ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
				table = meterConfiguration.getKeyspace() + "." + Tables.LAST_BILLING_DATA_CT;
			}
			else {
				response.setMessage("Wrong Device type sent");
				response.setCode(404);
				response.setError(true);
				return response;
				}
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" billing_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and billing_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
//			query = query.concat(") ").concat(new PaginationUtils().setOrderBy(req.getPageReq()));
			query = query.concat(") order by mdas_datetime desc");
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<MonthlyBillingResponse> ispResponseList = new ArrayList<>();
				monthlyBillingCaster.prepareSinglePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Monthly Billing Data Service Response by Hier for single phase meter Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<MonthlyThreePhaseBillingResponse> ispResponseList = new ArrayList<>();
				monthlyBillingCaster.prepareThreePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Monthly Billing Data Service Response by Hier for three phase  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<MonthlyCTBillingResponse> ispResponseList = new ArrayList<>();
				monthlyBillingCaster.prepareCT(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Monthly Billing Data Service Response by Hier for CT meter Success.");
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getEventsPushData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Event Push Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PUSH_EVENTS_SP;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
				if(fieldName=="subdevision_name") {
				fieldName="subdivision_name";
				}
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" meter_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and meter_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ");
					if(req.getDevType() != null) {
						if (Constants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())){							
							queryBuilder.append("(device_type = '").append(Constants.DeviceTypes.SINGLE_PHASE).append("' or device_type is null) and ");
						}
						else if (Constants.DeviceTypes.THREE_PHASE.contains(req.getDevType())){							
							queryBuilder.append("device_type = '").append(Constants.DeviceTypes.THREE_PHASE).append("' and ");
						}
						else if (Constants.DeviceTypes.CT.contains(req.getDevType())){							
							queryBuilder.append("device_type = '").append(Constants.DeviceTypes.CT).append("' and ");
						}
						else if (Constants.DeviceTypes.LT_METER.contains(req.getDevType())){							
							queryBuilder.append("device_type = '").append(Constants.DeviceTypes.LT_METER).append("' and ");
						}
					}
					if(req.getEventName() != null && !req.getEventName().isEmpty()) {
						String[] events = req.getEventName().split(",");
						queryBuilder.append("(");
					for (String event : events) {
						queryBuilder.append("lower(event_data_desc) like '%").append(event.trim().toLowerCase()).append("%'").append(" or ");
					}
					queryBuilder.replace(queryBuilder.length()-4, queryBuilder.length(), ") and ");
					}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			
			List<PushEventsResponse> ispResponseList = new ArrayList<>();
			pushEventsDataCaster.prepareSinglePhase(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Event Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getInstantPushData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Instant Push Data from DB:--> ");
		try {

			String table = req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)
					? meterConfiguration.getKeyspace() + "." + Tables.PUSH_INSTANT_SP
					: meterConfiguration.getKeyspace() + "." + Tables.PUSH_INSTANT_TP;
			
//			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
//			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
//			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
//				response.setMessage("Device Not Found");
//				response.setCode(404);
//				return response;
//			   }
//			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			
			if(fieldName=="subdevision_name") {
				fieldName="subdivision_name";
				}

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" meter_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and meter_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<PushInstantResponse> ispResponseList = new ArrayList<>();
				pushInstantDataCaster.prepareSinglePhase(outputList, ispResponseList,req);
				response.setData(ispResponseList);
				LOG.info("Push Instant Data Service Response for single phase Success.");
			}  
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<PushInstantThreePhaseResponse> ispResponseList = new ArrayList<>();
				pushInstantDataCaster.prepareThreePhase(outputList, ispResponseList, req);
				response.setData(ispResponseList);
				LOG.info("Push Instant Data Service Response for three phase Success.");
				
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getNamePlateDevicesInfo(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Name Plate Device Info from DB:--> ");
		try {
 
			String table = meterConfiguration.getKeyspace() + "." + Tables.NAME_PLATE;
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append("device_serial_number = ('")
					.append(req.getDeviceSerialNo()).append("')");
			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<NamePlateDeviceResponse> ispResponseList = new ArrayList<>();
			namePlateCaster.prepareNamePlateDeviceInfo(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("NamePlate Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getRecentRelayStatus(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting recent relay status from DB:--> ");
		try {
 
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select serial, device_serial_number, command_name, status, command_completion_datetime from (")
			.append("select row_number() over (partition by device_serial_number,command_name")
			.append(" order by device_serial_number, command_name, command_completion_datetime desc) as Serial, device_serial_number, command_name,status,command_completion_datetime from ")
			.append(table).append(" where ");
			
			if(req.getCommand() != null && !req.getCommand().isEmpty()) {
				String[] commands = req.getCommand().split(",");
				queryBuilder.append("command_name in (");
			for (String command : commands) {
			    queryBuilder.append("'").append(command).append("',");
		        }
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and ");
			}
			
			if(req.getStatus() != null && !req.getStatus().isEmpty()) {
				String[] status = req.getStatus().split(",");
			queryBuilder.append("lower(status) in (");
			for (String stat : status) {
				queryBuilder.append("'").append(stat.trim().toLowerCase()).append("',");
			    }
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and ");
			}
			
			queryBuilder.append(fieldName).append(" in (");
			
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(")) t WHERE t.serial=1");
			
			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
			
			devicesCommandCaster.prepareDeviceCommands(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Recent relay status Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getPushEventsPowerFailure(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting Event Push Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PUSH_EVENTS_SP;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
				if(fieldName=="subdevision_name") {
				fieldName="subdivision_name";
				}
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select t1.* from ").append(table).append(" t1 inner join (")
			.append(" select device_serial_number, MAX(mdas_datetime) AS LatestTimestamp from ").append(table)
			.append(" where event_data_desc like '%Last Gasp - Occurrence%' and ");
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				queryBuilder.append(" device_type = '").append(req.getDevType()).append("' and ");
				
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
			queryBuilder.append(" GROUP BY device_serial_number )")
			.append(" t2 on t1.device_serial_number = t2.device_serial_number AND t1.mdas_datetime = t2.LatestTimestamp ")
			.append("where not exists (")
			.append(" select 1 from ").append(table).append(" t3 where t3.device_serial_number = t1.device_serial_number")
			.append(" AND t3.mdas_datetime > t1.mdas_datetime AND t3.event_data_desc like '%First Breath - Restoration%')");
			
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			
			List<PushEventsResponse> ispResponseList = new ArrayList<>();
			pushEventsDataCaster.prepareSinglePhase(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Event Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse invalidRelayStatusReport(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		DevicesInfo devicesInfo = null;
		LOG.info("Getting invalid relay status report Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PREPAY_DATA;
			
			if(req.getHier().getName().equals(Constants.HIRE_NAME)) {
			    devicesInfo = devicesInfoService.getDevicesInfo(req.getHier().getValue());
			    if(devicesInfo == null || !devicesInfo.getDevice_type().contains(req.getDevType())) {
				response.setMessage("Device Not Found");
				response.setCode(404);
				return response;
			   }
			}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append("and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ")
					.append("relay_status IS NOT NULL AND current_relay_status IS NOT NULL AND relay_status != current_relay_status and ")
					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");

			if(req.getDevType() != null ) {
				if (Constants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())){							
					queryBuilder.append(" and device_type = '").append(Constants.DeviceTypes.SINGLE_PHASE);
				}
				else if (Constants.DeviceTypes.THREE_PHASE.contains(req.getDevType())){							
					queryBuilder.append(" and device_type = '").append(Constants.DeviceTypes.THREE_PHASE);
				}
				else if (Constants.DeviceTypes.CT.contains(req.getDevType())){							
					queryBuilder.append(" and device_type = '").append(Constants.DeviceTypes.CT);
				}
				else if (Constants.DeviceTypes.LT_METER.contains(req.getDevType())){							
					queryBuilder.append(" and device_type = '").append(Constants.DeviceTypes.LT_METER);
				}
				queryBuilder.append("'");
			}
			if(req.getManufacturer() != null && !req.getManufacturer().isEmpty())
			{
			queryBuilder.append(" and lower(meter_type) in (");
			for (String meterType : req.getManufacturer().split(",")) {
				queryBuilder.append("'").append(meterType.trim().toLowerCase()).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
			}
			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<ConfigurationReadDataResponse> ispResponseList = new ArrayList<>();
			configurationReadDataCaster.prepareInvalidRelayStatus(outputList, ispResponseList,req);
			response.setData(ispResponseList);
			LOG.info("Configuration Read Data Service Response by Hier  Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getRecentConfigurationReport(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Recent Configuration Report:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.devInfo;

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append("device_serial_number = ('")
					.append(req.getDeviceSerialNo()).append("')");
			String query = queryBuilder.substring(0, queryBuilder.length());
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<ConfigurationReadDataResponse> ispResponseList = new ArrayList<>();
			configurationReadDataCaster.prepareRecentConfigurationReadData(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("Recent Configuration Read Data Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
	
}
