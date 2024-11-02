package com.global.meter.inventive.serviceImpl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.DevicesInfoLogs;
import com.global.meter.business.model.MdmPushLogs;
import com.global.meter.common.enums.ConfigCommands;
import com.global.meter.data.repository.DevicesInfoLogsRepository;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.NamePlateRepository;
import com.global.meter.inventive.controller.OnDemandGroupCommandsController;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.mdm.repository.MdmPushLogRepository;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DtTransDataRequest;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.FeederDataRequest;
import com.global.meter.inventive.models.GetDeviceListsRequest;
import com.global.meter.inventive.models.MeterDataRes;
import com.global.meter.inventive.models.MeterDataResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.inventive.models.MetersDataRequest;
import com.global.meter.inventive.models.SubdivisionsDataRequest;
import com.global.meter.inventive.models.SubstationsDataRequest;
import com.global.meter.inventive.service.DevicesService;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.request.model.Device;
import com.global.meter.service.DeviceCommandService;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.Constants;
import com.global.meter.utils.Constants.CreateBatch;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;
import com.global.meter.v3.inventive.models.SingleConnectionCommandReq;
import com.global.meter.v3.inventive.service.SingleConnectionPerformService;
import com.global.meter.v3.inventive.serviceImpl.SingleConnectionCommandLogsServiceImple;
import com.global.meter.v3.inventive.task.OnDemandSingleConnectionCommandTask;
import com.global.meter.v3.utils.HazelcastUtil;

@Service
public class DevicesServiceImpl implements DevicesService{

	private static final Logger LOG = LoggerFactory.getLogger(DevicesServiceImpl.class);
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;
	
	@Autowired
	DeviceCommandService deviceCommandService;
	
	@Autowired
	SingleConnectionPerformService singleConnectionPerformService;
	
	@Autowired
	OnDemandGroupCommandsController groupCommandsController;
	
	@Autowired
	MdmPushLogRepository mdmPushLogRepository;
	
	@Autowired
	NamePlateRepository namePlateRepository;
	
	@Autowired
	SingleConnectionCommandLogsServiceImple singleConnectionCommandLogsServiceImple;
	
	@Autowired
	DevicesInfoLogsRepository devicesInfoLogsRepository;
	
	@Autowired
	SubdivisionsServiceImpl subdivisionsService;
	
	@Autowired
	SubstationsServiceImpl substationsService;
	
	@Autowired
	FeedersServiceImpl feedersService;
	
	@Autowired
	DtTransServiceImpl dtTransService;
	
	@Autowired
	HazelcastUtil hazelcastUtil;
	
