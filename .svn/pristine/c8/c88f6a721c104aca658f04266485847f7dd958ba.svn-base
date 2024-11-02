package com.global.meter.inventive.mdm.serviceImpl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.models.SingleConnectionCommandLogMdmResponse;
import com.global.meter.inventive.mdm.service.SingleConnectionCommandMdmService;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.repository.SingleConnectionMeterCommandLogRepository;
import com.global.meter.v3.inventive.service.SingleConnectionCommandExecutionService;
import com.global.meter.v3.inventive.validator.SingleConnectionCommandValidator;

@Service
public class SingleConnectionCommandLogsMdmServiceImple implements SingleConnectionCommandMdmService {
	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionCommandLogsMdmServiceImple.class);
	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	CommonUtils commonUtils;

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	MetersCommandsConfiguration meteConfiguration;

	@Autowired
	SingleConnectionCommandValidator commandValidator;

	@Autowired
	SingleConnectionCommandExecutionService commandExecutorService;

	@Autowired
	SingleConnectionMeterCommandLogRepository singleConnectionMeterCommandLogRepository;

	@Override
	public CommonResponse getSingleConnectionCommandLogs(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String devType = "";
		ArrayList<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<SingleConnectionMeterCommandLog>();
		ArrayList<SingleConnectionCommandLogMdmResponse> responsesList = new ArrayList<SingleConnectionCommandLogMdmResponse>();
		String[] commands = req.getCommand().split(",");
		Set<String> commandSet = new HashSet<>();
		Optional<List<SingleConnectionMeterCommandLog>> singleConnectionInfo = null;
		LOG.info("Getting data from DB :");
		try {
			if (ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV.contains(req.getDevType())) {
				devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
			} else if (ExternalConstants.DeviceTypes.THREE_PHASE_DEV.contains(req.getDevType())) {
				devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			} else if (ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
				devType = ExternalConstants.DeviceTypes.CT_METER;
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("1")) {

				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByUtility(
						new DateConverter().convertStringToDate(req.getFromDate()),
						new DateConverter().convertStringToDate(req.getToDate()), req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("7")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByMeter(
						new DateConverter().convertStringToDate(req.getFromDate()),
						new DateConverter().convertStringToDate(req.getToDate()), req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("2")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataBySubdivision(
						new DateConverter().convertStringToDate(req.getFromDate()),
						new DateConverter().convertStringToDate(req.getToDate()), req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("3")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataBySubstation(
						new DateConverter().convertStringToDate(req.getFromDate()),
						new DateConverter().convertStringToDate(req.getToDate()), req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("4")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByFeeder(
						new DateConverter().convertStringToDate(req.getFromDate()),
						new DateConverter().convertStringToDate(req.getToDate()), req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("5")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByDt(
						new DateConverter().convertStringToDate(req.getFromDate()),
						new DateConverter().convertStringToDate(req.getToDate()), req.getHier().getValue(), devType);
			}

			if (!singleConnectionInfo.isPresent()) {
				error.setErrorMessage(req.getBatchId() + " : " + ExternalConstants.Message.NOT_EXISTS);
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			meterInfoList = (ArrayList<SingleConnectionMeterCommandLog>) singleConnectionInfo.get();

			for (String str : commands) {
				commandSet.add(str.trim());
			}
			for (SingleConnectionMeterCommandLog isData : meterInfoList) {

				for (DevicesCommandLog commandLog : isData.getCommand_list()) {
					if (req.getCommand() != null && !req.getCommand().isEmpty()) {
						if (commandSet.contains(commandLog.getCommand_name())) {
							responsesList.add(setCommandResponse(isData, commandLog, req));
						}
					} else {
						responsesList.add(setCommandResponse(isData, commandLog, req));
					}
				}
			}

			response.setData(responsesList);
			LOG.info("Single connection command logs Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	SingleConnectionCommandLogMdmResponse setCommandResponse(SingleConnectionMeterCommandLog isData,
			DevicesCommandLog commandLog, SingleConnectionCommandLogsReq req) throws ParseException {

		SingleConnectionCommandLogMdmResponse logResponse = new SingleConnectionCommandLogMdmResponse();
		logResponse
				.setHes_date(isData.getHes_date() != null ? dateConverter.convertDateToHesString(isData.getHes_date())
						: req.getReplaceBy());
		logResponse.setDevice_serial_number(isData.getDevice_serial_number());
		logResponse.setBatch_id(isData.getBatch_id());
		logResponse.setDevice_type(isData.getDevice_type());
		logResponse.setMdas_datetime(commandLog.getCommand_datetime() != null
				? dateConverter.convertDateToHesString(commandLog.getCommand_datetime())
				: req.getReplaceBy());
		logResponse.setOwner_name(isData.getOwner_name());
		logResponse.setReason(
				isData.getReason() != null ? CommonUtils.splitReason(isData.getReason(), commandLog.getRetry()).trim()
						: req.getReplaceBy());
		logResponse.setSource(commandLog.getSource());
		logResponse.setStatus(commandLog.getStatus());
		logResponse.setTot_attempts(String.valueOf(commandLog.getRetry()));
		logResponse.setUser_id(commandLog.getUser_id());
		logResponse.setExt_batch_id(commandLog.getExt_batch_id());
		logResponse.setTracking_id(commandLog.getTracking_id());
		logResponse.setCommand_completion_datetime(commandLog.getCommand_completion_datetime() != null
				? dateConverter.convertDateToHesString(commandLog.getCommand_completion_datetime())
				: req.getReplaceBy());
		logResponse.setCommand_name(commandLog.getCommand_name());

		if (commandLog.getCommands() != null && commandLog.getCommands().size() > 0) {
			logResponse.setCommand_list(commandLog.getCommands().toString().replace("=", ":"));
		} else {
			logResponse.setCommand_list(req.getReplaceBy() != null ? req.getReplaceBy() : "-");
		}

		return logResponse;
	}
}
