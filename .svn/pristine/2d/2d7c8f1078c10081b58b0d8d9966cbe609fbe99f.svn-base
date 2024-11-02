package com.global.meter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.global.meter.business.model.MeterPwds;
import com.global.meter.common.model.Devices;
import com.global.meter.data.repository.MeterPasswordRepository;
import com.global.meter.data.repository.MeterTypeRepository;
import com.global.meter.utils.MetersCommandsConfiguration;

@EnableScheduling
@SpringBootApplication
@EnableCaching
public class MeterDBAppStarter {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeterDBAppStarter.class);
	
	//public static List<MeterTypeInfo> meterTypeList = null;
	public static List<MeterPwds> meterPwdsList = null;
	public static Map<String , String> pushPortsInfo = new LinkedHashMap<String, String>(); 
	public static Map<String , Devices> devicesMasterInfo = new LinkedHashMap<String, Devices>();
	public static Properties eventProperties = null;
	public static Properties meterDivFactorProperties = null;
	public static String keyspace = null;
	
	private static ConfigurableApplicationContext context;
	public static ConfigurableApplicationContext mContext;
	
	public static void main(String[] args) {
	 context = SpringApplication.run(MeterDBAppStarter.class, args);
	 mContext = context;
	}
	
	@Bean
	public RestTemplate getRestTemplate()
	{
		LOG.info("Creating rest template to connect with rest client...");
		return new RestTemplate();
	}
	
	@Autowired
	MeterTypeRepository meterTypeRepository;
	
	@Autowired
	MeterPasswordRepository meterPasswordRepository;
	
	@Autowired
	MetersCommandsConfiguration metersCommandsConfiguration;

	@PostConstruct
	private void init() throws IOException
	{
		LOG.info("Intializing Meter Group info...");
		//meterTypeList = meterTypeRepository.findAll();
		meterPwdsList = meterPasswordRepository.findAll();
		
		LOG.info("Meter Group List collected from Store.");
		if(meterPwdsList != null && meterPwdsList.size() > 0)
		{
			for (MeterPwds meterPwd : meterPwdsList) {
				Devices devices= new Devices();
				devices.setAuthKey(meterPwd.getAuthKey());
				devices.setAuthMode(meterPwd.getAuthMode());
				devices.setCipheringKey(meterPwd.getCipheringKey());
				devices.setCipheringMode(meterPwd.getCipheringMode());
				devices.setHighPwd(meterPwd.getHighPwd());
				devices.setLowPwd(meterPwd.getLowPwd());
				devices.setManufacturer(meterPwd.getManufacturer());
				devices.setPart(meterPwd.getPart() != null ? meterPwd.getPart() : 3);
				devices.setSystemTitle(meterPwd.getSystemTitle());
				devices.setFirwarePwd(meterPwd.getFimrwarePwd());
				
				//String key = meterPwd.getManufacturer() + ":" + (meterPwd.getDeviceType() != null ? meterPwd.getDeviceType() : "");
				String key = meterPwd.getManufacturer()  + ((meterPwd.getDeviceType() != null && !meterPwd.getDeviceType().isEmpty()) ? ":" + meterPwd.getDeviceType() : "")+":"+meterPwd.getModeOfComm();
				//devicesMasterInfo.put(meterPwd.getMeterType(), devices);
				//pushPortsInfo.put(meterPwd.getMeterType(), meterPwd.getPushPorts());
				
				devicesMasterInfo.put(key, devices);
				pushPortsInfo.put(key, meterPwd.getPushPorts());
			}
			
			LOG.info("Metere Group Info Loaded...");
		}
		
		LOG.info("Loading EventList Property File:---> ");
		loadEventPropertyFile();
		
		LOG.info("Loading Meter Div Factor Property File:---> ");
		loadMeterDivFactorPropFile();
		
		setKeySpaceValue();
	}
	
	
	private  void setKeySpaceValue() {
		keyspace = metersCommandsConfiguration.getKeyspace();
	}
	
	@SuppressWarnings("unused")
	private void loadEventPropertyFile() throws IOException
	{
		InputStream inputStream = null;
		
		try {
			eventProperties = new Properties();

			File file = new File(metersCommandsConfiguration.getEventTypeList());
			
			inputStream = new FileInputStream(file);

			if (inputStream != null) {
				eventProperties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"  + metersCommandsConfiguration.getEventTypeList() 
				+ "' not found in the classpath");
			}
		} catch (Exception e) {
			LOG.info("Event List Property File not uploaded due to "+ e.getMessage());
		}
		finally {
			inputStream.close();
		}
	}
	
	@SuppressWarnings("unused")
	private void loadMeterDivFactorPropFile() throws IOException
	{
		InputStream inputStream = null;
		
		try {
			meterDivFactorProperties = new Properties();

			File file = new File(metersCommandsConfiguration.getDivFactorList());
			
			inputStream = new FileInputStream(file);
			
			if (inputStream != null) {
				meterDivFactorProperties.load(inputStream);
				LOG.info("Division Factor File Loaded....");
			} else {
				throw new FileNotFoundException("property file '"  + metersCommandsConfiguration.getDivFactorList() 
				+ "' not found in the classpath");
			}
		} catch (Exception e) {
			LOG.info("Meter Div Factor Property File not uploaded due to :" + e.getMessage());
		}
		finally {
			inputStream.close();
		}
	}
	
	
	public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(MeterDBAppStarter.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }
	
	public void reload() throws IOException {
		//reload master pwds
		init();
		//reload events property
		loadEventPropertyFile();
		//reload division factor
		loadMeterDivFactorPropFile();
	}
	
}
