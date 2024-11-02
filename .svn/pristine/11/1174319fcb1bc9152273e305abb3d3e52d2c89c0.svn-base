package com.global.meter.inventive.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DcuMasterInfo;
import com.global.meter.business.model.DcuMasterLogs;
import com.global.meter.business.model.DcuMasterResponse;
import com.global.meter.business.model.DcuPowerData;
import com.global.meter.data.repository.DcuMasterLogsRepository;
import com.global.meter.data.repository.DcuMasterRepository;
import com.global.meter.data.repository.DcuPowerRepository;
import com.global.meter.inventive.exception.ExceptionHandlerConfig;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.DcuMasterReq;
import com.global.meter.inventive.models.DcuPowerReq;
import com.global.meter.inventive.models.DcuPushResponse;
import com.global.meter.inventive.models.ErrorData;
import com.global.meter.inventive.models.Payload;
import com.global.meter.inventive.service.DcuMasterService;
import com.global.meter.inventive.utils.DcuPowerDataCaster;
import com.global.meter.inventive.utils.ExternalConstants;
import com.global.meter.inventive.utils.Tables;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;
import com.global.meter.utils.MetersCommandsConfiguration;

@Service
public class DcuServiceImpl implements DcuMasterService {

	private static final Logger LOG = LoggerFactory.getLogger(DcuMasterService.class);

	@Autowired
	DcuMasterRepository masterRepository;
	
	@Autowired
	DcuPowerRepository powerRepository;

	@Autowired
	DcuMasterLogsRepository masterLogsRepository;

	@Autowired
	DateConverter dateConverter;

	@Autowired
	MetersCommandsConfiguration meterConfiguration;
	
	@Autowired
	DcuPowerDataCaster dcuDataCaster;

	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate;

