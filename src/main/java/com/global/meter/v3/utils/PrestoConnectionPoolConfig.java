package com.global.meter.v3.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrestoConnectionPoolConfig {

    @Value("${spring.datasource.presto.driver-class-name}")
    private String driverClassName;
    
    @Value("${spring.datasource.presto.url}")
    private String dbUrl;

    @Value("${spring.datasource.presto.username}")
    private String username;

    @Value("${spring.datasource.presto.hikari.maximum-pool-size}")
    private int maxPoolSize;

    @Value("${spring.datasource.presto.hikari.minimum-idle}")
    private int minIdle;

    @Value("${spring.datasource.presto.hikari.idle-timeout}")
    private long idleTimeout;

    @Value("${spring.datasource.presto.hikari.connection-timeout}")
    private long connectionTimeout;

    @Value("${spring.datasource.presto.hikari.max-lifetime}")
    private long maxLifetime;
    
    @Value("${spring.datasource.presto.hikari.leak-detection-threshold}")
    private long leakDetectionThreshold;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public long getIdleTimeout() {
		return idleTimeout;
	}

	public void setIdleTimeout(long idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public long getMaxLifetime() {
		return maxLifetime;
	}

	public void setMaxLifetime(long maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public long getLeakDetectionThreshold() {
		return leakDetectionThreshold;
	}

	public void setLeakDetectionThreshold(long leakDetectionThreshold) {
		this.leakDetectionThreshold = leakDetectionThreshold;
	}
    

}
