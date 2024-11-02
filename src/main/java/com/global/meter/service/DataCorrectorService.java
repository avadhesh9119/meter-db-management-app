package com.global.meter.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.global.meter.business.model.BillingDataSinglePhase;
import com.global.meter.business.model.DailyLoadProfileSinglePhase;
import com.global.meter.business.model.DevicesInfo;
import com.global.meter.business.model.InstantaneousSinglePhase;
import com.global.meter.business.model.LastBillingDataSinglePhase;
import com.global.meter.data.repository.BillingDataRepository;
import com.global.meter.data.repository.DailyLoadProfileRepository;
import com.global.meter.data.repository.DevicesInfoRepository;
import com.global.meter.data.repository.InstantaneousSinglePhaseRepository;
import com.global.meter.data.repository.LastBillingDataRepository;
import com.global.meter.request.model.DataCorrector;
import com.global.meter.utils.Constants;

@Service
public class DataCorrectorService {

	private static final Logger LOG = LoggerFactory.getLogger(DataCorrectorService.class);
	
	@Autowired
	InstantaneousSinglePhaseRepository instantaneousSinglePhaseRepository;
	
	@Autowired
	LastBillingDataRepository lastBillingDataRepository;
	
	@Autowired
	DailyLoadProfileRepository dailyLoadProfileRepository;
	
	@Autowired
	BillingDataRepository billingDataRepository;
	
	@Autowired
	DevicesInfoRepository devicesInfoRepository; 
	
	@Autowired
	private CassandraOperations cassandraTemplate;
	
	@Autowired
	@Qualifier("prestoTemplate")
	JdbcTemplate prestoJdbcTemplate ;
	
