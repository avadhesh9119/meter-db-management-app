package com.global.meter.v3.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.MeterRawDataResponse;
import com.global.meter.v3.inventive.models.MeterResponseRawDataReq;
import com.global.meter.v3.inventive.repository.MeterResponseRawDataRepository;
import com.global.meter.v3.inventive.service.MeterResponseRawDataService;
import com.global.meter.v3.utils.MeterResponseRawDataCaster;

@Service
public class MeterResponseRawDataServiceImpl implements MeterResponseRawDataService {
	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionCommandLogsServiceImple.class);

	@Autowired
	MeterResponseRawDataRepository rawDataRepository;

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MeterResponseRawDataService meterResponseRawDataService;
	
	@Autowired
	MeterResponseRawDataRepository meterResponseRawDataRepository;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	MeterResponseRawDataCaster meterResponseRawDataCaster;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse getMeterResponseRawDataByTrackingId(MeterResponseRawDataReq req) {

		CommonResponse response = new CommonResponse();

		LOG.info("Getting Meter Response Raw Data Logs from DB:-->");
		try {
			
			String	table = meterConfiguration.getKeyspace() + "." + Tables.METER_RESPONSE_RAW_DATA;

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT * from ").append(table).append(" where ")
     		.append(" tracking_id = '").append(req.getTrackingId()).append("' order by mdas_datetime desc");
			
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<MeterRawDataResponse> ispResponseList = new ArrayList<>();
				meterResponseRawDataCaster.prepareRawData(outputList, ispResponseList, req);
				response.setData(ispResponseList);

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

}
