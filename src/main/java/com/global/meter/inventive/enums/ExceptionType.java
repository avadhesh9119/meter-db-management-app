package com.global.meter.inventive.enums;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.UncategorizedSQLException;

import com.alibaba.druid.pool.DataSourceNotAvailableException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.exceptions.ReadFailureException;
import com.fasterxml.jackson.core.JsonProcessingException;
 
public enum ExceptionType {
 
    NULL_POINTER_EXCEPTION(500, "A system issue occurred while processing your request.", NullPointerException.class),
    JSON_PROCESSING_EXCEPTION(400, "An error occurred while processing your data.", JsonProcessingException.class),
    PARSE_EXCEPTION(400, "There was an issue interpreting the date format.", ParseException.class),
    SQL_EXCEPTION(500, "A database error occurred while processing your request.", SQLException.class),
    GENERAL_EXCEPTION(500, "An unexpected error occurred.", Exception.class),
    QUERY_EXECUTION_EXCEPTION(500, "We encountered an issue executing your request.", QueryExecutionException.class),
    QUERY_TIMEOUT_EXCEPTION(504, "Your request took too long to process.", QueryTimeoutException.class),
    NO_HOST_AVAILABLE_EXCEPTION(503, "We're currently experiencing connectivity issues.", NoHostAvailableException.class),
    DATA_SOURCE_NOT_AVAILABLE_EXCEPTION(503, "A required data source is currently unavailable.", DataSourceNotAvailableException.class),
    READ_FAILURE_EXCEPTION(500, "We encountered a problem retrieving the data.", ReadFailureException.class),
	UNCATEGORIZED_SQL_EXCEPTION(500, "An unexpected database issue occurred.", UncategorizedSQLException.class);
	
    private final int code;
    private final String message;
    private final Class<? extends Exception> exceptionClass;
 
    private static final Map<Class<? extends Exception>, ExceptionType> EXCEPTION_MAP = new HashMap<>();
 
    // Static block to initialize the map
    static {
        for (ExceptionType type : values()) {
            EXCEPTION_MAP.put(type.getExceptionClass(), type);
        }
    }
 
    ExceptionType(int code, String message, Class<? extends Exception> exceptionClass) {
        this.code = code;
        this.message = message;
        this.exceptionClass = exceptionClass;
    }
 
    public int getCode() {
        return code;
    }
 
    public String getMessage() {
        return message;
    }
 
    public Class<? extends Exception> getExceptionClass() {
        return exceptionClass;
    }
 
    public static ExceptionType fromException(Exception e) {
        // Return the mapped exception type or GENERAL_EXCEPTION if not found
        return EXCEPTION_MAP.getOrDefault(e.getClass(), GENERAL_EXCEPTION);
    }
}