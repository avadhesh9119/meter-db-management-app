package com.global.meter.v3.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.PullEventsCategoryRepository;
import com.global.meter.inventive.dashboard.model.DeviceStatusCountRequest;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DevicesCountResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.business.model.DevicesCommandLog;
import com.global.meter.v3.inventive.business.model.SingleConnectionMeterCommandLog;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogResponse;
import com.global.meter.v3.inventive.models.SingleConnectionCommandLogsReq;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.repository.SingleConnectionMeterCommandLogRepository;
import com.global.meter.v3.inventive.service.SingleConnectionCommandExecutionService;
import com.global.meter.v3.inventive.service.SingleConnectionCommandLogService;
import com.global.meter.v3.inventive.service.SingleConnectionDashboardService;
import com.global.meter.v3.inventive.validator.SingleConnectionCommandValidator;

@Service
public class SingleConnectionDashboardServiceImple implements SingleConnectionDashboardService {
	private static final Logger LOG = LoggerFactory.getLogger(SingleConnectionCommandLogsServiceImple.class);
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

	@Autowired
	DevicesInfoRepository devicesInfoRepository;

	@Autowired
	SingleConnectionCommandLogService meterCommandService;

	@Autowired
	PullEventsCategoryRepository pullEventsCategoryRepository;

	@Autowired
	SingleConnectionCommandLogsServiceImple singleConnectionCommandLogsServiceImple;

