package com.global.meter.inventive.mdm.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.service.InstantDevicesInfoMdmService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.InstantDevicesInfoCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class InstantDevicesInfoMdmImpl implements InstantDevicesInfoMdmService {
	private static final Logger LOG = LoggerFactory.getLogger(InstantDevicesInfoMdmImpl.class);

	@Autowired
	DevicesInfoRepository devicesInfoRepo;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	InstantDevicesInfoCaster instantCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getDevicesInfo(MeterDataVisualizationReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices Info Data from DB: ");
		
		try {

			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_INFO;
		
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String level = req.getHier().getValue();

			String deviceType = Constants.ALL;
			 
            if(req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
            	deviceType = Constants.DeviceTypes.SINGLE_PHASE;
            }
            else if (req.getDevType().contains(ExternalConstants.DeviceTypes.THREE_PHASE)) {
            	deviceType = Constants.DeviceTypes.THREE_PHASE;
			}
            else if (req.getDevType().contains(ExternalConstants.DeviceTypes.CT_METER)) {
				deviceType = Constants.DeviceTypes.CT;
			}
			else if (req.getDevType().contains(ExternalConstants.DeviceTypes.LT_METER)) {
				deviceType = Constants.DeviceTypes.LT_METER;
			}
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(fieldName).append("= '")
					.append(level).append("' and commissioning_status = 'Up' ");
					if(!Constants.ALL.equalsIgnoreCase(req.getDevType())) {
						queryBuilder.append("and device_type = '").append(deviceType).append("'");
					}
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			if (req.getDevType().contains(ExternalConstants.DeviceTypes.SINGLE_PHASE)) {
				List<DevicesInfo> instantData = CommonUtils.getMapper()
						.readValue(outputList, new TypeReference<List<DevicesInfo>>() {});
				response.setData(instantData);
				LOG.info("DevicesInfo Service Data Response Success.");
			} else {
				List<DevicesInfo> instantData = CommonUtils.getMapper()
						.readValue(outputList, new TypeReference<List<DevicesInfo>>() {});
				response.setData(instantData);
				LOG.info("DevicesInfo Service Data Response Success.");
			}

		} catch (Exception e) {
			LOG.info("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);

		}
		return response;
	}

}
