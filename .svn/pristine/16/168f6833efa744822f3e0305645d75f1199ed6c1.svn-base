package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.NamePlate;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.NamePlateDeviceResponse;
import com.global.meter.inventive.models.NamePlateResponse;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class NamePlateDataCaster {

	private static final Logger LOG = LoggerFactory.getLogger(NamePlateDataCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareNamePlate(String outputList, List<NamePlateResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Name Plate Data Caster called....");
		List<NamePlate> namePlateData = new ArrayList<NamePlate>();
		namePlateData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<NamePlate>>() {
		});
		
		LOG.info("Name Plate Response Data Addding");
		for (NamePlate ispData : namePlateData) {
			NamePlateResponse ispResponse = new NamePlateResponse();

			ispResponse.setDeviceSerialNo(ispData.getDevice_serial_number() != null ? String.valueOf(ispData.getDevice_serial_number()): req.getReplaceBy());
			ispResponse.setCategory(ispData.getCategory() != null ? String.valueOf(ispData.getCategory()): req.getReplaceBy());
			ispResponse.setCurrentRatings(ispData.getCurrent_ratings() != null ? String.valueOf(ispData.getCurrent_ratings()): req.getReplaceBy());
			ispResponse.setDeviceId(ispData.getDevice_id() != null ? String.valueOf(ispData.getDevice_id()): req.getReplaceBy());
			ispResponse.setFirmwareVersion(ispData.getFirmware_version() != null ? String.valueOf(ispData.getFirmware_version()): req.getReplaceBy());
		    ispResponse.setManufacturerName(ispData.getManufacturer_name() != null ? String.valueOf(ispData.getManufacturer_name()): req.getReplaceBy());
		    ispResponse.setManufacturerYear(ispData.getManufacturer_year() != null ? String.valueOf(ispData.getManufacturer_year()): req.getReplaceBy());
			ispResponse.setMdasDateTime(ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime()) : req.getReplaceBy());
			ispResponse.setMeterSerialNo(ispData.getMeter_serial_number() != null ? String.valueOf(ispData.getMeter_serial_number()): req.getReplaceBy());
			ispResponse.setMeterType(ispData.getMeter_type() != null ? String.valueOf(ispData.getMeter_type()): req.getReplaceBy());
			ispResponse.setStatus(ExternalConstants.Status.VALID.equalsIgnoreCase(ispData.getStatus())
			? ExternalConstants.Status.COMMISSIONED
			: ExternalConstants.Status.NON_COMMISSIONED);
			ispResponse.setCtRatio(ispData.getCt_ratio() != null ? String.valueOf(ispData.getCt_ratio()): req.getReplaceBy());
			ispResponse.setPtRatio(ispData.getPt_ratio() != null ? String.valueOf(ispData.getPt_ratio()): req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		
		LOG.info("Name Plate Data Added Success.");
	}
	
	public void prepareNamePlateDeviceInfo(String outputList, List<NamePlateDeviceResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Name Plate Data Caster called....");
		List<NamePlate> namePlateData = new ArrayList<NamePlate>();
		namePlateData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<NamePlate>>() {
		});
		
		LOG.info("Name Plate Response Data Addding");
		for (NamePlate ispData : namePlateData) {
			NamePlateDeviceResponse ispResponse = new NamePlateDeviceResponse();

			ispResponse.setDeviceSerialNo(ispData.getDevice_serial_number() != null ? String.valueOf(ispData.getDevice_serial_number()): req.getReplaceBy());
			ispResponse.setCategory(ispData.getCategory() != null ? String.valueOf(ispData.getCategory()): req.getReplaceBy());
			ispResponse.setCurrentRatings(ispData.getCurrent_ratings() != null ? String.valueOf(ispData.getCurrent_ratings()): req.getReplaceBy());
			ispResponse.setDeviceId(ispData.getDevice_id() != null ? String.valueOf(ispData.getDevice_id()): req.getReplaceBy());
			ispResponse.setDtName(ispData.getDt_name() != null ? String.valueOf(ispData.getDt_name()): req.getReplaceBy());
			ispResponse.setFeederName(ispData.getFeeder_name() != null ? String.valueOf(ispData.getFeeder_name()): req.getReplaceBy());
			ispResponse.setFirmwareVersion(ispData.getFirmware_version() != null ? String.valueOf(ispData.getFirmware_version()): req.getReplaceBy());
		    ispResponse.setManufacturerName(ispData.getManufacturer_name() != null ? String.valueOf(ispData.getManufacturer_name()): req.getReplaceBy());
		    ispResponse.setManufacturerYear(ispData.getManufacturer_year() != null ? String.valueOf(ispData.getManufacturer_year()): req.getReplaceBy());
			ispResponse.setMdasDateTime(dateConverter.convertDateToHesString(ispData.getMdas_datetime()));
			ispResponse.setMeterSerialNo(ispData.getMeter_serial_number() != null ? String.valueOf(ispData.getMeter_serial_number()): req.getReplaceBy());
			ispResponse.setMeterType(ispData.getMeter_type() != null ? String.valueOf(ispData.getMeter_type()): req.getReplaceBy());
			ispResponse.setStatus(ExternalConstants.Status.VALID.equalsIgnoreCase(ispData.getStatus())
			? ExternalConstants.Status.COMMISSIONED
			: ExternalConstants.Status.NON_COMMISSIONED);
			ispResponse.setSubDivisionName(ispData.getSubdevision_name() != null ? String.valueOf(ispData.getSubdevision_name()): req.getReplaceBy());
			ispResponse.setSubStationName(ispData.getSubstation_name() != null ? String.valueOf(ispData.getSubstation_name()): req.getReplaceBy());
			ispResponse.setCtRatio(ispData.getCt_ratio() != null ? String.valueOf(ispData.getCt_ratio()): req.getReplaceBy());
			ispResponse.setPtRatio(ispData.getPt_ratio() != null ? String.valueOf(ispData.getPt_ratio()): req.getReplaceBy());

			ispResponseList.add(ispResponse);
		}
		
		LOG.info("Name Plate Data Added Success.");
	}


}

