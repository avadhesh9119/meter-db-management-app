package com.global.meter.inventive.mdm.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.BillingDataCT;
import com.global.meter.business.model.BillingDataSinglePhase;
import com.global.meter.business.model.BillingDataThreePhase;
import com.global.meter.business.model.DailyLoadProfileCT;
import com.global.meter.business.model.DailyLoadProfileSinglePhase;
import com.global.meter.business.model.DailyLoadProfileThreePhase;
import com.global.meter.business.model.DeltaLoadProfileCT;
import com.global.meter.business.model.DeltaLoadProfileSinglePhase;
import com.global.meter.business.model.DeltaLoadProfileThreePhase;
import com.global.meter.business.model.EventDataCT;
import com.global.meter.business.model.EventDataSinglePhase;
import com.global.meter.business.model.EventDataThreePhase;
import com.global.meter.business.model.InstantaneousCt;
import com.global.meter.business.model.InstantaneousSinglePhase;
import com.global.meter.business.model.InstantaneousThreePhase;
import com.global.meter.business.model.LastBillingDataCT;
import com.global.meter.business.model.LastBillingDataSinglePhase;
import com.global.meter.business.model.LastBillingDataThreePhase;
import com.global.meter.business.model.NamePlate;
import com.global.meter.business.model.PushEventsSinglePhase;
import com.global.meter.business.model.PushInstantsSinglePhase;
import com.global.meter.business.model.PushInstantsThreePhase;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.service.MeterDataVisualizationMdmService;
import com.global.meter.inventive.mdm.utils.BillingDataMdmCaster;
import com.global.meter.inventive.mdm.utils.ConfigurationReadDataLogsMdmCaster;
import com.global.meter.inventive.mdm.utils.ConfigurationReadDataMdmCaster;
import com.global.meter.inventive.mdm.utils.DailyLPDataMdmCaster;
import com.global.meter.inventive.mdm.utils.DeltaLPDataMdmCaster;
import com.global.meter.inventive.mdm.utils.DeviceConfigLogsMdmCaster;
import com.global.meter.inventive.mdm.utils.DevicesCommandMdmCaster;
import com.global.meter.inventive.mdm.utils.EventDataMdmCaster;
import com.global.meter.inventive.mdm.utils.InstantaneousDataMdmCaster;
import com.global.meter.inventive.mdm.utils.MonthlyBillingMdmCaster;
import com.global.meter.inventive.mdm.utils.NamePlateDataMdmCaster;
import com.global.meter.inventive.mdm.utils.PushEventsDataMdmCaster;
import com.global.meter.inventive.mdm.utils.PushInstantDataMdmCaster;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ConfigurationReadDataLogsResponse;
import com.global.meter.inventive.models.ConfigurationReadDataResponse;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.DeviceConfigLogsResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.NamePlateDeviceResponse;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class MeterDataVisualizationMdmServiceImpl implements MeterDataVisualizationMdmService {

	private static final Logger LOG = LoggerFactory.getLogger(MeterDataVisualizationMdmServiceImpl.class);

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	InstantaneousDataMdmCaster instantCaster;

	@Autowired
	DailyLPDataMdmCaster dailyLPCaster;
	
	@Autowired
	ConfigurationReadDataMdmCaster configurationReadDataCaster;
	
	@Autowired
	ConfigurationReadDataLogsMdmCaster configurationReadDataLogsCaster;

	@Autowired
	DeltaLPDataMdmCaster deltaLPCaster;

	@Autowired
	EventDataMdmCaster eventCaster;

	@Autowired
	NamePlateDataMdmCaster namePlateCaster;
	
	@Autowired
	DeviceConfigLogsMdmCaster deviceConfigLogsCaster;

	@Autowired
	PushEventsDataMdmCaster pushEventsDataCaster;
	
	@Autowired
	PushInstantDataMdmCaster pushInstantDataCaster;
	
	@Autowired
	BillingDataMdmCaster billingCaster;
	
	@Autowired
	MonthlyBillingMdmCaster monthlyBillingCaster;
	
	@Autowired
	DevicesCommandMdmCaster devicesCommandCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getInstantData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
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
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {
				queryBuilder.append(" mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp) ")
						.append(" and mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<InstantaneousSinglePhase> singlePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<InstantaneousSinglePhase>>() {
						});
				response.setData(singlePhaseData);
				LOG.info("Instant Single Phase Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<InstantaneousThreePhase> threePhaseData  = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<InstantaneousThreePhase>>() {
						});
				response.setData(threePhaseData);
				LOG.info("Instant Three Phase Data Service Response by Hier  Success.");
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<InstantaneousCt> ctData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<InstantaneousCt>>() {
						});
				response.setData(ctData);
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
		LOG.info("Getting Daily LP Data from DB:--> ");
		try {

		    String table = "";
            
		if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {				
				table=meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_SP;
			}
			else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {	
			    table=	meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_TP;
			}
			else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				table=	meterConfiguration.getKeyspace() + "." + Tables.DAILY_LP_CT;		
			}
			else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
		
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {

				queryBuilder.append(" mdas_datetime > cast('").append(req.getFromDate()).append("' as timestamp) ")
						.append(" and mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<DailyLoadProfileSinglePhase> singlePhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<DailyLoadProfileSinglePhase>>() {});
				response.setData(singlePhaseData);
				LOG.info("Single Phase: Daily LP Data Service Response by Hier  Success.");
			} 
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<DailyLoadProfileThreePhase> threePhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<DailyLoadProfileThreePhase>>() {});
				response.setData(threePhaseData);
				LOG.info("Three Phase: Daily LP Data Service Response by Hier  Success.");
			}
			else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<DailyLoadProfileCT> ctPhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<DailyLoadProfileCT>>() {});
				response.setData(ctPhaseData);
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
		LOG.info("Getting Delta LP Data from DB:--> ");
		try {

			String table = "";
			if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_SP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_TP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.DELTA_LP_CT;
			}
			else {
				response.setMessage(Constants.WRONG_DEVICE_TYPE);
				response.setCode(404);
				response.setError(true);
				return response;
				}
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {
				queryBuilder.append(" mdas_datetime > cast('").append(req.getFromDate()).append("' as timestamp) ")
						.append(" and mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<DeltaLoadProfileSinglePhase> singlePhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<DeltaLoadProfileSinglePhase>>() {});
				response.setData(singlePhaseData);
				LOG.info("Delta LP Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){
				List<DeltaLoadProfileThreePhase> threePhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<DeltaLoadProfileThreePhase>>() {});
				response.setData(threePhaseData);
				LOG.info("Delta LP Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)){
				List<DeltaLoadProfileCT> ctData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<DeltaLoadProfileCT>>() {});
				response.setData(ctData);
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
		LOG.info("Getting Name Plate Data from DB:--> ");
		try {

			String deviceType = "";
 
            if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
            	deviceType = Constants.DeviceTypes.SINGLE_PHASE_ID;
            }else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
            	deviceType = Constants.DeviceTypes.THREE_PHASE_ID;
			}else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				deviceType = Constants.DeviceTypes.CT_METER_ID;
			}
            
            if(deviceType.isEmpty()) {
            	response.setError(true);
            	response.setCode(404);
            	response.setMessage("Device Type Not Found");
            	return response;
            }
 
			String table = meterConfiguration.getKeyspace() + "." + Tables.NAME_PLATE;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {

				queryBuilder.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
						.append(req.getToDate()).append("' as timestamp) and ");
			}
			if (!req.getDevType().contains(Constants.ALL)) {
				queryBuilder.append("meter_type = '").append(deviceType).append("' and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<NamePlate> namePlateData = new ArrayList<NamePlate>();
			namePlateData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<NamePlate>>() {
			});
			response.setData(namePlateData);
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
		LOG.info("Getting Name Plate History Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.NAME_PLATE_LOGS;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {
				queryBuilder.append(" mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp) ")
						.append(" and mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<NamePlate> ispResponseList = new ArrayList<>();
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
		LOG.info("Getting Billing Data from DB:--> ");
		try {
			
			String table = "";
			if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_SP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_TP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.BILLING_DATA_CT;
			}
			else {
				response.setMessage("Wrong Device type sent");
				response.setCode(404);
				response.setError(true);
				return response;
				}

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {
				queryBuilder.append(" mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp) ")
						.append(" and mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			
			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<BillingDataSinglePhase> singlePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<BillingDataSinglePhase>>() {
						});
				response.setData(singlePhaseData);
				LOG.info("Instant Single Phase Data Service Response by Hier  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<BillingDataThreePhase> threePhaseData  = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<BillingDataThreePhase>>() {
						});
				response.setData(threePhaseData);
				LOG.info("Instant Three Phase Data Service Response by Hier  Success.");
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<BillingDataCT> ctData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<BillingDataCT>>() {
						});
				response.setData(ctData);
				LOG.info("Instant CT Data Service Response by Hier  Success.");
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
		LOG.info("Getting event Data from DB:--> ");
		try {

            String table = "";
            if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
		
		         table=meterConfiguration.getKeyspace() + "." + Tables.EVENTS_LP_SP;
	           }
	           else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
		
	             table=	meterConfiguration.getKeyspace() + "." + Tables.EVENTS_LP_TP;
	           }
	           else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
		
		         table=	meterConfiguration.getKeyspace() + "." + Tables.EVENTS_LP_CT;		
	           }
	           else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
	
	          String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
	          String[] levels = req.getHier().getValue().split(",");

	          StringBuilder queryBuilder = new StringBuilder();
	          queryBuilder.append("select * from ").append(table).append(" where ");
				if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
						&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
					queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
							.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
							.append("' and ");
				} else {
					queryBuilder.append(" mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp) ")
							.append(" and mdas_datetime <= cast('").append(req.getToDate())
							.append("' as timestamp) and ");
				}
				queryBuilder.append(fieldName).append(" in (");
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
         		List<EventDataSinglePhase> singlePhaseData = CommonUtils.getMapper().readValue(outputList,
        						new TypeReference<List<EventDataSinglePhase>>() {});
		        response.setData(singlePhaseData);
		        LOG.info("Single Phase: Event Data Service Response by Hier  Success.");
	         } 
	         else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
	        	 List<EventDataThreePhase> threePhaseData = CommonUtils.getMapper().readValue(outputList,
	     						new TypeReference<List<EventDataThreePhase>>() {});
		        response.setData(threePhaseData);
		        LOG.info("Three Phase: Event Data Service Response by Hier  Success.");
	         }
	         else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
	        	 List<EventDataCT> ctData = CommonUtils.getMapper().readValue(outputList,
	     						new TypeReference<List<EventDataCT>>() {});
	        	response.setData(ctData);
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
		LOG.info("Getting Device Config Logs Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CONFIG_LOGS;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

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
				queryBuilder.append("'").append(level).append("',");
			}
			if(req.getStatus() != null && !req.getStatus().isEmpty()) {
				queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
				String[] status = req.getStatus().split(",");
			queryBuilder.append(") and lower(status) in (");
			for (String stat : status) {
				queryBuilder.append("'").append(stat.trim().toLowerCase()).append("',");
			    }
			
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") order by mdas_datetime desc");
			}else {
			query = query.concat(") and command_name like '%").concat(req.getCommand()).concat("%' order by mdas_datetime desc");
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
		LOG.info("Getting Configuration Read Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PREPAY_DATA;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] meterTypes = req.getManufacturer().split(",");
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");

			if(!req.getManufacturer().isEmpty())
			{
			queryBuilder.append(") and meter_type in (");
			for (String meterType : meterTypes) {
				queryBuilder.append("'").append(meterType).append("',");
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
			}else if(Constants.ALL.equalsIgnoreCase(req.getCommand())){
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
		LOG.info("Getting Configuration Read Data Logs from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS_PREPAY_LOGS;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

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
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			if(Constants.ALL.equalsIgnoreCase(req.getCommand())) {
				query = query.concat(") order by mdas_datetime desc");
			}else {
			query = query.concat(") and command_name like '%").concat(req.getCommand()).concat("%' order by mdas_datetime desc");
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
		LOG.info("Getting Monthly Data from DB:--> ");
		try {

			String table = "";
			if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.LAST_BILLING_DATA_SP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.LAST_BILLING_DATA_TP;
			}
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				table = meterConfiguration.getKeyspace() + "." + Tables.LAST_BILLING_DATA_CT;
			}
			else {
				response.setMessage("Wrong Device type sent");
				response.setCode(404);
				response.setError(true);
				return response;
				}
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if(req.getDataRequest() != null && !req.getDataRequest().isEmpty() && req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '")
			.append(req.getFromDate()).append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '")
			.append(req.getToDate()).append("' and ");
			}else {
				queryBuilder.append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
//			query = query.concat(") ").concat(new PaginationUtils().setOrderBy(req.getPageReq()));
			query = query.concat(") order by mdas_datetime desc");
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<LastBillingDataSinglePhase> singlePhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<LastBillingDataSinglePhase>>() {});
				response.setData(singlePhaseData);
				LOG.info("Monthly Billing Data Service Response by Hier for single phase meter Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<LastBillingDataThreePhase> ThreePhaseData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<LastBillingDataThreePhase>>() {});
				response.setData(ThreePhaseData);
				LOG.info("Monthly Billing Data Service Response by Hier for three phase  Success.");
			} else if(req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				List<LastBillingDataCT> ctData = CommonUtils.getMapper().readValue(outputList,
								new TypeReference<List<LastBillingDataCT>>() {});
				response.setData(ctData);
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
		LOG.info("Getting Event Push Data from DB:--> ");
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.PUSH_EVENTS_SP;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			
				if(fieldName=="subdevision_name") {
				fieldName="subdivision_name";
				}
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if(req.getDataRequest() != null && !req.getDataRequest().isEmpty() && req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '")
			.append(req.getFromDate()).append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '")
			.append(req.getToDate()).append("' and ");
			}else {
				queryBuilder.append(" meter_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and meter_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) and ");
			}
					if(req.getDevType() != null) {
						if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)){							
							queryBuilder.append("(device_type = '").append(Constants.DeviceTypes.SINGLE_PHASE).append("' or device_type is null) and ");
						}
						else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)){							
							queryBuilder.append("device_type = '").append(Constants.DeviceTypes.THREE_PHASE).append("' and ");
						}
						else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)){							
							queryBuilder.append("device_type = '").append(Constants.DeviceTypes.CT).append("' and ");
						}
						else if (req.getDevType().contains(ExternalConstants.DeviceTypes.LT_METER)){							
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
			
			List<PushEventsSinglePhase> pushEvent = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<PushEventsSinglePhase>>() {});
			response.setData(pushEvent);
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
		LOG.info("Getting Instant Push Data from DB:--> ");
		try {

			String table = req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)
					? meterConfiguration.getKeyspace() + "." + Tables.PUSH_INSTANT_SP
					: meterConfiguration.getKeyspace() + "." + Tables.PUSH_INSTANT_TP;

			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			
			if(fieldName=="subdevision_name") {
				fieldName="subdivision_name";
				}

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getDataRequest() != null && !req.getDataRequest().isEmpty()
					&& req.getDataRequest().equals(Constants.DataRequest.PUSH)) {
				queryBuilder.append(" date_trunc('second', mdas_datetime) > TIMESTAMP '").append(req.getFromDate())
						.append("' and date_trunc('second', mdas_datetime) <= TIMESTAMP '").append(req.getToDate())
						.append("' and ");
			} else {
				queryBuilder.append(" mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp) ")
						.append(" and mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			}
			queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by mdas_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<PushInstantsSinglePhase>	singlePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<PushInstantsSinglePhase>>() {
						});
				response.setData(singlePhaseData);
				LOG.info("Push Instant Data Service Response for single phase Success.");
			}  
			else if(req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
				List<PushInstantsThreePhase>	threePhaseData = CommonUtils.getMapper().readValue(outputList,
						new TypeReference<List<PushInstantsThreePhase>>() {
						});
				response.setData(threePhaseData);
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
		LOG.info("Getting recent relay status from DB:--> ");
		try {
 
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
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
	public CommonResponse getDataByQuery(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting data from DB:--> ");
		try {
 
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(req.getQuery()));
			
			response.setData(outputList);
			LOG.info("Get data by query Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}
