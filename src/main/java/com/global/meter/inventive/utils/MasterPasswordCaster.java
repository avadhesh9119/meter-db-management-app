package com.global.meter.inventive.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.global.meter.inventive.models.MasterPasswordResponse;
import com.global.meter.inventive.models.MeterTypeInfoRead;
import com.global.meter.utils.CommonUtils;

@Component
public class MasterPasswordCaster {
	private static final Logger LOG = LoggerFactory.getLogger(MasterPasswordCaster.class);

	
	public void prepareMasterPassword(String outputList, List<MasterPasswordResponse> ispResponseList)
			throws Exception {
		LOG.info("Master Password Caster called....");
		List<MeterTypeInfoRead> meterTypeInfos = new ArrayList<MeterTypeInfoRead>();
		meterTypeInfos = CommonUtils.getMapper().readValue(outputList, new TypeReference<List<MeterTypeInfoRead>>() {
		});
		LOG.info("Master Password Caster Response Adding");
		for (MeterTypeInfoRead meterTypeInfo : meterTypeInfos) {
			MasterPasswordResponse mPassword = new MasterPasswordResponse();
			
			mPassword.setMeterType(meterTypeInfo.getMeter_type());
			mPassword.setAuthKey(meterTypeInfo.getAuthkey());
			mPassword.setAuthMode(meterTypeInfo.getAuthmode());
			mPassword.setCipheringKey(meterTypeInfo.getCipheringkey());
			mPassword.setCipheringMode(meterTypeInfo.getCipheringmode());
			mPassword.setFirmwarePwd(meterTypeInfo.getFirmwarepwd());
			mPassword.setHighPwd(meterTypeInfo.getHighpwd());
			mPassword.setLowPwd(meterTypeInfo.getLowpwd());
			mPassword.setManufacturer(meterTypeInfo.getManufacturer());
			mPassword.setMiosFile(meterTypeInfo.getMios_file());
			mPassword.setMiosFormat(meterTypeInfo.getMiosformat() != null ? meterTypeInfo.getMiosformat() : false);
			mPassword.setPart(meterTypeInfo.getPart());
			mPassword.setPushPorts(meterTypeInfo.getPushports());
			mPassword.setSystemTitle(meterTypeInfo.getSystemtitle());
		
			ispResponseList.add(mPassword);
		}
		
		LOG.info("Master Password Caster Response Added Success");
	}
}