	public boolean correctSPInstantData(DataCorrector dataCorrector) throws Exception{
		if(dataCorrector.getDeviceList() != null && dataCorrector.getDeviceList().size()>0) {
			for (String devSno : dataCorrector.getDeviceList()) {
				
				List<InstantaneousSinglePhase> data = findSPInstantDevDataList(devSno);
				if(data != null && data.size() > 0) {
					for (InstantaneousSinglePhase instantaneousSinglePhase : data) {
						if(instantaneousSinglePhase.getCumulative_energy_kvah_import() > 10000) {
							double newVal_Cumulative_energy_kvah_import = instantaneousSinglePhase.getCumulative_energy_kvah_import()/1000;
							instantaneousSinglePhase.setCumulative_energy_kvah_import(newVal_Cumulative_energy_kvah_import);
							
							double newVal_cumulative_energy_kwh_import = instantaneousSinglePhase.getCumulative_energy_kwh_import()/1000;
							instantaneousSinglePhase.setCumulative_energy_kwh_import(newVal_cumulative_energy_kwh_import);
							
							double active_power_kw = instantaneousSinglePhase.getActive_power_kw()/1000;
							instantaneousSinglePhase.setActive_power_kw(active_power_kw);
							
							double apparent_power_kva = instantaneousSinglePhase.getApparent_power_kva()/1000;
							instantaneousSinglePhase.setApparent_power_kva(apparent_power_kva);
							
							double maximum_demand_kva = instantaneousSinglePhase.getMaximum_demand_kva()/1000;
							instantaneousSinglePhase.setMaximum_demand_kva(maximum_demand_kva);
							
							double maximum_demand_kw = instantaneousSinglePhase.getMaximum_demand_kw()/1000;
							instantaneousSinglePhase.setMaximum_demand_kw(maximum_demand_kw);
							
							LOG.info("Cumulative_energy_kvah_import is : "+ instantaneousSinglePhase.getCumulative_energy_kvah_import()+
							":: New Value will be :" + newVal_Cumulative_energy_kvah_import + ": " + newVal_cumulative_energy_kwh_import
							+ " : "+ active_power_kw+" : "+apparent_power_kva+" : "+maximum_demand_kva+" : "+maximum_demand_kw);
							
							instantaneousSinglePhaseRepository.save(instantaneousSinglePhase);
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean correctSPLastBillingData(DataCorrector dataCorrector) throws Exception{
		if(dataCorrector.getDeviceList() != null && dataCorrector.getDeviceList().size()>0) {
			for (String devSno : dataCorrector.getDeviceList()) {
				
				List<LastBillingDataSinglePhase> data = findLastSPBillingDevDataList(devSno);
				if(data != null && data.size() > 0) {
					for (LastBillingDataSinglePhase lastBillingData : data) {
						if(lastBillingData.getCumulative_energy_kvah_import() > 10500) {
							
							double cumulative_energy_kvah_import = lastBillingData.getCumulative_energy_kvah_import()/1000;
							lastBillingData.setCumulative_energy_kvah_import(cumulative_energy_kvah_import);
							
							double cumulative_energy_kvah_tier1 = lastBillingData.getCumulative_energy_kvah_tier1()/1000;
							lastBillingData.setCumulative_energy_kvah_tier1(cumulative_energy_kvah_tier1);
							
							double cumulative_energy_kvah_tier2 = lastBillingData.getCumulative_energy_kvah_tier2()/1000;
							lastBillingData.setCumulative_energy_kvah_tier2(cumulative_energy_kvah_tier2);
							
							double cumulative_energy_kvah_tier3 = lastBillingData.getCumulative_energy_kvah_tier3()/1000;
							lastBillingData.setCumulative_energy_kvah_tier3(cumulative_energy_kvah_tier3);
							
							double cumulative_energy_kvah_tier4 = lastBillingData.getCumulative_energy_kvah_tier4()/1000;
							lastBillingData.setCumulative_energy_kvah_tier4(cumulative_energy_kvah_tier4);
							
							double cumulative_energy_kwh_import = lastBillingData.getCumulative_energy_kwh_import()/1000;
							lastBillingData.setCumulative_energy_kwh_import(cumulative_energy_kwh_import);
							
							double cumulative_energy_kwh_tier1 = lastBillingData.getCumulative_energy_kwh_tier1()/1000;
							lastBillingData.setCumulative_energy_kwh_tier1(cumulative_energy_kwh_tier1);
							
							double cumulative_energy_kwh_tier2 = lastBillingData.getCumulative_energy_kwh_tier2()/1000;
							lastBillingData.setCumulative_energy_kwh_tier2(cumulative_energy_kwh_tier2);
							
							double cumulative_energy_kwh_tier3 = lastBillingData.getCumulative_energy_kwh_tier3()/1000;
							lastBillingData.setCumulative_energy_kwh_tier3(cumulative_energy_kwh_tier3);
							
							double cumulative_energy_kwh_tier4 = lastBillingData.getCumulative_energy_kwh_tier4()/1000;
							lastBillingData.setCumulative_energy_kwh_tier4(cumulative_energy_kwh_tier4);
							
							double maximum_demand_kva = lastBillingData.getMaximum_demand_kva()/1000;
							lastBillingData.setMaximum_demand_kva(maximum_demand_kva);
							
							double maximum_demand_kw = lastBillingData.getMaximum_demand_kw()/1000;
							lastBillingData.setMaximum_demand_kw(maximum_demand_kw);
							    
							LOG.info("Cumulative_energy_kvah_import is : "+ lastBillingData.getCumulative_energy_kvah_import()+
							":: New Value will be :" + cumulative_energy_kvah_import);
							
							lastBillingDataRepository.save(lastBillingData);
						}
					}
				}
			}
		}
		return true;
	}
	
	
	public boolean correctSPBillingData(DataCorrector dataCorrector) throws Exception{
		if(dataCorrector.getDeviceList() != null && dataCorrector.getDeviceList().size()>0) {
			for (String devSno : dataCorrector.getDeviceList()) {
				
				List<BillingDataSinglePhase> data = findSPBillingDevDataList(devSno);
				if(data != null && data.size() > 0) {
					for (BillingDataSinglePhase billingData : data) {
						if(billingData.getCumulative_energy_kvah_import() > 10500) {
							
							double cumulative_energy_kvah_import = billingData.getCumulative_energy_kvah_import()/1000;
							billingData.setCumulative_energy_kvah_import(cumulative_energy_kvah_import);
							
							double cumulative_energy_kvah_tier1 = billingData.getCumulative_energy_kvah_tier1()/1000;
							billingData.setCumulative_energy_kvah_tier1(cumulative_energy_kvah_tier1);
							
							double cumulative_energy_kvah_tier2 = billingData.getCumulative_energy_kvah_tier2()/1000;
							billingData.setCumulative_energy_kvah_tier2(cumulative_energy_kvah_tier2);
							
							double cumulative_energy_kvah_tier3 = billingData.getCumulative_energy_kvah_tier3()/1000;
							billingData.setCumulative_energy_kvah_tier3(cumulative_energy_kvah_tier3);
							
							double cumulative_energy_kvah_tier4 = billingData.getCumulative_energy_kvah_tier4()/1000;
							billingData.setCumulative_energy_kvah_tier4(cumulative_energy_kvah_tier4);
							
							double cumulative_energy_kwh_import = billingData.getCumulative_energy_kwh_import()/1000;
							billingData.setCumulative_energy_kwh_import(cumulative_energy_kwh_import);
							
							double cumulative_energy_kwh_tier1 = billingData.getCumulative_energy_kwh_tier1()/1000;
							billingData.setCumulative_energy_kwh_tier1(cumulative_energy_kwh_tier1);
							
							double cumulative_energy_kwh_tier2 = billingData.getCumulative_energy_kwh_tier2()/1000;
							billingData.setCumulative_energy_kwh_tier2(cumulative_energy_kwh_tier2);
							
							double cumulative_energy_kwh_tier3 = billingData.getCumulative_energy_kwh_tier3()/1000;
							billingData.setCumulative_energy_kwh_tier3(cumulative_energy_kwh_tier3);
							
							double cumulative_energy_kwh_tier4 = billingData.getCumulative_energy_kwh_tier4()/1000;
							billingData.setCumulative_energy_kwh_tier4(cumulative_energy_kwh_tier4);
							
							double maximum_demand_kva = billingData.getMaximum_demand_kva()/1000;
							billingData.setMaximum_demand_kva(maximum_demand_kva);
							
							double maximum_demand_kw = billingData.getMaximum_demand_kw()/1000;
							billingData.setMaximum_demand_kw(maximum_demand_kw);
							    
							LOG.info("Cumulative_energy_kvah_import is : "+ billingData.getCumulative_energy_kvah_import()+
							":: New Value will be :" + cumulative_energy_kvah_import);
							
							billingDataRepository.save(billingData);
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean correctSPDailyLPData(DataCorrector dataCorrector) throws Exception{
		if(dataCorrector.getDeviceList() != null && dataCorrector.getDeviceList().size()>0) {
			for (String devSno : dataCorrector.getDeviceList()) {
				
				List<DailyLoadProfileSinglePhase> data = findSPDailyLPDevDataList(devSno);
				if(data != null && data.size() > 0) {
					for (DailyLoadProfileSinglePhase dailyLP : data) {
						if(dailyLP.getCumulative_energy_kvah_import() > 10500) {
							
							double cumulative_energy_kvah_import = dailyLP.getCumulative_energy_kvah_import()/1000;
							dailyLP.setCumulative_energy_kvah_import(cumulative_energy_kvah_import);
							
							double cumulative_energy_kwh_import = dailyLP.getCumulative_energy_kwh_import()/1000;
							dailyLP.setCumulative_energy_kwh_import(cumulative_energy_kwh_import);
							    
							LOG.info("Cumulative_energy_kvah_import is : "+ dailyLP.getCumulative_energy_kvah_import()+
							":: New Value will be :" + cumulative_energy_kvah_import);
							
							dailyLoadProfileRepository.save(dailyLP);
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean correctDevicesInfoData(DataCorrector dataCorrector) throws Exception{
		if(dataCorrector.getDeviceList() != null && dataCorrector.getDeviceList().size()>0) {
			for (String devSno : dataCorrector.getDeviceList()) {
				
				DevicesInfo devicesInfo = findDevicesInfoDevDataList(devSno);
				if(devicesInfo != null && devicesInfo.getDevice_serial_number() != null) {
					if(devicesInfo.getCumulative_energy_kvah_import() > 11000) {
						
						double cumulative_energy_kvah_import = devicesInfo.getCumulative_energy_kvah_import()/1000;
						devicesInfo.setCumulative_energy_kvah_import(cumulative_energy_kvah_import);
						
						double  cumulative_energy_kwh_import = devicesInfo.getCumulative_energy_kwh_import()/1000;
						devicesInfo.setCumulative_energy_kwh_import(cumulative_energy_kwh_import);
						
						double maximum_demand_kva = devicesInfo.getMaximum_demand_kva()/1000;
						devicesInfo.setMaximum_demand_kva(maximum_demand_kva);
						
						double maximum_demand_kw = devicesInfo.getMaximum_demand_kw()/1000;
						devicesInfo.setMaximum_demand_kw(maximum_demand_kw);
						
						LOG.info(devSno + " : Cumulative_energy_kvah_import is : "+ devicesInfo.getCumulative_energy_kvah_import()+
								":: New Value will be :" + cumulative_energy_kvah_import+" : "
								+cumulative_energy_kwh_import+" : "+maximum_demand_kva+" : "+maximum_demand_kw);
						devicesInfoRepository.save(devicesInfo);
					}
				}
			}
		}
		return true;
	}
	
	
	
	public List<InstantaneousSinglePhase> findSPInstantDevDataList(String devSno) throws Exception
	{
		List<InstantaneousSinglePhase> data = null;
		try {
			Where query = QueryBuilder.select().from(Constants.Table.INSTANT_SP).where(
					QueryBuilder.eq("device_serial_number", devSno));
			
			data =  cassandraTemplate.select(query, InstantaneousSinglePhase.class);
		} catch (Exception e) {
			LOG.info("No Data Found Single Phase Instant for " + devSno);
		}
		return data;
	}
	
	public List<LastBillingDataSinglePhase> findLastSPBillingDevDataList(String devSno) throws Exception
	{
		List<LastBillingDataSinglePhase> data = null;
		try {
			Where query = QueryBuilder.select().from(Constants.Table.LAST_BILLING_SP).where(
					QueryBuilder.eq("device_serial_number", devSno));
			
			data =  cassandraTemplate.select(query, LastBillingDataSinglePhase.class);
		} catch (Exception e) {
			LOG.info("No Data Found Single Phase Instant for " + devSno);
		}
		return data;
	}
	
	public List<BillingDataSinglePhase> findSPBillingDevDataList(String devSno) throws Exception
	{
		List<BillingDataSinglePhase> data = null;
		try {
			Where query = QueryBuilder.select().from(Constants.Table.BILLING_SP).where(
					QueryBuilder.eq("device_serial_number", devSno));
			
			data =  cassandraTemplate.select(query, BillingDataSinglePhase.class);
		} catch (Exception e) {
			LOG.info("No Data Found Single Phase Instant for " + devSno);
		}
		return data;
	}
	
	public List<DailyLoadProfileSinglePhase> findSPDailyLPDevDataList(String devSno) throws Exception
	{
		List<DailyLoadProfileSinglePhase> data = null;
		try {
			Where query = QueryBuilder.select().from(Constants.Table.DAILY_LP_SP).where(
					QueryBuilder.eq("device_serial_number", devSno));
			
			data =  cassandraTemplate.select(query, DailyLoadProfileSinglePhase.class);
		} catch (Exception e) {
			LOG.info("No Data Found Single Phase Instant for " + devSno);
		}
		return data;
	}
	
	public DevicesInfo findDevicesInfoDevDataList(String devSno) throws Exception
	{
		DevicesInfo data = null;
		try {
			Where query = QueryBuilder.select().from(Constants.Table.DEVICES_INFO).where(
					QueryBuilder.eq("device_serial_number", devSno));
			
			data =  cassandraTemplate.select(query, DevicesInfo.class).get(0);
		} catch (Exception e) {
			LOG.info("No Data Found For " + devSno);
		}
		return data;
	}
	
	
}