	@Override
	public CommonResponse addDevice(List<MetersDataRequest> metersData) {
		List<DevicesInfo> devicesInfos = new ArrayList<>();
		List<String> devicesList = new ArrayList<>();
		List<MdmPushLogs> pushLogList = new ArrayList<>();
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Devices Info Data to save in DB:--> ");
		
		try {
			for (MetersDataRequest meters : metersData) {
				DevicesInfo devInfo = new DevicesInfo();
			    devicesList.add(meters.getMeterNumber());
			    if(meters.getSource() == null || meters.getSource().isEmpty()) {
					error.setErrorMessage(meters.getMeterNumber() + ": Invalid source");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
			    if(!StringUtils.isEmpty(meters.getSimNo())) {
			    Optional<List<DevicesInfo>> devInfoBySimNo = devicesInfoRepository.findDeviceBySimNo(meters.getSimNo());
				if(!devInfoBySimNo.get().isEmpty()) {
					error.setErrorMessage(meters.getSimNo() + " : " +ExternalConstants.Message.SIM_NO_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				 }
			    }
				if(!StringUtils.isEmpty(meters.getMeterNumber())) {
					
					switch (meters.getModeOfComm()) {
					case Constants.ModeComm.RF:
						Optional<List<DevicesInfo>> devInfoByNicId = devicesInfoRepository.findDeviceByNic(meters.getMeterNICId());
						if(!devInfoByNicId.get().isEmpty()) {
							error.setErrorMessage(meters.getMeterNICId() + " : " +ExternalConstants.Message.NIC_EXISTS);
							response.setCode(400);
							response.setError(true);
							response.addErrorMessage(error);
							return response;
						}
						break;
					case Constants.ModeComm.CELLULAR:
						Optional<List<DevicesInfo>> devInfoByIp = devicesInfoRepository.findDeviceByIp(meters.getIpAddress());
						if(!devInfoByIp.get().isEmpty()) {
							error.setErrorMessage(meters.getIpAddress() + " : " +ExternalConstants.Message.IP_EXISTS);
							response.setCode(400);
							response.setError(true);
							response.addErrorMessage(error);
							return response;
						}
						break;
					default:
						error.setErrorMessage(meters.getModeOfComm() + " : " +ExternalConstants.Message.INVALID_MODE_COMM);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(meters.getMeterNumber());
					
					if(devicesInfo.isPresent()) {
						error.setErrorMessage(meters.getMeterNumber() + " : " +ExternalConstants.Message.DEV_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				//Add device info in mdm push log
				if(meters.getMdmId() != null && !meters.getMdmId().isEmpty()) {
				Optional<MdmPushLogs> pushLogInfo = mdmPushLogRepository.findById(meters.getMeterNumber());

				if (!pushLogInfo.isPresent()) {
					MdmPushLogs pushLogs = new MdmPushLogs();
					
					pushLogs.setMdm_id(meters.getMdmId());
					pushLogs.setDevice_serial_number(meters.getMeterNumber());
					pushLogs.setDevice_status(StringUtils.isEmpty(meters.getCommissioningStatus())
							? Constants.Status.INACTIVE : meters.getCommissioningStatus());
					pushLogs.setDevice_type(meters.getDeviceType());
					pushLogs.setManufacturer(meters.getManufacturer());
					pushLogs.setCreated_by(meters.getCreatedBy());
					pushLogs.setCreated_on(new Date(System.currentTimeMillis()));
					
					pushLogList.add(pushLogs);
				}
				}
				devInfo.setDevice_serial_number(meters.getMeterNumber());
				devInfo.setAddress(meters.getConsumerAddress());
				devInfo.setCommissioning_status(StringUtils.isEmpty(meters.getCommissioningStatus())
						? Constants.Status.INACTIVE : meters.getCommissioningStatus());
				devInfo.setConsumer_name(meters.getConsumerName());
				devInfo.setCreation_date(new Date(System.currentTimeMillis())); 
				devInfo.setCrn(meters.getCrn());
				devInfo.setCrn_new(meters.getCrnNew());
				devInfo.setSim_no(meters.getSimNo());
				devInfo.setDcu_serial_number(meters.getDcuSerialNumber());
				devInfo.setDescription(meters.getDescription());
				devInfo.setDev_mode(meters.getDevMode());
				devInfo.setDevice_type(meters.getDeviceType());
				devInfo.setDt_name(meters.getDtName());
				devInfo.setEmail_id(meters.getEmailId());
				devInfo.setFeeder_name(meters.getFeederName());
				devInfo.setIp_address_main(meters.getIpAddress());
				devInfo.setIp_port_main(String.valueOf(meters.getPort()));
				devInfo.setLatitude(meters.getLatitude());
				devInfo.setLoad_limits(meters.getSanctionedLoad());
				devInfo.setLongitude(meters.getLongitude());
				devInfo.setMeter_type(meters.getManufacturer());
				devInfo.setModification_date(new Date(System.currentTimeMillis())); 
				devInfo.setOwner_name(meters.getUtility());
				devInfo.setPhone_number(meters.getConsumerPhoneNumber());
				devInfo.setSanction_load(meters.getSanctionedLoad());
				devInfo.setStatus(Constants.Status.CONNECTED);
				devInfo.setSubdevision_name(meters.getSubdivisionName());
				devInfo.setSubstation_name(meters.getSubstationName());
				devInfo.setNetwork(meters.getNetwork());
				devInfo.setZone_name(meters.getZone());
				devInfo.setCircle_name(meters.getCircle());
				devInfo.setDivision_name(meters.getDivision());
				
				devInfo.setCreated_by(meters.getCreatedBy());
				devInfo.setCreated_on_datetime((meters.getCreatedOnDatetime() != null && !meters.getCreatedOnDatetime().isEmpty())
						? dateConverter.convertStringToDate(meters.getCreatedOnDatetime())
						: new Date(System.currentTimeMillis()));
				devInfo.setBatch_id(meters.getBatchId());
				devInfo.setSource(meters.getSource());
				devInfo.setCategory_level(meters.getCategoryLevel());
				devInfo.setMeter_nic_id(meters.getMeterNICId());
				devInfo.setMode_of_comm(meters.getModeOfComm());
				devInfo.setAuthkey(meters.getAuthKey());
				devInfo.setCipheringkey(meters.getCipheringKey());
				devInfo.setFirmwarepwd(meters.getFirmwarePwd());
				devInfo.setHighpwd(meters.getHighPwd());
				devInfo.setLowpwd(meters.getLowPwd());
			
				devicesInfos.add(devInfo);
				
			}
			devicesInfoRepository.saveAll(devicesInfos);
			if(pushLogList.size()>0) {
			mdmPushLogRepository.saveAll(pushLogList);
			}
			LOG.info("Devices Info Successfully Stored in DB.");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.DEV_ADDED);
		} catch (Exception e) {
			LOG.error("Issue in adding data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateDevice(List<MetersDataRequest> metersData) {
		DevicesInfo devInfo = null;
		List<DevicesInfo> devicesInfos = new ArrayList<>();
		List<String> devicesList = new ArrayList<>();
		List<MdmPushLogs> pushLogList = new ArrayList<>();
		List<String> obisCodeList = new ArrayList<>();
		List<DevicesInfoLogs> devicesInfoLogs = new ArrayList<>();
		
		String commandName = ConfigCommands.NAME_PLATES.commandName;
		obisCodeList.add(commandName);
		String balanceCommand = System.currentTimeMillis() + commandName;
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		boolean isAdded = false;
		String source = "";
		String userId = "";
		LOG.info("Wrapping Devices Info Data to update in DB:--> ");
		
		try {
			for (MetersDataRequest meters : metersData) {
				
				if(!StringUtils.isEmpty(meters.getMeterNumber())) {
					Object cacheData = hazelcastUtil.devicesCache().get(meters.getMeterNumber());
					if(cacheData != null && cacheData instanceof DevicesInfo) {
						devInfo = (DevicesInfo) cacheData;
					}
					else {
	                	
					Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(meters.getMeterNumber());
					
					if(!devicesInfo.isPresent()) {
						error.setErrorMessage(meters.getMeterNumber() + " : " +ExternalConstants.Message.DEV_NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
					devInfo = devicesInfo.get();
				 }
				}
				if(meters.getIpAddress() != null && !meters.getIpAddress().isEmpty() && !devInfo.getIp_address_main().equalsIgnoreCase(meters.getIpAddress()))
				{
					Optional<List<DevicesInfo>> devInfoByIp = devicesInfoRepository.findDeviceByIp(meters.getIpAddress());
					if(!devInfoByIp.get().isEmpty()) {
						error.setErrorMessage(meters.getIpAddress() + " : " +ExternalConstants.Message.IP_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				if(meters.getMeterNICId() != null && !meters.getMeterNICId().isEmpty() && !devInfo.getMeter_nic_id().equalsIgnoreCase(meters.getMeterNICId()))
				{
					Optional<List<DevicesInfo>> devInfoByNic = devicesInfoRepository.findDeviceByNic(meters.getMeterNICId());
					if(!devInfoByNic.get().isEmpty()) {
						error.setErrorMessage(meters.getIpAddress() + " : " +ExternalConstants.Message.NIC_EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				 if(meters.getSimNo() != null && !meters.getSimNo().isEmpty() && !devInfo.getSim_no().equalsIgnoreCase(meters.getSimNo())) {
					    Optional<List<DevicesInfo>> devInfoBySimNo = devicesInfoRepository.findDeviceBySimNo(meters.getSimNo());
						if(!devInfoBySimNo.get().isEmpty()) {
							error.setErrorMessage(meters.getSimNo() + " : " +ExternalConstants.Message.SIM_NO_EXISTS);
							response.setCode(400);
							response.setError(true);
							response.addErrorMessage(error);
							return response;
						 }
					    }
				if(devInfo.getStatus().equals(Constants.Status.BUSY)) {
					error.setErrorMessage(meters.getMeterNumber() + ": Device is busy");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				
				// Check Hier Values
				if (!validateHier(meters, response, error)) {
				    return response;
				}

				
				//check approval status while change commission status UP or Down
				if((Constants.Status.DOWN.equalsIgnoreCase(meters.getCommissioningStatus()) 
						|| Constants.Status.UP.equalsIgnoreCase(meters.getCommissioningStatus()))
						&& devInfo.getApproved_datetime() == null && devInfo.getApproved_by() == null) {
					
					error.setErrorMessage(meters.getMeterNumber() + ": "+ExternalConstants.Message.DEV_NOT_APPROVED);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				//update devices info logs
				DevicesInfoLogs devInfoLogs = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(devInfo), DevicesInfoLogs.class);
				devInfoLogs.setTracking_id(String.valueOf(System.nanoTime()));
				devInfoLogs.setAction_taken_on(new Date(System.currentTimeMillis()));
				devInfoLogs.setAction_taken_by(meters.getLastUpdatedBy() != null ? meters.getLastUpdatedBy() : devInfo.getLast_updated_by());
				devInfoLogs.setAction_taken(meters.getDescription() != null ? meters.getDescription() : devInfo.getDescription());
				
				source = meters.getSource() != null && meters.getSource() != "" ? meters.getSource() : devInfo.getSource();
				userId = meters.getLastUpdatedBy() != null && meters.getLastUpdatedBy() != "" ? meters.getLastUpdatedBy() : devInfo.getCreated_by();
				devInfo.setAddress(meters.getConsumerAddress() != null ? meters.getConsumerAddress() : devInfo.getAddress());
				devInfo.setConsumer_name(meters.getConsumerName() != null ? meters.getConsumerName() : devInfo.getConsumer_name());
				devInfo.setCreation_date(new Date(System.currentTimeMillis())); 
				devInfo.setSim_no(meters.getSimNo() != null ? meters.getSimNo() : devInfo.getSim_no());
				devInfo.setCrn(meters.getCrn() != null ? meters.getCrn() : devInfo.getCrn());
				devInfo.setCrn_new(meters.getCrnNew() != null ? meters.getCrnNew() : devInfo.getCrn_new());
				devInfo.setDcu_serial_number(meters.getDcuSerialNumber() != null ? meters.getDcuSerialNumber() : devInfo.getDcu_serial_number());
				devInfo.setDescription(meters.getDescription() != null ? meters.getDescription() : devInfo.getDescription());
				devInfo.setDev_mode(meters.getDevMode() != null ? meters.getDevMode() : devInfo.getDev_mode());
				devInfo.setDevice_type(meters.getDeviceType() != null ? meters.getDeviceType() : devInfo.getDevice_type());
				devInfo.setDt_name(meters.getDtName());
				devInfo.setFeeder_name(meters.getFeederName());
				devInfo.setIp_address_main(meters.getIpAddress() != null ? meters.getIpAddress() : devInfo.getIp_address_main());
				devInfo.setIp_port_main(String.valueOf(meters.getPort()) != null ? String.valueOf(meters.getPort()) : devInfo.getIp_port_main() );
				devInfo.setLatitude(meters.getLatitude() != null ? meters.getLatitude() : devInfo.getLatitude());
				devInfo.setLoad_limits(String.valueOf(meters.getSanctionedLoad()) != null ? meters.getSanctionedLoad() : devInfo.getSanction_load());
				devInfo.setLongitude(meters.getLongitude() != null ? meters.getLongitude() : devInfo.getLongitude());
				devInfo.setMeter_type(meters.getManufacturer() != null ? meters.getManufacturer() : devInfo.getMeter_type());
				devInfo.setModification_date(new Date(System.currentTimeMillis())); 
				devInfo.setOwner_name(meters.getUtility() != null ? meters.getUtility() : devInfo.getOwner_name());
				devInfo.setPhone_number(meters.getConsumerPhoneNumber() != null ? meters.getConsumerPhoneNumber() : devInfo.getPhone_number());
				devInfo.setSanction_load(String.valueOf(meters.getSanctionedLoad()) != null ? meters.getSanctionedLoad() : devInfo.getSanction_load());
				devInfo.setStatus(Constants.Status.CONNECTED);
				devInfo.setSubdevision_name(meters.getSubdivisionName());
				devInfo.setSubstation_name(meters.getSubstationName());
				devInfo.setNetwork(meters.getNetwork() != null ? meters.getNetwork() : devInfo.getNetwork());
			
				devInfo.setLast_updated_by(meters.getLastUpdatedBy() != null ? meters.getLastUpdatedBy() : devInfo.getLast_updated_by());
				devInfo.setLast_updated_on_datetime(new Date(System.currentTimeMillis()));
				devInfo.setCategory_level(meters.getCategoryLevel() != null ? meters.getCategoryLevel() : devInfo.getCategory_level());
				
				devInfo.setZone_name(meters.getZone());
				devInfo.setCircle_name(meters.getCircle());
				devInfo.setDivision_name(meters.getDivision());
				
				devInfo.setCommissioning_status(meters.getCommissioningStatus() != null ? 
						 meters.getCommissioningStatus() : devInfo.getCommissioning_status());
				devInfo.setMeter_nic_id(meters.getMeterNICId() != null ? 
						meters.getMeterNICId() : devInfo.getMeter_nic_id());
				devInfo.setMode_of_comm(meters.getModeOfComm() != null ? 
						meters.getModeOfComm() : devInfo.getMode_of_comm());
				devInfo.setAuthkey(meters.getAuthKey() != null ? meters.getAuthKey() : devInfo.getAuthkey());
				devInfo.setCipheringkey(meters.getCipheringKey() != null ? meters.getCipheringKey() : devInfo.getCipheringkey());
				devInfo.setFirmwarepwd(meters.getFirmwarePwd() != null ? meters.getFirmwarePwd() : devInfo.getFirmwarepwd());
				devInfo.setHighpwd(meters.getHighPwd() != null ? meters.getHighPwd() : devInfo.getHighpwd());
				devInfo.setLowpwd(meters.getLowPwd() != null ? meters.getLowPwd() : devInfo.getLowpwd());
				//Approval process
				if(meters.getApprovedBy() != null && !meters.getApprovedBy().isEmpty() 
						&& devInfo.getApproved_datetime() == null && devInfo.getApproved_by() == null) {
					
					if(devInfo.getInstallation_datetime() != null 
							&& Constants.Types.INSTALLED.equalsIgnoreCase(devInfo.getCommissioning_status())) {
						devInfo.setCommissioning_status(Constants.Status.DOWN);
						devInfo.setApproved_by(meters.getApprovedBy());
						devInfo.setApproved_datetime(new Date(System.currentTimeMillis()));
					}else {
						error.setErrorMessage(meters.getMeterNumber() + ": "+ExternalConstants.Message.DEV_NOT_INSTALLED);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
				} 
				
				//update mdm push logs
				
				Optional<MdmPushLogs> pushLogInfo = mdmPushLogRepository.findById(meters.getMeterNumber());
				MdmPushLogs pushLogs = new MdmPushLogs();
				if (pushLogInfo.isPresent()) {
					
					pushLogs = pushLogInfo.get();
					
					pushLogs.setMdm_id(meters.getMdmId() != null ? meters.getMdmId() : pushLogs.getMdm_id());
					pushLogs.setDevice_status(devInfo.getCommissioning_status());
					pushLogs.setDevice_type(meters.getDeviceType() != null ? meters.getDeviceType() : pushLogs.getDevice_type());
					pushLogs.setManufacturer(meters.getManufacturer() != null ? meters.getManufacturer() : pushLogs.getManufacturer());
					pushLogs.setUpdated_by(meters.getLastUpdatedBy() != null ? meters.getLastUpdatedBy() : pushLogs.getUpdated_by());
					pushLogs.setUpdated_on(new Date(System.currentTimeMillis()));
										
					pushLogList.add(pushLogs);
				  }else {
					  if(meters.getMdmId() != null && !meters.getMdmId().isEmpty()) {
						  
				          pushLogs.setDevice_serial_number(meters.getMeterNumber());
					      pushLogs.setMdm_id(meters.getMdmId() != null ? meters.getMdmId() : pushLogs.getMdm_id());
					      pushLogs.setDevice_status(devInfo.getCommissioning_status());
					      pushLogs.setDevice_type(meters.getDeviceType() != null ? meters.getDeviceType() : pushLogs.getDevice_type());
					      pushLogs.setManufacturer(meters.getManufacturer() != null ? meters.getManufacturer() : pushLogs.getManufacturer());
					      pushLogs.setCreated_by(meters.getLastUpdatedBy() != null ? meters.getLastUpdatedBy() : pushLogs.getUpdated_by());
					      pushLogs.setCreated_on(new Date(System.currentTimeMillis()));
											
						pushLogList.add(pushLogs);
					  }
				   } 
				//replace faulty meter with new meter
				if(meters.getNewMeterNo() != null && !meters.getNewMeterNo().isEmpty() 
						&& meters.getReplacedOn() != null && !meters.getReplacedOn().isEmpty()) {
					DevicesInfo newDevice = new DevicesInfo();
					if(!StringUtils.isEmpty(meters.getNewMeterNo())) {
						Optional<DevicesInfo> newDevInfo = devicesInfoRepository.findById(meters.getNewMeterNo());
						
						if(!newDevInfo.isPresent()) {
							error.setErrorMessage(meters.getMeterNumber() + " : " +ExternalConstants.Message.DEV_NOT_EXISTS);
							response.setCode(404);
							response.setError(true);
							response.addErrorMessage(error);
							return response;
						}
						
						newDevice = newDevInfo.get();
					}
					replaceDevice(devInfo,newDevice,devInfoLogs,meters);
					devInfo = CommonUtils.getMapper().readValue(
							CommonUtils.getMapper().writeValueAsString(newDevice), DevicesInfo.class);

				}
				
				devicesInfoLogs.add(devInfoLogs);
				devicesInfos.add(devInfo);
				// Save updated devInfo to cache
			    hazelcastUtil.devicesCache().put(devInfo.getDevice_serial_number(), devInfo);
				LOG.info("Data cache created successfully.");
				
				if(!devInfo.getCommissioning_status().equals(Constants.Status.INACTIVE) 
						&& !devInfo.getCommissioning_status().equals(Constants.Status.FAULTY)) {
					devicesList.add(devInfo.getDevice_serial_number());
				}
				
			}	
			
			devicesInfoRepository.saveAll(devicesInfos);
			mdmPushLogRepository.saveAll(pushLogList);
			devicesInfoLogsRepository.saveAll(devicesInfoLogs);
			isAdded = true;
			LOG.info("Devices Info Successfully Updated in DB.");
			if(isAdded && devicesList.size() > 0) {
				OnDemandSingleConnectionCommandTask onDemandSingleConnectionCommandTask = new OnDemandSingleConnectionCommandTask(singleConnectionPerformService, devicesList,
					    commandName, balanceCommand, null, obisCodeList, null, null, null, source, userId, null, null, null);
				
				onDemandSingleConnectionCommandTask.start();
				}
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.DEV_UPDATED);
		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			error.setErrorMessage(e.getMessage());
			response.setCode(500);
			response.setError(true);
			response.addErrorMessage(error);
		}
		return response;
	}

	@Override
	public CommonResponse deleteDevice(MetersDataRequest meters) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Wrapping Devices Info Data to delete from DB:--> ");
		
		try {
			DevicesInfo devInfo = new DevicesInfo();
			if(!StringUtils.isEmpty(meters.getMeterNumber())) {

				Object deviceCache = hazelcastUtil.devicesCache().get(meters.getMeterNumber());
				if (deviceCache != null && deviceCache instanceof DevicesInfo) {

					devInfo = (DevicesInfo) deviceCache;

				} else {

					Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(meters.getMeterNumber());

					if (!devicesInfo.isPresent()) {
						error.setErrorMessage(
								meters.getMeterNumber() + " : " + ExternalConstants.Message.DEV_NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					devInfo = devicesInfo.get();
				}
				if(devInfo.getStatus().equals(Constants.Status.BUSY)) {
					error.setErrorMessage(meters.getMeterNumber() + ": Device is busy");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				
				DevicesInfoLogs devLogs = CommonUtils.getMapper().readValue(
						CommonUtils.getMapper().writeValueAsString(devInfo), DevicesInfoLogs.class);
				
				devLogs.setRemarks(meters.getDescription() != null ? meters.getDescription() : devLogs.getRemarks());
				devLogs.setAction_taken_on(devLogs.getAction_taken_on() != null ? devLogs.getAction_taken_on() : new Date(System.currentTimeMillis()));
				devLogs.setAction_taken_by(meters.getActionTakenBy());
				devLogs.setDecommissioning_req_datetime(devInfo.getDecommissioning_req_datetime() != null ? devInfo.getDecommissioning_req_datetime() : new Date(System.currentTimeMillis()));
				devLogs.setDecommissioning_req_by(devInfo.getDecommissioning_req_by());
				devLogs.setTracking_id(String.valueOf(System.nanoTime()));
				
				if(meters.getIsApproved().equalsIgnoreCase(Constants.Status.APPROVED)) {
					
					devLogs.setAction_taken(Constants.Status.APPROVED);
					
				}else if (meters.getIsApproved().equalsIgnoreCase(Constants.Status.CANCELLED)) {
					
					devLogs.setAction_taken(Constants.Status.CANCELLED);
					devInfo.setDecommissioning_status(Constants.NO);
					devicesInfoRepository.save(devInfo);
					hazelcastUtil.devicesCache().put(meters.getMeterNumber(), devInfo);
					response.setMessage(ExternalConstants.Message.CANCELLED_SUCCESSFULLY);
				}

				devicesInfoLogsRepository.save(devLogs);
				LOG.info("Devices History Successfully Created."+ meters.getMeterNumber());
				
				if (meters.getIsApproved().equalsIgnoreCase(Constants.Status.APPROVED)) {

					hazelcastUtil.devicesCache().remove(meters.getMeterNumber());
					devicesInfoRepository.deleteById(meters.getMeterNumber());
					LOG.info("Devices Info Successfully Deleted from DB." + meters.getMeterNumber());
					response.setMessage(ExternalConstants.Message.DEV_DELETED);
				}
				response.setCode(200);
				
			}
			
		} catch (Exception e) {
			LOG.error("Issue in deleting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDevice(MetersDataRequest meters) {
	      CommonResponse response = new CommonResponse();
	      ErrorData error = new ErrorData();
	      MdmPushLogs mdmInfo = new MdmPushLogs();
	      MeterDataResponse meterDataResponse = null;
	      DevicesInfo devInfo = null;
	      LOG.info("Getting Devices Info Data to from DB:--> ");

	      try {
	         if (!StringUtils.isEmpty(meters.getMeterNumber())) {
	           
					Object cacheResData = hazelcastUtil.resDataCache().get(meters.getMeterNumber());

					if (cacheResData != null && cacheResData instanceof MeterDataResponse) {
						response.setData(cacheResData);
						return response;
					}
					Object cacheData = hazelcastUtil.devicesCache().get(meters.getMeterNumber());
					meterDataResponse = new MeterDataResponse();
					if (cacheData != null && cacheData instanceof DevicesInfo) {

						devInfo = (DevicesInfo) cacheData;
					} else {

						Optional<DevicesInfo> devicesInfo = this.devicesInfoRepository
								.findById(meters.getMeterNumber());
						if (!devicesInfo.isPresent()) {
							error.setErrorMessage(meters.getMeterNumber() + " : " + "Device Not Exists..");
							response.setCode(404);
							response.setError(true);
							response.addErrorMessage(error);
							return response;
						}

						devInfo = (DevicesInfo) devicesInfo.get();
						hazelcastUtil.devicesCache().put(meters.getMeterNumber(), devInfo);
					}
					Optional<MdmPushLogs> mdmPushLogInfo = this.mdmPushLogRepository.findById(meters.getMeterNumber());
					if (mdmPushLogInfo.isPresent()) {
						mdmInfo = (MdmPushLogs) mdmPushLogInfo.get();
					}
	           
				   if (devInfo != null) {
						meterDataResponse.setConsumerAddress(devInfo.getAddress() != null ? devInfo.getAddress() : meters.getReplaceBy());
						meterDataResponse.setCommissioningStatus(devInfo.getCommissioning_status() != null ? devInfo.getCommissioning_status() : meters.getReplaceBy());
						meterDataResponse.setConsumerName(devInfo.getConsumer_name() != null ? devInfo.getConsumer_name() : meters.getReplaceBy());
						meterDataResponse.setSimNo(devInfo.getSim_no() != null ? devInfo.getSim_no() : meters.getReplaceBy());
						meterDataResponse.setCrn(devInfo.getCrn() != null ? devInfo.getCrn() : meters.getReplaceBy());
						meterDataResponse.setCrnNew(devInfo.getCrn_new() != null ? devInfo.getCrn_new() : meters.getReplaceBy());
						meterDataResponse.setDcuSerialNumber(devInfo.getDcu_serial_number() != null ? devInfo.getDcu_serial_number() : meters.getReplaceBy());
						meterDataResponse.setDescription(devInfo.getDescription() != null ? devInfo.getDescription() : meters.getReplaceBy());
						meterDataResponse.setDevMode(devInfo.getDev_mode() != null ? devInfo.getDev_mode() : meters.getReplaceBy());
						meterDataResponse.setDeviceType(devInfo.getDevice_type() != null ? devInfo.getDevice_type() : meters.getReplaceBy());
						meterDataResponse.setDtName(devInfo.getDt_name() != null ? devInfo.getDt_name() : meters.getReplaceBy());
						meterDataResponse.setEmailId(devInfo.getEmail_id() != null ? devInfo.getEmail_id() : meters.getReplaceBy());
						meterDataResponse.setFeederName(devInfo.getFeeder_name() != null ? devInfo.getFeeder_name() : meters.getReplaceBy());
						meterDataResponse.setIpAddress(devInfo.getIp_address_main() != null ? devInfo.getIp_address_main() : meters.getReplaceBy());
						meterDataResponse.setPort(devInfo.getIp_port_main()!= null ? devInfo.getIp_port_main() : "4059");
						meterDataResponse.setLatitude(devInfo.getLatitude() != null ? devInfo.getLatitude() : meters.getReplaceBy());
						meterDataResponse.setLongitude(devInfo.getLongitude() != null ? devInfo.getLongitude() : meters.getReplaceBy());
						meterDataResponse.setSanctionedLoad(devInfo.getSanction_load() != null ? String.valueOf(devInfo.getSanction_load()) : meters.getReplaceBy());
						meterDataResponse.setManufacturer(devInfo.getMeter_type() != null ? devInfo.getMeter_type() : meters.getReplaceBy());
						meterDataResponse.setUtility(devInfo.getOwner_name() != null ? devInfo.getOwner_name() : meters.getReplaceBy());
						meterDataResponse.setConsumerPhoneNumber(devInfo.getPhone_number() != null ? devInfo.getPhone_number() : meters.getReplaceBy());
						meterDataResponse.setSubdivisionName(devInfo.getSubdevision_name() != null ? devInfo.getSubdevision_name() : meters.getReplaceBy());
						meterDataResponse.setSubstationName(devInfo.getSubstation_name() != null ? devInfo.getSubstation_name() : meters.getReplaceBy());
						meterDataResponse.setNetwork(devInfo.getNetwork() != null ? devInfo.getNetwork() : meters.getReplaceBy());
						
						meterDataResponse.setCreatedBy(devInfo.getCreated_by() != null ? devInfo.getCreated_by() : meters.getReplaceBy());
						meterDataResponse.setLastUpdatedBy(devInfo.getLast_updated_by() != null ? devInfo.getLast_updated_by() : meters.getReplaceBy());
						meterDataResponse.setCreatedOnDatetime(devInfo.getCreated_on_datetime() != null?
								dateConverter.convertDateToHesString(devInfo.getCreated_on_datetime()) : meters.getReplaceBy());
						meterDataResponse.setLastUpdatedOnDatetime(devInfo.getLast_updated_on_datetime() != null?
								dateConverter.convertDateToHesString(devInfo.getLast_updated_on_datetime()) : meters.getReplaceBy());
						meterDataResponse.setInstallationDatetime(devInfo.getInstallation_datetime() != null?
								dateConverter.convertDateToHesString(devInfo.getInstallation_datetime()) : meters.getReplaceBy());
						meterDataResponse.setSource(devInfo.getSource() != null ? devInfo.getSource() : meters.getReplaceBy());
						meterDataResponse.setUserId(devInfo.getCreated_by() != null ? devInfo.getCreated_by() : meters.getReplaceBy());
						meterDataResponse.setMeterNumber(devInfo.getDevice_serial_number() != null ? devInfo.getDevice_serial_number() : meters.getReplaceBy());;
						meterDataResponse.setCategoryLevel(devInfo.getCategory_level() != null ? devInfo.getCategory_level() : meters.getReplaceBy());
						meterDataResponse.setZone(devInfo.getZone_name() != null ? devInfo.getZone_name() : meters.getReplaceBy());
						meterDataResponse.setCircle(devInfo.getCircle_name() != null ? devInfo.getCircle_name() : meters.getReplaceBy());
						meterDataResponse.setDivision(devInfo.getDivision_name() != null ? devInfo.getDivision_name() : meters.getReplaceBy());
						meterDataResponse.setMeterNICId(devInfo.getMeter_nic_id() != null ? devInfo.getMeter_nic_id() : meters.getReplaceBy());
						meterDataResponse.setModeOfComm(devInfo.getMode_of_comm() != null ? devInfo.getMode_of_comm() : meters.getReplaceBy());
						meterDataResponse.setApprovedBy(devInfo.getApproved_by() != null ? devInfo.getApproved_by() : meters.getReplaceBy());
						meterDataResponse.setApprovedDatetime(devInfo.getApproved_datetime() != null ? dateConverter.convertDateToHesString(devInfo.getApproved_datetime()) : meters.getReplaceBy());
						meterDataResponse.setMdmId(mdmInfo.getMdm_id() != null ? mdmInfo.getMdm_id() : meters.getReplaceBy());
						meterDataResponse.setAuthKey(devInfo.getAuthkey() != null ? devInfo.getAuthkey() : meters.getReplaceBy());
						meterDataResponse.setCipheringKey(devInfo.getCipheringkey() != null ? devInfo.getCipheringkey() : meters.getReplaceBy());
						meterDataResponse.setHighPwd(devInfo.getHighpwd() != null ? devInfo.getHighpwd() : meters.getReplaceBy());
						meterDataResponse.setLowPwd(devInfo.getLowpwd() != null ? devInfo.getLowpwd() : meters.getReplaceBy());
						meterDataResponse.setFirmwarePwd(devInfo.getFirmwarepwd() != null ? devInfo.getFirmwarepwd() : meters.getReplaceBy());
	                    hazelcastUtil.resDataCache().put(meters.getMeterNumber(), meterDataResponse);
	            }

	            response.setData(meterDataResponse);
	            LOG.info("Devices info data send successfully.");
	         }
	      } catch (Exception e) {
				
	    	  LOG.error("Issue in getting data due to : " + e.getMessage());
				response = ExceptionHandlerConfig.setErrorData(e);
			}

	      return response;
	   }

	@Override
	public CommonResponse getDeviceList(MeterDataVisualizationReq inputReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices List from DB:--> ");
		
		try {

			String fieldName = ExternalConstants.getLevelValue(inputReq.getHier().getName());
			String[] levels = inputReq.getHier().getValue().split(",");
            String devType = "";
            if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
            if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(inputReq.getDevType())) {				
				devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(inputReq.getDevType())) {	
				devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.CT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.CT_METER;		
			}
			else if (ExternalConstants.DeviceTypes.LT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.LT_METER;		
			}
			else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
            }
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO)
						.append(" where ");
			if(inputReq.getModeOfComm() != null && !StringUtils.isEmpty(inputReq.getModeOfComm())) {
				queryBuilder.append("lower(mode_of_comm) = '").append(inputReq.getModeOfComm().trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getCommissioningStatus() != null && !StringUtils.isEmpty(inputReq.getCommissioningStatus())) {
				queryBuilder.append("lower(commissioning_status) = '").append(inputReq.getCommissioningStatus().trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
				queryBuilder.append("lower(device_type) = '").append(devType.trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getMeteringMode() != null && !StringUtils.isEmpty(inputReq.getMeteringMode())) {
				queryBuilder.append("lower(dev_mode) = '").append(inputReq.getMeteringMode().trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getManufacturer() != null && !StringUtils.isEmpty(inputReq.getManufacturer())) {

				String[] manufacturers = inputReq.getManufacturer().split(",");
			    queryBuilder.append("lower(meter_type) in (");
			for (String manufacturer : manufacturers) {
				queryBuilder.append("'").append(manufacturer.trim().toLowerCase()).append("',");
			    }
			    queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ") and ");
			}
			    queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by installation_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfo> devicesInfo = new ArrayList<DevicesInfo>();
			devicesInfo = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfo>>() {});
			
			List<MeterDataResponse> devicesList = new ArrayList<>();
			
			for (DevicesInfo devInfo : devicesInfo) {
				MeterDataResponse meterDataResponse = new MeterDataResponse();
				MdmPushLogs mdmInfo = new MdmPushLogs();
				//Get mdm id from mdm_push_log
				Optional<MdmPushLogs> mdmPushLogInfo = mdmPushLogRepository.findById(devInfo.getDevice_serial_number());
				
				if(mdmPushLogInfo.isPresent()) {
					mdmInfo = mdmPushLogInfo.get();
				}
				
				meterDataResponse.setMeterNumber(devInfo.getDevice_serial_number() != null ? devInfo.getDevice_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerAddress(devInfo.getAddress() != null ? devInfo.getAddress() : inputReq.getReplaceBy());
				meterDataResponse.setCommissioningStatus(devInfo.getCommissioning_status() != null ? devInfo.getCommissioning_status() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerName(devInfo.getConsumer_name() != null ? devInfo.getConsumer_name() : inputReq.getReplaceBy());
				meterDataResponse.setSimNo(devInfo.getSim_no() != null ? devInfo.getSim_no() : inputReq.getReplaceBy());
				meterDataResponse.setCrn(devInfo.getCrn() != null ? devInfo.getCrn() : inputReq.getReplaceBy());
				meterDataResponse.setCrnNew(devInfo.getCrn_new() != null ? devInfo.getCrn_new() : inputReq.getReplaceBy());
				meterDataResponse.setDcuSerialNumber(devInfo.getDcu_serial_number() != null ? devInfo.getDcu_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setDescription(devInfo.getDescription() != null ? devInfo.getDescription() : inputReq.getReplaceBy());
				meterDataResponse.setDevMode(devInfo.getDev_mode() != null ? devInfo.getDev_mode() : inputReq.getReplaceBy());
				meterDataResponse.setDeviceType(devInfo.getDevice_type() != null ? devInfo.getDevice_type() : inputReq.getReplaceBy());
				meterDataResponse.setDtName(devInfo.getDt_name() != null ? devInfo.getDt_name() : inputReq.getReplaceBy());
				meterDataResponse.setFeederName(devInfo.getFeeder_name() != null ? devInfo.getFeeder_name() : inputReq.getReplaceBy());
				meterDataResponse.setIpAddress(devInfo.getIp_address_main() != null ? devInfo.getIp_address_main() : inputReq.getReplaceBy());
				meterDataResponse.setPort(devInfo.getIp_port_main() !=null ? devInfo.getIp_port_main() : "4059");
				meterDataResponse.setLatitude(devInfo.getLatitude() != null ? devInfo.getLatitude() : inputReq.getReplaceBy());
				meterDataResponse.setLongitude(devInfo.getLongitude() != null ? devInfo.getLongitude() : inputReq.getReplaceBy());
				meterDataResponse.setSanctionedLoad(devInfo.getSanction_load() != null ? String.valueOf(devInfo.getSanction_load()) : inputReq.getReplaceBy());
				meterDataResponse.setManufacturer(devInfo.getMeter_type() != null ? devInfo.getMeter_type() : inputReq.getReplaceBy());
				meterDataResponse.setUtility(devInfo.getOwner_name() != null ? devInfo.getOwner_name() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerPhoneNumber(devInfo.getPhone_number() != null ? devInfo.getPhone_number() : inputReq.getReplaceBy());
				meterDataResponse.setSubdivisionName(devInfo.getSubdevision_name() != null ? devInfo.getSubdevision_name() : inputReq.getReplaceBy());
				meterDataResponse.setSubstationName(devInfo.getSubstation_name() != null ? devInfo.getSubstation_name() : inputReq.getReplaceBy());
				meterDataResponse.setNetwork(devInfo.getNetwork() != null ? devInfo.getNetwork() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedBy(devInfo.getCreated_by() != null ? devInfo.getCreated_by() : inputReq.getReplaceBy());
				meterDataResponse.setLastUpdatedBy(devInfo.getLast_updated_by() != null ? devInfo.getLast_updated_by() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedOnDatetime(devInfo.getCreated_on_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getCreated_on_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setLastUpdatedOnDatetime(devInfo.getLast_updated_on_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getLast_updated_on_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setInstallationDatetime(devInfo.getInstallation_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getInstallation_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setSource(devInfo.getSource() != null ? devInfo.getSource() : inputReq.getReplaceBy());
				meterDataResponse.setZone(devInfo.getZone_name() != null ? devInfo.getZone_name() : inputReq.getReplaceBy());
				meterDataResponse.setCircle(devInfo.getCircle_name() != null ? devInfo.getCircle_name() : inputReq.getReplaceBy());
				meterDataResponse.setDivision(devInfo.getDivision_name() != null ? devInfo.getDivision_name() : inputReq.getReplaceBy());
				meterDataResponse.setMeterNICId(devInfo.getMeter_nic_id() != null ? devInfo.getMeter_nic_id() : inputReq.getReplaceBy());
				meterDataResponse.setModeOfComm(devInfo.getMode_of_comm() != null ? devInfo.getMode_of_comm() : inputReq.getReplaceBy());
				meterDataResponse.setAuthKey(devInfo.getAuthkey() != null ? devInfo.getAuthkey() : inputReq.getReplaceBy());
				meterDataResponse.setCipheringKey(devInfo.getCipheringkey() != null ? devInfo.getCipheringkey() : inputReq.getReplaceBy());
				meterDataResponse.setHighPwd(devInfo.getHighpwd() != null ? devInfo.getHighpwd() : inputReq.getReplaceBy());
				meterDataResponse.setLowPwd(devInfo.getLowpwd() != null ? devInfo.getLowpwd() : inputReq.getReplaceBy());
				meterDataResponse.setFirmwarePwd(devInfo.getFirmwarepwd() != null ? devInfo.getFirmwarepwd() : inputReq.getReplaceBy());
				meterDataResponse.setMdmId(mdmInfo.getMdm_id() != null ? mdmInfo.getMdm_id() : inputReq.getReplaceBy());
				meterDataResponse.setCategoryLevel(devInfo.getCategory_level() != null ? devInfo.getCategory_level() : inputReq.getReplaceBy());
				meterDataResponse.setApprovedBy(devInfo.getApproved_by() != null ? devInfo.getApproved_by() : inputReq.getReplaceBy());
				meterDataResponse.setApprovedDatetime(devInfo.getApproved_datetime() != null ? dateConverter.convertDateToHesString(devInfo.getApproved_datetime()) : inputReq.getReplaceBy());

				devicesList.add(meterDataResponse);
			}
			
			response.setData(devicesList);
			LOG.info("Devices Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDeviceLists(GetDeviceListsRequest inputReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices List from DB:--> ");
		
		try {
			String fieldName = ExternalConstants.getLevelValue(inputReq.getHier().getName());
			String[] levels = inputReq.getHier().getValue().split(",");
			String table = meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO;
			
			StringBuilder queryBuilder = new StringBuilder();
			
			
			queryBuilder.append("select ROUND(cast(latitude as double),")
						.append(inputReq.getRange()).append(") as latitude, ROUND(cast(longitude as double),")
						.append(inputReq.getRange()).append(") as longitude,");
			if("Y".equalsIgnoreCase(inputReq.getShowCount())) {
				queryBuilder.append("count(*) as device_serial_number");
			}else {
				queryBuilder.append("device_serial_number");
			}
			queryBuilder.append(" from  ")
						.append(table)
						.append(" where latitude IS NOT Null AND latitude != '' AND cast(latitude as double) BETWEEN ")
						.append(inputReq.getStartLatitude()).append(" AND ").append(inputReq.getEndLatitude())
						.append(" AND longitude IS NOT NULL AND longitude != '' AND cast(longitude as double) BETWEEN ")
						.append(inputReq.getStartLongitude()).append(" AND ").append(inputReq.getEndLongitude())
						.append(" AND ").append(fieldName).append(" in (");
			
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			queryBuilder=queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), "")
			
			.append(") group by ROUND(cast(latitude as double),")
			.append(inputReq.getRange()).append("), ROUND(cast(longitude as double),")
			.append(inputReq.getRange()).append(")");
			
			if("N".equalsIgnoreCase(inputReq.getShowCount())) {			
				queryBuilder.append(", device_serial_number");
			}
			
			String query = queryBuilder.substring(0, queryBuilder.length());
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfo> devicesInfo = new ArrayList<DevicesInfo>();
			devicesInfo = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfo>>() {});
			
		    List<GetDeviceListsRequest> devInfo = new ArrayList<GetDeviceListsRequest>();
			
			for (DevicesInfo devInfo1 : devicesInfo) {
				GetDeviceListsRequest device = new GetDeviceListsRequest();
				
				device.setLatitude(devInfo1.getLatitude());
				device.setLongitude(devInfo1.getLongitude());
				if ("Y".equalsIgnoreCase(inputReq.getShowCount())) {
					device.setDeviceCount(devInfo1.getDevice_serial_number());
				}else {
					device.setMeterNumber(devInfo1.getDevice_serial_number());
				}
				
				devInfo.add(device);
			}
			
			response.setData(devInfo);
			LOG.info("Devices Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getDeletedDeviceList(MeterDataVisualizationReq inputReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices List from DB:--> ");
		
		try {

			String fieldName = ExternalConstants.getLevelValue(inputReq.getHier().getName());
			String[] levels = inputReq.getHier().getValue().split(",");
            String devType = "";
            if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
            if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(inputReq.getDevType())) {				
				devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(inputReq.getDevType())) {	
				devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.CT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.CT_METER;		
			}
			else if (ExternalConstants.DeviceTypes.LT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.LT_METER;		
			}
			else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
            }
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO_LOGS)
						.append(" where ").append(" action_taken_on >= cast('")
						.append(inputReq.getFromDate()).append("' as timestamp) ").append(" and action_taken_on <= cast('")
						.append(inputReq.getToDate()).append("' as timestamp) and action_taken in ('").append(Constants.Status.APPROVED)
						.append("','").append(Constants.Status.CANCELLED).append("') and ");
			if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
				queryBuilder.append("lower(device_type) = '").append(devType.trim().toLowerCase()).append("' and ");
			}
			    queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by action_taken_on desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfoLogs> devicesHistory = new ArrayList<DevicesInfoLogs>();
			devicesHistory = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfoLogs>>() {});
			
			List<MeterDataResponse> devicesList = new ArrayList<>();
			
			for (DevicesInfoLogs devInfo : devicesHistory) {
				MeterDataResponse meterDataResponse = new MeterDataResponse();
				meterDataResponse.setMeterNumber(devInfo.getDevice_serial_number() != null ? devInfo.getDevice_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerAddress(devInfo.getAddress() != null ? devInfo.getAddress() : inputReq.getReplaceBy());
				meterDataResponse.setCommissioningStatus(devInfo.getCommissioning_status() != null ? devInfo.getCommissioning_status() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerName(devInfo.getConsumer_name() != null ? devInfo.getConsumer_name() : inputReq.getReplaceBy());
				meterDataResponse.setSimNo(devInfo.getSim_no() != null ? devInfo.getSim_no() : inputReq.getReplaceBy());
				meterDataResponse.setCrn(devInfo.getCrn() != null ? devInfo.getCrn() : inputReq.getReplaceBy());
				meterDataResponse.setCrnNew(devInfo.getCrn_new() != null ? devInfo.getCrn_new() : inputReq.getReplaceBy());
				meterDataResponse.setDcuSerialNumber(devInfo.getDcu_serial_number() != null ? devInfo.getDcu_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setDescription(devInfo.getDescription() != null ? devInfo.getDescription() : inputReq.getReplaceBy());
				meterDataResponse.setDevMode(devInfo.getDev_mode() != null ? devInfo.getDev_mode() : inputReq.getReplaceBy());
				meterDataResponse.setDeviceType(devInfo.getDevice_type() != null ? devInfo.getDevice_type() : inputReq.getReplaceBy());
				meterDataResponse.setDtName(devInfo.getDt_name() != null ? devInfo.getDt_name() : inputReq.getReplaceBy());
				meterDataResponse.setFeederName(devInfo.getFeeder_name() != null ? devInfo.getFeeder_name() : inputReq.getReplaceBy());
				meterDataResponse.setIpAddress(devInfo.getIp_address_main() != null ? devInfo.getIp_address_main() : inputReq.getReplaceBy());
				meterDataResponse.setPort(devInfo.getIp_port_main() !=null ? devInfo.getIp_port_main() : "4059");
				meterDataResponse.setLatitude(devInfo.getLatitude() != null ? devInfo.getLatitude() : inputReq.getReplaceBy());
				meterDataResponse.setLongitude(devInfo.getLongitude() != null ? devInfo.getLongitude() : inputReq.getReplaceBy());
				meterDataResponse.setSanctionedLoad(devInfo.getSanction_load() != null ? String.valueOf(devInfo.getSanction_load()) : inputReq.getReplaceBy());
				meterDataResponse.setManufacturer(devInfo.getMeter_type() != null ? devInfo.getMeter_type() : inputReq.getReplaceBy());
				meterDataResponse.setUtility(devInfo.getOwner_name() != null ? devInfo.getOwner_name() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerPhoneNumber(devInfo.getPhone_number() != null ? devInfo.getPhone_number() : inputReq.getReplaceBy());
				meterDataResponse.setSubdivisionName(devInfo.getSubdevision_name() != null ? devInfo.getSubdevision_name() : inputReq.getReplaceBy());
				meterDataResponse.setSubstationName(devInfo.getSubstation_name() != null ? devInfo.getSubstation_name() : inputReq.getReplaceBy());
				meterDataResponse.setNetwork(devInfo.getNetwork() != null ? devInfo.getNetwork() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedBy(devInfo.getCreated_by() != null ? devInfo.getCreated_by() : inputReq.getReplaceBy());
				meterDataResponse.setLastUpdatedBy(devInfo.getLast_updated_by() != null ? devInfo.getLast_updated_by() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedOnDatetime(devInfo.getCreated_on_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getCreated_on_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setLastUpdatedOnDatetime(devInfo.getLast_updated_on_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getLast_updated_on_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setInstallationDatetime(devInfo.getInstallation_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getInstallation_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setSource(devInfo.getSource() != null ? devInfo.getSource() : inputReq.getReplaceBy());
				meterDataResponse.setUserId(devInfo.getCreated_by() != null ? devInfo.getCreated_by() : inputReq.getReplaceBy());
				meterDataResponse.setCategoryLevel(devInfo.getCategory_level() != null ? devInfo.getCategory_level() : inputReq.getReplaceBy());
				meterDataResponse.setZone(devInfo.getZone_name() != null ? devInfo.getZone_name() : inputReq.getReplaceBy());
				meterDataResponse.setCircle(devInfo.getCircle_name() != null ? devInfo.getCircle_name() : inputReq.getReplaceBy());
				meterDataResponse.setDivision(devInfo.getDivision_name() != null ? devInfo.getDivision_name() : inputReq.getReplaceBy());
				meterDataResponse.setMeterNICId(devInfo.getMeter_nic_id() != null ? devInfo.getMeter_nic_id() : inputReq.getReplaceBy());
				meterDataResponse.setModeOfComm(devInfo.getMode_of_comm() != null ? devInfo.getMode_of_comm() : inputReq.getReplaceBy());
				meterDataResponse.setActionTaken(devInfo.getAction_taken() != null ? devInfo.getAction_taken() : inputReq.getReplaceBy());
				meterDataResponse.setActionTakenBy(devInfo.getAction_taken_by() != null ? devInfo.getAction_taken_by() : inputReq.getReplaceBy());
				meterDataResponse.setActionTakenOn(devInfo.getAction_taken_on() != null ? dateConverter.convertDateToHesString(devInfo.getAction_taken_on()) : inputReq.getReplaceBy());
				meterDataResponse.setDecommissioningReqBy(devInfo.getDecommissioning_req_by() != null ? devInfo.getDecommissioning_req_by() : inputReq.getReplaceBy());
				meterDataResponse.setDecommissioningReqDatetime(devInfo.getDecommissioning_req_datetime() != null ? 
						dateConverter.convertDateToHesString(devInfo.getDecommissioning_req_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setAuthKey(devInfo.getAuthkey() != null ? devInfo.getAuthkey() : inputReq.getReplaceBy());
				meterDataResponse.setCipheringKey(devInfo.getCipheringkey() != null ? devInfo.getCipheringkey() : inputReq.getReplaceBy());
				meterDataResponse.setHighPwd(devInfo.getHighpwd() != null ? devInfo.getHighpwd() : inputReq.getReplaceBy());
				meterDataResponse.setLowPwd(devInfo.getLowpwd() != null ? devInfo.getLowpwd() : inputReq.getReplaceBy());
				meterDataResponse.setFirmwarePwd(devInfo.getFirmwarepwd() != null ? devInfo.getFirmwarepwd() : inputReq.getReplaceBy());
				meterDataResponse.setDecommissioningReason(devInfo.getDecommissioning_reason() != null ? devInfo.getDecommissioning_reason() : inputReq.getReplaceBy());
				meterDataResponse.setRemarks(devInfo.getRemarks() != null ? devInfo.getRemarks() : inputReq.getReplaceBy());

				devicesList.add(meterDataResponse);
			}
			
			response.setData(devicesList);
			LOG.info("Devices Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse decommissioningApproval(MetersDataRequest inputReq) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		DevicesInfo devInfo = null;
		LOG.info("Wrapping Devices Info Data to request the decommissioned to device:--> ");
		
		try {
				
				if(!StringUtils.isEmpty(inputReq.getMeterNumber())) {
					Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(inputReq.getMeterNumber());
					
					if(!devicesInfo.isPresent()) {
						error.setErrorMessage(inputReq.getMeterNumber() + " : " +ExternalConstants.Message.DEV_NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					
					devInfo = devicesInfo.get();
				}
				if(devInfo.getDecommissioning_status() != null 
						&& !devInfo.getDecommissioning_status().isEmpty() 
						&& devInfo.getDecommissioning_status().equalsIgnoreCase(Constants.YES)) {
					
					error.setErrorMessage("Already requested for decommission to "+ devInfo.getDevice_serial_number());
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				
				}else {

					devInfo.setDecommissioning_status(Constants.YES);	
					devInfo.setDecommissioning_reason(inputReq.getDescription());	
					devInfo.setDecommissioning_req_by(inputReq.getUserId());	
					devInfo.setDecommissioning_req_datetime(new Date(System.currentTimeMillis()));
			
				}

				devicesInfoRepository.save(devInfo);
				
			LOG.info("Devices Approval request send Successfully!!!");
			response.setCode(200);
			response.setMessage(ExternalConstants.Message.DECOMMISSIONED_REQUEST);
		} catch (Exception e) {
			LOG.error("Issue in approve the device due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getDecommissionedRequestDeviceList(MeterDataVisualizationReq inputReq) {
		
		CommonResponse response = new CommonResponse();
		List<MeterDataRes> meterResponseList = new ArrayList<>();
		MdmPushLogs mdmInfo = new MdmPushLogs();
		LOG.info("Getting Devices Info Data to from DB:--> ");
		
		try {
			String fieldName = ExternalConstants.getLevelValue(inputReq.getHier().getName());
			String[] levels = inputReq.getHier().getValue().split(",");
            String devType = "";
            if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
            if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(inputReq.getDevType())) {				
				devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(inputReq.getDevType())) {	
				devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.CT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.CT_METER;		
			}
			else if (ExternalConstants.DeviceTypes.LT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.LT_METER;		
			}
			else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
            }
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO)
						.append(" where decommissioning_status = '").append(Constants.YES).append("' and ");
			if(inputReq.getCommissioningStatus() != null && !StringUtils.isEmpty(inputReq.getCommissioningStatus())) {
				queryBuilder.append("lower(commissioning_status) = '").append(inputReq.getCommissioningStatus().trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
				queryBuilder.append("lower(device_type) = '").append(devType.trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getMeteringMode() != null && !StringUtils.isEmpty(inputReq.getMeteringMode())) {
				queryBuilder.append("lower(dev_mode) = '").append(inputReq.getMeteringMode().trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getManufacturer() != null && !StringUtils.isEmpty(inputReq.getManufacturer())) {

				String[] manufacturers = inputReq.getManufacturer().split(",");
			    queryBuilder.append("lower(meter_type) in (");
			for (String manufacturer : manufacturers) {
				queryBuilder.append("'").append(manufacturer.trim().toLowerCase()).append("',");
			    }
			    queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ") and ");
			}
			    queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by installation_datetime desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfo> devicesInfo = new ArrayList<DevicesInfo>();
			devicesInfo = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfo>>() {});
			
				for(DevicesInfo isData : devicesInfo) {
					//Get mdm id from mdm_push_log
					Optional<MdmPushLogs> mdmPushLogInfo = mdmPushLogRepository.findById(isData.getDevice_serial_number());
					
					if(mdmPushLogInfo.isPresent()) {
						mdmInfo = mdmPushLogInfo.get();
					}

					MeterDataRes meterRes = new MeterDataRes();
				
				meterRes.setMeterNumber(isData.getDevice_serial_number() != null ? isData.getDevice_serial_number() : inputReq.getReplaceBy());
				meterRes.setConsumerName(isData.getConsumer_name() != null ? isData.getConsumer_name() : inputReq.getReplaceBy());
				meterRes.setCommissioningStatus(isData.getCommissioning_status() != null ? isData.getCommissioning_status() : inputReq.getReplaceBy());
				meterRes.setStatus(isData.getStatus() != null ? isData.getStatus() : inputReq.getReplaceBy());
				meterRes.setDevMode(isData.getDev_mode() != null ? isData.getDev_mode() : inputReq.getReplaceBy());
				meterRes.setDeviceType(isData.getDevice_type() != null ? isData.getDevice_type() : inputReq.getReplaceBy());
				meterRes.setIpAddress(isData.getIp_address_main() != null ? isData.getIp_address_main() : inputReq.getReplaceBy());
				meterRes.setPort(isData.getIp_port_main() != null ? isData.getIp_port_main() : inputReq.getReplaceBy());
				meterRes.setManufacturer(isData.getMeter_type() != null ? isData.getMeter_type() :inputReq.getReplaceBy());
				meterRes.setDecommissioningReason(isData.getDecommissioning_reason() != null ? isData.getDecommissioning_reason() :  inputReq.getReplaceBy());
				meterRes.setDecommissioningReqBy(isData.getDecommissioning_req_by() != null ? isData.getDecommissioning_req_by(): inputReq.getReplaceBy());
				meterRes.setDecommissioningReqDatetime(isData.getDecommissioning_req_datetime() != null ? dateConverter.convertDateToHesString(isData.getDecommissioning_req_datetime()) : inputReq.getReplaceBy());
				meterRes.setModeOfComm(isData.getMode_of_comm() != null ? isData.getMode_of_comm() : inputReq.getReplaceBy());
				meterRes.setMeterNICId(isData.getMeter_nic_id() != null ? isData.getMeter_nic_id() : inputReq.getReplaceBy());
				meterRes.setSubdivisionName(isData.getSubdevision_name() != null ? isData.getSubdevision_name() : inputReq.getReplaceBy());
				meterRes.setSubstationName(isData.getSubstation_name() != null ? isData.getSubstation_name() : inputReq.getReplaceBy());
				meterRes.setFeederName(isData.getFeeder_name() != null ? isData.getFeeder_name() : inputReq.getReplaceBy());
				meterRes.setDtName(isData.getDt_name() != null ? isData.getDt_name() : inputReq.getReplaceBy());
				meterRes.setConsumerAddress(isData.getAddress() != null ? isData.getAddress() : inputReq.getReplaceBy());
				meterRes.setSimNo(isData.getSim_no() != null ? isData.getSim_no() : inputReq.getReplaceBy());
				meterRes.setCrn(isData.getCrn() != null ? isData.getCrn() : inputReq.getReplaceBy());
				meterRes.setCrnNew(isData.getCrn_new() != null ? isData.getCrn_new() : inputReq.getReplaceBy());
				meterRes.setDescription(isData.getDescription() != null ? isData.getDescription() : inputReq.getReplaceBy());
				meterRes.setLatitude(isData.getLatitude() != null ? isData.getLatitude() : inputReq.getReplaceBy());
				meterRes.setLongitude(isData.getLongitude() != null ? isData.getLongitude() : inputReq.getReplaceBy());
				meterRes.setNetwork(isData.getNetwork() != null ? isData.getNetwork() : inputReq.getReplaceBy());
				meterRes.setConsumerPhoneNumber(isData.getPhone_number() != null ? isData.getPhone_number() : inputReq.getReplaceBy());
				meterRes.setSanctionedLoad(isData.getSanction_load() != null ? String.valueOf(isData.getSanction_load()) : inputReq.getReplaceBy());
				meterRes.setCreatedBy(isData.getCreated_by() != null ? String.valueOf(isData.getCreated_by()) : inputReq.getReplaceBy());
				meterRes.setCreatedOnDatetime(isData.getCreated_on_datetime() != null ? String.valueOf(isData.getCreated_on_datetime()) : inputReq.getReplaceBy());
				meterRes.setLastUpdatedOnDatetime(isData.getLast_updated_on_datetime() != null ? String.valueOf(isData.getLast_updated_on_datetime()) : inputReq.getReplaceBy());
				meterRes.setLastUpdatedBy(isData.getLast_updated_by() != null ? String.valueOf(isData.getLast_updated_by()) : inputReq.getReplaceBy());
				meterRes.setInstallationDatetime(isData.getInstallation_datetime() != null ? String.valueOf(isData.getInstallation_datetime()) : inputReq.getReplaceBy());
				meterRes.setUtility(isData.getOwner_name() != null ? String.valueOf(isData.getOwner_name()) : inputReq.getReplaceBy());
				meterRes.setDcuSerialNumber(isData.getDcu_serial_number() != null ? String.valueOf(isData.getDcu_serial_number()) : inputReq.getReplaceBy());
				meterRes.setSource(isData.getSource() != null ? String.valueOf(isData.getSource()) : inputReq.getReplaceBy());
				meterRes.setMdmId(mdmInfo.getMdm_id() != null ? String.valueOf(mdmInfo.getMdm_id()) : inputReq.getReplaceBy());
				meterRes.setZone(isData.getZone_name() != null ? String.valueOf(isData.getZone_name()) : inputReq.getReplaceBy());
				meterRes.setCircle(isData.getCircle_name() != null ? String.valueOf(isData.getCircle_name()) : inputReq.getReplaceBy());
				meterRes.setDivision(isData.getDivision_name() != null ? String.valueOf(isData.getDivision_name()) : inputReq.getReplaceBy());
				meterRes.setCategoryLevel(isData.getCategory_level() != null ? String.valueOf(isData.getCategory_level()) : inputReq.getReplaceBy());
				meterRes.setAuthkey(isData.getAuthkey() != null ? String.valueOf(isData.getAuthkey()) : inputReq.getReplaceBy());
				meterRes.setCipheringKey(isData.getCipheringkey() != null ? String.valueOf(isData.getCipheringkey()) : inputReq.getReplaceBy());
				meterRes.setHighPwd(isData.getHighpwd() != null ? String.valueOf(isData.getHighpwd()) : inputReq.getReplaceBy());
				meterRes.setLowPwd(isData.getLowpwd() != null ? String.valueOf(isData.getLowpwd()) : inputReq.getReplaceBy());
				meterRes.setFirmwarePwd(isData.getFirmwarepwd() != null ? String.valueOf(isData.getFirmwarepwd()) : inputReq.getReplaceBy());
				
				
				meterResponseList.add(meterRes);
				}
				response.setData(meterResponseList);
			LOG.info("Devices Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse pingDevice(MetersDataRequest req) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		DevicesInfo devInfo = new DevicesInfo();
		String batchId = CommonUtils.createBatchId();
		String extBatchId = CreateBatch.EXT_BATCH + String.valueOf(System.nanoTime());

		try {
			if(req.getInstallationDatetime() == null || req.getInstallationDatetime().isEmpty()) {
				error.setErrorMessage("Invalid Installation date time.");
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if(!StringUtils.isEmpty(req.getMeterNumber())) {
				Optional<DevicesInfo> devicesInfo = devicesInfoRepository.findById(req.getMeterNumber());
				
				if(!devicesInfo.isPresent()) {
					error.setErrorMessage(req.getMeterNumber() + " : " +ExternalConstants.Message.DEV_NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				devInfo = devicesInfo.get();  
			}
			switch (devInfo.getCommissioning_status()) {
			case Constants.Status.FAULTY:
				error.setErrorMessage("Device Status is : "+devInfo.getCommissioning_status());
				response.setCode(404);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			case Constants.Status.UP:
		    case Constants.Status.DOWN:
		    case Constants.Types.INSTALLED:
		        if (devInfo.getInstallation_datetime() != null) {
		            response.setMessage(Constants.SUCCESS);
		            response.setCode(200);
		            response.setError(false);
		            return response;
		        }

		    default:
		    	
				List<String> obisCodeList = new ArrayList<>();
		        List<String> devicesList = new ArrayList<>();
		        devicesList.add(req.getMeterNumber());
		        String commandName = ConfigCommands.NAME_PLATES.commandName;
		        obisCodeList.add(commandName);
		        SingleConnectionCommandReq pingReq = new SingleConnectionCommandReq();
		        Device device = new Device();

		        device.setPlainText(req.getMeterNumber());
		        pingReq.setDevice(device);
		        pingReq.setCommand(commandName);
		        pingReq.setObisCodeList(obisCodeList);
		        pingReq.setInstallation_datetime(req.getInstallationDatetime());
		        pingReq.setSource(req.getSource());
		        pingReq.setUserId(req.getUserId());
		        pingReq.setBatchId(batchId);
		        pingReq.setExtBatchId(extBatchId);

		        // Create a new thread for ping
		        Thread thread = new Thread(() -> {
		            singleConnectionCommandLogsServiceImple.addSingleConnectionCommandLogs(pingReq);
		        });

		        // Start the thread
		        thread.start();
		}
			response.setCode(200);
			response.setMessage(Constants.IN_PROGRESS);
			response.setBatchId(batchId);
			response.setExtBatchId(extBatchId);
		} catch (Exception e) {
			LOG.error("Issue in ping device due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
	
	@Override
	public CommonResponse getDeviceLogs(MeterDataVisualizationReq inputReq) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Getting Devices info log List from DB:--> ");
		
		try {
			List<DevicesInfoLogs> devicesInfoLogs = new ArrayList<DevicesInfoLogs>();
			if(inputReq.getTrackingId() != null && !inputReq.getTrackingId().isEmpty()) {
                Optional<DevicesInfoLogs> devicesInfolog = devicesInfoLogsRepository.findDeviceByTrackingId(inputReq.getTrackingId());
				
				if(!devicesInfolog.isPresent()) {
					error.setErrorMessage(inputReq.getTrackingId() + " : " +ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				devicesInfoLogs.add(devicesInfolog.get());
			}
			else {
			String fieldName = ExternalConstants.getLevelValue(inputReq.getHier().getName());
			String[] levels = inputReq.getHier().getValue().split(",");
            String devType = "";
            if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
            if(ExternalConstants.DeviceTypes.SINGLE_PHASE.contains(inputReq.getDevType())) {				
				devType = ExternalConstants.DeviceTypes.SINGLE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.THREE_PHASE.contains(inputReq.getDevType())) {	
				devType = ExternalConstants.DeviceTypes.THREE_PHASE_DEV;
			}
			else if (ExternalConstants.DeviceTypes.CT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.CT_METER;		
			}
			else if (ExternalConstants.DeviceTypes.LT_METER.contains(inputReq.getDevType())) {
				devType = ExternalConstants.DeviceTypes.LT_METER;		
			}
			else {
	        	   response.setMessage(Constants.WRONG_DEVICE_TYPE);
				   response.setCode(404);
				   response.setError(true);
				   return response;
			   }
            }
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO_LOGS)
						.append(" where ")
			.append(" action_taken_on >= cast('")
			.append(inputReq.getFromDate()).append("' as timestamp) ").append(" and action_taken_on <= cast('")
			.append(inputReq.getToDate()).append("' as timestamp) and ");
			if(inputReq.getDevType() != null && !StringUtils.isEmpty(inputReq.getDevType())) {
				queryBuilder.append("lower(device_type) = '").append(devType.trim().toLowerCase()).append("' and ");
			}
			if(inputReq.getManufacturer() != null && !StringUtils.isEmpty(inputReq.getManufacturer())) {

				String[] manufacturers = inputReq.getManufacturer().split(",");
			    queryBuilder.append("lower(meter_type) in (");
			for (String manufacturer : manufacturers) {
				queryBuilder.append("'").append(manufacturer.trim().toLowerCase()).append("',");
			    }
			    queryBuilder.replace(queryBuilder.length()-1, queryBuilder.length(), ") and ");
			}
			    queryBuilder.append(fieldName).append(" in (");
			for (String level : levels) {
				queryBuilder.append("'").append(level).append("',");
			}
			String query = queryBuilder.substring(0, queryBuilder.length() - 1);
			query = query.concat(") order by action_taken_on desc");

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			devicesInfoLogs = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfoLogs>>() {});
			}
			List<MeterDataResponse> devicesList = new ArrayList<>();
			
			for (DevicesInfoLogs devInfo : devicesInfoLogs) {
				MeterDataResponse meterDataResponse = new MeterDataResponse();
				
				meterDataResponse.setMeterNumber(devInfo.getDevice_serial_number() != null ? devInfo.getDevice_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerAddress(devInfo.getAddress() != null ? devInfo.getAddress() : inputReq.getReplaceBy());
				meterDataResponse.setCommissioningStatus(devInfo.getCommissioning_status() != null ? devInfo.getCommissioning_status() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerName(devInfo.getConsumer_name() != null ? devInfo.getConsumer_name() : inputReq.getReplaceBy());
				meterDataResponse.setSimNo(devInfo.getSim_no() != null ? devInfo.getSim_no() : inputReq.getReplaceBy());
				meterDataResponse.setCrn(devInfo.getCrn() != null ? devInfo.getCrn() : inputReq.getReplaceBy());
				meterDataResponse.setCrnNew(devInfo.getCrn_new() != null ? devInfo.getCrn_new() : inputReq.getReplaceBy());
				meterDataResponse.setDcuSerialNumber(devInfo.getDcu_serial_number() != null ? devInfo.getDcu_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setDescription(devInfo.getDescription() != null ? devInfo.getDescription() : inputReq.getReplaceBy());
				meterDataResponse.setDevMode(devInfo.getDev_mode() != null ? devInfo.getDev_mode() : inputReq.getReplaceBy());
				meterDataResponse.setDeviceType(devInfo.getDevice_type() != null ? devInfo.getDevice_type() : inputReq.getReplaceBy());
				meterDataResponse.setDtName(devInfo.getDt_name() != null ? devInfo.getDt_name() : inputReq.getReplaceBy());
				meterDataResponse.setFeederName(devInfo.getFeeder_name() != null ? devInfo.getFeeder_name() : inputReq.getReplaceBy());
				meterDataResponse.setIpAddress(devInfo.getIp_address_main() != null ? devInfo.getIp_address_main() : inputReq.getReplaceBy());
				meterDataResponse.setPort(devInfo.getIp_port_main() !=null ? devInfo.getIp_port_main() : "4059");
				meterDataResponse.setLatitude(devInfo.getLatitude() != null ? devInfo.getLatitude() : inputReq.getReplaceBy());
				meterDataResponse.setLongitude(devInfo.getLongitude() != null ? devInfo.getLongitude() : inputReq.getReplaceBy());
				meterDataResponse.setSanctionedLoad(devInfo.getSanction_load() != null ? String.valueOf(devInfo.getSanction_load()) : inputReq.getReplaceBy());
				meterDataResponse.setManufacturer(devInfo.getMeter_type() != null ? devInfo.getMeter_type() : inputReq.getReplaceBy());
				meterDataResponse.setUtility(devInfo.getOwner_name() != null ? devInfo.getOwner_name() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerPhoneNumber(devInfo.getPhone_number() != null ? devInfo.getPhone_number() : inputReq.getReplaceBy());
				meterDataResponse.setSubdivisionName(devInfo.getSubdevision_name() != null ? devInfo.getSubdevision_name() : inputReq.getReplaceBy());
				meterDataResponse.setSubstationName(devInfo.getSubstation_name() != null ? devInfo.getSubstation_name() : inputReq.getReplaceBy());
				meterDataResponse.setNetwork(devInfo.getNetwork() != null ? devInfo.getNetwork() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedBy(devInfo.getCreated_by() != null ? devInfo.getCreated_by() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedOnDatetime(devInfo.getCreated_on_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getCreated_on_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setInstallationDatetime(devInfo.getInstallation_datetime() != null?
						dateConverter.convertDateToHesString(devInfo.getInstallation_datetime()) : inputReq.getReplaceBy());
				meterDataResponse.setSource(devInfo.getSource() != null ? devInfo.getSource() : inputReq.getReplaceBy());
				meterDataResponse.setActionTakenOn(dateConverter.convertDateToHesString(devInfo.getAction_taken_on()));
				meterDataResponse.setActionTakenBy(devInfo.getAction_taken_by() != null ? devInfo.getAction_taken_by() : inputReq.getReplaceBy());
				meterDataResponse.setActionTaken(devInfo.getAction_taken() != null ? devInfo.getAction_taken() : inputReq.getReplaceBy());
				meterDataResponse.setMeterNICId(devInfo.getMeter_nic_id() != null ? devInfo.getMeter_nic_id() : inputReq.getReplaceBy());
				meterDataResponse.setModeOfComm(devInfo.getMode_of_comm() != null ? devInfo.getMode_of_comm() : inputReq.getReplaceBy());
				meterDataResponse.setZone(devInfo.getZone_name() != null ? devInfo.getZone_name() : inputReq.getReplaceBy());
				meterDataResponse.setCircle(devInfo.getCircle_name() != null ? devInfo.getCircle_name() : inputReq.getReplaceBy());
				meterDataResponse.setDivision(devInfo.getDivision_name() != null ? devInfo.getDivision_name() : inputReq.getReplaceBy());
				meterDataResponse.setAuthKey(devInfo.getAuthkey() != null ? devInfo.getAuthkey() : inputReq.getReplaceBy());
				meterDataResponse.setCipheringKey(devInfo.getCipheringkey() != null ? devInfo.getCipheringkey() : inputReq.getReplaceBy());
				meterDataResponse.setHighPwd(devInfo.getHighpwd() != null ? devInfo.getHighpwd() : inputReq.getReplaceBy());
				meterDataResponse.setLowPwd(devInfo.getLowpwd() != null ? devInfo.getLowpwd() : inputReq.getReplaceBy());
				meterDataResponse.setFirmwarePwd(devInfo.getFirmwarepwd() != null ? devInfo.getFirmwarepwd() : inputReq.getReplaceBy());
				meterDataResponse.setRemarks(devInfo.getRemarks() != null ? devInfo.getRemarks() : inputReq.getReplaceBy());

				devicesList.add(meterDataResponse);
			}
			
			response.setData(devicesList);
			LOG.info("Devices info logs Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device info logs list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	private boolean validateHier(MetersDataRequest meters, CommonResponse response, ErrorData error) {

		if (meters.getCategoryLevel() == null || meters.getCategoryLevel().isEmpty()) {
			createErrorResponse(Constants.CATEGORY_LEVEL + " is " + Constants.MISSING, response, error);
			return false;
		}
		
		String category = meters.getCategoryLevel();
		String subdivision = meters.getSubdivisionName();
		String substation = meters.getSubstationName();
		String feeder = meters.getFeederName();
		String dt = meters.getDtName();

		
		if (category.equalsIgnoreCase(Constants.HierLevelName.SUBSTATION)) {
			if (!validateAndCheck(subdivision, Constants.BatchHierLevelName.SUBDIVISION, meters, response, error)
				|| !validateAndCheck(substation, Constants.BatchHierLevelName.SUBSTATION, meters, response, error)) {
	            return false;
	        }
			meters.setFeederName(null);
	        meters.setDtName(null);
		} else if (category.equalsIgnoreCase(Constants.HierLevelName.FEEDER)) {
			if (!validateAndCheck(subdivision, Constants.BatchHierLevelName.SUBDIVISION, meters, response, error)
					|| !validateAndCheck(substation, Constants.BatchHierLevelName.SUBSTATION, meters, response, error)
					|| !validateAndCheck(feeder, Constants.BatchHierLevelName.FEEDER, meters, response, error)) {
		            return false;
		        }
	        meters.setDtName(null);
		} else {
			if (!validateAndCheck(subdivision, Constants.BatchHierLevelName.SUBDIVISION, meters, response, error) ||
		            !validateAndCheck(substation, Constants.BatchHierLevelName.SUBSTATION, meters, response, error) ||
		            !validateAndCheck(feeder, Constants.BatchHierLevelName.FEEDER, meters, response, error) ||
		            !validateAndCheck(dt, Constants.BatchHierLevelName.DTMETER, meters, response, error)) {
		            return false;
		     }
		}
		return true;
	}

	private CommonResponse SubDivisionCheck(MetersDataRequest meters) {
		List<SubdivisionsDataRequest> subDivList = new ArrayList<>();
		SubdivisionsDataRequest subDiv = new SubdivisionsDataRequest();
		subDiv.setSubdivisionName(meters.getSubdivisionName());
		subDiv.setLatitude(meters.getLatitude());
		subDiv.setLongitude(meters.getLongitude());
		subDiv.setUserId(meters.getUserId());
		subDiv.setUtility(meters.getUtility());
		subDiv.setSource(meters.getSource());
		subDivList.add(subDiv);
		return subdivisionsService.addSubdivision(subDivList);
	}

	private CommonResponse SubStationCheck(MetersDataRequest meters) {
		List<SubstationsDataRequest> subStationList = new ArrayList<>();
		SubstationsDataRequest subStation = new SubstationsDataRequest();
		subStation.setSubdivisionName(meters.getSubdivisionName());
		subStation.setSubstationName(meters.getSubstationName());
		subStation.setLatitude(meters.getLatitude());
		subStation.setLongitude(meters.getLongitude());
		subStation.setUserId(meters.getUserId());
		subStation.setSource(meters.getSource());
		subStation.setUtility(meters.getUtility());
		subStationList.add(subStation);
		return substationsService.addSubstation(subStationList);
	}

	private CommonResponse FeederCheck(MetersDataRequest meters) {
		List<FeederDataRequest> feederList = new ArrayList<>();
		FeederDataRequest feeder = new FeederDataRequest();
		feeder.setSubdivisionName(meters.getSubdivisionName());
		feeder.setSubstationName(meters.getSubstationName());
		feeder.setFeederName(meters.getFeederName());
		feeder.setLatitude(meters.getLatitude());
		feeder.setLongitude(meters.getLongitude());
		feeder.setUserId(meters.getUserId());
		feeder.setSource(meters.getSource());
		feeder.setUtility(meters.getUtility());
		feederList.add(feeder);
		return feedersService.addFeeders(feederList);
	}

	private CommonResponse DTCheck(MetersDataRequest meters) {
		List<DtTransDataRequest> dtList = new ArrayList<>();
		DtTransDataRequest dt = new DtTransDataRequest();
		dt.setSubdivisionName(meters.getSubdivisionName());
		dt.setSubstationName(meters.getSubstationName());
		dt.setFeederName(meters.getFeederName());
		dt.setDtName(meters.getDtName());
		dt.setLatitude(meters.getLatitude());
		dt.setLongitude(meters.getLongitude());
		dt.setUserId(meters.getUserId());
		dt.setSource(meters.getSource());
		dt.setUtility(meters.getUtility());
		dtList.add(dt);
		return dtTransService.addDtTrans(dtList);
	}

	private CommonResponse createErrorResponse(String errorMessage, CommonResponse response, ErrorData error) {
		error.setErrorMessage(errorMessage);
		response.setCode(400);
		response.setError(true);
		response.addErrorMessage(error);
		return response;
	}
	
	private boolean validateAndCheck(String value, String errorMessagePrefix, MetersDataRequest meters, CommonResponse response, ErrorData error) {
	    if (value == "") {
	        createErrorResponse(errorMessagePrefix + " is " + Constants.MISSING, response, error);
	        return false;
	    } else {
	        switch (errorMessagePrefix) {
	            case Constants.BatchHierLevelName.SUBDIVISION:
	                return SubDivisionCheck(meters).isError();
	            case Constants.BatchHierLevelName.SUBSTATION:
	            	return SubStationCheck(meters).isError();
	            case Constants.BatchHierLevelName.FEEDER:
	            	return FeederCheck(meters).isError();
	            case Constants.BatchHierLevelName.DTMETER:
	            	return DTCheck(meters).isError();
	            default:
	            	return false;
	        }
	    }
	}
	
	private void replaceDevice(DevicesInfo devInfo, DevicesInfo newDevice, DevicesInfoLogs devicesInfoLogs, MetersDataRequest meters) throws JsonMappingException, JsonProcessingException, ParseException {

		devicesInfoLogs.setAction_taken(Constants.Status.REPLACED);
		devicesInfoLogs.setCreated_on_datetime(new Date(System.currentTimeMillis()));
		devicesInfoLogs.setReplace_by_device_serial_number(meters.getNewMeterNo());
		devicesInfoLogs.setDescription(Constants.Status.FAULTY_METER_REPLACE);
		devicesInfoLogs.setAction_taken_on(dateConverter.convertStringToDate(meters.getReplacedOn()));
		devicesInfoLogs.setAction_taken_by(meters.getActionTakenBy());
		
		newDevice.setAddress(devInfo.getAddress());
		newDevice.setCrn_new(devInfo.getCrn_new());
		newDevice.setPhone_number(devInfo.getPhone_number());
		newDevice.setConsumer_name(devInfo.getConsumer_name());
		newDevice.setCommissioning_status(Constants.Status.INACTIVE);
		
		devicesInfoRepository.deleteById(devInfo.getDevice_serial_number());
		}

	@Override
	public CommonResponse getReplaceDeviceReport(MeterDataVisualizationReq inputReq) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Devices List from DB:--> ");
		
		try {

			String fieldName = ExternalConstants.getLevelValue(inputReq.getHier().getName());
			String[] levels = inputReq.getHier().getValue().split(",");
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." +Tables.DEVICES_INFO_LOGS).append(" where ");
			 queryBuilder.append(fieldName).append(" in (");
				for (String level : levels) {
					queryBuilder.append("'").append(level).append("')");
				}
			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DevicesInfoLogs> devicesInfo = new ArrayList<DevicesInfoLogs>();
			devicesInfo = CommonUtils.getMapper().readValue(outputList,
					new TypeReference<List<DevicesInfoLogs>>() {});
			
			List<MeterDataResponse> devicesList = new ArrayList<>();
			
			for (DevicesInfoLogs devInfo : devicesInfo) {
				MeterDataResponse meterDataResponse = new MeterDataResponse();
				
				meterDataResponse.setMeterNumber(devInfo.getDevice_serial_number() != null ? devInfo.getDevice_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setDeviceType(devInfo.getDevice_type() != null ? devInfo.getDevice_type() : inputReq.getReplaceBy());
				meterDataResponse.setManufacturer(devInfo.getMeter_type() != null ? devInfo.getMeter_type() : inputReq.getReplaceBy());
				meterDataResponse.setIpAddress(devInfo.getIp_address_main() != null ? devInfo.getIp_address_main() : inputReq.getReplaceBy());
				meterDataResponse.setReplacedMeterNo(devInfo.getReplace_by_device_serial_number() != null ? devInfo.getReplace_by_device_serial_number() : inputReq.getReplaceBy());
				meterDataResponse.setReplacedOn(devInfo.getAction_taken_on() != null ? dateConverter.convertDateToHesString(devInfo.getAction_taken_on()) : inputReq.getReplaceBy());
				meterDataResponse.setRemarks(devInfo.getDescription() != null ? devInfo.getDescription() : inputReq.getReplaceBy());
				meterDataResponse.setCrn(devInfo.getCrn() != null ? devInfo.getCrn() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerName(devInfo.getConsumer_name() != null ? devInfo.getConsumer_name() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerPhoneNumber(devInfo.getPhone_number() != null ? devInfo.getPhone_number() : inputReq.getReplaceBy());
				meterDataResponse.setConsumerAddress(devInfo.getAddress() != null ? devInfo.getAddress() : inputReq.getReplaceBy());
				meterDataResponse.setCreatedOnDatetime(devInfo.getCreated_on_datetime() != null ? dateConverter.convertDateToHesString(devInfo.getCreated_on_datetime()) : inputReq.getReplaceBy());

				devicesList.add(meterDataResponse);
			}
			
			response.setData(devicesList);
			LOG.info("Devices Service Data List Response Success.");
		
		} catch (Exception e) {
			LOG.error("Issue in getting device list due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}
}
