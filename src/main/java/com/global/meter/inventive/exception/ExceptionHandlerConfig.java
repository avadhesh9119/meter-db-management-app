package com.global.meter.inventive.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.global.meter.inventive.enums.ExceptionType;
import com.global.meter.inventive.models.CommonResponse;
import com.global.meter.inventive.models.ErrorData;
 
public class ExceptionHandlerConfig {
 
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerConfig.class);
 
    public static CommonResponse setErrorData(Exception e) {
        ExceptionType exceptionType = ExceptionType.fromException(e);
 
        CommonResponse response = new CommonResponse();
        ErrorData error = new ErrorData();
 
        LOG.error("Exception occurred: {}", e.getMessage());
 
        error.setErrorMessage(exceptionType.getMessage());
        response.setCode(exceptionType.getCode());
 
        response.setError(true);
        response.addErrorMessage(error);
  
        return response;
    }
}
