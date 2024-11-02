package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.inventive.models.InstantDevicesInfoResponse;
import com.global.meter.inventive.models.MeterDataVisualizationReq;
import com.global.meter.utils.CommonUtils;
import com.global.meter.utils.DateConverter;

@Component
public class InstantDevicesInfoCaster {
	private static final Logger LOG = LoggerFactory.getLogger(InstantDevicesInfoCaster.class);

	@Autowired
	DateConverter dateConverter;

	public void prepareInstantData(String outputList, List<InstantDevicesInfoResponse> ispResponseList, MeterDataVisualizationReq req) throws Exception {
		LOG.info("Instant Device Info Data Caster called....");
		List<DevicesInfo> instantData = new ArrayList<DevicesInfo>();
		instantData = CommonUtils.getMapper()
				.readValue(outputList, new TypeReference<List<DevicesInfo>>() {});

		LOG.info("Instant Device Responce Data Adding....");

		for (DevicesInfo devInfo : instantData) {
			InstantDevicesInfoResponse ispResponse = new InstantDevicesInfoResponse();

			try {
				ispResponse.setMeterNumber(String.valueOf(devInfo.getDevice_serial_number()));
				ispResponse.setCumulativeEnergyKvahExport(devInfo.getCumulative_energy_kvah_export() != null ?
						Double.valueOf(devInfo.getCumulative_energy_kvah_export()) : 0.0);
				ispResponse.setCumulativeEnergyKvahImport(devInfo.getCumulative_energy_kvah_import() != null ?
						Double.valueOf(devInfo.getCumulative_energy_kvah_import()) : 0.0);
				ispResponse.setCumulativeEnergyKwhExport(devInfo.getCumulative_energy_kwh_export() != null ?
						Double.valueOf(devInfo.getCumulative_energy_kwh_export()) : 0.0);
				ispResponse.setCumulativeEnergyKwhImport(devInfo.getCumulative_energy_kwh_import() != null ?
						Double.valueOf(devInfo.getCumulative_energy_kwh_import()) : 0.0);
				ispResponse.setCumulativeTamperCount(devInfo.getCumulative_tamper_count() != null ?
						Integer.valueOf(devInfo.getCumulative_tamper_count()) : 0);
				ispResponse.setLoadLimits(devInfo.getLoad_limits() != null ?
						Double.valueOf(devInfo.getLoad_limits()) : 0.0);
				ispResponse.setMaximumDemandKva(devInfo.getMaximum_demand_kva() != null ?
						Double.valueOf(devInfo.getMaximum_demand_kva()) : 0.0);
				ispResponse.setMaximumDemandKvaDatetime(devInfo.getMaximum_demand_kva_datetime() != null ?
						dateConverter.convertDateToHesString(devInfo.getMaximum_demand_kva_datetime()) : req.getReplaceBy());
				ispResponse.setMaximumDemandKw(devInfo.getMaximum_demand_kw() != null ?
						Double.valueOf(devInfo.getMaximum_demand_kw()) : null);
				ispResponse.setMaximumDemandKwDatetime(devInfo.getMaximum_demand_kw_datetime() != null ?
						dateConverter.convertDateToHesString(devInfo.getMaximum_demand_kw_datetime()) : req.getReplaceBy());
				ispResponse.setMeterDatetime(devInfo.getMeter_datetime() != null ?
						dateConverter.convertDateToHesString(devInfo.getMeter_datetime()) : req.getReplaceBy());
			if(devInfo.getDevice_type().equalsIgnoreCase(ExternalConstants.DeviceTypes.CT_METER)) {
								ispResponse.setRelayStatus("-");
			}else {
				ispResponse.setRelayStatus(ExternalConstants.Status.RELAY_STATUS.equalsIgnoreCase(devInfo.getRelay_status())
						? ExternalConstants.Status.CONNECTED
						: ExternalConstants.Status.DISCONNECTED);
			}

				ispResponseList.add(ispResponse);
				LOG.info("Instant Device Responce Data Added....");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
		}
	}

}
