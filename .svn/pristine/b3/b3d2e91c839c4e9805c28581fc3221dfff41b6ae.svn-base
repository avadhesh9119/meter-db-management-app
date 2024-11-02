package com.global.meter.inventive.dashboard.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.global.meter.business.model.DevicesInfo;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.inventive.dashboard.business.model.ProcessSlaData;
import com.global.meter.inventive.dashboard.model.DailySummaryReportResponse;
import com.global.meter.inventive.dashboard.model.ProcessSlaDataRequest;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;

@Component
public class DailySummaryReportCaster {
	private static final Logger LOG = LoggerFactory.getLogger(DailySummaryReportCaster.class);

	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	CommonUtils commonUtils;

	public void prepareDailySummaryData(List<DevicesInfo> devicesList, List<ProcessSlaData> processSlaDatas,
			List<DailySummaryReportResponse> ispResponseList, ProcessSlaDataRequest req) throws Exception {
		LOG.info("Configuration Read Data Caster called....");
        boolean isPresent = false;
		for (ProcessSlaData ispData : processSlaDatas) {
			isPresent = false;
			DailySummaryReportResponse ispResponse = new DailySummaryReportResponse();
			if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.DAILY_LOAD_PROFILE.commandName)) {
				
				ispResponse.setDailyLoadProfileTimestamp(ispData.getLastdailylpreaddatetime() != null
						? dateConverter.convertDateToHesString(ispData.getLastdailylpreaddatetime())
						: req.getReplaceBy());
				ispResponse.setDailyProfileSLA(
						ispData.getLastdailylpreaddatetime() != null ? Constants.RECEIVED : Constants.NOT_RECEIVED);
			}else if (req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.DELTA_LOAD_PROFILE.commandName)) {
				ispResponse.setBlockLoadCount(
						ispData.getLastdeltalp_count() != null ? ispData.getLastdeltalp_count() : req.getReplaceBy());
				ispResponse.setBlockLoadSuccessSlaAvg(
						ispData.getLastdeltalp_success_avg() != null ? ispData.getLastdeltalp_success_avg() + "%"
								: req.getReplaceBy());
				ispResponse.setBlockLoadProfileSLA(ispData.getLastdeltalpreaddatetime() != null
						 ? Constants.RECEIVED : Constants.NOT_RECEIVED);

			}
			
			else if(req.getCommand().equalsIgnoreCase(Constants.ALL)){
				ispResponse.setDailyLoadProfileTimestamp(ispData.getLastdailylpreaddatetime() != null
						? dateConverter.convertDateToHesString(ispData.getLastdailylpreaddatetime())
						: req.getReplaceBy());
				ispResponse.setDailyProfileSLA(
						ispData.getLastdailylpreaddatetime() != null ? Constants.RECEIVED : Constants.NOT_RECEIVED);
				ispResponse.setBlockLoadCount(
						ispData.getLastdeltalp_count() != null ? ispData.getLastdeltalp_count() : req.getReplaceBy());
				// ispResponse.setBlockLoadFailureSlaAvg(ispData.getLastdeltalp_failure_avg() !=
				// null ? ispData.getLastdeltalp_failure_avg() : req.getReplaceBy());
				ispResponse.setBlockLoadSuccessSlaAvg(
						ispData.getLastdeltalp_success_avg() != null ? ispData.getLastdeltalp_success_avg() + "%"
								: req.getReplaceBy());
				ispResponse.setBlockLoadProfileSLA(ispData.getLastdeltalpreaddatetime() != null
						 ? Constants.RECEIVED : Constants.NOT_RECEIVED);
			}
			ispResponse.setMeterNo(ispData.getDevice_serial_number());
			ispResponse.setHesTimestamp(ispData.getMdas_datetime() != null
					? dateConverter.convertDateToHesString(ispData.getMdas_datetime())
					: req.getReplaceBy());
			ispResponse.setDevicetype(ispData.getDevice_type() != null ? ispData.getDevice_type() : req.getReplaceBy());
			
			ispResponse.setUtility(ispData.getOwner_name() != null ? ispData.getOwner_name() : req.getReplaceBy());
			ispResponse.setManufacturer(ispData.getMeter_type() != null ? ispData.getMeter_type() : req.getReplaceBy());
			ispResponse.setSubdivision(
					ispData.getSubdivision_name() != null ? ispData.getSubdivision_name() : req.getReplaceBy());
			ispResponse.setSubstation(
					ispData.getSubstation_name() != null ? ispData.getSubstation_name() : req.getReplaceBy());
			ispResponse.setFeeder(ispData.getFeeder_name() != null ? ispData.getFeeder_name() : req.getReplaceBy());
			ispResponse.setDt(ispData.getDt_name() != null ? ispData.getDt_name() : req.getReplaceBy());
			
			if (devicesList != null) {
				for (DevicesInfo info : devicesList) {
					if (ispData.getDevice_serial_number().equals(info.getDevice_serial_number()))
					{
						isPresent = true;
						if(ispData.getMdas_datetime().after(CommonUtils.getTodayDate())) {
							
						ispResponse.setFirstBreath(
								(info.getFirst_breath_datetime() != null && info.getFirst_breath_datetime().after(CommonUtils.getTodayDate())) ? dateConverter.convertDateToHesString(info.getFirst_breath_datetime()) : req.getReplaceBy());
						ispResponse.setLastGasp(
								(info.getLast_gasp_datetime() != null && info.getLast_gasp_datetime().after(CommonUtils.getTodayDate())) ? dateConverter.convertDateToHesString(info.getLast_gasp_datetime()) : req.getReplaceBy());
						
						if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.INSTANT_IP_PUSH.commandName)) {
						ispResponse.setInstantPushData(
								(info.getInstant_push_datetime() != null && info.getInstant_push_datetime().after(CommonUtils.getTodayDate())) ? dateConverter.convertDateToHesString(info.getInstant_push_datetime()) : req.getReplaceBy());
						}
						
						else if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.INSTANTANEOUS_READ.commandName)) {

						ispResponse.setInstantaneous(
								(info.getLastinstanteousreaddatetime() != null && info.getLastinstanteousreaddatetime().after(CommonUtils.getTodayDate())) ? Constants.RECEIVED : Constants.NOT_RECEIVED);
						}
						else if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.NAME_PLATES.commandName)) {

						ispResponse.setNamePlate(
								(info.getLast_nameplate_datetime() != null && info.getLast_nameplate_datetime().after(CommonUtils.getTodayDate())) ? Constants.RECEIVED : Constants.NOT_RECEIVED);
						}else if(req.getCommand().equalsIgnoreCase(Constants.ALL)) {

							ispResponse.setInstantPushData(
									(info.getInstant_push_datetime() != null && info.getInstant_push_datetime().after(CommonUtils.getTodayDate())) ? dateConverter.convertDateToHesString(info.getInstant_push_datetime()) : req.getReplaceBy());

							ispResponse.setInstantaneous(
									(info.getLastinstanteousreaddatetime() != null && info.getLastinstanteousreaddatetime().after(CommonUtils.getTodayDate())) ? Constants.RECEIVED : Constants.NOT_RECEIVED);


							ispResponse.setNamePlate(
									(info.getLast_nameplate_datetime() != null && info.getLast_nameplate_datetime().after(CommonUtils.getTodayDate())) ? Constants.RECEIVED : Constants.NOT_RECEIVED);
							
						}
					}
					else {
						ispResponse.setFirstBreath(
								ispData.getLastfirstbreath_readdatetime() != null ? dateConverter.convertDateToHesString(ispData.getLastfirstbreath_readdatetime()) : req.getReplaceBy());
						ispResponse.setLastGasp(
								ispData.getLastgasp_readdatetime() != null ? dateConverter.convertDateToHesString(ispData.getLastgasp_readdatetime()) : req.getReplaceBy());
						if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.INSTANT_IP_PUSH.commandName)) {
							ispResponse.setInstantPushData(
								ispData.getLastinstantpushdata_readdatetime() != null ? dateConverter.convertDateToHesString(ispData.getLastinstantpushdata_readdatetime()) : req.getReplaceBy());
                           }
							else if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.INSTANTANEOUS_READ.commandName)) {
								ispResponse.setInstantaneous(
									ispData.getLastinstanteousreaddatetime() != null ? Constants.RECEIVED : Constants.NOT_RECEIVED);
                             }
							else if(req.getCommand() != null && req.getCommand().equalsIgnoreCase(ConfigCommands.NAME_PLATES.commandName)) {
								ispResponse.setNamePlate(
									ispData.getLastnameplatereaddatetime() != null ? Constants.RECEIVED : Constants.NOT_RECEIVED);
                           }else if(req.getCommand().equalsIgnoreCase("ALL")) {
						ispResponse.setInstantPushData(
								ispData.getLastinstantpushdata_readdatetime() != null ? dateConverter.convertDateToHesString(ispData.getLastinstantpushdata_readdatetime()) : req.getReplaceBy());
						ispResponse.setInstantaneous(
								ispData.getLastinstanteousreaddatetime() != null ? Constants.RECEIVED : Constants.NOT_RECEIVED);
						ispResponse.setNamePlate(
								ispData.getLastnameplatereaddatetime() != null ? Constants.RECEIVED : Constants.NOT_RECEIVED);

					}
						
					}
				  }
				}
				if (!isPresent) {
					ispResponse.setFirstBreath(ispData.getLastfirstbreath_readdatetime() != null
							? dateConverter.convertDateToHesString(ispData.getLastfirstbreath_readdatetime())
							: req.getReplaceBy());
					ispResponse.setLastGasp(ispData.getLastgasp_readdatetime() != null
							? dateConverter.convertDateToHesString(ispData.getLastgasp_readdatetime())
							: req.getReplaceBy());
					ispResponse.setInstantPushData(ispData.getLastinstantpushdata_readdatetime() != null
							? dateConverter.convertDateToHesString(ispData.getLastinstantpushdata_readdatetime())
							: req.getReplaceBy());
					ispResponse.setInstantaneous(ispData.getLastinstanteousreaddatetime() != null ? Constants.RECEIVED
							: Constants.NOT_RECEIVED);
					ispResponse.setNamePlate(ispData.getLastnameplatereaddatetime() != null ? Constants.RECEIVED
							: Constants.NOT_RECEIVED);
				}
			}
			ispResponseList.add(ispResponse);
		}

		LOG.info("Configuration Read Response Prepared.");
	}
}