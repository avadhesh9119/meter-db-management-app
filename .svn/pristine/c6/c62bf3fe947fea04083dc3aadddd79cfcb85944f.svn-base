package com.global.meter.inventive.mdm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesCommand;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.DevicesCommandRequest;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class DevicesCommandMdmCaster {

	private static final Logger LOG = LoggerFactory.getLogger(DevicesCommandMdmCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareDeviceCommand(String outputList, List<DeviceCommandResponse> ispResponseList) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<DevicesCommand> deviceCommandData = new ArrayList<DevicesCommand>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesCommand>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");

		for (DevicesCommand ispData : deviceCommandData) {
			DeviceCommandResponse ispResponse = new DeviceCommandResponse();

			ispResponse.setDeviceSerialNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setCommandName(String.valueOf(ispData.getCommand_name()));
			ispResponse.setTrackingId(String.valueOf(ispData.getTracking_id()));
			ispResponse.setCommandCompletionDatetime(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: "-");
			ispResponse.setDtName(String.valueOf(ispData.getDt_name()));
			ispResponse.setFeederName(String.valueOf(ispData.getFeeder_name()));
			ispResponse.setMdasDatetime(
					ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
							: "-");
			//ispResponse.setReason(String.valueOf(ispData.getReason()));
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));
			ispResponse.setSubdivisionName(String.valueOf(ispData.getSubdevision_name()));
			ispResponse.setSubstationName(String.valueOf(ispData.getSubstation_name()));
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUserId(ispData.getUser_id());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Caster Added.");
	}
	
	public void prepareDeviceCommand(String outputList, List<DeviceCommandResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<DevicesCommand> deviceCommandData = new ArrayList<DevicesCommand>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesCommand>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");

		for (DevicesCommand ispData : deviceCommandData) {
			DeviceCommandResponse ispResponse = new DeviceCommandResponse();

			ispResponse.setDeviceSerialNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setCommandName(String.valueOf(ispData.getCommand_name()));
			ispResponse.setTrackingId(String.valueOf(ispData.getTracking_id()));
			ispResponse.setCommandCompletionDatetime(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: req.getReplaceBy());
			ispResponse.setMdasDatetime(
					ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
							: req.getReplaceBy());
			//ispResponse.setReason(String.valueOf(ispData.getReason()));
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUserId(ispData.getUser_id());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Caster Added.");
	}
	
	public void prepareDeviceCommand(String outputList, List<DeviceCommandResponse> ispResponseList, DevicesCommandRequest req) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<DevicesCommand> deviceCommandData = new ArrayList<DevicesCommand>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesCommand>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");

		for (DevicesCommand ispData : deviceCommandData) {
			DeviceCommandResponse ispResponse = new DeviceCommandResponse();

			ispResponse.setDeviceSerialNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setCommandName(String.valueOf(ispData.getCommand_name()));
			ispResponse.setTrackingId(String.valueOf(ispData.getTracking_id()));
			ispResponse.setCommandCompletionDatetime(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: req.getReplaceBy());
			ispResponse.setDtName(String.valueOf(ispData.getDt_name()));
			ispResponse.setFeederName(String.valueOf(ispData.getFeeder_name()));
			ispResponse.setMdasDatetime(
					ispData.getMdas_datetime() != null ? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
							: req.getReplaceBy());
			//ispResponse.setReason(String.valueOf(ispData.getReason()));
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));
			ispResponse.setSubdivisionName(String.valueOf(ispData.getSubdevision_name()));
			ispResponse.setSubstationName(String.valueOf(ispData.getSubstation_name()));
			ispResponse.setTotalAttempts(String.valueOf(ispData.getTot_attempts()));
			ispResponse.setBatchId(ispData.getBatch_id());
			ispResponse.setSource(ispData.getSource());
			ispResponse.setUserId(ispData.getUser_id());

			ispResponseList.add(ispResponse);
		}
		LOG.info("Device Command Response Data Caster Added.");
	}
	
	public void prepareDeviceCommands(String outputList, List<DeviceCommandResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Device Command Data Caster called....");
		List<DevicesCommand> deviceCommandData = new ArrayList<DevicesCommand>();
		deviceCommandData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesCommand>>() {
		});

		LOG.info("Device Command Response Data Caster Adding.");
		for (DevicesCommand ispData : deviceCommandData) {
			DeviceCommandResponse ispResponse = new DeviceCommandResponse();
			ispResponse.setDeviceSerialNo(String.valueOf(ispData.getDevice_serial_number()));
			ispResponse.setCommandName(String.valueOf(ispData.getCommand_name()));
			ispResponse.setCommandCompletionDatetime(ispData.getCommand_completion_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getCommand_completion_datetime())
					: req.getReplaceBy());
			ispResponse.setStatus(String.valueOf(ispData.getStatus()));

			ispResponseList.add(ispResponse);
			}
		LOG.info("Device Command Response Data Caster Added.");
	}

}
