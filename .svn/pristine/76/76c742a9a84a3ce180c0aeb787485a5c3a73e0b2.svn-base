package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.global.meter.business.model.PullEventsCategory;
import com.global.meter.data.repository.PullEventsCategoryRepository;
import com.global.meter.inventive.controller.MeterDataVisualizationController;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.EventsCTResponse;
import com.global.meter.inventive.models.EventsSinglePhaseResponse;
import com.global.meter.inventive.models.EventsThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PriorityEventRequest;
import com.global.meter.inventive.models.PullEventsResponse;
import com.global.meter.inventive.models.PushEventsResponse;
import com.global.meter.inventive.service.PullEventCategoryService;
import com.global.meter.inventive.utils.EventDataCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.PullEventsDataCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class PullEventCategoryServiceImpl implements PullEventCategoryService {
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	PullEventsDataCaster pullEventsDataCaster;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	PullEventsCategoryRepository pullEventsCategoryRepository;
	
	@Autowired
	EventDataCaster eventCaster;

	private static final Logger LOG = LoggerFactory.getLogger(MeterDataVisualizationController.class);
	
	@Override
	public CommonResponse addEventsCategory(PriorityEventRequest req) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		Set<String> pEventSetProp = new HashSet<>();
		Properties pullProp = null;
		LOG.info("Wrapping pull Event Data Owner to save in DB:--> ");	
		try {
			
			pullProp = commonUtils.getPullEventPropertiesFile();		
			if(pullProp  != null) {
			pEventSetProp= pullProp.values().stream().map(String::valueOf).collect(Collectors.toSet());
			}	
				PullEventsCategory pEvent = new PullEventsCategory();
				if (req.getOwnerName() == null || req.getOwnerName().isEmpty()) {
					error.setErrorMessage("Invalid owner name");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				if (!StringUtils.isEmpty(req.getOwnerName())) {
					Optional<PullEventsCategory> priorityEvent = pullEventsCategoryRepository.findById(req.getOwnerName());

					if (priorityEvent.isPresent()) {
						error.setErrorMessage(
								req.getOwnerName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				if (req.getCriticalEventList() != null && !req.getCriticalEventList().isEmpty() &&
						pEventSetProp.containsAll(req.getCriticalEventList())) {
					
					pEvent.setCritical_event_list(req.getCriticalEventList());
					pEventSetProp.removeAll(req.getCriticalEventList());
				}
		        pEvent.setNon_critical_event_list(pEventSetProp);
				pEvent.setOwner_name(req.getOwnerName());
				pEvent.setCreated(new Date(System.currentTimeMillis()));
				pEvent.setSource(req.getSource());
				pEvent.setCreated_by(req.getCreatedBy());
				pEvent.setModified(new Date(System.currentTimeMillis()));

			pullEventsCategoryRepository.save(pEvent);
			LOG.info("Push Events Owner Successfully Stored in DB.");

			response.setCode(200);
			response.setMessage(ExternalConstants.Message.ADDED);
		} catch (Exception e) {
			LOG.error("Issue in storing data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse updateEventsCategory(PriorityEventRequest req){
		
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		Set<String> pullEventSet = new HashSet<>();
		
		LOG.info("Wrapping critical and non-critical Event Data to add in DB:--> ");
		try {
		    
		    Properties pullProp =commonUtils.getPullEventPropertiesFile();
		    pullEventSet= pullProp.values().stream().map(String::valueOf).collect(Collectors.toSet());

				PullEventsCategory pEvent = new PullEventsCategory();
				Set<String> criticalSet=new HashSet<String>();
				
				if (!StringUtils.isEmpty(req.getOwnerName())) {
					Optional<PullEventsCategory> categoryEvent = pullEventsCategoryRepository.findById(req.getOwnerName());

					if (!categoryEvent.isPresent()) {
						error.setErrorMessage(
								req.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					pEvent = categoryEvent.get();
				}
					criticalSet.addAll(req.getCriticalEventList() != null ? req.getCriticalEventList() : criticalSet);
					pEvent.setCritical_event_list(criticalSet);
					pullEventSet.removeAll(criticalSet);
					pEvent.setNon_critical_event_list(pullEventSet);
				    pEvent.setOwner_name(req.getOwnerName() != null ? req.getOwnerName() : pEvent.getOwner_name());				
      			    pEvent.setUpdated_by(req.getUpdatedBy() != null ? req.getUpdatedBy() : pEvent.getUpdated_by());
				    pEvent.setModified(new Date(System.currentTimeMillis()));
				    pEvent.setSource(req.getSource());
				    
			pullEventsCategoryRepository.save(pEvent);
			LOG.info("Critical and non-critical Events Info Successfully updated in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in add data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
	
	@Override
	public CommonResponse getEventsCategory(PriorityEventRequest req) {
		
		LOG.info("Wrapping High critical and non-critical Event Data to get from DB:--> ");
        PushEventsResponse isResponse = new PushEventsResponse();
        Set<String> empSet = new HashSet<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		try {
				if (!StringUtils.isEmpty(req.getOwnerName())) {
					Optional<PullEventsCategory> priorityEvent = pullEventsCategoryRepository.findById(req.getOwnerName());

					if (!priorityEvent.isPresent()) {
						error.setErrorMessage(
								req.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}		
				PullEventsCategory pEvent = priorityEvent.get();
				isResponse.setCriticalEvents(pEvent.getCritical_event_list() != null ? pEvent.getCritical_event_list() : empSet);
				isResponse.setNonCritical(pEvent.getNon_critical_event_list() != null ? pEvent.getNon_critical_event_list() : empSet);
				}
		        response.setData(isResponse);
				LOG.info("EventsCategory Service Data Response Success.");
				
		} catch (Exception e) {
			LOG.error("Issue in get EventsCategory data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}
	
	@Override
	public CommonResponse getPullEventsCategoryCountDetail(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		StringBuilder queryBuilder = new StringBuilder();
		StringBuilder devInfoQuery = new StringBuilder();
		
		LOG.info("Getting Event Pull Data from DB.");
		try {
			String devInfoTable =meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			String table;
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
			if(req.getEventType()!=null && !req.getEventType().isEmpty())
			{
				LOG.info("Generate Query For Event Pull Data Drilldown.");
				
				String[] events = req.getEventType().split(",");
				queryBuilder.append("SELECT * FROM ").append(table).append(" where event_type is not null and ")
				.append(" event_datetime >= cast('")
				.append(req.getFromDate()).append("' as timestamp) ").append(" and event_datetime <= cast('")
				.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
				
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("'");
				}
				queryBuilder.append(") and event_type in (");
				for (String event : events) {
					queryBuilder.append("'").append(event.trim()).append("',");
				}
				String query = queryBuilder.substring(0, queryBuilder.length()-1);
				query=query.concat(")");
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
			}
			else {
				LOG.info("Generate Query For Event Pull Data Count.");
				
				queryBuilder.append("SELECT event_type as event_name , count(event_type) as count FROM ").append(table).append(" where event_type is not null and ")
				.append(" event_datetime >= cast('")
				.append(req.getFromDate()).append("' as timestamp) ").append(" and event_datetime <= cast('")
				.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
				
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("'");
				}
				queryBuilder.append(") group by event_type");
				String cQuery=queryBuilder.substring(0, queryBuilder.length());
				String categoryList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(cQuery));
				
				List<PullEventsResponse> ispResponseList = new ArrayList<>();
				if(req.getCategorizedEvent() != null && req.getCategorizedEvent().equals(Constants.CATEGORIZED_EVENTS)) {
					
				devInfoQuery.append("select distinct owner_name from ").append(devInfoTable).append(" where ").append(fieldName).append(" in (");
				for (String level : levels) {
					devInfoQuery.append("'").append(level).append("'");
				}
				devInfoQuery.append(")");
				String queString=devInfoQuery.substring(0, devInfoQuery.length());
				String devInfoList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queString));
				
				pullEventsDataCaster.prepareCategorizedEvent(categoryList, ispResponseList, devInfoList);
				
					}
				 else if(req.getCategorizedEventList() != null && req.getCategorizedEventList().equals(Constants.CATEGORIZED_EVENTS_LIST)) {
						
						devInfoQuery.append("select distinct owner_name from ").append(devInfoTable).append(" where ").append(fieldName).append(" in (");
						for (String level : levels) {
							devInfoQuery.append("'").append(level).append("'");
						}
						devInfoQuery.append(")");
						String queString=devInfoQuery.substring(0, devInfoQuery.length());
						String devInfoList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queString));

						pullEventsDataCaster.prepareCategorizedEventList(categoryList, ispResponseList, devInfoList, req);
					}
				response.setData(ispResponseList);
			}	
				
				LOG.info("Event Push Data from DB Sucess.");


		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
