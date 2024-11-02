package com.global.meter.v3.inventive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.global.meter.utils.CommonUtils;

@Service
public class PushDataToExternalAddressService {

private static final Logger LOG = LoggerFactory.getLogger(PushDataToExternalAddressService.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	public ResponseEntity<?> pushDataToExternalURL(Object logs, String url) {
		ResponseEntity<?> response = null;
		try {
			response = restTemplate.exchange(url,
					HttpMethod.POST, CommonUtils.getHttpEntity(logs, MediaType.APPLICATION_JSON),
					String.class);
			
			LOG.info(response.toString());
		} catch (RestClientException e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