	@Override
	public CommonResponse addDcuMaster(List<DcuMasterReq> req) {
		List<DcuMasterInfo> dculist = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("Dcu Master Info Data to save in DB:--> ");

		try {

			for (DcuMasterReq request : req) {
				DcuMasterInfo dcuMaster = new DcuMasterInfo();
				if (request.getUserId() == null || request.getUserId().isEmpty()) {
					error.setErrorMessage("UserId can not be empty");
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				if (!StringUtils.isEmpty(request.getDcuSerialNo())) {
					Optional<DcuMasterInfo> masterInfo = masterRepository.findById(request.getDcuSerialNo());

					if (masterInfo.isPresent()) {
						error.setErrorMessage(
								request.getDcuSerialNo() + " : " + ExternalConstants.Message.EXISTS);
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
				}
				dcuMaster.setDcu_serial_number(request.getDcuSerialNo());
				dcuMaster.setDcu_mac_nic_id(request.getDcuMacNicId());
				dcuMaster.setFrequency(Double.parseDouble(request.getFrequency()));
				dcuMaster.setManufacturer(request.getManufacturer());
				dcuMaster.setIpv6_address(request.getIpv6Address());
				dcuMaster.setSim_no(request.getSimNo());
				dcuMaster.setNetwork(request.getNetwork());
				dcuMaster.setLatitude(request.getLatitude());
				dcuMaster.setLongitude(request.getLongitude());
				dcuMaster.setRemarks(request.getRemarks());
				dcuMaster.setOwner(request.getOwner());
				dcuMaster.setCreated_datetime(new Date(System.currentTimeMillis()));
				dcuMaster.setCreated_by(request.getUserId());

				dculist.add(dcuMaster);
			}
			
			
			if (dculist.size() > 0) {
				masterRepository.saveAll(dculist);
				LOG.info("Dcu Master Info Data Successfully Stored in DB.");

				response.setCode(200);
				response.setMessage(ExternalConstants.Message.ADDED);
			}else {
				LOG.info(ExternalConstants.Message.EMPTY_REQUEST_RECEIVED);
				response.setCode(200);
				response.setMessage(ExternalConstants.Message.EMPTY_REQUEST_RECEIVED);
			}
			
		} catch (Exception e) {
			LOG.error("Issue in adding data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse updateDcuMaster(List<DcuMasterReq> req) {

		List<DcuMasterInfo> dcuMasterInfos = new ArrayList<>();
		List<DcuMasterLogs> dcuMasterLogs = new ArrayList<>();

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();

		LOG.info("Dcu Master Info Data to update in DB:--> ");

		try {
			for (DcuMasterReq request : req) {

				DcuMasterInfo dcuMasterInfo = new DcuMasterInfo();
				if (!StringUtils.isEmpty(request.getDcuSerialNo())) {
					if (request.getUserId() == null || request.getUserId().isEmpty()) {
						error.setErrorMessage("UserId can not be empty");
						response.setCode(400);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}

					Optional<DcuMasterInfo> masterInfo = masterRepository.findById(request.getDcuSerialNo());

					if (!masterInfo.isPresent()) {
						error.setErrorMessage(request.getDcuSerialNo() + " : " + ExternalConstants.Message.NOT_EXISTS);
						response.setCode(404);
						response.setError(true);
						response.addErrorMessage(error);
						return response;
					}
					dcuMasterInfo = masterInfo.get();
				}

				dcuMasterInfo.setDcu_serial_number(request.getDcuSerialNo() != null ? request.getDcuSerialNo()
						: dcuMasterInfo.getDcu_serial_number());
				dcuMasterInfo.setDcu_mac_nic_id(
						request.getDcuMacNicId() != null ? request.getDcuMacNicId() : dcuMasterInfo.getDcu_mac_nic_id());
				dcuMasterInfo.setIpv6_address(
						request.getIpv6Address() != null ? request.getIpv6Address() : dcuMasterInfo.getIpv6_address());
				dcuMasterInfo
						.setLatitude(request.getLatitude() != null ? request.getLatitude() : dcuMasterInfo.getLatitude());
				dcuMasterInfo.setLongitude(
						request.getLongitude() != null ? request.getLongitude() : dcuMasterInfo.getLongitude());
				dcuMasterInfo.setManufacturer(
						request.getManufacturer() != null ? request.getManufacturer() : dcuMasterInfo.getManufacturer());
				dcuMasterInfo.setNetwork(request.getNetwork() != null ? request.getNetwork() : dcuMasterInfo.getNetwork());
				dcuMasterInfo.setOwner(request.getOwner() != null ? request.getOwner() : dcuMasterInfo.getOwner());
				dcuMasterInfo.setSim_no(request.getSimNo() != null ? request.getSimNo() : dcuMasterInfo.getSim_no());
				dcuMasterInfo.setRemarks(request.getRemarks() != null ? request.getRemarks() : dcuMasterInfo.getRemarks());
				dcuMasterInfo.setManufacturer(
						request.getManufacturer() != null ? request.getManufacturer() : dcuMasterInfo.getManufacturer());

				dcuMasterInfo.setUpdated_datetime(new Date(System.currentTimeMillis()));
				dcuMasterInfo
						.setUpdated_by(request.getUserId() != null ? request.getUserId() : dcuMasterInfo.getUpdated_by());

				dcuMasterInfos.add(dcuMasterInfo);
				
				
				DcuMasterLogs dculogs = CommonUtils.getMapper()
						.readValue(CommonUtils.getMapper().writeValueAsString(dcuMasterInfo), DcuMasterLogs.class);
				dculogs.setCreation_date(new Date(System.currentTimeMillis()));
				dculogs.setUpdated_by(request.getUserId());
				dculogs.setUpdated_datetime(new Date(System.currentTimeMillis()));

				dcuMasterLogs.add(dculogs);
			}
			
			if (dcuMasterInfos.size() > 0) {
				masterRepository.saveAll(dcuMasterInfos);
				LOG.info("Dcu Master Info Successfully Updated in DB.");
				masterLogsRepository.saveAll(dcuMasterLogs);
				LOG.info("Dcu Master Logs Successfully Added in DB.");
				response.setCode(200);
				response.setMessage(ExternalConstants.Message.UPDATED);
			}else {
				LOG.info(ExternalConstants.Message.EMPTY_REQUEST_RECEIVED);
				response.setCode(200);
				response.setMessage(ExternalConstants.Message.EMPTY_REQUEST_RECEIVED);
			}
			

		} catch (Exception e) {
			LOG.error("Issue in updating data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}

	@Override
	public CommonResponse deleteDcuMaster(DcuMasterReq req) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		LOG.info("DCU master Info Data to delete from DB:-->");

		try {
			DcuMasterInfo meterInfo = new DcuMasterInfo();
			if (req.getUserId() == null || req.getUserId().isEmpty()) {
				error.setErrorMessage("UserId can not be empty");
				response.setCode(400);
				response.setError(true);
				response.addErrorMessage(error);
				return response;
			}
			if (!StringUtils.isEmpty(req.getDcuSerialNo())) {
				Optional<DcuMasterInfo> gatewayInfo = masterRepository.findById(req.getDcuSerialNo());

				if (!gatewayInfo.isPresent()) {
					error.setErrorMessage(req.getDcuSerialNo() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				meterInfo = gatewayInfo.get();

				DcuMasterLogs dculogs = CommonUtils.getMapper()
						.readValue(CommonUtils.getMapper().writeValueAsString(meterInfo), DcuMasterLogs.class);
				dculogs.setCreation_date(new Date(System.currentTimeMillis()));
				dculogs.setDeleted_by(req.getUserId());
				dculogs.setDeleted_datetime(new Date(System.currentTimeMillis()));

				masterLogsRepository.save(dculogs);
				LOG.info("Dcu master Logs Successfully Added into DB.");
				masterRepository.deleteById(((DcuMasterReq) req).getDcuSerialNo());
				
				LOG.info("Dcu master Info Successfully Deleted from DB.");

				response.setCode(200);
				response.setMessage(ExternalConstants.Message.DELETED);
			}

		} catch (Exception e) {
			LOG.error("Issue in deleting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}

	@Override
	public CommonResponse getDcuMaster(DcuMasterReq req) {

		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		DcuMasterResponse res = new DcuMasterResponse();
		LOG.info("Dcu master Info from DB:--> ");

		try {
			if (!StringUtils.isEmpty(req.getDcuSerialNo())) {
				Optional<DcuMasterInfo> dcuInfo = masterRepository.findById(req.getDcuSerialNo());

				if (!dcuInfo.isPresent()) {
					error.setErrorMessage(req.getDcuSerialNo() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(404);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}
				DcuMasterInfo info = dcuInfo.get();

				res.setDcuSerialNo(info.getDcu_serial_number() != null ? info.getDcu_serial_number(): req.getReplaceBy());
				res.setDcuMacNicId(info.getDcu_mac_nic_id() != null ? info.getDcu_mac_nic_id() : req.getReplaceBy());
				res.setFrequency(String.valueOf(info.getFrequency()));
				res.setIpv6Address(info.getIpv6_address() != null ? info.getIpv6_address() : req.getReplaceBy());
				res.setLatitude(info.getLatitude() != null ? info.getLatitude() : req.getReplaceBy());
				res.setLongitude(info.getLongitude() != null ? info.getLongitude() : req.getReplaceBy());
				res.setManufacturer(info.getManufacturer() != null ? info.getManufacturer() : req.getReplaceBy());
				res.setNetwork(info.getNetwork() != null ? info.getNetwork() : req.getReplaceBy());
				res.setOwner(info.getOwner() != null ? info.getOwner() : req.getReplaceBy());
				res.setRemarks(info.getRemarks() != null ? info.getRemarks() : req.getReplaceBy());
				res.setSimNo(info.getSim_no() != null ? info.getSim_no() : req.getReplaceBy());

				res.setCreatedBy(info.getCreated_by() != null ? info.getCreated_by() : req.getReplaceBy());
				res.setCreatedDatetime(info.getCreated_datetime() != null
						? dateConverter.convertDateToHesString(info.getCreated_datetime())
						: req.getReplaceBy());
				res.setUpdatedBy(info.getUpdated_by() != null ? info.getUpdated_by() : req.getReplaceBy());
				res.setUpdatedDatetime(info.getUpdated_datetime() != null
						? dateConverter.convertDateToHesString(info.getUpdated_datetime())
						: req.getReplaceBy());

				response.setData(res);
				LOG.info("Dcu master Service Data Response Success.");
			}
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse getListDcuMaster(@Valid DcuMasterReq req) {

		CommonResponse response = new CommonResponse();
		List<DcuMasterResponse> resList = new ArrayList<>();
		LOG.info("Getting Dcu gat master Info from DB:--> ");

		try {
			List<DcuMasterInfo> areaInfo = masterRepository.findAll();

			for (DcuMasterInfo dcuInfo : areaInfo) {
				DcuMasterResponse res = new DcuMasterResponse();

				res.setDcuSerialNo(
						dcuInfo.getDcu_serial_number() != null ? dcuInfo.getDcu_serial_number() : req.getReplaceBy());
				res.setDcuMacNicId(
						dcuInfo.getDcu_mac_nic_id() != null ? dcuInfo.getDcu_mac_nic_id() : req.getReplaceBy());
				res.setFrequency(String.valueOf(dcuInfo.getFrequency()));
				res.setIpv6Address(dcuInfo.getIpv6_address() != null ? dcuInfo.getIpv6_address() : req.getReplaceBy());
				res.setLatitude(dcuInfo.getLatitude() != null ? dcuInfo.getLatitude() : req.getReplaceBy());
				res.setLongitude(dcuInfo.getLongitude() != null ? dcuInfo.getLongitude() : req.getReplaceBy());
				res.setManufacturer(dcuInfo.getManufacturer() != null ? dcuInfo.getManufacturer() : req.getReplaceBy());
				res.setNetwork(dcuInfo.getNetwork() != null ? dcuInfo.getNetwork() : req.getReplaceBy());
				res.setOwner(dcuInfo.getOwner() != null ? dcuInfo.getOwner() : req.getReplaceBy());
				res.setRemarks(dcuInfo.getRemarks() != null ? dcuInfo.getRemarks() : req.getReplaceBy());
				res.setSimNo(dcuInfo.getSim_no() != null ? dcuInfo.getSim_no() : req.getReplaceBy());

				resList.add(res);
			}
			response.setData(resList);
			LOG.info("DCU master Service Data Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

	}

	@Override
	public CommonResponse getDcuDeleteMaster(DcuMasterReq req) {

		CommonResponse response = new CommonResponse();
		LOG.info("Getting Dcu Get deleted Log Data from DB:--> ");

		try {
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ")
					.append(meterConfiguration.getKeyspace() + "." + Tables.DCU_MASTER_LOGS).append(" where");

			if (req.getDcuSerialNo() != null && !StringUtils.isEmpty(req.getDcuSerialNo())) {
				queryBuilder.append(" dcu_serial_number = '").append(req.getDcuSerialNo()).append("' and");
			}
			queryBuilder.append(" deleted_datetime >= cast('").append(req.getFromDate()).append("' as timestamp) ")
					.append(" and deleted_datetime <= cast('").append(req.getToDate()).append("' as timestamp)");

			String query = queryBuilder.substring(0, queryBuilder.length());

			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

			List<DcuMasterLogs> dcuLogs = new ArrayList<DcuMasterLogs>();
			dcuLogs = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DcuMasterLogs>>() {
			});

			List<DcuMasterResponse> dcuList = new ArrayList<>();

			for (DcuMasterLogs dcuInfo : dcuLogs) {
				DcuMasterResponse dcuResponse = new DcuMasterResponse();
				dcuResponse.setDcuSerialNo(dcuInfo.getDcu_serial_number() != null ? dcuInfo.getDcu_serial_number(): req.getReplaceBy());
				dcuResponse.setDcuMacNicId(
						dcuInfo.getDcu_mac_nic_id() != null ? dcuInfo.getDcu_mac_nic_id() : req.getReplaceBy());
				dcuResponse.setFrequency(String.valueOf(dcuInfo.getFrequency()));
				dcuResponse.setIpv6Address(
						dcuInfo.getIpv6_address() != null ? dcuInfo.getIpv6_address() : req.getReplaceBy());
				dcuResponse.setLatitude(dcuInfo.getLatitude() != null ? dcuInfo.getLatitude() : req.getReplaceBy());
				dcuResponse.setLongitude(dcuInfo.getLongitude() != null ? dcuInfo.getLongitude() : req.getReplaceBy());
				dcuResponse.setManufacturer(
						dcuInfo.getManufacturer() != null ? dcuInfo.getManufacturer() : req.getReplaceBy());
				dcuResponse.setNetwork(dcuInfo.getNetwork() != null ? dcuInfo.getNetwork() : req.getReplaceBy());
				dcuResponse.setRemarks(dcuInfo.getRemarks() != null ? dcuInfo.getRemarks() : req.getReplaceBy());
				dcuResponse.setSimNo(dcuInfo.getSim_no() != null ? dcuInfo.getSim_no() : req.getReplaceBy());
				dcuResponse
						.setCreatedBy(dcuInfo.getCreated_by() != null ? dcuInfo.getCreated_by() : req.getReplaceBy());
				dcuResponse.setCreatedDatetime(dcuInfo.getCreated_datetime() != null
						? dateConverter.convertDateToHesString(dcuInfo.getCreated_datetime())
						: req.getReplaceBy());
				dcuResponse
						.setUpdatedBy(dcuInfo.getUpdated_by() != null ? dcuInfo.getUpdated_by() : req.getReplaceBy());
				dcuResponse.setUpdatedDatetime(dcuInfo.getUpdated_datetime() != null
						? dateConverter.convertDateToHesString(dcuInfo.getUpdated_datetime())
						: req.getReplaceBy());
				dcuResponse
						.setDeletedBY(dcuInfo.getDeleted_by() != null ? dcuInfo.getDeleted_by() : req.getReplaceBy());		
				dcuResponse.setDeletedDatetime(dcuInfo.getDeleted_datetime() != null
						? dateConverter.convertDateToHesString(dcuInfo.getDeleted_datetime())
						: req.getReplaceBy());

				dcuList.add(dcuResponse);

			}
			response.setData(dcuList);
			LOG.info("dcu deleted Log Get Data List Response Success.");
		} catch (Exception e) {
			LOG.error("Issue in getting deleted Log due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;
	}

	@Override
	public CommonResponse dcuPower(DcuPowerReq request) {
		CommonResponse response = new CommonResponse();
		ErrorData error = new ErrorData();
		DcuPowerData dcuPower = new DcuPowerData();
		LOG.info("Dcu Power Info Data to save in DB:--> ");

		try {

			if (!StringUtils.isEmpty(request.getMeterId())) {

				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("select * from ").append(meterConfiguration.getKeyspace() + "." + Tables.DCU_INFO)
						.append(" where").append(" dcu_mac_nic_id = '").append(request.getMeterId()).append("'");

				String query = queryBuilder.substring(0, queryBuilder.length());

				String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));

				List<DcuMasterInfo> dcuPowerData = new ArrayList<DcuMasterInfo>();
				dcuPowerData = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<DcuMasterInfo>>() {
				});

				if (dcuPowerData.size() < 1) {
					LOG.info(request.getMeterId() + ": is not onboarded in Dcu Info");
					error.setErrorMessage(request.getMeterId() + " : " + ExternalConstants.Message.NOT_EXISTS);
					response.setCode(400);
					response.setError(true);
					response.addErrorMessage(error);
					return response;
				}

				DcuMasterInfo masterInfo = dcuPowerData.get(0);

				dcuPower.setDcu_mac_nic_id(request.getMeterId());
				dcuPower.setDcu_serial_number(masterInfo.getDcu_serial_number());
				dcuPower.setMessage_type(request.getMessageType());
				dcuPower.setRequest_id(String.valueOf(request.getRequestId()));
				dcuPower.setMdas_datetime(new Date(System.currentTimeMillis()));
				
				Payload payload = request.getPayload();
				if (payload != null) {
					dcuPower.setBattery_charging_status(String.valueOf(payload.getBatteryChargingStatus()));
					dcuPower.setPower_good_status(String.valueOf(payload.getPowerGoodStatus()));
					dcuPower.setBattery_raw_value(String.valueOf(payload.getBatteryRawValue()));
					dcuPower.setBattery_mv_value(String.valueOf(payload.getBatteryMVValue()));
				}

				powerRepository.save(dcuPower);
				LOG.info("Dcu Power Info Data Successfully Stored in DB.");
				response.setCode(200);
				response.setMessage(ExternalConstants.Message.ADDED);
			}
				
		} catch (Exception e) {
			LOG.error("Issue in adding data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}

		return response;
	}

	@Override
	public CommonResponse getDcuPush(DcuPowerReq req) {
		CommonResponse response = new CommonResponse();
		LOG.info("Getting Dcu Push Data from DB:--> ");
		
		try {
			
			String table = meterConfiguration.getKeyspace() + "." + Tables.DCU_POWER_DATA;


			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("select * from ").append(table).append(" where ").append(" mdas_datetime >= cast('")
					.append(req.getFromDate()).append("' as timestamp) ").append(" and mdas_datetime <= cast('")
					.append(req.getToDate()).append("' as timestamp) ");
			
			String query = queryBuilder.substring(0, queryBuilder.length());
			
			String outputList = CommonUtils.getMapper().writeValueAsString(prestoJdbcTemplate.queryForList(query));
			List<DcuPushResponse> dcuResponses = new ArrayList<>();
			
			dcuDataCaster.dcuDataEvent(outputList, dcuResponses, req);
			response.setData(dcuResponses);
			LOG.info("Event Data Service Response by Hier  Success.");
	
		}catch(Exception e) {
			LOG.error("Issue in fetching data due to : " + e.getMessage());
			response = ExceptionHandlerConfig.setErrorData(e);
		}
		return response;

}
}