	@Override
	public CommonResponse getSingleConnectionCommandStatusCount(SingleConnectionCommandReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String devType = "";
		int count = 0;
		ArrayList<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<>();
		Set<String> fullCommandSet = new HashSet<>();
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

			List<DevicesCountResponse> responseList = new ArrayList<>();
			Date date = null;
			Boolean isFirst = false;

			// Sorting the list based on hes_date
			Collections.sort(meterInfoList,
					Comparator.nullsFirst(Comparator.comparing(SingleConnectionMeterCommandLog::getHes_date)));

			DevicesCountResponse res = new DevicesCountResponse();
			for (SingleConnectionMeterCommandLog isData : meterInfoList) {
				count++;
				// set data for new date
				if (res.getDate() != null && !isData.getHes_date().equals(date)) {
					setSuccessPercentage(res);
					responseList.add(res);
					date = isData.getHes_date();
					res = new DevicesCountResponse();
				}

				if (isData.getHes_date().equals(date) || !isFirst) {

					res.setDate(dateConverter.convertDateToHesDateString(isData.getHes_date()));
					for (DevicesCommandLog commandLog : isData.getCommand_list()) {
						if (commandLog.getCommand_name().equalsIgnoreCase(Constants.CommandName.FullData)) {
							fullCommandSet = commandLog.getCommands().keySet();
						}
						setSuccessCount(commandLog.getCommand_name(), fullCommandSet, commandLog, res);
						fullCommandSet = new HashSet<>();
					}
					date = isData.getHes_date();
					isFirst = true;
				}
				// set last data
				if (count == meterInfoList.size() && res.getDate() != null && isData.getHes_date().equals(date)) {
					setSuccessPercentage(res);
					responseList.add(res);
					date = isData.getHes_date();
					res = new DevicesCountResponse();
				}

			}

			response.setData(responseList);
			LOG.info("Single connection get command status count Success.");
		} 
		catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

//Set command success count
	private void setSuccessCount(String command_name, Set<String> fullCommandSet, DevicesCommandLog commandLog,
			DevicesCountResponse res) {

		if (Constants.CommandName.Connect.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Connect)) {

			if (isSuccessStatus(Constants.CommandName.Connect, fullCommandSet, commandLog)) {
				res.setConnect(res.getConnect() != null ? String.valueOf(Integer.valueOf(res.getConnect()) + 1) : "1");
			}
			res.setConnectCount(
					res.getConnectCount() != null ? String.valueOf(Integer.valueOf(res.getConnectCount()) + 1) : "1");
		} 
		if (Constants.CommandName.Disconnect_Cmd.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Disconnect_Cmd)) {

			if (isSuccessStatus(Constants.CommandName.Disconnect_Cmd, fullCommandSet, commandLog)) {
				res.setDisconnect(
						res.getDisconnect() != null ? String.valueOf(Integer.valueOf(res.getDisconnect()) + 1) : "1");
			}
			res.setDisconnectCount(
					res.getDisconnectCount() != null ? String.valueOf(Integer.valueOf(res.getDisconnectCount()) + 1)
							: "1");
		} 
		if (Constants.CommandName.Daily_Load_Profile.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Daily_Load_Profile)) {

			if (isSuccessStatus(Constants.CommandName.Daily_Load_Profile, fullCommandSet, commandLog)) {
				res.setDailyLoadProfile(res.getDailyLoadProfile() != null
						? String.valueOf(Integer.valueOf(res.getDailyLoadProfile()) + 1)
						: "1");
			}
			res.setDailyLoadProfileCount(res.getDailyLoadProfileCount() != null
					? String.valueOf(Integer.valueOf(res.getDailyLoadProfileCount()) + 1)
					: "1");
		}
		if (Constants.CommandName.Delta_Load_Profile.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Delta_Load_Profile)) {

			if (isSuccessStatus(Constants.CommandName.Delta_Load_Profile, fullCommandSet, commandLog)) {
				res.setDeltaLoadProfile(res.getDeltaLoadProfile() != null
						? String.valueOf(Integer.valueOf(res.getDeltaLoadProfile()) + 1)
						: "1");
			}
			res.setDeltaLoadProfileCount(res.getDeltaLoadProfileCount() != null
					? String.valueOf(Integer.valueOf(res.getDeltaLoadProfileCount()) + 1)
					: "1");
		}
		if (Constants.CommandName.Billing_Data.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Billing_Data)) {

			if (isSuccessStatus(Constants.CommandName.Billing_Data, fullCommandSet, commandLog)) {
				res.setBillingData(
						res.getBillingData() != null ? String.valueOf(Integer.valueOf(res.getBillingData()) + 1) : "1");
			}
			res.setBillingDataCount(
					res.getBillingDataCount() != null ? String.valueOf(Integer.valueOf(res.getBillingDataCount()) + 1)
							: "1");
		} 
		if (Constants.CommandName.Instantaneous_Read.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Instantaneous_Read)) {

			if (isSuccessStatus(Constants.CommandName.Instantaneous_Read, fullCommandSet, commandLog)) {
				res.setInstantaneousRead(res.getInstantaneousRead() != null
						? String.valueOf(Integer.valueOf(res.getInstantaneousRead()) + 1)
						: "1");
			}
			res.setInstantaneousReadCount(res.getInstantaneousReadCount() != null
					? String.valueOf(Integer.valueOf(res.getInstantaneousReadCount()) + 1)
					: "1");
		}
		if (Constants.CommandName.Power_Related_Events.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Power_Related_Events)) {

			if (isSuccessStatus(Constants.CommandName.Power_Related_Events, fullCommandSet, commandLog)) {
				res.setPowerRelatedEvents(res.getPowerRelatedEvents() != null
						? String.valueOf(Integer.valueOf(res.getPowerRelatedEvents()) + 1)
						: "1");
			}
			res.setPowerRelatedEventsCount(res.getPowerRelatedEventsCount() != null
					? String.valueOf(Integer.valueOf(res.getPowerRelatedEventsCount()) + 1)
					: "1");
		} 
		if (Constants.CommandName.Voltage_Related_Events.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Voltage_Related_Events)) {

			if (isSuccessStatus(Constants.CommandName.Voltage_Related_Events, fullCommandSet, commandLog)) {
				res.setVoltageRelatedEvents(res.getVoltageRelatedEvents() != null
						? String.valueOf(Integer.valueOf(res.getVoltageRelatedEvents()) + 1)
						: "1");
			}
			res.setVoltageRelatedEventsCount(res.getVoltageRelatedEventsCount() != null
					? String.valueOf(Integer.valueOf(res.getVoltageRelatedEventsCount()) + 1)
					: "1");
		} 
		if (Constants.CommandName.Current_Related_Events.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Current_Related_Events)) {

			if (isSuccessStatus(Constants.CommandName.Current_Related_Events, fullCommandSet, commandLog)) {
				res.setCurrentRelatedEvents(res.getCurrentRelatedEvents() != null
						? String.valueOf(Integer.valueOf(res.getCurrentRelatedEvents()) + 1)
						: "1");
				;
			}
			res.setCurrentRelatedEventsCount(res.getCurrentRelatedEventsCount() != null
					? String.valueOf(Integer.valueOf(res.getCurrentRelatedEventsCount()) + 1)
					: "1");
		}
		if (Constants.CommandName.Control_Related_Events.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Control_Related_Events)) {

			if (isSuccessStatus(Constants.CommandName.Control_Related_Events, fullCommandSet, commandLog)) {
				res.setControlRelatedEvents(res.getControlRelatedEvents() != null
						? String.valueOf(Integer.valueOf(res.getControlRelatedEvents()) + 1)
						: "1");
			}
			res.setControlRelatedEventsCount(res.getControlRelatedEventsCount() != null
					? String.valueOf(Integer.valueOf(res.getControlRelatedEventsCount()) + 1)
					: "1");
		}
		if (Constants.CommandName.Other_Related_Events.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Other_Related_Events)) {

			if (isSuccessStatus(Constants.CommandName.Other_Related_Events, fullCommandSet, commandLog)) {
				res.setOtherRelatedEvents(res.getOtherRelatedEvents() != null
						? String.valueOf(Integer.valueOf(res.getOtherRelatedEvents()) + 1)
						: "1");
			}
			res.setOtherRelatedEventsCount(res.getOtherRelatedEventsCount() != null
					? String.valueOf(Integer.valueOf(res.getOtherRelatedEventsCount()) + 1)
					: "1");
		} 
		if (Constants.CommandName.Transaction_Related_Events.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Transaction_Related_Events)) {

			if (isSuccessStatus(Constants.CommandName.Transaction_Related_Events, fullCommandSet, commandLog)) {
				res.setTransactionRelatedEvents(res.getTransactionRelatedEvents() != null
						? String.valueOf(Integer.valueOf(res.getTransactionRelatedEvents()) + 1)
						: "1");
			}
			res.setTransactionRelatedEventsCount(res.getTransactionRelatedEventsCount() != null
					? String.valueOf(Integer.valueOf(res.getTransactionRelatedEventsCount()) + 1)
					: "1");
		} 
		if (Constants.CommandName.Name_Plate.equalsIgnoreCase(command_name)
				|| fullCommandSet.contains(Constants.CommandName.Name_Plate)) {

			if (isSuccessStatus(Constants.CommandName.Name_Plate, fullCommandSet, commandLog)) {
				res.setNamePlate(
						res.getNamePlate() != null ? String.valueOf(Integer.valueOf(res.getNamePlate()) + 1) : "1");
			}
			res.setNamePlateCount(
					res.getNamePlateCount() != null ? String.valueOf(Integer.valueOf(res.getNamePlateCount()) + 1)
							: "1");
		}

	}

