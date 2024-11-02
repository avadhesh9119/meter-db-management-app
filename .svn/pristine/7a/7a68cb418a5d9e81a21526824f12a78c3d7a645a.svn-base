package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.ProcessBillingData;
import com.global.meter.data.repository.ProcessBillingDataRepository;
import com.global.meter.data.repository.SubdivisionsMasterRepository;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.ProcessBillingDataReq;
import com.global.meter.inventive.models.ProcessBillingDataResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class ProcessBillingDataCaster {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessBillingDataCaster.class);

	@Autowired
	SubdivisionsMasterRepository subdMasterRepository;

	@Autowired
	ProcessBillingDataRepository processBillingDataRepository;

	@Autowired
	DateConverter dateConverter;

	public void saveProcessBillingData(String outputList, DevicesInfo device) throws Exception {
		LOG.info("process billing Data Caster called....");
		List<ProcessBillingDataReq> billingData = new ArrayList<ProcessBillingDataReq>();

		try {
			billingData = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<ProcessBillingDataReq>>() {
					});
			ProcessBillingData processData = new ProcessBillingData();

			    processData.setOwner_name(device.getOwner_name());
				processData.setSubdevision_name(device.getSubdevision_name());
				processData.setSubstation_name(device.getSubstation_name());
				processData.setFeeder_name(device.getFeeder_name());
				processData.setDt_name(device.getDt_name());
				processData.setDevice_serial_number(device.getDevice_serial_number());
				processData.setDevice_type(device.getDevice_type());
				processData.setCommissioning_status(device.getCommissioning_status());

				processData.setIs_on_time_billing(Constants.NO);
				processData.setIs_received_billing(Constants.NO);
				
			if(!billingData.isEmpty()) {
			    for (ProcessBillingDataReq ispData : billingData) {
				
				  if (ispData.getBilling_datetime() != null && device.getInstallation_datetime() != null) {
					  
					 if (new DateConverter().convertDateTimeToDateLocaldate(ispData.getBilling_datetime())
							.isAfter(CommonUtils.getFirstDateOfMonth()) || new DateConverter().convertDateTimeToDateLocaldate(ispData.getBilling_datetime())
							.equals(CommonUtils.getFirstDateOfMonth()) 
							&& !new DateConverter().convertDateTimeToDateLocaldate(device.getInstallation_datetime())
							.isAfter(CommonUtils.getFirstDateOfMonth())) {

						processData.setIs_received_billing(Constants.YES);
						if (device.getBilling_datetime() != null && dateConverter.convertDateTimeToDateLocaldate(ispData.getBilling_datetime())
								.equals(dateConverter.convertDateTimeToDateLocaldate(dateConverter
										.convertBillingDateToDateTimestamp(device.getBilling_datetime())))) {
							 
							 processData.setIs_on_time_billing(Constants.YES);
						 }
					 }
					 
				}
				
			  }
			}
			processBillingDataRepository.save(processData);
			LOG.info("Data save successfully");
		} catch (Exception e) {
			LOG.error("Issue in save data due to : " + e.getMessage());

		}
	}

	public void prepareProcessBillingData(String outputList, ProcessBillingDataResponse ispResponse,
			MeterDataVisualizationReq req) {
		LOG.info("process billing Data Caster called....");
		try {
			ProcessBillingDataReq billingData = new ProcessBillingDataReq();
			billingData = CommonUtils.getMapper().readValue(outputList, new TypeReference<ProcessBillingDataReq>() {
			});
			ispResponse.setRecievedCount(billingData.getRecivedCount());
			ispResponse.setNonRecievedCount(String.valueOf(Integer.valueOf(billingData.getDevCount())-Integer.valueOf(billingData.getRecivedCount())));
			ispResponse.setOnTimeCount(billingData.getOnTimeCount());
			ispResponse.setOffTimeCount(String.valueOf(Integer.valueOf(billingData.getRecivedCount())-Integer.valueOf(billingData.getOnTimeCount())));
			ispResponse.setTotalCount(billingData.getDevCount());
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
		}
	}
}
