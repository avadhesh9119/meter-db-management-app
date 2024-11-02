package com.global.meter.inventive.utils;

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
import com.global.meter.business.model.PullEventsCategory;
import com.global.meter.data.repository.PullEventsCategoryRepository;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.PullEventsResponse;
import com.global.meter.inventive.models.PushEventdataRequest;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class PullEventsDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(FirmwareConfigCaster.class);

	@Autowired
	DateConverter dateConverter;

	@Autowired
	PullEventsCategoryRepository pullEventsCategoryRepository;

	public void prepareCategorizedEvent(String outputList, List<PullEventsResponse> ispResponseList, String devInfoList)
			throws Exception {
		LOG.info("Categorized Event Data Caster called....");
		int criticalCount = 0;
		int nonCriticalCount = 0;
		PullEventsResponse ispResponse = new PullEventsResponse();
		List<PushEventdataRequest> pushEventData = new ArrayList<PushEventdataRequest>();
		List<DevicesInfo> devinfo = new ArrayList<DevicesInfo>();
		Set<String> ownerSet = new HashSet<>();
		Set<String> criticalEvent = new HashSet<>();

		pushEventData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<PushEventdataRequest>>() {
		});
		devinfo = CommonUtils.getMapper().readValue(devInfoList, new TypeReference<List<DevicesInfo>>() {
		});

		for (DevicesInfo info : devinfo) {
			if (!ownerSet.contains(info.getOwner_name())) {
				PullEventsCategory pEvent = new PullEventsCategory();
				Optional<PullEventsCategory> priorityEvent = pullEventsCategoryRepository
						.findById(info.getOwner_name());
				pEvent = priorityEvent.get();
				criticalEvent.addAll(
						pEvent.getCritical_event_list() != null ? pEvent.getCritical_event_list() : criticalEvent);
			}
			ownerSet.add(info.getOwner_name());
		}
		LOG.info("Categorized Event Data Response Data Adding....");

		ispResponse.setCritical(String.valueOf(criticalCount));
		ispResponse.setNonCritical(String.valueOf(nonCriticalCount));
		for (PushEventdataRequest ispData : pushEventData) {
			if (ispData.getCount() != null && criticalEvent.contains(ispData.getEvent_name())) {
				criticalCount = criticalCount + Integer.parseInt(ispData.getCount());
				ispResponse.setCritical(String.valueOf(criticalCount));
			} else {
				nonCriticalCount = nonCriticalCount + Integer.parseInt(ispData.getCount());
				ispResponse.setNonCritical(String.valueOf(nonCriticalCount));
			}
		}
		ispResponseList.add(ispResponse);
		LOG.info("Categorized Event Data Response Data Added....");
	}

	public void prepareCategorizedEventList(String outputList, List<PullEventsResponse> ispResponseList,
			String devInfoList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Categorized Event List Data Caster called....");
		List<PushEventdataRequest> pushEventData = new ArrayList<PushEventdataRequest>();
		List<DevicesInfo> devinfo = new ArrayList<DevicesInfo>();
		Set<String> ownerSet = new HashSet<>();
		Set<String> criticalEvent = new HashSet<>();

		pushEventData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<PushEventdataRequest>>() {
		});
		devinfo = CommonUtils.getMapper().readValue(devInfoList, new TypeReference<List<DevicesInfo>>() {
		});
		for (DevicesInfo info : devinfo) {
			if (!ownerSet.contains(info.getOwner_name())) {
				PullEventsCategory pEvent = new PullEventsCategory();
				Optional<PullEventsCategory> priorityEvent = pullEventsCategoryRepository
						.findById(info.getOwner_name());
				pEvent = priorityEvent.get();
				criticalEvent.addAll(pEvent.getCritical_event_list());
			}
			ownerSet.add(info.getOwner_name());
		}
		LOG.info("Categorized Event List Data Response Data Adding....");

		for (PushEventdataRequest ispData : pushEventData) {
			PullEventsResponse ispResponse = new PullEventsResponse();
			if (req.getEventCategory() != null && !req.getEventCategory().isEmpty() && ispData.getCount() != null
					&& criticalEvent.contains(ispData.getEvent_name())
					&& req.getEventCategory().equalsIgnoreCase(Constants.CRITICAL_EVENTS)) {
				ispResponse.setCount(ispData.getCount());
				ispResponse.setEventName(ispData.getEvent_name());
				ispResponseList.add(ispResponse);
			} else if (req.getEventCategory() != null && !req.getEventCategory().isEmpty()
					&& !criticalEvent.contains(ispData.getEvent_name())
					&& req.getEventCategory().equalsIgnoreCase(Constants.NON_CRITICAL_EVENTS)) {
				ispResponse.setCount(ispData.getCount());
				ispResponse.setEventName(ispData.getEvent_name());
				ispResponseList.add(ispResponse);
			}
		}
		LOG.info("Categorized Event List Data Response Data Added....");
	}
}