//check status
	public boolean isSuccessStatus(String command, Set<String> fullCommandSet, DevicesCommandLog commandLog) {

		boolean status = false;

		if (Constants.SUCCESS.equalsIgnoreCase(commandLog.getStatus()) || Constants.BILL_ALREADY_EXIST_CUURENT_MONTH.equalsIgnoreCase(commandLog.getStatus())
				|| Constants.FAILURE_NA.equalsIgnoreCase(commandLog.getStatus())
				|| (commandLog.getCommands().get(command) != null && !commandLog.getCommands().get(command).isEmpty()
						&& (commandLog.getCommands().get(command).equalsIgnoreCase(Constants.SUCCESS) 
								|| commandLog.getCommands().get(command).equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH) 
								|| commandLog.getCommands().get(command).equalsIgnoreCase(Constants.FAILURE_NA)))) {
			status = true;
		}
		return status;
	}

//set success percentage
	private void setSuccessPercentage(DevicesCountResponse res) {
		res.setConnect(res.getConnect() != null
						? String.valueOf(CommonUtils.decimalFormatTwoDigits(
								(Double.valueOf(res.getConnect()) * 100) / Double.valueOf(res.getConnectCount())))
						: "0");
		res.setConnectCount(res.getConnectCount() != null ? res.getConnectCount() : "0");

		res.setDisconnect(res.getDisconnect() != null
						? String.valueOf(CommonUtils.decimalFormatTwoDigits(
								(Double.valueOf(res.getDisconnect()) * 100) / Double.valueOf(res.getDisconnectCount())))
						: "0");
		res.setDisconnectCount(res.getDisconnectCount() != null ? res.getDisconnectCount() : "0");

		res.setDailyLoadProfile(res.getDailyLoadProfile() != null ? String.valueOf(CommonUtils.decimalFormatTwoDigits(
				(Double.valueOf(res.getDailyLoadProfile()) * 100) / Double.valueOf(res.getDailyLoadProfileCount())))
				: "0");
		res.setDailyLoadProfileCount(res.getDailyLoadProfileCount() != null ? res.getDailyLoadProfileCount() : "0");

		res.setDeltaLoadProfile(res.getDeltaLoadProfile() != null ? String.valueOf(CommonUtils.decimalFormatTwoDigits(
				(Double.valueOf(res.getDeltaLoadProfile()) * 100) / Double.valueOf(res.getDeltaLoadProfileCount())))
				: "0");
		res.setDeltaLoadProfileCount(res.getDeltaLoadProfileCount() != null ? res.getDeltaLoadProfileCount() : "0");

		res.setBillingData(res.getBillingData() != null
				? String.valueOf(CommonUtils.decimalFormatTwoDigits(
						(Double.valueOf(res.getBillingData()) * 100) / Double.valueOf(res.getBillingDataCount())))
				: "0");
		res.setBillingDataCount(res.getBillingDataCount() != null ? res.getBillingDataCount() : "0");

		res.setInstantaneousRead(res.getInstantaneousRead() != null ? String.valueOf(CommonUtils.decimalFormatTwoDigits(
				(Double.valueOf(res.getInstantaneousRead()) * 100) / Double.valueOf(res.getInstantaneousReadCount())))
				: "0");
		res.setInstantaneousReadCount(res.getInstantaneousReadCount() != null ? res.getInstantaneousReadCount() : "0");

		res.setPowerRelatedEvents(res.getPowerRelatedEvents() != null
				? String.valueOf(CommonUtils.decimalFormatTwoDigits((Double.valueOf(res.getPowerRelatedEvents()) * 100)
						/ Double.valueOf(res.getPowerRelatedEventsCount())))
				: "0");
		res.setPowerRelatedEventsCount(
				res.getPowerRelatedEventsCount() != null ? res.getPowerRelatedEventsCount() : "0");

		res.setVoltageRelatedEvents(res.getVoltageRelatedEvents() != null ? String
				.valueOf(CommonUtils.decimalFormatTwoDigits((Double.valueOf(res.getVoltageRelatedEvents()) * 100)
						/ Double.valueOf(res.getVoltageRelatedEventsCount())))
				: "0");
		res.setVoltageRelatedEventsCount(
				res.getVoltageRelatedEventsCount() != null ? res.getVoltageRelatedEventsCount() : "0");

		res.setCurrentRelatedEvents(res.getCurrentRelatedEvents() != null ? String
				.valueOf(CommonUtils.decimalFormatTwoDigits((Double.valueOf(res.getCurrentRelatedEvents()) * 100)
						/ Double.valueOf(res.getCurrentRelatedEventsCount())))
				: "0");
		res.setCurrentRelatedEventsCount(
				res.getCurrentRelatedEventsCount() != null ? res.getCurrentRelatedEventsCount() : "0");

		res.setTransactionRelatedEvents(res.getTransactionRelatedEvents() != null ? String
				.valueOf(CommonUtils.decimalFormatTwoDigits((Double.valueOf(res.getTransactionRelatedEvents()) * 100)
						/ Double.valueOf(res.getTransactionRelatedEventsCount())))
				: "0");
		res.setTransactionRelatedEventsCount(
				res.getTransactionRelatedEventsCount() != null ? res.getTransactionRelatedEventsCount() : "0");

		res.setOtherRelatedEvents(res.getOtherRelatedEvents() != null
				? String.valueOf(CommonUtils.decimalFormatTwoDigits((Double.valueOf(res.getOtherRelatedEvents()) * 100)
						/ Double.valueOf(res.getOtherRelatedEventsCount())))
				: "0");
		res.setOtherRelatedEventsCount(
				res.getOtherRelatedEventsCount() != null ? res.getOtherRelatedEventsCount() : "0");

		res.setControlRelatedEvents(res.getControlRelatedEvents() != null ? String
				.valueOf(CommonUtils.decimalFormatTwoDigits((Double.valueOf(res.getControlRelatedEvents()) * 100)
						/ Double.valueOf(res.getControlRelatedEventsCount())))
				: "0");
		res.setControlRelatedEventsCount(
				res.getControlRelatedEventsCount() != null ? res.getControlRelatedEventsCount() : "0");

		res.setNamePlate(res.getNamePlate() != null
						? String.valueOf(CommonUtils.decimalFormatTwoDigits(
								(Double.valueOf(res.getNamePlate()) * 100) / Double.valueOf(res.getNamePlateCount())))
						: "0");
		res.setNamePlateCount(res.getNamePlateCount() != null ? res.getNamePlateCount() : "0");

	}

	@Override
	public CommonResponse getSingleConnectionCommandSuccessCount(SingleConnectionCommandReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String devType = "";
		int count = 0;
		ArrayList<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<>();
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

			List<DevicesCountResponse> responseList = new ArrayList<>();
			Date date = null;
			Boolean isFirst = false;
			Boolean isSuccess = false;

			// Sorting the list based on hes_date
			Collections.sort(meterInfoList,
					Comparator.nullsFirst(Comparator.comparing(SingleConnectionMeterCommandLog::getHes_date)));

			DevicesCountResponse res = new DevicesCountResponse();
			res.setCount("0");
			for (SingleConnectionMeterCommandLog isData : meterInfoList) {
				count++;
				// set data for new date
				if (res.getDate() != null && !isData.getHes_date().equals(date)) {
					responseList.add(res);
					date = isData.getHes_date();
					res = new DevicesCountResponse();
					res.setCount("0");
				}

				if (isData.getHes_date().equals(date) || !isFirst) {
					isSuccess = false;
					res.setDate(dateConverter.convertDateToHesDateString(isData.getHes_date()));
					for (DevicesCommandLog commandLog : isData.getCommand_list()) {
						if (commandLog.getCommand_name().equalsIgnoreCase(Constants.CommandName.FullData)) {
						}
						if ((commandLog.getCommand_name().equalsIgnoreCase(req.getCommand().trim())
								&& (commandLog.getStatus().equalsIgnoreCase(Constants.SUCCESS) 
										|| commandLog.getStatus().equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)))
								|| commandLog.getCommands().get(req.getCommand().trim()) != null
										&& !commandLog.getCommands().get(req.getCommand().trim()).isEmpty()
										&& (commandLog.getCommands().get(req.getCommand().trim())
												.equalsIgnoreCase(Constants.SUCCESS) || commandLog.getCommands().get(req.getCommand().trim())
												.equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH))) {
							isSuccess = true;
						}
					}
					if (isSuccess) {
						res.setCount(
								res.getCount() != null ? String.valueOf(Integer.valueOf(res.getCount()) + 1) : "1");
					}
					date = isData.getHes_date();
					isFirst = true;
				}
				// set last data
				if (count == meterInfoList.size() && res.getDate() != null && isData.getHes_date().equals(date)) {
					responseList.add(res);
					date = isData.getHes_date();
					res = new DevicesCountResponse();
					res.setCount("0");
				}

			}

			response.setData(responseList);
			LOG.info("Single connection get device count command Success.");
		} 
		catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getSingleConnectionCommandSuccessDrillDown(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		String devType = "";
		ArrayList<SingleConnectionMeterCommandLog> meterInfoList = new ArrayList<>();
		Optional<List<SingleConnectionMeterCommandLog>> singleConnectionInfo = null;

		LOG.info("Getting data from DB :");
		try {
			String fromDate = dateConverter.convertDayToString(
					dateConverter.convertStringDayToDay(req.getBarChartDate())) + Constants.FROM_MID_NIGHT;
			String toDate = dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))
					+ Constants.TO_MID_NIGHT;

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
						dateConverter.convertStringToDate(fromDate), dateConverter.convertStringToDate(toDate),
						req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("7")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByMeter(
						dateConverter.convertStringToDate(fromDate), dateConverter.convertStringToDate(toDate),
						req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("2")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataBySubdivision(
						dateConverter.convertStringToDate(fromDate), dateConverter.convertStringToDate(toDate),
						req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("3")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataBySubstation(
						dateConverter.convertStringToDate(fromDate), dateConverter.convertStringToDate(toDate),
						req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("4")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByFeeder(
						dateConverter.convertStringToDate(fromDate), dateConverter.convertStringToDate(toDate),
						req.getHier().getValue(), devType);
			}

			if (req.getHier().getName() != null && !req.getHier().getName().isEmpty()
					&& req.getHier().getName().equals("5")) {
				singleConnectionInfo = singleConnectionMeterCommandLogRepository.getDataByDt(
						dateConverter.convertStringToDate(fromDate), dateConverter.convertStringToDate(toDate),
						req.getHier().getValue(), devType);
			}

			if (!singleConnectionInfo.isPresent()) {
				error.setErrorMessage(req.getBatchId() + " : " + ExternalConstants.Message.NOT_EXISTS);
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			meterInfoList = (ArrayList<SingleConnectionMeterCommandLog>) singleConnectionInfo.get();

			ArrayList<SingleConnectionCommandLogResponse> responsesList = new ArrayList<SingleConnectionCommandLogResponse>();

			DevicesCountResponse res = new DevicesCountResponse();
			res.setCount("0");
			for (SingleConnectionMeterCommandLog isData : meterInfoList) {

				for (DevicesCommandLog commandLog : isData.getCommand_list()) {
					if (req.getCommand() != null && !req.getCommand().isEmpty()) {
						if (req.getStatus() != null && !req.getStatus().isEmpty()
								&& req.getStatus().equalsIgnoreCase(Constants.SUCCESS)
								&& ((commandLog.getCommand_name().equalsIgnoreCase(req.getCommand().trim())
										&& (commandLog.getStatus().equalsIgnoreCase(Constants.SUCCESS) 
												|| commandLog.getStatus().equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)
												|| commandLog.getStatus().equalsIgnoreCase(Constants.FAILURE_NA)))
										|| (commandLog.getCommands().get(req.getCommand().trim()) != null
												&& !commandLog.getCommands().get(req.getCommand().trim()).isEmpty()
												&& (commandLog.getCommands().get(req.getCommand().trim())
														.equalsIgnoreCase(Constants.SUCCESS) 
														|| commandLog.getCommands().get(req.getCommand().trim())
														.equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)
														|| commandLog.getCommands().get(req.getCommand().trim())
														.equalsIgnoreCase(Constants.FAILURE_NA))))) {
							responsesList.add(singleConnectionCommandLogsServiceImple.setCommandResponse(isData,
									commandLog, req));
						} else if (req.getStatus() != null && !req.getStatus().isEmpty()
								&& req.getStatus().equalsIgnoreCase(Constants.UNSUCCESS)
								&& ((commandLog.getCommand_name().equalsIgnoreCase(req.getCommand().trim())
										&& !(commandLog.getStatus().equalsIgnoreCase(Constants.SUCCESS) 
												|| commandLog.getStatus().equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)
												|| commandLog.getStatus().equalsIgnoreCase(Constants.FAILURE_NA)))
										|| (commandLog.getCommands().get(req.getCommand().trim()) != null
												&& !commandLog.getCommands().get(req.getCommand().trim()).isEmpty()
												&& !(commandLog.getCommands().get(req.getCommand().trim())
														.equalsIgnoreCase(Constants.SUCCESS) || commandLog.getCommands().get(req.getCommand().trim())
														.equalsIgnoreCase(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH)
														|| commandLog.getCommands().get(req.getCommand().trim())
														.equalsIgnoreCase(Constants.FAILURE_NA))))) {
							responsesList.add(singleConnectionCommandLogsServiceImple.setCommandResponse(isData,
									commandLog, req));
						}
					}
				}
			}
			response.setData(responsesList);
			LOG.info("Single connection get device DrillDown for success command.");
		} 
		catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getSingleConnectionCommandNetworkReport(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting data from DB :");
		try {
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
			StringBuilder queryBuilder = new StringBuilder();

			queryBuilder.append("select sum(case when ").append("lastcommunicationdatetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp)")
					.append(" then 1 else 0 end) as successCount, sum(case when ")
					.append("lastcommunicationdatetime < cast('").append(req.getFromDate()).append("' as timestamp)")
					.append(" or lastcommunicationdatetime IS NULL then 1 else 0 end) as failureCount from ")
					.append(table).append(" where device_type = '").append(req.getDevType())
					.append("' and commissioning_status in ('Up','Installed') and installation_datetime IS NOT NULL and ")
					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(")");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DeviceStatusCountRequest> devStatusCount = new ArrayList<DeviceStatusCountRequest>();
			devStatusCount = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DeviceStatusCountRequest>>() {
					});
			response.setData(devStatusCount.get(0));
			LOG.info("Single connection get device count command Success.");
		} 
		catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getSingleConnectionCommandNetworkReportDrillDown(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting data from DB :");
		try {
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
			StringBuilder queryBuilder = new StringBuilder();
			List<MeterDataRes> devInfoList = new ArrayList<MeterDataRes>();

			queryBuilder.append("select * from ").append(table).append(" where ");
			if (req.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
				queryBuilder.append("lastcommunicationdatetime >= cast('").append(req.getFromDate())
						.append("' as timestamp)");
			} else if (req.getStatus().equalsIgnoreCase(Constants.FAILURE)) {
				queryBuilder.append("(lastcommunicationdatetime < cast('").append(req.getFromDate())
						.append("' as timestamp) or lastcommunicationdatetime IS NULL)");
			}
			queryBuilder.append(" and device_type = '").append(req.getDevType()).append(
					"' and commissioning_status in ('Up','Installed') and installation_datetime IS NOT NULL and ")
					.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(")");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfo> deviceInfos = new ArrayList<DevicesInfo>();
			deviceInfos = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {
			});

			for (DevicesInfo data : deviceInfos) {
				MeterDataRes devInfo = new MeterDataRes();
				devInfo.setMeterNumber(data.getDevice_serial_number());
				devInfo.setConsumerName(data.getConsumer_name());
				devInfo.setCommissioningStatus(data.getCommissioning_status());
				devInfo.setStatus(data.getStatus());
				devInfo.setDevMode(data.getDev_mode());
				devInfo.setDeviceType(data.getDevice_type());
				devInfo.setIpAddress(data.getIp_address_main());
			//	devInfo.setPort(data.getIp_port_main());
				devInfo.setManufacturer(data.getMeter_type());				
				devInfo.setLastCommunicationDatetime(data.getLastcommunicationdatetime() != null
						? dateConverter.convertDateToHesString(data.getLastcommunicationdatetime())
						: req.getReplaceBy());	

				devInfoList.add(devInfo);
			}
			response.setData(devInfoList);
			LOG.info("Single connection get device count command Success.");
		} 
		catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getGISReport(SingleConnectionCommandLogsReq req) {
		CommonResponse response = new CommonResponse();
		List<MeterDataRes> devInfoList = new ArrayList<MeterDataRes>();
		LOG.info("Getting data from DB :");
		try {
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
			StringBuilder queryBuilder = new StringBuilder();
			
			queryBuilder.append("select device_serial_number, latitude, longitude, category_level from ").append(table).append(" where device_type = '").append(req.getDevType())
			.append("' and ").append(fieldName).append(" in (");

			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}

			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(")");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfo> deviceInfos = new ArrayList<DevicesInfo>();
			deviceInfos = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {
			});

			for (DevicesInfo data : deviceInfos) {
				MeterDataRes devInfo = new MeterDataRes();
				devInfo.setMeterNumber(data.getDevice_serial_number());
				devInfo.setCategoryLevel(data.getCategory_level());
				devInfo.setLatitude(data.getLatitude());
				devInfo.setLongitude(data.getLongitude());

				devInfoList.add(devInfo);
			}
			response.setData(devInfoList);
			LOG.info("Getting GIS Info Success.");
		} 
		catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}
