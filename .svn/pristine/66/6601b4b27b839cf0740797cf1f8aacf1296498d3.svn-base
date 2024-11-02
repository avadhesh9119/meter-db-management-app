package com.global.meter.inventive.dashboard.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.MeterDBAppStarter;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.DtTrans;
import com.global.meter.business.model.Feeders;
import com.global.meter.business.model.MeterTypeInfo;
import com.global.meter.business.model.Subdivisions;
import com.global.meter.business.model.Substations;
import com.global.meter.data.repository.DtTransRepository;
import com.global.meter.data.repository.FeedersRepository;
import com.global.meter.data.repository.MeterTypeRepository;
import com.global.meter.data.repository.SubdivisionsRepository;
import com.global.meter.data.repository.SubstationRepository;
import com.global.meter.inventive.dashboard.business.model.MeterCommissioningLogs;
import com.global.meter.inventive.dashboard.business.model.ProcessSlaData;
import com.global.meter.inventive.dashboard.model.CommunicationSummaryDataResponse;
import com.global.meter.inventive.dashboard.model.DailySummaryReportResponse;
import com.global.meter.inventive.dashboard.model.MeterCommissioningReportResponse;
import com.global.meter.inventive.dashboard.model.ProcessSlaDataRequest;
import com.global.meter.inventive.dashboard.repository.MeterCommissioningLogsRepository;
import com.global.meter.inventive.dashboard.repository.ProcessSlaDataRepository;
import com.global.meter.inventive.dashboard.service.ProcessSlaDataService;
import com.global.meter.inventive.dashboard.utils.DailySummaryReportCaster;
import com.global.meter.inventive.dashboard.utils.MeterCommissioningReportCaster;
import com.global.meter.inventive.dashboard.utils.ProcessSlaDataUtils;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.AllDevices;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.service.DevicesInfoService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class ProcessSlaDataServiceImpl implements ProcessSlaDataService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessSlaDataService.class);
	
	@Autowired
	CommonUtils commonUtils;
	
	@Autowired
	ProcessSlaDataRepository processSlaDataRepository;
	
	@Autowired
	DevicesInfoService devicesInfoService;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired 
	SubdivisionsRepository subDivisionRepo;

	@Autowired 
	SubstationRepository subStationRepo;
	
	@Autowired
	FeedersRepository feedersRepo;
	
	@Autowired
	DtTransRepository dtTransRepo;
	
	@Autowired
	MeterTypeRepository meterTypeRepo;
	
	@Autowired
	DailySummaryReportCaster summaryReportCaster;

	@Autowired
	MeterCommissioningReportCaster commissioningReportCaster;

	@Autowired
	MeterCommissioningLogsRepository meterCommissioningLogsRepo;
	
	@Override
	public CommonResponse addDailySummaryData() {
		CommonResponse response = new CommonResponse();
		LOG.info("Generate Daily Summary Data ServiceImpl Called.");
		try {

			// Fetching Owner Name List From DB
			List<String> ownerList = devicesInfoService.getAllOwnersList();
			LOG.info("Getting all owner list name from owners");
			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;

			ProcessSlaDataUtils.allDeviceList = new CopyOnWriteArrayList<AllDevices>();
			
			if (ownerList != null && ownerList.size() > 0) {
				for (String owner : ownerList) {
					LOG.info("Fetching device list by: " + owner);
					// Fetching DevicesInfo List From By Owner Name
					String query = "Select * from " + table + " Where owner_name = '" + owner + "'" + " AND commissioning_status in ('Up','Installed')";

					String devicesInfoList = CommonUtils.getMapper()
							.writeValueAsString(prestoJdbcTemplate.queryForList(query));
					List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
							new TypeReference<List<DevicesInfo>>() {
							});
					if (devicesList != null && devicesList.size() > 0) {
						
						AllDevices device = new AllDevices();
						device.setUtility(owner);
						device.setDevicesInfo(devicesList);
						ProcessSlaDataUtils.allDeviceList.add(device);
						LOG.info("Generate Daily Summary Data Successfully Stored in DB.");
						//response.setData(new ProcessSlaDataUtils(prestoJdbcTemplate,meterConfiguration,commonUtils).processSlaData(owner));
						processSlaDataRepository.saveAll(new ProcessSlaDataUtils(prestoJdbcTemplate,meterConfiguration,commonUtils).processSlaData(owner));
						response.setCode(200);
						response.setMessage(ExternalConstants.Message.ADDED);
						
						ProcessSlaDataUtils.allDeviceList = null;
						ProcessSlaDataUtils.loadProfileSpList = null;
						ProcessSlaDataUtils.loadProfileTpList = null;
						ProcessSlaDataUtils.loadProfileCtList = null;
						ProcessSlaDataUtils.deltaLpSpList = null;
						ProcessSlaDataUtils.deltaLpTpList = null;
						ProcessSlaDataUtils.deltaLpCtList = null;
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
	public CommonResponse getDailySummaryData(ProcessSlaDataRequest req) {
	    CommonResponse response = new CommonResponse();
	    LOG.info("Generate Daily Summary Data ServiceImpl Called.");

	    try {
	        String processTable = meterConfiguration.getKeyspace() + "." + Tables.PROCESS_SLA_DATA;
	        String deviceTable = meterConfiguration.getKeyspace() + "." + Tables.DEVICES_INFO;
	        String meterType = "";

	        if (ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(req.getDevType())) {
	            meterType = Constants.DeviceTypes.SINGLE_PHASE;
	        } else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(req.getDevType())) {
	            meterType = Constants.DeviceTypes.THREE_PHASE;
	        } else if (ExternalConstants.DeviceTypes.CT_METER.contains(req.getDevType())) {
	            meterType = Constants.DeviceTypes.CT;
	        } else if (!Constants.ALL.equalsIgnoreCase(req.getDevType())) {
	            response.setMessage(Constants.WRONG_DEVICE_TYPE);
	            response.setCode(404);
	            response.setError(true);
	            return response;
	        }

	        List<DevicesInfo> devicesInfo = null;

	        if (req.getHier() != null && req.getHier().getName().equals(Constants.HIRE_NAME)) {
	            devicesInfo = devicesInfoService.getDevicesList(req.getHier().getName(), req.getHier().getValue());
	            if (devicesInfo == null) {
	                response.setMessage(Constants.NO_DATA_FOUND);
	                response.setCode(404);
	                return response;
	            }
	        }

	        String fieldName = ExternalConstants.getLevelValue(req.getHier().getName());
	        String[] levels = req.getHier().getValue().split(",");
	        StringBuilder processBuilder = new StringBuilder();

	        processBuilder.append("SELECT * FROM ").append(processTable).append(" WHERE ");
	        if (!meterType.isEmpty() && !meterType.equals(Constants.ALL)) {
	            processBuilder.append("device_type = '").append(meterType).append("' AND ");
	        }
	        processBuilder
	            .append("mdas_datetime >= CAST('").append(req.getFromDate()).append("' AS TIMESTAMP) ")
	            .append("AND mdas_datetime <= CAST('").append(req.getToDate()).append("' AS TIMESTAMP) AND ")
	            .append(fieldName).append(" IN (");
	        
	        for (String level : levels) {
	            processBuilder.append("'").append(level).append("',");
	        }

	        String processQuery = processBuilder.substring(0, processBuilder.length() - 1) + ") ORDER BY mdas_datetime DESC";
	        String processOutputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(processQuery));

	        List<ProcessSlaData> processSlaDatas = CommonUtils.getMapper().readValue(processOutputList, new TypeReference<List<ProcessSlaData>>() {});

	        StringBuilder queryBuilder = new StringBuilder();
	        queryBuilder.append("SELECT * FROM ").append(deviceTable).append(" WHERE ");
	        if (!meterType.isEmpty()) {
	            queryBuilder.append("device_type = '").append(meterType).append("' AND ");
	        }
	        queryBuilder.append("commissioning_status in ('Up','Installed') AND ");
	        queryBuilder.append(fieldName).append(" IN (");

	        for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(")");
	        

	        String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

	        List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DevicesInfo>>() {});

	        List<DailySummaryReportResponse> ispResponseList = new ArrayList<>();

	        if (devicesList != null && !devicesList.isEmpty() && processSlaDatas != null && !processSlaDatas.isEmpty()) {
	            summaryReportCaster.prepareDailySummaryData(devicesList, processSlaDatas, ispResponseList, req);
	        } else {
	            response.setMessage(Constants.NO_DATA_FOUND);
	            response.setCode(404);
	            return response;
	        }

	        response.setData(ispResponseList);
	        response.setCode(200);
	        response.setError(false);

	    } catch (Exception e) {
	        LOG.error("Issue in fetching data due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
	    }

	    return response;
	}

	
	@Override
	public CommonResponse getCommunicationSummaryData() {
	    CommonResponse response = new CommonResponse();
	    LOG.info("Generate Communication Summary Data ServiceImpl Called.");

	    try {
	        String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;

	        List<CommunicationSummaryDataResponse> communicationSummaryDataResponses = new ArrayList<>();

	        List<Subdivisions> subDivisions = subDivisionRepo.findAll();
	        List<String> subdivisionList = subDivisions.stream().map(division -> division.getSubdivision_name()).collect(Collectors.toList());

	        List<Substations> subStations = subStationRepo.findAll();
	        List<String> substationList = subStations.stream().map(division -> division.getSubstation_name()).collect(Collectors.toList());

	        List<Feeders> feeders = feedersRepo.findAll();
	        List<String> feederList = feeders.stream().map(division -> division.getFeeder_name()).collect(Collectors.toList());

	        List<DtTrans> dts = dtTransRepo.findAll();
	        List<String> dtList = dts.stream().map(division -> division.getDt_name()).collect(Collectors.toList());

	        List<MeterTypeInfo> meters = meterTypeRepo.findAll();
	        List<String> meterList = meters.stream().map(division -> division.getManufacturer()).collect(Collectors.toList());

	        HashMap<String, List<String>> countMap = new HashMap<>();
	        countMap.put(Constants.HierLevelKey.SUBDEVISION, subdivisionList);
	        countMap.put(Constants.HierLevelKey.SUBSTATION, substationList);
	        countMap.put(Constants.HierLevelKey.FEEDER, feederList);
	        countMap.put(Constants.HierLevelKey.DTMETER, dtList);
	        countMap.put(Constants.HierLevelKey.MANUFACTURER, meterList);

	        for (Map.Entry<String, List<String>> entry : countMap.entrySet()) {
	            String hierarchyName = ExternalConstants.getLevelValue(entry.getKey());

	            if (hierarchyName.equalsIgnoreCase(Constants.HierLevelName.MANUFACTURER)) {
	                hierarchyName = "meter_type";
	            }

	            String query = String.format("SELECT * FROM %s WHERE %s IN ('%s')", table, hierarchyName, String.join("','", entry.getValue()));

	            List<Map<String, Object>> devicesList = prestoJdbcTemplate.queryForList(query);

	            if (!devicesList.isEmpty()) {
	                int communicationCount = 0;
	                int nonCommunicationCount = 0;

	                for (Map<String, Object> device : devicesList) {
	                    if (commonUtils.isGreaterFromMidNight(Constants.CommType.DayWise, (Date) device.get("lastcommunicationdatetime"))) {
	                        communicationCount++;
	                    } else {
	                        nonCommunicationCount++;
	                    }
	                }

	                CommunicationSummaryDataResponse summaryDataResponse = new CommunicationSummaryDataResponse();
	                summaryDataResponse.setCommunicating(String.valueOf(communicationCount));
	                summaryDataResponse.setNonCommunicating(String.valueOf(nonCommunicationCount));
	                summaryDataResponse.setCommunicatingAvg(String.valueOf(Math.round(CommonUtils.calculatePercentage(devicesList.size(), communicationCount)))+"%");
	                summaryDataResponse.setNonCommunicatingAvg(String.valueOf(Math.round(CommonUtils.calculatePercentage(devicesList.size(), nonCommunicationCount)))+"%");
	                summaryDataResponse.setHesTimeStamp(String.valueOf(new Date(System.currentTimeMillis())));
	                summaryDataResponse.setTotalDevice(String.valueOf(devicesList.size()));
	                summaryDataResponse.setHierName(entry.getKey());
	                summaryDataResponse.setHierValue(ExternalConstants.getUIBatchLevelName(entry.getKey()));

	                communicationSummaryDataResponses.add(summaryDataResponse);
	            }
	        }

	        response.setData(communicationSummaryDataResponses);
	        response.setCode(200);
	        response.setMessage(ExternalConstants.Status.SUCCESS);
	    } catch (Exception e) {
	        LOG.error("Issue in fetching data due to: " + e.getMessage(), e);
			response = ExceptionHandlerConfig.setErrorData(e);
	    }

	    return response;
	}
	
	
	@Override
	public CommonResponse getCommunicationSummaryData(ProcessSlaDataRequest processSlaDataRequest) {
	    CommonResponse response = new CommonResponse();
	    
	    LOG.info("Get Communication Summary Data ServiceImpl Called.");

	    try {
	    	List<MeterDataRes> communicatingDev = new ArrayList<MeterDataRes>();
	    	List<MeterDataRes> nonCommunicatingDev = new ArrayList<MeterDataRes>();
			
			 List<CommunicationSummaryDataResponse> communicationSummaryDataResponses = new ArrayList<>();
			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
			
					LOG.info("Fetching device list by: " + processSlaDataRequest.getOwner());
					DevicesInfo devicesInfo = null;
					
					if(processSlaDataRequest.getHier().getName().equals(Constants.HIRE_NAME)) {
					    devicesInfo = devicesInfoService.getDevicesInfo(processSlaDataRequest.getHier().getValue());
					    if(devicesInfo == null) {
						response.setMessage("Device Not Found");
						response.setCode(404);
						return response;
					   }
					}
					String fieldName = ExternalConstants.getLevelValue(processSlaDataRequest.getHier().getName());
					String[] levels = processSlaDataRequest.getHier().getValue().split(",");

					StringBuilder queryBuilder = new StringBuilder();
					queryBuilder.append("select * from ").append(table).append(" where ").append(" lastcommunicationdatetime >= cast('")
							.append(processSlaDataRequest.getFromDate()).append("' as timestamp) ").append(" and lastcommunicationdatetime <= cast('")
							.append(processSlaDataRequest.getToDate()).append("' as timestamp) and ").append(fieldName).append(" in (");
					for (String level : levels) {
						queryBuilder.append("'").append(level).append("'").append(") ");
					}
					
					if(processSlaDataRequest.getDevType() != null && !processSlaDataRequest.getDevType().isEmpty()) {
						if (ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV.contains(processSlaDataRequest.getDevType())){							
							queryBuilder.append("and (device_type = '").append(Constants.DeviceTypes.SINGLE_PHASE).append("') ");
						}
						else if (ExternalConstants.DeviceTypes.THREE_PHASE_DEV.contains(processSlaDataRequest.getDevType())){							
							queryBuilder.append("and (device_type = '").append(Constants.DeviceTypes.THREE_PHASE).append("') ");
						}
						else if (ExternalConstants.DeviceTypes.CT_METER.contains(processSlaDataRequest.getDevType())){							
							queryBuilder.append("and (device_type = '").append(Constants.DeviceTypes.CT).append("') ");
						}
					}
					if(processSlaDataRequest.getManufacturer() != null && !processSlaDataRequest.getManufacturer().isEmpty()) {
						queryBuilder.append(" and lower(meter_type) = '").append(processSlaDataRequest.getManufacturer().trim().toLowerCase()).append("'");
					}
					if(processSlaDataRequest.getNetwork() != null && !processSlaDataRequest.getNetwork().isEmpty()) {
						queryBuilder.append(" and lower(network) = '").append(processSlaDataRequest.getNetwork().trim().toLowerCase()).append("'");
					}
					
					String query = queryBuilder.substring(0, queryBuilder.length());

					query = query.concat(" order by lastcommunicationdatetime desc");
					// Fetching DevicesInfo List From By Owner Name

					String devicesInfoList = CommonUtils.getMapper()
							.writeValueAsString(prestoJdbcTemplate.queryForList(query));
					List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
							new TypeReference<List<DevicesInfo>>() {
							});
					if (devicesList != null && devicesList.size() > 0) {
						  int communicationCount = 0;
			              int nonCommunicationCount = 0;
						
						for(DevicesInfo info : devicesList) {
							MeterDataRes devRes = new MeterDataRes();
							if (info.getLastcommunicationdatetime() != null && commonUtils.isGreaterFromMidNight(Constants.CommType.DayWise,info.getLastcommunicationdatetime())) {
								communicationCount++;
								 setDevicesInfo(info, devRes);
								 communicatingDev.add(devRes);
							}else {
								nonCommunicationCount++;
								setDevicesInfo(info, devRes);
								nonCommunicatingDev.add(devRes);
							}
						}
											
						CommunicationSummaryDataResponse summaryDataResponse = new CommunicationSummaryDataResponse();
		                summaryDataResponse.setCommunicating(String.valueOf(communicationCount));
		                summaryDataResponse.setNonCommunicating(String.valueOf(nonCommunicationCount));
		                summaryDataResponse.setCommunicatingAvg(String.valueOf(Math.round(CommonUtils.calculatePercentage(devicesList.size(), communicationCount)))+"%");
		                summaryDataResponse.setNonCommunicatingAvg(String.valueOf(Math.round(CommonUtils.calculatePercentage(devicesList.size(), nonCommunicationCount)))+"%");
		                summaryDataResponse.setHesTimeStamp(dateConverter.convertDateToHesString(new Date(System.currentTimeMillis())));
		                summaryDataResponse.setTotalDevice(String.valueOf(devicesList.size()));
		                summaryDataResponse.setHierName(processSlaDataRequest.getOwner());
		                summaryDataResponse.setHierValue(Constants.HierLevelName.ALL);
		                summaryDataResponse.setCommunicatingDevice(communicatingDev);
		                summaryDataResponse.setNonCommunicatingDevice(nonCommunicatingDev);
		                
		                communicationSummaryDataResponses.add(summaryDataResponse);
						
						LOG.info("Get Communication Summary Data Successfully.");

						response.setCode(200);
						response.setData(communicationSummaryDataResponses);
						response.setMessage(ExternalConstants.Message.ADDED);
					
			}
	    
	    } catch (Exception e) {
	        LOG.error("Issue in fetching data due to: " + e.getMessage(), e);
			response = ExceptionHandlerConfig.setErrorData(e);
	    }

	    return response;
	}

	@Override
	public CommonResponse addmeterCommissioningReport() {
		CommonResponse response = new CommonResponse();

		LOG.info("Generate Meter Commissioning Report Data ServiceImpl Called.");

		try {

			// Fetching Owner Name List From DB
			List<String> ownerList = devicesInfoService.getAllOwnersList();
			LOG.info("Getting all owner list name from owners");
			String table = MeterDBAppStarter.keyspace + "." + Tables.DEVICES_INFO;
			if (ownerList != null && ownerList.size() > 0) {
				for (String owner : ownerList) {
					LOG.info("Fetching device list by: " + owner);
					// Fetching DevicesInfo List From By Owner Name
					String query = "Select * from " + table + " Where owner_name = '" + owner + "'";

					String devicesInfoList = CommonUtils.getMapper()
							.writeValueAsString(prestoJdbcTemplate.queryForList(query));
					List<DevicesInfo> devicesList = CommonUtils.getMapper().readValue(devicesInfoList,
							new TypeReference<List<DevicesInfo>>() {
							});
					if (devicesList != null && devicesList.size() > 0) {
						MeterCommissioningLogs commissioningLogs = new MeterCommissioningLogs();
						commissioningReportCaster.prepareAddCommissioningReportData(devicesList, commissioningLogs);
						meterCommissioningLogsRepo.save(commissioningLogs);
						LOG.info("Generate Meter Commissioning Report Data Successfully Inserted in MeterCommissioningLogs Table.");
						// response.setData(commissioningLogs);
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
	public CommonResponse getmeterCommissioningReport(ProcessSlaDataRequest req) {
		CommonResponse response = new CommonResponse();

		LOG.info("Get Meter Commissioning Report Data ServiceImpl Called.");

		try {
			if (req != null) {

				List<MeterCommissioningReportResponse> commissioningReportResponses = new ArrayList<>();

				String table = MeterDBAppStarter.keyspace + "." + Tables.METER_COMMISSIONING_LOGS;
				
				StringBuilder processBuilder = new StringBuilder();

				processBuilder.append("SELECT * FROM ").append(table).append(" WHERE ");
				
				processBuilder.append("datetime >= CAST('").append(req.getFromDate()).append("' AS TIMESTAMP) ")
						.append("AND datetime <= CAST('").append(req.getToDate()).append("' AS TIMESTAMP)");
						
				String query = processBuilder.substring(0, processBuilder.length())
						+ " ORDER BY datetime DESC";

				String meterCommissioningList = CommonUtils.getMapper()
						.writeValueAsString(prestoJdbcTemplate.queryForList(query));
				List<MeterCommissioningLogs> meterCommissioningLogs = CommonUtils.getMapper()
						.readValue(meterCommissioningList, new TypeReference<List<MeterCommissioningLogs>>() {
						});

				if (meterCommissioningLogs != null && !meterCommissioningLogs.isEmpty()) {
					commissioningReportCaster.prepareGetCommissioningReportData(meterCommissioningLogs,
							commissioningReportResponses);
				} else {
					response.setMessage(Constants.NO_DATA_FOUND);
					response.setCode(404);
					return response;
				}

				response.setData(commissioningReportResponses);
				response.setCode(200);
				response.setMessage(ExternalConstants.Status.SUCCESS);
				response.setError(false);
				LOG.info("Get Meter Commissioning Report Data Successfully.");
			}
		} catch (Exception e) {
			LOG.error("Issue in fetching data due to: " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}

	public MeterDataRes setDevicesInfo(DevicesInfo ispData, MeterDataRes ispResponse) {
	
	ispResponse.setMeterNumber(ispData.getDevice_serial_number());
	ispResponse.setManufacturer(ispData.getMeter_type());
	ispResponse.setCommissioningStatus(ispData.getCommissioning_status());
	ispResponse.setDeviceType(ispData.getDevice_type());
	ispResponse.setDevMode(ispData.getDev_mode());
	ispResponse.setIpAddress(ispData.getIp_address_main());
	ispResponse.setPort(ispData.getIp_port_main());
	ispResponse.setConsumerName(ispData.getConsumer_name());
	return ispResponse;
	}

}