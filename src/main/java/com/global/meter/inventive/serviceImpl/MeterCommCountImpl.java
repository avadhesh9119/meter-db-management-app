package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.MeterCommCount;
import com.global.meter.data.repository.MeterCommCountRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.AllDevices;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DeviceCommandResponse;
import com.global.meter.inventive.models.DevicesCountResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.MeterBarChartDataCountRequest;
import com.global.meter.inventive.models.MeterCommCountRequest;
import com.global.meter.inventive.models.MeterCommCountResponse;
import com.global.meter.inventive.models.MeterCommDrillDownRequest;
import com.global.meter.inventive.models.MeterCommDrillDownResponse;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.service.MeterCommCountService;
import com.global.meter.inventive.utils.DataUtils;
import com.global.meter.inventive.utils.DevicesCommandCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.MeterCommCountCaster;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class MeterCommCountImpl implements MeterCommCountService {

	private static final Logger LOG = LoggerFactory.getLogger(MeterCommCountImpl.class);

	@Autowired
	DevicesInfoService devicesInfoService;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Autowired
	MeterCommCountCaster meterCommCountCaster;
	
	@Autowired
	MeterCommCountRepository meterCommCountRepo;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	DevicesCommandCaster deviceCmdCaster;

	@Override
	public CommonResponse getMeterCommCount() {
		CommonResponse response = new CommonResponse();

		LOG.info("Meter Comm Count Process Service Called");

		try {

			// Fetching Owner Name List From DB
			List<String> ownerList = devicesInfoService.getAllOwnersList();
			LOG.info("Getting all owner list name from owners");
			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;

			DataUtils.allDeviceList = new CopyOnWriteArrayList<AllDevices>();
			
			if (ownerList != null && ownerList.size() > 0) {
				for (String owner : ownerList) {
					LOG.info("Fetching device list by: " + owner);
					// Fetching DevicesInfo List By Owner Name
					String query = "Select * from " + table + " Where owner_name = '" + owner + "'";

					String devicesInfoList = CommonUtils.getMapper()
							.writeValueAsString(prestoJdbcTemplate.queryForList(query));
					List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
							new TypeReference<List<DevicesInfo>>() {
							});
					if (devicesList != null && devicesList.size() > 0) {
						
						AllDevices device = new AllDevices();
						device.setUtility(owner);
						device.setDevicesInfo(devicesList);
						DataUtils.allDeviceList.add(device);
						
						meterCommCountRepo.saveAll(new DataUtils(prestoJdbcTemplate).meterCommCountData(owner));
						LOG.info("Meter Comm Count Successfully Stored in DB.");

						response.setCode(200);
						response.setMessage(ExternalConstants.Message.ADDED);
					}
				}
			}

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	

	
	@Override
	public CommonResponse getMeterCommCountInfoByOwnerName(MeterCommCountRequest meterCommCountRequest) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Meter Comm Count Service Called..");
		try {
			MeterCommCountResponse meterCommCountRes = new MeterCommCountResponse();

			if (!StringUtils.isEmpty(meterCommCountRequest.getUtility())) {
				Optional<MeterCommCount> meterCommCount = meterCommCountRepo
						.findByOwnerName(meterCommCountRequest.getUtility());

				if (!meterCommCount.isPresent()) {
					LOG.info("Utility Name Does Not Exixts In Device Meter Comm Count: "
							+ meterCommCountRequest.getUtility());
					error.setErrorMessage(
							meterCommCountRequest.getUtility() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				LOG.info("Data add in MeterCommCountResponse");
				MeterCommCount meterComCount = meterCommCount.get();

				meterCommCountRes.setBillingDayFailureCount(meterComCount.getBillingdayfailurecount());
				meterCommCountRes.setBillingDaySuccessCount(meterComCount.getBillingdaysuccesscount());
				meterCommCountRes.setBillingMonthFailureCount(meterComCount.getBillingmonthfailurecount());
				meterCommCountRes.setBillingMonthSuccessCount(meterComCount.getBillingmonthsuccesscount());
				meterCommCountRes.setBillingWeekFailureCount(meterComCount.getBillingweekfailurecount());
				meterCommCountRes.setBillingWeekSuccessCount(meterComCount.getBillingweeksuccesscount());
				meterCommCountRes.setBillingYesFailureCount(meterComCount.getBillingyestfailurecount());
				meterCommCountRes.setBillingYesSuccessCount(meterComCount.getBillingyestsuccesscount());
				meterCommCountRes.setCommDayFailureCount(meterComCount.getCommdayfailurecount());
				meterCommCountRes.setCommDaySuccessCount(meterComCount.getCommdaysuccesscount());
				meterCommCountRes.setComMonthFailureCount(meterComCount.getCommmonthfailurecount());
				meterCommCountRes.setComMonthSuccessCount(meterComCount.getCommmonthsuccesscount());
				meterCommCountRes.setCommWeekFailureCount(meterComCount.getCommweekfailurecount());
				meterCommCountRes.setCommWeekSuccessCount(meterComCount.getCommweeksuccesscount());
				meterCommCountRes.setCommYestFailureCount(meterComCount.getCommyestfailurecount());
				meterCommCountRes.setCommYestSuccessCount(meterComCount.getCommyestsuccesscount());
				meterCommCountRes.setCtPhase(meterComCount.getCtphase());
				meterCommCountRes.setDailyDayFailureCount(meterComCount.getDailydayfailurecount());
				meterCommCountRes.setDailyDaySuccessCount(meterComCount.getDailydaysuccesscount());
				meterCommCountRes.setDailyMonthFailureCount(meterComCount.getDailymonthfailurecount());
				meterCommCountRes.setDailyMonthSuccessCount(meterComCount.getDailymonthsuccesscount());
				meterCommCountRes.setDailyWeekFailureCount(meterComCount.getDailyweekfailurecount());
				meterCommCountRes.setDailyWeekSuccessCount(meterComCount.getDailyweeksuccesscount());
				meterCommCountRes.setDailyYesFailureCount(meterComCount.getDailyyestfailurecount());
				meterCommCountRes.setDailyYesSuccessCount(meterComCount.getDailyyestsuccesscount());
				meterCommCountRes.setDcu(meterComCount.getDcu());
				meterCommCountRes.setDeltaDayFailureCount(meterComCount.getDeltadayfailurecount());
				meterCommCountRes.setDeltaDaySuccessCount(meterComCount.getDeltadaysuccesscount());
				meterCommCountRes.setDeltaMonthFailureCount(meterComCount.getDeltamonthfailurecount());
				meterCommCountRes.setDeltaMonthSuccessCount(meterComCount.getDeltamonthsuccesscount());
				meterCommCountRes.setDeltaWeekFailureCount(meterComCount.getDeltaweekfailurecount());
				meterCommCountRes.setDeltaWeekSuccessCount(meterComCount.getDeltaweeksuccesscount());
				meterCommCountRes.setDeltaYesFailureCount(meterComCount.getDeltayestfailurecount());
				meterCommCountRes.setDeltaYesSuccessCount(meterComCount.getDeltayestsuccesscount());
				meterCommCountRes.setDevice(meterComCount.getDevice());
				meterCommCountRes.setDt(meterComCount.getDt());
				meterCommCountRes.setEventDayFailureCount(meterComCount.getEventdayfailurecount());
				meterCommCountRes.setEventDaySuccessCount(meterComCount.getEventdaysuccesscount());
				meterCommCountRes.setEventMonthFailureCount(meterComCount.getEventmonthfailurecount());
				meterCommCountRes.setEventMonthSuccessCount(meterComCount.getEventmonthsuccesscount());
				meterCommCountRes.setEventWeekFailureCount(meterComCount.getEventweekfailurecount());
				meterCommCountRes.setEventWeekSuccessCount(meterComCount.getEventweeksuccesscount());
				meterCommCountRes.setEventYesFailureCount(meterComCount.getEventyestfailurecount());
				meterCommCountRes.setEventYesSuccessCount(meterComCount.getEventyestsuccesscount());
				meterCommCountRes.setFeeder(meterComCount.getFeeder());
				meterCommCountRes.setHtPhase(meterComCount.getHtphase());
				meterCommCountRes.setInActiveDev(meterComCount.getInactivedev());
				meterCommCountRes.setInstantDayFailureCount(meterComCount.getInstantdayfailurecount());
				meterCommCountRes.setInstantDaySuccessCount(meterComCount.getInstantdaysuccesscount());
				meterCommCountRes.setInstantMonthFailureCount(meterComCount.getInstantmonthfailurecount());
				meterCommCountRes.setInstantMonthSuccessCount(meterComCount.getInstantmonthsuccesscount());
				meterCommCountRes.setInstantWeekFailureCount(meterComCount.getInstantweekfailurecount());
				meterCommCountRes.setInstantWeekSuccessCount(meterComCount.getInstantweeksuccesscount());
				meterCommCountRes.setInstantYestFailureCount(meterComCount.getInstantyestfailurecount());
				meterCommCountRes.setInstantYestSuccessCount(meterComCount.getInstantyestsuccesscount());
				meterCommCountRes.setLastUpdatedTime(dateConverter.convertDateToString(meterComCount.getLastupdatedtime()));
				meterCommCountRes.setManualActiveDev(meterComCount.getManual_active_dev());
				meterCommCountRes.setManualFaultyDev(meterComCount.getManual_faulty_dev());
				meterCommCountRes.setSinglePhase(meterComCount.getSinglephase());
				meterCommCountRes.setSubDevision(meterComCount.getSubdevision());
				meterCommCountRes.setSubstation(meterComCount.getSubstation());
				meterCommCountRes.setThreePhase(meterComCount.getThreephase());
				LOG.info("Data add in MeterCommCountResponse Success");
				
			}
			 
			LOG.info("Meter Comm Count Data Fetch Sucess.");
			response.setData(meterCommCountRes);
		} catch (Exception e) {
			LOG.info("Meter Comm Count Data Fetch Failure: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getMeterCommDrillDown(MeterCommDrillDownRequest req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Meter Comm Count Drill Down Service Called");

		try {
			if(req != null) {
				
				List<MeterCommDrillDownResponse> drillDownResponse = new ArrayList<>();
				
				if(DataUtils.allDeviceList != null && DataUtils.allDeviceList.size() > 0) {
					
					for(AllDevices device : DataUtils.allDeviceList) {
						if(device != null) {
							if(device.getUtility() != null && device.getUtility().equalsIgnoreCase(req.getHier().getValue())) {
								meterCommCountCaster.prepareMeterCommDrillDown(device.getDevicesInfo(), drillDownResponse, req);
							}
						}
					}
					
					
				}else {
					String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;

					String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
					String[] levels = req.getHier().getValue().split(",");

					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append("select * from ").append(table).append(" where ").append(fieldName).append(" in (");
					
					for (String level : levels) {
						queryBuilder.append("'").append(level).append("',");
					}
					String query = queryBuilder.substring(0, queryBuilder.length() - 1);
					query = query.concat(")");

					String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
				
					List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(outputList,
							new TypeReference<List<DevicesInfo>>() {
							});
					
					meterCommCountCaster.prepareMeterCommDrillDown(devicesList, drillDownResponse, req);
				}
				
				
				
				if(drillDownResponse.size() > 0) {
					response.setData(drillDownResponse);
				}else {
					response.setMessage(Constants.NO_DATA_FOUND);
				}
				
				LOG.info("Meter Comm Count Drill Down Result Success.");
				
			}else {
				LOG.error("Issue in fetching data due to MeterCommDrillDown Request is Null.");
			}
			
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	
	@Override
	public CommonResponse getMeterDataCount(MeterDataVisualizationReq req) {

		LOG.info("Meter data count service called.");
		CommonResponse response = new CommonResponse();
	
		try {
			LOG.info("Get devices info & name plate data count from DB:");
			
			String devInfoTable = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_INFO;
			String namePlateTable = meterConfiguration.getKeyspace() + "." + Tables.NAME_PLATE;
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			StringBuilder fieldBuilder = new StringBuilder();
			StringBuilder queryBuilderInfo = new StringBuilder();
			StringBuilder queryBuilderName = new StringBuilder();
			
			fieldBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				fieldBuilder.append("'").append(level).append("',");
			}
			String fieldNameValue=fieldBuilder.substring(0, fieldBuilder.length()-1);
			queryBuilder.append("select (")
			.append("select count(commissioning_status) from ").append(devInfoTable).append(" where ").append(fieldNameValue).append(") and commissioning_status = 'Up') as activeCount, (")
			.append("select count(lastcommunicationdatetime) from ").append(devInfoTable).append(" where ").append("lastcommunicationdatetime <= cast('")
			.append(req.getFromDate()).append("' as timestamp) and ").append(fieldNameValue).append(")) as communationFieldCount");

			String query = queryBuilder.substring(0, queryBuilder.length());
			
			queryBuilderInfo.append("select ")
			.append("device_serial_number as devicesinfoSet from ").append(devInfoTable).append(" where ").append(fieldNameValue).append(")");
			String queryInfo = queryBuilderInfo.substring(0, queryBuilderInfo.length());
			queryBuilderName.append("select ")
			.append("device_serial_number as devicesNamePlateSet from ").append(namePlateTable).append(" where ").append(fieldNameValue).append(") and status = 'VALID'");
			String queryNamePlate = queryBuilderName.substring(0, queryBuilderName.length());
	
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			String outputListInfo = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryInfo));
			String outputListName = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryNamePlate));

			List<DevicesCountResponse> ispResponseList = new ArrayList<>();
			meterCommCountCaster.prepareMeterDataCount(outputList, outputListInfo, outputListName, ispResponseList);
			response.setData(ispResponseList);
			LOG.info("Meter data count service success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;	
	}

	@Override
	public CommonResponse getMeterBarChartDataCount(MeterDataVisualizationReq req) {
		
		LOG.info("Meter data count service called.");
		CommonResponse response = new CommonResponse();
		Set<String> cmdSet = new HashSet<>();
		
		try {
			LOG.info("Get devices command data count from DB:");
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
			String tableFull = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;
			String tableDevInfo = meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] commandTypes = req.getCommand().split(",");

			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select cast(devices_commands.mdas_datetime AS DATE) date , count(devices_commands.command_name) count , devices_commands.command_name, sum(case when lower(devices_commands.status) ='success' then 1 else 0 end) as success_count from ")
			.append(table).append(" inner join ").append(tableDevInfo).append(" on ").append(table).append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
			.append(" devices_commands.mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp)")
			.append(" and devices_commands.mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
			
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				
				queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
			}
			
			queryBuilder.append("devices_commands.").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and devices_commands.command_name in (");
			for (String commandType : commandTypes) {
				queryBuilder.append("'").append(commandType.trim()).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") group by devices_commands.command_name,cast(devices_commands.mdas_datetime AS DATE) order by date");
			String query = queryBuilder.substring(0, queryBuilder.length());
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<MeterBarChartDataCountRequest> singleCmdList = new ArrayList<MeterBarChartDataCountRequest>();
			singleCmdList = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<MeterBarChartDataCountRequest>>() {
			});
			
			List<MeterBarChartDataCountRequest> combinedList = new ArrayList<MeterBarChartDataCountRequest>();
			List<MeterBarChartDataCountRequest> fullCommandList = new ArrayList<MeterBarChartDataCountRequest>();

			for(String cmd : commandTypes) {
				
				StringBuilder queryBuilderFull = new StringBuilder();
				queryBuilderFull.append("select cast(devices_commands_logs.mdas_datetime AS DATE) date , count(*) as count , sum(case when (lower(devices_commands_logs.command_name) like '%\"")
				.append(cmd.trim().toLowerCase()).append("\":\"success\"%' or lower(devices_commands_logs.command_name) like '%\"")
				.append(cmd.trim().toLowerCase()).append("\":\"").append(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH.toLowerCase()).append("\"%') then 1 else 0 end) as success_count from ")                                     
				.append(tableFull).append(" inner join ").append(tableDevInfo).append(" on ").append(tableFull).append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
				.append(" devices_commands_logs.mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp)")
				.append(" and devices_commands_logs.mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
				
				if(req.getDevType() != null && !req.getDevType().isEmpty()) {
					
					queryBuilderFull.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
				}
				queryBuilderFull.append("devices_commands_logs.").append(fieldName).append(" in (");
				for (String level : levels) {
					queryBuilderFull.append("'").append(level).append("',");
				}
				queryBuilderFull.replace(queryBuilderFull.length()-1, queryBuilderFull.length(), "");
				queryBuilderFull.append(") and lower(devices_commands_logs.command_name) like '%\"").append(cmd.trim().toLowerCase()).append("\"%' ")
				.append("group by cast(devices_commands_logs.mdas_datetime AS DATE) order by date");
				
				String queryFull = queryBuilderFull.substring(0, queryBuilderFull.length());
				
				String outputListFull = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryFull));
				meterCommCountCaster.prepareProcessOnDemandCommandCombined(singleCmdList, outputListFull, combinedList, cmd, fullCommandList, req, cmdSet);
			}
				for(MeterBarChartDataCountRequest data : singleCmdList) {
					if (!cmdSet.contains(data.getCommand_name())) {
					       
					MeterBarChartDataCountRequest newReq = new MeterBarChartDataCountRequest();
					newReq.setCommand_name(data.getCommand_name());
					newReq.setSuccess_count(data.getSuccess_count());
					newReq.setCount(data.getCount());
					newReq.setSuccess_percentage(String.valueOf(String.valueOf(CommonUtils.decimalFormat((Double.valueOf(data.getSuccess_count())*100)/Double.valueOf(data.getCount())))));
					newReq.setDate(data.getDate());
					combinedList.add(newReq);
		           }
				}
			List<DevicesCountResponse> ispResponseList = new ArrayList<>();
			meterCommCountCaster.prepareMeterBarChartCount(combinedList, ispResponseList);
			meterCommCountCaster.prepareProcessOnDemandCommand(combinedList);
			response.setData(ispResponseList);
			LOG.info("Meter data count service success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;	
	}
	@Override
	public CommonResponse getMeterBarChartDataCountDrillDown(MeterDataVisualizationReq req) {
		
		LOG.info("Meter data count service called.");
		CommonResponse response = new CommonResponse();
		
		try {
			LOG.info("Get devices command data count from DB:");
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
			String tableFullCommand = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] commands = req.getCommand().split(",");
			
			String fromDate= dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))+Constants.FROM_MID_NIGHT;
			String toDate= dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))+Constants.TO_MID_NIGHT;
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ( ")
			.append("select device_serial_number, tracking_id, batch_id, command_completion_datetime, command_name, mdas_datetime, owner_name, reason, source, status, tot_attempts, user_id from ").append(table).append(" where ")
			.append(" mdas_datetime >= cast('").append(fromDate).append("' as timestamp)")
			.append(" and mdas_datetime <= cast('").append(toDate).append("' as timestamp) and ")
			.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and command_name in (");
			for (String command : commands) {
				queryBuilder.append("'").append(command).append("',");
			}
			
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and lower(status) = 'success'")
			.append(" UNION ALL ")
 			.append("select device_serial_number, tracking_id, batch_id, command_completion_datetime, command_name, mdas_datetime, owner_name, reason, source, status, tot_attempts, user_id from ").append(tableFullCommand).append(" where ")
			.append(" mdas_datetime >= cast('").append(fromDate).append("' as timestamp)")
			.append(" and mdas_datetime <= cast('").append(toDate).append("' as timestamp) and ")
			.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ") and (");
			for (String command : commands) {
			queryBuilder.append(" lower(command_name) like '%\"").append(command.trim().toLowerCase()).append("\":\"success\"%' or");
			}
			queryBuilder.replace(queryBuilder.length()-3, queryBuilder.length(), "))");
			String query = queryBuilder.substring(0, queryBuilder.length());
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
			meterCommCountCaster.prepareDeviceCommandDrillDown(outputList, ispResponseList, req);
			response.setData(ispResponseList);
		
			LOG.info("Devices Config Data Service By Hier Response Success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	
		
	}
	
	@Override
	public CommonResponse getMeterCountSuccessCommand(MeterDataVisualizationReq req) {
		
		LOG.info("Meter count for success command service called.");
		CommonResponse response = new CommonResponse();
		try {
			LOG.info("Get devices command data count from DB:");
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
			String tableFull = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;
			String tableDevInfo = meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select Distinct device_serial_number as device, date from (")
			.append("select devices_commands.device_serial_number, cast(devices_commands.mdas_datetime AS DATE) date from ")
			.append(table).append(" inner join ").append(tableDevInfo).append(" on ").append(table).append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
			.append(" devices_commands.mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp)")
			.append(" and devices_commands.mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and lower(devices_commands.status) = 'success' and ");
			
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				
				queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
			}
			queryBuilder.append("devices_commands.").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and devices_commands.command_name = '").append(req.getCommand().trim()).append("'")
			
				.append(" UNION ALL ")
				.append("select devices_commands_logs.device_serial_number, cast(devices_commands_logs.mdas_datetime AS DATE) date from ")
				.append(tableFull).append(" inner join ").append(tableDevInfo).append(" on ")
				.append(tableFull).append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
				.append(" devices_commands_logs.mdas_datetime >= cast('").append(req.getFromDate()).append("' as timestamp)")
				.append(" and devices_commands_logs.mdas_datetime <= cast('").append(req.getToDate()).append("' as timestamp) and ");
				if(req.getDevType() != null && !req.getDevType().isEmpty()) {
					
					queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
				}
				queryBuilder.append("devices_commands_logs.").append(fieldName).append(" in (");
			
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("',");
				}
				queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
				queryBuilder.append(") and (lower(devices_commands_logs.command_name) like '%\"").append(req.getCommand().trim().toLowerCase()).append("\":\"success\"%' or lower(devices_commands_logs.command_name) like '%\"").append(req.getCommand().trim().toLowerCase()).append("\":\"").append(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH.toLowerCase()).append("\"%')) order by date desc");
				String query = queryBuilder.substring(0, queryBuilder.length());
				
				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<DevicesCountResponse> ispResponseList = new ArrayList<>();
			meterCommCountCaster.prepareMeterCountForSuccessCommandRes(outputList, ispResponseList);
			response.setData(ispResponseList);
			LOG.info("Meter count for success command  service success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;	
	}
	@Override
	public CommonResponse getMeterCountSuccessCommandDrillDown(MeterDataVisualizationReq req) {
		
		LOG.info("DrillDown Meter count for success command service called.");
		CommonResponse response = new CommonResponse();
		try {
			LOG.info("Get devices command data count from DB:");
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
			String tableFull = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;
			String tableDevInfo = meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			String fromDate= dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))+Constants.FROM_MID_NIGHT;
			String toDate= dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))+Constants.TO_MID_NIGHT;
	
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] commandTypes = req.getCommand().split(",");
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ( ")
			.append("select devices_commands.device_serial_number, devices_commands.tracking_id, "
					+ "devices_commands.batch_id, devices_commands.command_completion_datetime, "
					+ "devices_commands.command_name, devices_commands.mdas_datetime, devices_commands.owner_name,"
					+ " devices_commands.reason, devices_commands.source, devices_commands.status, devices_commands.tot_attempts,"
					+ " devices_commands.user_id from ")
			.append(table).append(" inner join ").append(tableDevInfo).append(" on ").append(table)
			.append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")

			.append(" devices_commands.mdas_datetime >= cast('").append(fromDate).append("' as timestamp)")
			.append(" and devices_commands.mdas_datetime <= cast('").append(toDate)
			.append("' as timestamp) and ");
			if(req.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
				queryBuilder.append("(lower(devices_commands.status) = 'success' or lower(devices_commands.status) = '").append(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH.toLowerCase()).append("') and ");
			}
			else if (req.getStatus().equalsIgnoreCase(Constants.UNSUCCESS)) {
				queryBuilder.append("(lower(devices_commands.status) = 'in_progress'  or lower(devices_commands.status) = 'failure') and ");
			}
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				
				queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
			}
			queryBuilder.append("devices_commands.").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and devices_commands.command_name in (");
			for (String commandType : commandTypes) {
				queryBuilder.append("'").append(commandType.trim()).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
							
			queryBuilder.append(" UNION ALL ")
			.append("select devices_commands_logs.device_serial_number, devices_commands_logs.tracking_id,"
						+ " devices_commands_logs.batch_id, devices_commands_logs.command_completion_datetime, devices_commands_logs.command_name,"
						+ " devices_commands_logs.mdas_datetime, devices_commands_logs.owner_name, devices_commands_logs.reason,"
						+ " devices_commands_logs.source, devices_commands_logs.status, devices_commands_logs.tot_attempts, devices_commands_logs.user_id from ")
				.append(tableFull).append(" inner join ").append(tableDevInfo).append(" on ").append(tableFull).append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
				.append(" devices_commands_logs.mdas_datetime >= cast('").append(fromDate).append("' as timestamp)")
				.append(" and devices_commands_logs.mdas_datetime <= cast('").append(toDate).append("' as timestamp) and ");
				if(req.getDevType() != null && !req.getDevType().isEmpty()) {
					
					queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
				}
				queryBuilder.append("devices_commands_logs.").append(fieldName).append(" in (");
			
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("',");
				}
				queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ") and (");
				if(req.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
					for (String command : commandTypes) {
					     queryBuilder.append(" (lower(command_name) like '%\"").append(command.trim().toLowerCase()).append("\":\"success\"%' or");
					     queryBuilder.append(" lower(command_name) like '%\"").append(command.trim().toLowerCase()).append("\":\"").append(Constants.BILL_ALREADY_EXIST_CUURENT_MONTH.toLowerCase()).append("\"%') or");
					}
				}
				else if (req.getStatus().equalsIgnoreCase(Constants.UNSUCCESS)) {
					for (String command : commandTypes) {
					     queryBuilder.append(" (lower(command_name) like '%\"").append(command.trim().toLowerCase()).append("\":\"in_progress\"%' or");
					     queryBuilder.append(" lower(command_name) like '%\"").append(command.trim().toLowerCase()).append("\":\"failure\"%') or");
					}	
				}
					queryBuilder.replace(queryBuilder.length()-3, queryBuilder.length(), "))")
				.append(" order by mdas_datetime");
				String queryFull = queryBuilder.substring(0, queryBuilder.length());
				String outputListFull = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryFull));
			List<DeviceCommandResponse> ispResponseList = new ArrayList<>();
			meterCommCountCaster.prepareDeviceCommandDrillDown(outputListFull, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("DrillDown Meter count for success command  service success.");

		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;	
	}
	
	@Override
	public CommonResponse getMeterSuccessCommandDrillDown(MeterDataVisualizationReq req) {
		
		LOG.info("DrillDown Meter count for success command service called.");
		CommonResponse response = new CommonResponse();
		try {
			LOG.info("Get devices command data count from DB:");
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_COMMANDS;
			String tableFull = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_CMD_LOGS;
			String tableDevInfo = meterConfiguration.getKeyspace() + "." + Tables.devInfo;
			String fromDate= dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))+Constants.FROM_MID_NIGHT;
			String toDate= dateConverter.convertDayToString(dateConverter.convertStringDayToDay(req.getBarChartDate()))+Constants.TO_MID_NIGHT;
			
			String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
			String[] levels = req.getHier().getValue().split(",");
			String[] commandTypes = req.getCommand().split(",");
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select DISTINCT * from ( ")
			.append("select devices_info.device_serial_number, devices_info.device_type,"
					+ " devices_info.commissioning_status, devices_info.ip_address_main, devices_info.ip_port_main,"
					+ " devices_info.meter_type, devices_info.consumer_name, devices_info.dev_mode,"
					+ " devices_info.sanction_load from ")
			.append(table).append(" inner join ").append(tableDevInfo).append(" on ").append(table)
			.append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
			
			.append(" devices_commands.mdas_datetime >= cast('").append(fromDate).append("' as timestamp)")
			.append(" and devices_commands.mdas_datetime <= cast('").append(toDate).append("' as timestamp) and lower(devices_commands.status) = 'success' and ");
			
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				
				queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
			}
			queryBuilder.append("devices_commands.").append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "");
			queryBuilder.append(") and devices_commands.command_name in (");
			for (String commandType : commandTypes) {
				queryBuilder.append("'").append(commandType.trim()).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ")");
			
			queryBuilder.append(" UNION ALL ")
			.append("select devices_info.device_serial_number, devices_info.device_type,"
					+ " devices_info.commissioning_status, devices_info.ip_address_main, devices_info.ip_port_main,"
					+ " devices_info.meter_type, devices_info.consumer_name, devices_info.dev_mode,"
					+ " devices_info.sanction_load from ")
			.append(tableFull).append(" inner join ").append(tableDevInfo).append(" on ").append(tableFull).append(".device_serial_number=").append(tableDevInfo).append(".device_serial_number where ")
			.append(" devices_commands_logs.mdas_datetime >= cast('").append(fromDate).append("' as timestamp)")
			.append(" and devices_commands_logs.mdas_datetime <= cast('").append(toDate).append("' as timestamp) and ");
			if(req.getDevType() != null && !req.getDevType().isEmpty()) {
				
				queryBuilder.append("devices_info.device_type ='").append(req.getDevType()).append("' and ");
			}
			queryBuilder.append("devices_commands_logs.").append(fieldName).append(" in (");
			
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ") and (");
			for (String command : commandTypes) {
				queryBuilder.append(" lower(command_name) like '%\"").append(command.trim().toLowerCase()).append("\":\"success\"%' or");
			}
			queryBuilder.replace(queryBuilder.length()-3, queryBuilder.length(), "))");
			String queryFull = queryBuilder.substring(0, queryBuilder.length());
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(queryFull));
			List<MeterDataRes> ispResponseList = new ArrayList<>();
			meterCommCountCaster.prepareDeviceCommandSuccessDrillDown(outputList, ispResponseList, req);
			response.setData(ispResponseList);
			LOG.info("DrillDown Meter count for success command  service success.");
			
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;	
	}
}
