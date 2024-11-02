package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.PriorityEvent;
import com.global.meter.business.model.PushEventsSinglePhase;
import com.global.meter.data.repository.PriorityEventRepository;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PushEventdataRequest;
import com.global.meter.inventive.models.PushEventsResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class PushEventsDataMdmCaster {
	private static final Logger LOG = LoggerFactory.getLogger(PushEventsDataMdmCaster.class);

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	PriorityEventRepository priorityEventRepository;

	public void prepareSinglePhase(String outputList, List<PushEventsSinglePhase> ispResponseList)
			throws Exception {
		LOG.info("Push Event Data Caster called....");
		List<PushEventsSinglePhase> singlePhaseData = new ArrayList<PushEventsSinglePhase>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushEventsSinglePhase>>() {
				});
		LOG.info("Push Event Data Response Data Adding....");

		for (PushEventsSinglePhase ispData : singlePhaseData) {
			PushEventsSinglePhase ispResponse = new PushEventsSinglePhase();

			ispResponse.setDevice_serial_number(ispData.getDevice_serial_number());
			ispResponse.setEvent_data_desc(ispData.getEvent_data_desc());
			ispResponse.setEvent_data_val(ispData.getEvent_data_val());
			
			ispResponse.setMdas_datetime(ispData.getMdas_datetime());
			ispResponse.setMeter_datetime(ispData.getMeter_datetime());
			
			ispResponseList.add(ispResponse);
		}
		LOG.info("Push Event Data Response Data Added....");
	}
	public void prepareSinglePhasePushEvent(String outputList, List<PushEventsResponse> ispResponseList)
			throws Exception {
		LOG.info("Push Event Data Caster called....");
		List<PushEventdataRequest> singlePhaseData = new ArrayList<PushEventdataRequest>();
		singlePhaseData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushEventdataRequest>>() {
				});
		LOG.info("Push Event Data Response Data Adding....");

		for (PushEventdataRequest ispData : singlePhaseData) {
			PushEventsResponse ispResponse = new PushEventsResponse();

			if(ispData.getCount()!=null) {
				ispResponse.setCount(ispData.getCount());
				ispResponse.setEventName(ispData.getEvent_name());
				ispResponseList.add(ispResponse);
			}
			else {
				
			ispResponse.setMeterNumber(ispData.getDevice_serial_number());
			ispResponse.setEventVal(ispData.getEvent_data_val());		
			ispResponse.setHesDatetime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setMeterDatetime(dateConverter.convertDateToHesString(ispData.getMeter_datetime()));			
			ispResponse.setDt(ispData.getDt_name());
			ispResponse.setFeeder(ispData.getFeeder_name());
			ispResponse.setSubdivision_name(ispData.getSubdivision_name());
			ispResponse.setSubstation_name(ispData.getSubstation_name());

			ispResponseList.add(ispResponse);
			}
		}
		LOG.info("Push Event Data Response Data Added....");
	}

	public void prepareHighPriorityEvent(String outputList, List<PushEventsResponse> ispResponseList, String devInfoList)
			throws Exception {
		LOG.info("High Priority Event Data Caster called....");
		
		List<PushEventdataRequest> pushEventData = new ArrayList<PushEventdataRequest>();
		List<DevicesInfo> devinfo = new ArrayList<DevicesInfo>();
		Set<String> ownerSet=new HashSet<>();
		Set<String> priorityEvents=new HashSet<>();
		pushEventData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushEventdataRequest>>() {
				});
		devinfo = CommonUtils.getMapper().readValue(devInfoList,
				new TypeReference<List<DevicesInfo>>() {
			
				});
		for (DevicesInfo  info:devinfo) {
			if(!ownerSet.contains(info.getOwner_name())) {
				PriorityEvent pEvent = new PriorityEvent();
				Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(info.getOwner_name());
				pEvent = priorityEvent.get();
				priorityEvents.addAll(pEvent.getHigh_priority_event_list() != null ? pEvent.getHigh_priority_event_list() : priorityEvents);
			}
			ownerSet.add(info.getOwner_name());
		}

		LOG.info("High Priority Event Data Response Data Adding....");

		for (PushEventdataRequest ispData : pushEventData) {
			PushEventsResponse ispResponse = new PushEventsResponse();

			if(ispData.getCount()!=null && priorityEvents.contains(ispData.getEvent_name().
					substring(0, ispData.getEvent_name().length()))){
				ispResponse.setCount(ispData.getCount());
				ispResponse.setEventName(ispData.getEvent_name());
				ispResponseList.add(ispResponse);
			}	
		}
		LOG.info("High Priority Event Data Response Data Added....");
	}

	public void prepareCategorizedEvent(String outputList, List<PushEventsResponse> ispResponseList, String devInfoList)
			throws Exception {
		LOG.info("Categorized Event Data Caster called....");
		int criticalCount=0;
		int nonCriticalCount=0;
		PushEventsResponse ispResponse = new PushEventsResponse();
		List<PushEventdataRequest> pushEventData = new ArrayList<PushEventdataRequest>();
		List<DevicesInfo> devinfo = new ArrayList<DevicesInfo>();
		Set<String> ownerSet=new HashSet<>();		
		Set<String> criticalEvent=new HashSet<>();
		
		pushEventData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushEventdataRequest>>() {
				});
		devinfo = CommonUtils.getMapper().readValue(devInfoList,
				new TypeReference<List<DevicesInfo>>() {
				});
		
		for (DevicesInfo  info : devinfo) {
			if(!ownerSet.contains(info.getOwner_name())) {
				PriorityEvent pEvent = new PriorityEvent();
				Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(info.getOwner_name());
				pEvent = priorityEvent.get();
				criticalEvent.addAll(pEvent.getCritical_event_list() != null ? pEvent.getCritical_event_list() : criticalEvent);
			}
			ownerSet.add(info.getOwner_name());
		}
		LOG.info("Categorized Event Data Response Data Adding....");

		ispResponse.setCritical(String.valueOf(criticalCount));
		ispResponse.setNonCritical(String.valueOf(nonCriticalCount));
		for (PushEventdataRequest ispData : pushEventData) {
			if(ispData.getCount()!=null && criticalEvent.contains(ispData.getEvent_name().
					substring(1, ispData.getEvent_name().length()-1))) {
				criticalCount=criticalCount +Integer.parseInt(ispData.getCount());
				ispResponse.setCritical(String.valueOf(criticalCount));
			} 
			else {
				nonCriticalCount=nonCriticalCount +Integer.parseInt(ispData.getCount());
				ispResponse.setNonCritical(String.valueOf(nonCriticalCount));
			}
		}
		ispResponseList.add(ispResponse);
		LOG.info("Categorized Event Data Response Data Added....");
	}
	
	public void prepareCategorizedEventList(String outputList, List<PushEventsResponse> ispResponseList, String devInfoList, MeterDataVisualizationReq req)
			throws Exception {
		LOG.info("Categorized Event List Data Caster called....");
		List<PushEventdataRequest> pushEventData = new ArrayList<PushEventdataRequest>();
		List<DevicesInfo> devinfo = new ArrayList<DevicesInfo>();
	    Set<String> ownerSet=new HashSet<>();
		Set<String> criticalEvent=new HashSet<>();
		
		pushEventData = CommonUtils.getMapper().readValue(outputList,
				new TypeReference<List<PushEventdataRequest>>() {
				});
		devinfo = CommonUtils.getMapper().readValue(devInfoList,
				new TypeReference<List<DevicesInfo>>() {
				});
		for (DevicesInfo  info : devinfo) {
			if(!ownerSet.contains(info.getOwner_name())) {
				PriorityEvent pEvent = new PriorityEvent();
				Optional<PriorityEvent> priorityEvent = priorityEventRepository.findById(info.getOwner_name());
				pEvent = priorityEvent.get();
				criticalEvent.addAll(pEvent.getCritical_event_list());
			}
			ownerSet.add(info.getOwner_name());
		}
		LOG.info("Categorized Event List Data Response Data Adding....");

		for (PushEventdataRequest ispData : pushEventData) {
			PushEventsResponse ispResponse = new PushEventsResponse();
			if(ispData.getCount()!=null && criticalEvent.contains(ispData.getEvent_name().substring(1, ispData.getEvent_name().length()-1)) &&
					req.getEventCategory().equalsIgnoreCase(Constants.CRITICAL_EVENTS)) {
				ispResponse.setCount(ispData.getCount());
				ispResponse.setEventName(ispData.getEvent_name());
				ispResponseList.add(ispResponse);
			}
			else if(!criticalEvent.contains(ispData.getEvent_name().substring(1, ispData.getEvent_name().length()-1)) 
					&& req.getEventCategory().equalsIgnoreCase(Constants.NON_CRITICAL_EVENTS)) {
				ispResponse.setCount(ispData.getCount());
				ispResponse.setEventName(ispData.getEvent_name());
				ispResponseList.add(ispResponse);
			}
		}
		LOG.info("Categorized Event List Data Response Data Added....");
	}
}
