package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import com.global.meter.business.model.PriorityEvent;
import com.global.meter.business.model.PullEventsCategory;
import com.global.meter.data.repository.PriorityEventRepository;
import com.global.meter.data.repository.PullEventsCategoryRepository;
import com.global.meter.inventive.controller.MeterDataVisualizationController;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.EventCategory;
import com.global.meter.inventive.models.EventsCTResponse;
import com.global.meter.inventive.models.EventsSinglePhaseResponse;
import com.global.meter.inventive.models.EventsThreePhaseResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PriorityEventRequest;
import com.global.meter.inventive.models.PushEventsResponse;
import com.global.meter.inventive.service.PushEventAlarmService;
import com.global.meter.inventive.utils.EventDataCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.PushEventsDataCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class PushEventAlarmServiceImpl implements PushEventAlarmService {
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	PushEventsDataCaster pushEventsDataCaster;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	PriorityEventRepository priorityEventRepository;
	
	@Autowired
	PullEventsCategoryRepository pullEventsCategoryRepository;
	
	@Autowired
	EventDataCaster eventCaster;

	private static final Logger LOG = LoggerFactory.getLogger(MeterDataVisualizationController.class);
	
	@Override
	public CommonResponse getEventsPushData(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Event Push Data from DB.");
		try {
			String fieldName = "";
			String table =meterConfiguration.getKeyspace() + "." + Tables.PUSH_EVENTS_SP;
			String devInfoTable =meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			if(req.getHier().getName().equals("2")) {
				fieldName = Constants.Subdivision.SUBDIVISION;
			}else {
				fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			}
			
			String[] levels = req.getHier().getValue().split(",");
					
			StringBuilder queryBuilder = new StringBuilder();
			StringBuilder devInfoQuery = new StringBuilder();
			if(req.getEventType()!=null)
			{
				LOG.info("Generate Query For Event Push Data Drilldown.");
				
				String[] events = req.getEventType().split(",");
				queryBuilder.append("SELECT * FROM ").append(table).append(" where (");
				for (String event : events) {
					queryBuilder.append("event_data_desc like '%").append(event.trim()).append("%'").append(" or ");
				}
				 queryBuilder.replace(queryBuilder.length()-4, queryBuilder.length(), "");
				queryBuilder.append(") and meter_datetime >= cast('")
				.append(req.getFromDate()).append("' as timestamp) ").append(" and meter_datetime <= cast('")
				.append(req.getToDate()).append("' as timestamp) and ");
				if(req.getDevType() != null && !req.getDevType().isEmpty()) {
					queryBuilder.append("device_type = '").append(req.getDevType()).append("' and ");
					}
					queryBuilder.append(fieldName).append(" in (");
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("')");
				}
			}
			else {
				LOG.info("Generate Query For Event Push Data Count.");
				
				queryBuilder.append("Select event as event_name, count(*) as count from (select replace(event,']','') as event from (select replace(event,'[','') as event from (SELECT event FROM ")
				.append(table).append(" CROSS JOIN UNNEST(SPLIT(event_data_desc,',')) AS t (event) ").append("where")
				.append(" meter_datetime >= cast('")
				.append(req.getFromDate()).append("' as timestamp) ").append(" and meter_datetime <= cast('")
				.append(req.getToDate()).append("' as timestamp) and ");
				if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				queryBuilder.append("device_type = '").append(req.getDevType()).append("' and ");
				}
				queryBuilder.append(fieldName).append(" in (");
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("'");
				}
				 queryBuilder.append(")))) group by event");
			}	
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<PushEventsResponse> ispResponseList = new ArrayList<>();
				if(req.getCategorizedEvent() != null && req.getCategorizedEvent().equals(Constants.CATEGORIZED_EVENTS)) {
					
					devInfoQuery.append("select distinct owner_name from ").append(devInfoTable).append(" where ").append(fieldName).append(" in (");
					for (String level : levels) {
						devInfoQuery.append("'").append(level).append("'");
					}
					devInfoQuery.append(")");
					String queString=devInfoQuery.substring(0, devInfoQuery.length());
					String devInfoList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queString));

					pushEventsDataCaster.prepareCategorizedEvent(outputList, ispResponseList, devInfoList);
				}
                else if(req.getCategorizedEventList() != null && req.getCategorizedEventList().equals(Constants.CATEGORIZED_EVENTS_LIST)) {
					
					devInfoQuery.append("select distinct owner_name from ").append(devInfoTable).append(" where ").append(fieldName).append(" in (");
					for (String level : levels) {
						devInfoQuery.append("'").append(level).append("'");
					}
					devInfoQuery.append(")");
					String queString=devInfoQuery.substring(0, devInfoQuery.length());
					String devInfoList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queString));

					pushEventsDataCaster.prepareCategorizedEventList(outputList, ispResponseList, devInfoList, req);
				}
				else {
					
				    pushEventsDataCaster.prepareSinglePhasePushEvent(outputList, ispResponseList);
				}
				response.setData(ispResponseList);
				LOG.info("Event Push Data from DB Sucess.");


		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse addEventsCategory(List<PriorityEventRequest> req) {
		List<PriorityEvent> priorityEvents = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		Set<Object> pEventSet = new HashSet<>();
		Set<String> pushEventSet = new HashSet<>();
		Set<Object> pEventSetProp = new HashSet<>();
		Properties pushProp = null;
		Properties pullProp = null;
		Collection<Object> set;
		LOG.info("Wrapping push Event Data Owner to save in DB:--> ");	
		try {
			
			pushProp = commonUtils.getPushEventPropertiesFile();		
			if(pushProp  != null) {
			set = pushProp.values();
			pEventSet= set.stream().distinct().collect(Collectors.toSet());
			pEventSet.remove(Constants.RESERVED);
			pushEventSet = pEventSet.stream().map(String::valueOf).collect(Collectors.toSet());
			}	
			pullProp = commonUtils.getPullEventPropertiesFile();		
			if(pullProp  != null) {
			set = pullProp.values();
			pEventSetProp= set.stream().distinct().collect(Collectors.toSet());
			}	
			for (PriorityEventRequest dataRequest : req) {
				PriorityEvent pEvent = new PriorityEvent();
				if (dataRequest.getOwnerName() == null || dataRequest.getOwnerName().isEmpty()) {
					error.setErrorMessage("Invalid owner name");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				if (!StringUtils.isEmpty(dataRequest.getOwnerName())) {
					Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(dataRequest.getOwnerName());

					if (priorityEvent.isPresent()) {
						error.setErrorMessage(
								dataRequest.getOwnerName() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				if (dataRequest.getHighPriorityEventList() != null && !dataRequest.getHighPriorityEventList().isEmpty() &&
						pEventSetProp.containsAll(dataRequest.getHighPriorityEventList())) {
					
					pEvent.setHigh_priority_event_list(dataRequest.getHighPriorityEventList());
				}
				if (dataRequest.getCriticalEventList() != null && !dataRequest.getCriticalEventList().isEmpty() &&
						pEventSet.containsAll(dataRequest.getCriticalEventList())) {
					
					pEvent.setCritical_event_list(dataRequest.getCriticalEventList());
					pushEventSet.removeAll(dataRequest.getCriticalEventList());
				}
		        pEvent.setNon_critical_event_list(pushEventSet);
				pEvent.setOwner_name(dataRequest.getOwnerName());
				pEvent.setCreated(new Date(System.currentTimeMillis()));
				pEvent.setSource(dataRequest.getSource());
				pEvent.setCreated_by(dataRequest.getCreatedBy());
				pEvent.setModified(new Date(System.currentTimeMillis()));

				priorityEvents.add(pEvent);
			}
			priorityEventRepository.saveAll(priorityEvents);
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
	public CommonResponse updateEventsCategory(List<PriorityEventRequest> req){
		
		List<PriorityEvent> priorityEvents = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		Set<Object> pushEventSetProp = new HashSet<>();
		Set<Object> pullEventSetProp = new HashSet<>();
		Set<String> pushEventSet = new HashSet<>();
		
		LOG.info("Wrapping High Priority and critical Event Data to add in DB:--> ");
		try {
			Properties pushProp =commonUtils.getPushEventPropertiesFile();
			Collection<Object> pushSet = pushProp.values();
			pushEventSetProp= pushSet.stream().distinct().collect(Collectors.toSet());
		    pushEventSetProp.remove(Constants.RESERVED);
		    pushEventSet = pushEventSetProp.stream().map(String::valueOf).collect(Collectors.toSet());
		    
		    Properties pullProp =commonUtils.getPullEventPropertiesFile();
			Collection<Object> pullSet = pullProp.values();
			pullEventSetProp= pullSet.stream().distinct().collect(Collectors.toSet());

			
			for (PriorityEventRequest dataRequest : req) {
				PriorityEvent pEvent = new PriorityEvent();
				Set<String> prioritySet=new HashSet<String>();
				Set<String> criticalSet=new HashSet<String>();
				
				if (!StringUtils.isEmpty(dataRequest.getOwnerName())) {
					Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(dataRequest.getOwnerName());

					if (!priorityEvent.isPresent()) {
						error.setErrorMessage(
								dataRequest.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					pEvent = priorityEvent.get();
				}
					prioritySet.addAll(dataRequest.getHighPriorityEventList() != null ? dataRequest.getHighPriorityEventList() : prioritySet);
					if(pullEventSetProp.containsAll(prioritySet)) {
					pEvent.setHigh_priority_event_list(prioritySet);
					}
					criticalSet.addAll(dataRequest.getCriticalEventList() != null ? dataRequest.getCriticalEventList() : criticalSet);
					pEvent.setCritical_event_list(criticalSet);
					pushEventSet.removeAll(criticalSet);
					pEvent.setNon_critical_event_list(pushEventSet);
				    pEvent.setOwner_name(dataRequest.getOwnerName() != null ? dataRequest.getOwnerName() : pEvent.getOwner_name());				
      			    pEvent.setUpdated_by(dataRequest.getUpdatedBy() != null ? dataRequest.getUpdatedBy() : pEvent.getUpdated_by());
				    pEvent.setModified(new Date(System.currentTimeMillis()));
				    pEvent.setSource(dataRequest.getSource());
				    priorityEvents.add(pEvent);
				
			}
			priorityEventRepository.saveAll(priorityEvents);
			LOG.info("High Priority and critical Events Info Successfully add in DB.");
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
		
		LOG.info("Wrapping High Priority and critical Event Data to get from DB:--> ");
        PushEventsResponse isResponse = new PushEventsResponse();
        Set<String> empSet = new HashSet<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		try {
				if (!StringUtils.isEmpty(req.getOwnerName())) {
					Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(req.getOwnerName());

					if (!priorityEvent.isPresent()) {
						error.setErrorMessage(
								req.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}		
				PriorityEvent pEvent = priorityEvent.get();
				isResponse.setHighPriorityEvents(pEvent.getHigh_priority_event_list() != null ? pEvent.getHigh_priority_event_list() : empSet);
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
	public CommonResponse deleteEventsCategory(PriorityEventRequest req) {
		List<PriorityEvent> priorityEvents = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		PriorityEvent pEvent = new PriorityEvent();
		Set<String> prioritySet=new HashSet<String>();
		Set<String> criticalSet=new HashSet<String>();
		Set<String> nonCriticalSet=new HashSet<String>();
		boolean isPriority = false;
		boolean isCritical = false;
		String msg = "";
		LOG.info("Wrapping High Priority and critical Event Data to delete from DB:--> ");

		try {
				if (!StringUtils.isEmpty(req.getOwnerName())) {
					Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(req.getOwnerName());

					if (!priorityEvent.isPresent()) {
						error.setErrorMessage(
								req.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;	
					}
					pEvent = priorityEvent.get();
					
					if (pEvent.getHigh_priority_event_list() != null && req.getHighPriorityEventList() != null && !req.getHighPriorityEventList().isEmpty() &&
							pEvent.getHigh_priority_event_list().containsAll(req.getHighPriorityEventList())) {
						
					    prioritySet.addAll(pEvent.getHigh_priority_event_list());
						prioritySet.removeAll(req.getHighPriorityEventList());
						pEvent.setHigh_priority_event_list(prioritySet);
						msg = Constants.PRIORITY_EVENTS+" ";
						isPriority = true;
					}
					if (pEvent.getCritical_event_list() != null && req.getCriticalEventList() != null && !req.getCriticalEventList().isEmpty() &&
							pEvent.getCritical_event_list().containsAll(req.getCriticalEventList())) {
						
						criticalSet.addAll(pEvent.getCritical_event_list());
						criticalSet.removeAll(req.getCriticalEventList());
						pEvent.setCritical_event_list(criticalSet);
						nonCriticalSet.addAll(pEvent.getNon_critical_event_list());
						nonCriticalSet.addAll(req.getCriticalEventList());
						pEvent.setNon_critical_event_list(nonCriticalSet);
						msg = Constants.CRITICAL_EVENTS+" ";
						isCritical = true;
					}
					if(!isPriority && !isCritical)
					{
						error.setErrorMessage("Events is not exist in Priority and critical list.");
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					else if (isPriority && isCritical) {
						msg = "";
					}
				}	
				pEvent.setOwner_name(req.getOwnerName() != null ? req.getOwnerName()
						: pEvent.getOwner_name());				
      			pEvent.setUpdated_by(req.getUpdatedBy() != null ? req.getUpdatedBy() : pEvent.getUpdated_by());
				pEvent.setModified(new Date(System.currentTimeMillis()));
				priorityEvents.add(pEvent);
				
			priorityEventRepository.saveAll(priorityEvents);
			LOG.info("High Priority and critical Events Info Successfully deleted from DB.");

			response.setCode(200);
			response.setMessage(msg+ExternalConstants.Message.DELETED);
		} catch (Exception e) {
			LOG.error("Issue in delete data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getPushEventsList() {
		
		LOG.info("Wrapping Push Events Data to get from PushEventsPropertisFile:--> ");
        PushEventsResponse isResponse = new PushEventsResponse();
		CommonResponse response = new CommonResponse();
		Set<Object> pushEventSet = new HashSet<>();
		Set<Object> pullEventSet = new HashSet<>();
		
		try {
			Properties pushProp =commonUtils.getPushEventPropertiesFile();
			Collection<Object> pushSet = pushProp.values();
			pushEventSet= pushSet.stream().distinct().collect(Collectors.toSet());
		    pushEventSet.remove(Constants.RESERVED);
		    
		    Properties pullProp =commonUtils.getPullEventPropertiesFile();
			Collection<Object> pullSet = pullProp.values();
			pullEventSet= pullSet.stream().distinct().collect(Collectors.toSet());
			
		    isResponse.setPushEventsList(pushEventSet);
		    isResponse.setPullEventsList(pullEventSet);
		    response.setData(isResponse);
			LOG.info("Push Events Service Data Response Success.");
				
		} catch (Exception e) {
			LOG.error("Issue in get Push Events List data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getHighPriorityEvents(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		StringBuilder queryBuilder = new StringBuilder();
		StringBuilder devInfoQuery = new StringBuilder();
		
		LOG.info("Getting Event Push Data from DB.");
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
				LOG.info("Generate Query For Event Push Data Drilldown.");
				
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
				LOG.info("Generate Query For Event Push Data Count.");
				
				queryBuilder.append("SELECT event_type as event_name , count(event_type) as count FROM ").append(table).append(" where event_type is not null and ")
				.append(" event_datetime >= cast('")
				.append(req.getFromDate()).append("' as timestamp) ").append(" and event_datetime <= cast('")
				.append(req.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
				
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("'");
				}
				queryBuilder.append(") group by event_type");
				String highPreQuery=queryBuilder.substring(0, queryBuilder.length());
				String highPriorityList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(highPreQuery));
				
				devInfoQuery.append("select distinct owner_name from ").append(devInfoTable).append(" where ").append(fieldName).append(" in (");
				for (String level : levels) {
					devInfoQuery.append("'").append(level).append("'");
				}
				devInfoQuery.append(")");
				String queString=devInfoQuery.substring(0, devInfoQuery.length());
				String devInfoList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queString));
				List<PushEventsResponse> ispResponseList = new ArrayList<>();
				pushEventsDataCaster.prepareHighPriorityEvent(highPriorityList, ispResponseList, devInfoList);
				response.setData(ispResponseList);
			}	
				
				LOG.info("Event Push Data from DB Sucess.");


		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	
    @Override
	public CommonResponse getPushEventsCategoryList(PriorityEventRequest req) {
	    LOG.info("Wrapping Pull Events Category Data to get from PullEventsPropertiesFile:--> ");
	    PushEventsResponse isResponse = new PushEventsResponse();
	    CommonResponse response = new CommonResponse();
	    HashMap<String,List<String>> pullEventCategorySet  = new HashMap<>();
	    HashSet<String> criticalEvent = new HashSet<>();

	    try {
	        if(!StringUtils.isEmpty(req.getOwnerName())) 
	        {
	            PullEventsCategory pEvent = new PullEventsCategory();
	            Optional<PullEventsCategory> pullEventCategory = pullEventsCategoryRepository.findById(req.getOwnerName());
	            if(pullEventCategory.isPresent()){
//					error.setErrorMessage(
//							req.getOwnerName() + " : " + ExternalConstants.Message.NOT_EXISTS);
//					response.setCode(400);
//					response.setError(true);
//					response.addErrorMessage(error);
//					return response;
					pEvent = pullEventCategory.get();
		            criticalEvent.addAll(pEvent.getCritical_event_list() != null ? pEvent.getCritical_event_list() : criticalEvent);
				}
	         }
//	        else {
//				error.setErrorMessage("Invalid owner : "+req.getOwnerName());
//				response.setCode(400);
//				response.setError(true);
//				response.addErrorMessage(error);
//				return response;
//			}
	        Set<Object> pushEventSet = new HashSet<>(commonUtils.getPushEventPropertiesFile().values());
		    pushEventSet.remove(Constants.RESERVED);

	        Properties pullProp = commonUtils.getPullEventCategoryPropertiesFile();

	        if (pullProp != null) {
	            pullProp.values().stream()
	                    .distinct()
	                    .map(Object::toString)
	                    .filter(s -> s.contains(":"))
	                    .map(s -> s.split(":"))
	                    .peek(arr -> pullEventCategorySet
	                            .computeIfAbsent(arr[0].trim(), k -> new ArrayList<>())
	                            .add(arr[1].trim()))
	                    .collect(Collectors.toSet());

	            List<EventCategory> eventCategoriesList = pullEventCategorySet.entrySet().stream()
	                    .map(entry -> {
	                        EventCategory eventCategory = new EventCategory();
	                        eventCategory.setType(entry.getKey());
	                        Map<String, List<String>> eventsMap = new LinkedHashMap<>();
	                        eventsMap.put(Constants.CRITICAL_EVENTS, new ArrayList<>());
	                        eventsMap.put(Constants.NON_CRITICAL_EVENTS, new ArrayList<>());
	                        for (String event : entry.getValue()) {
	                            if (criticalEvent.contains(event)) {
	                                eventsMap.get(Constants.CRITICAL_EVENTS).add(event);
	                            } else {
	                                eventsMap.get(Constants.NON_CRITICAL_EVENTS).add(event);
	                            }
	                        }
	                        eventCategory.setEvents(eventsMap);
	                        return eventCategory;
	                    })
	                    .collect(Collectors.toList());

	            isResponse.setPullEventsList(eventCategoriesList);
	            isResponse.setPushEventsList(pushEventSet);
	            response.setData(isResponse);
	            LOG.info("Push Events Category Service Data Response Success.");
	        } else {
	            throw new Exception("Pull Event Properties not found.");
	        }

	    } catch (Exception e) {
	        LOG.error("Issue in get Push Events Category List data due to : " + e.getMessage());
	        response = ExceptionHandlerConfig.setErrorData(e);
	    }
	    return response;
	}
}