package com.global.meter.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.MdmPushLogs;
import com.global.meter.business.model.NamePlate;
import com.global.meter.business.model.NamePlateLogs;
import com.global.meter.common.model.MeterResponse;
import com.global.meter.data.repository.NamePlateLogsRepository;
import com.global.meter.data.repository.NamePlateRepository;
import com.global.meter.inventive.mdm.repository.MdmPushLogRepository;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;

@Service
public class NamePlatesDataService {

	private static final Logger LOG = LoggerFactory.getLogger(NamePlatesDataService.class);
	
	@Autowired
	NamePlateRepository namePlateRepository;
	
	@Autowired
	NamePlateLogsRepository namePlateLogsRepository;
	
	@Autowired
	MdmPushLogRepository mdmPushLogRepository;
	
	public boolean insertNamePlateData(DevicesInfo deviceInfo,
			MeterResponse meterResponse)
	{
		boolean flag = false;
		try {
			Object responseData[] = meterResponse.getData();
			List<String> obisCodeList = meterResponse.getObisCode();
			List<NamePlate> namePlateList = new ArrayList<NamePlate>();
			NamePlateLogs namePlateLogs = new NamePlateLogs();
			
			for (Object object : responseData) {
				@SuppressWarnings("unchecked")
				List<Object> data = (List<Object>) object;	
				
				String meterSno = CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.METER_SERIAL_NUMBER);
				
				NamePlate namePlate = new NamePlate();
				namePlate.setDevice_serial_number(deviceInfo.getDevice_serial_number());
				namePlate.setMdas_datetime(new Date(System.currentTimeMillis()));
				namePlate.setCategory(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.METER_CATEGORY));
				namePlate.setCurrent_ratings(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.CURRENT_RATING));
				namePlate.setDevice_id(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.DEVICE_ID).trim());
				namePlate.setFirmware_version(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.FIRMWARE_VERSION));
				
				String manufacturerName = CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.MANUFACTURER_NAME);
				manufacturerName = manufacturerName.replace("\\u0000", "");
				namePlate.setManufacturer_name(manufacturerName);
				
				namePlate.setManufacturer_year(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.YEARS_OF_MANUFACT));
				namePlate.setMeter_type(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.METER_TYPE));
				namePlate.setMeter_serial_number(meterSno.trim());
				namePlate.setDcu_serial_number(deviceInfo.getDcu_serial_number());
				namePlate.setDt_name(deviceInfo.getDt_name());
				namePlate.setFeeder_name(deviceInfo.getFeeder_name());
				namePlate.setSubdevision_name(deviceInfo.getSubdevision_name());
				namePlate.setSubstation_name(deviceInfo.getSubstation_name());
				namePlate.setOwner_name(deviceInfo.getOwner_name());
				
				String devNo = deviceInfo.getDevice_serial_number().substring(deviceInfo.getDevice_serial_number().length()-6, 
						deviceInfo.getDevice_serial_number().length());
				
				namePlate.setStatus(meterSno != null && meterSno.contains(devNo) 
						? Constants.Status.VALID : Constants.Status.INVALID);
				
					deviceInfo.setCommissioning_status(meterSno != null && meterSno.contains(devNo) 
							? Constants.Status.UP : Constants.Status.DOWN);
					
					if(deviceInfo.getCommissioning_datetime() == null && meterSno != null && meterSno.contains(devNo)) {
						deviceInfo.setCommissioning_datetime(new Date(System.currentTimeMillis())); 
					}
				deviceInfo.setDevice_id(namePlate.getDevice_id());
				deviceInfo.setVersion(namePlate.getFirmware_version());
				deviceInfo.setLast_nameplate_datetime(new Date(System.currentTimeMillis()));
				
				namePlate.setCt_ratio(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.INTERNAL_CT_RATIO_STRING));
				namePlate.setPt_ratio(CommonUtils.getObisStringObject(data, obisCodeList, Constants.ObisCode.NAME_PLATE.INTERNAL_PT_RATIO_STRING));
				
				//update mdm push logs
				Optional<MdmPushLogs> pushLogInfo = mdmPushLogRepository.findById(deviceInfo.getDevice_serial_number());

				if (pushLogInfo.isPresent()) {
					MdmPushLogs pushLogs = new MdmPushLogs();
					pushLogs = pushLogInfo.get();
					
					pushLogs.setDevice_status(Constants.Status.UP);
					mdmPushLogRepository.save(pushLogs);
				}
				
				namePlateList.add(namePlate);
				
				try {
					namePlateLogs = CommonUtils.getMapper().readValue(
							CommonUtils.getMapper().writeValueAsString(namePlate), NamePlateLogs.class);
				} catch (Exception e) {
					LOG.error("Issue in maintaining Name Plate History");
				}
			}
			
			LOG.info("Data Parsed.. Now saving Name Plates Data : "+ deviceInfo.getDevice_serial_number());
			namePlateRepository.saveAll(namePlateList);
			namePlateLogsRepository.save(namePlateLogs);
			LOG.info("Name Plates Data Inserted Successfully. "+ deviceInfo.getDevice_serial_number());
			flag = true;
		} catch (Exception e) {
			LOG.error("Issue in inserting Name Plate Data for meter "+ deviceInfo.getDevice_serial_number() 
				+" in db due to :"+ e.getMessage());
			meterResponse.setMessage("Issue in inserting data due to: "+e.getMessage());
		}
		return flag;
	}
}