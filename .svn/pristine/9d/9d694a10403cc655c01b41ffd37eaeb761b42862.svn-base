package com.global.meter.v3.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:hazelcast.properties")
public class HazelcastProperties {

    // Hazelcast instance name
    @Value("${spring.hazelcast.config.instance-name}")
    private String instanceName;
    
    // Hazelcast network configuration
    @Value("${hazelcast.network.join.multicast.enabled}")
    private boolean multicastEnabled;

    @Value("${hazelcast.network.tcp-ip.enabled}")
    private boolean tcpIpEnabled;

    @Value("${hazelcast.network.tcp-ip.members}")
    private String tcpIpMembers;

    // DevicesCache configuration
    @Value("${spring.hazelcast.config.map.devicesCache.time-to-live-seconds}")
    private int devicesCacheTtl;

    @Value("${spring.hazelcast.config.map.devicesCache.eviction.size}")
    private int devicesCacheEvictionSize;

    @Value("${spring.hazelcast.config.map.devicesCache.eviction.max-size-policy}")
    private String devicesCacheMaxSizePolicy;

    @Value("${spring.hazelcast.config.map.devicesCache.eviction.eviction-policy}")
    private String devicesCacheEvictionPolicy;

    // commandCache configuration
    @Value("${spring.hazelcast.config.map.commandCache.time-to-live-seconds}")
    private int commandCacheTtl;
    
    @Value("${spring.hazelcast.config.map.commandCache.eviction.size}")
    private int commandCacheEvictionSize;
    
    @Value("${spring.hazelcast.config.map.commandCache.eviction.max-size-policy}")
    private String commandCacheMaxSizePolicy;
    
    @Value("${spring.hazelcast.config.map.commandCache.eviction.eviction-policy}")
    private String commandCacheEvictionPolicy;
    
    // resDataCache configuration
    @Value("${spring.hazelcast.config.map.resDataCache.time-to-live-seconds}")
    private int resDataCacheTtl;
    
    @Value("${spring.hazelcast.config.map.resDataCache.eviction.size}")
    private int resDataCacheEvictionSize;
    
    @Value("${spring.hazelcast.config.map.resDataCache.eviction.max-size-policy}")
    private String resDataCacheMaxSizePolicy;
    
    @Value("${spring.hazelcast.config.map.resDataCache.eviction.eviction-policy}")
    private String resDataCacheEvictionPolicy;
    
    // manufacturerCache configuration
    @Value("${spring.hazelcast.config.map.manufacturerCache.time-to-live-seconds}")
    private int manufacturerCacheTtl;
    
    @Value("${spring.hazelcast.config.map.manufacturerCache.eviction.size}")
    private int manufacturerCacheEvictionSize;
    
    @Value("${spring.hazelcast.config.map.manufacturerCache.eviction.max-size-policy}")
    private String manufacturerCacheMaxSizePolicy;
    
    @Value("${spring.hazelcast.config.map.manufacturerCache.eviction.eviction-policy}")
    private String manufacturerCacheEvictionPolicy;
    
    // mdmMasterCache configuration
    @Value("${spring.hazelcast.config.map.mdmMasterCache.time-to-live-seconds}")
    private int mdmMasterCacheTtl;
    
    @Value("${spring.hazelcast.config.map.mdmMasterCache.eviction.size}")
    private int mdmMasterCacheEvictionSize;
    
    @Value("${spring.hazelcast.config.map.mdmMasterCache.eviction.max-size-policy}")
    private String mdmMasterCacheMaxSizePolicy;
    
    @Value("${spring.hazelcast.config.map.mdmMasterCache.eviction.eviction-policy}")
    private String mdmMasterCacheEvictionPolicy;
    

    public String getInstanceName() {
        return instanceName;
    }

    public boolean isMulticastEnabled() {
		return multicastEnabled;
	}

	public void setMulticastEnabled(boolean multicastEnabled) {
		this.multicastEnabled = multicastEnabled;
	}

	public boolean isTcpIpEnabled() {
		return tcpIpEnabled;
	}

	public void setTcpIpEnabled(boolean tcpIpEnabled) {
		this.tcpIpEnabled = tcpIpEnabled;
	}


	public String getTcpIpMembers() {
		return tcpIpMembers;
	}


	public void setTcpIpMembers(String tcpIpMembers) {
		this.tcpIpMembers = tcpIpMembers;
	}


	public int getDevicesCacheTtl() {
        return devicesCacheTtl;
    }

    public int getDevicesCacheEvictionSize() {
        return devicesCacheEvictionSize;
    }

    public String getDevicesCacheMaxSizePolicy() {
        return devicesCacheMaxSizePolicy;
    }

    public String getDevicesCacheEvictionPolicy() {
        return devicesCacheEvictionPolicy;
    }

	public int getCommandCacheTtl() {
		return commandCacheTtl;
	}

	public void setCommandCacheTtl(int commandCacheTtl) {
		this.commandCacheTtl = commandCacheTtl;
	}

	public int getCommandCacheEvictionSize() {
		return commandCacheEvictionSize;
	}

	public void setCommandCacheEvictionSize(int commandCacheEvictionSize) {
		this.commandCacheEvictionSize = commandCacheEvictionSize;
	}

	public String getCommandCacheMaxSizePolicy() {
		return commandCacheMaxSizePolicy;
	}

	public void setCommandCacheMaxSizePolicy(String commandCacheMaxSizePolicy) {
		this.commandCacheMaxSizePolicy = commandCacheMaxSizePolicy;
	}

	public String getCommandCacheEvictionPolicy() {
		return commandCacheEvictionPolicy;
	}

	public void setCommandCacheEvictionPolicy(String commandCacheEvictionPolicy) {
		this.commandCacheEvictionPolicy = commandCacheEvictionPolicy;
	}

	public int getResDataCacheTtl() {
		return resDataCacheTtl;
	}

	public void setResDataCacheTtl(int resDataCacheTtl) {
		this.resDataCacheTtl = resDataCacheTtl;
	}

	public int getResDataCacheEvictionSize() {
		return resDataCacheEvictionSize;
	}

	public void setResDataCacheEvictionSize(int resDataCacheEvictionSize) {
		this.resDataCacheEvictionSize = resDataCacheEvictionSize;
	}

	public String getResDataCacheMaxSizePolicy() {
		return resDataCacheMaxSizePolicy;
	}

	public void setResDataCacheMaxSizePolicy(String resDataCacheMaxSizePolicy) {
		this.resDataCacheMaxSizePolicy = resDataCacheMaxSizePolicy;
	}

	public String getResDataCacheEvictionPolicy() {
		return resDataCacheEvictionPolicy;
	}

	public void setResDataCacheEvictionPolicy(String resDataCacheEvictionPolicy) {
		this.resDataCacheEvictionPolicy = resDataCacheEvictionPolicy;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public void setDevicesCacheTtl(int devicesCacheTtl) {
		this.devicesCacheTtl = devicesCacheTtl;
	}

	public void setDevicesCacheEvictionSize(int devicesCacheEvictionSize) {
		this.devicesCacheEvictionSize = devicesCacheEvictionSize;
	}

	public void setDevicesCacheMaxSizePolicy(String devicesCacheMaxSizePolicy) {
		this.devicesCacheMaxSizePolicy = devicesCacheMaxSizePolicy;
	}

	public void setDevicesCacheEvictionPolicy(String devicesCacheEvictionPolicy) {
		this.devicesCacheEvictionPolicy = devicesCacheEvictionPolicy;
	}

	public int getManufacturerCacheTtl() {
		return manufacturerCacheTtl;
	}

	public void setManufacturerCacheTtl(int manufacturerCacheTtl) {
		this.manufacturerCacheTtl = manufacturerCacheTtl;
	}

	public int getManufacturerCacheEvictionSize() {
		return manufacturerCacheEvictionSize;
	}

	public void setManufacturerCacheEvictionSize(int manufacturerCacheEvictionSize) {
		this.manufacturerCacheEvictionSize = manufacturerCacheEvictionSize;
	}

	public String getManufacturerCacheMaxSizePolicy() {
		return manufacturerCacheMaxSizePolicy;
	}

	public void setManufacturerCacheMaxSizePolicy(String manufacturerCacheMaxSizePolicy) {
		this.manufacturerCacheMaxSizePolicy = manufacturerCacheMaxSizePolicy;
	}

	public String getManufacturerCacheEvictionPolicy() {
		return manufacturerCacheEvictionPolicy;
	}

	public void setManufacturerCacheEvictionPolicy(String manufacturerCacheEvictionPolicy) {
		this.manufacturerCacheEvictionPolicy = manufacturerCacheEvictionPolicy;
	}

	public int getMdmMasterCacheTtl() {
		return mdmMasterCacheTtl;
	}

	public void setMdmMasterCacheTtl(int mdmMasterCacheTtl) {
		this.mdmMasterCacheTtl = mdmMasterCacheTtl;
	}

	public int getMdmMasterCacheEvictionSize() {
		return mdmMasterCacheEvictionSize;
	}

	public void setMdmMasterCacheEvictionSize(int mdmMasterCacheEvictionSize) {
		this.mdmMasterCacheEvictionSize = mdmMasterCacheEvictionSize;
	}

	public String getMdmMasterCacheMaxSizePolicy() {
		return mdmMasterCacheMaxSizePolicy;
	}

	public void setMdmMasterCacheMaxSizePolicy(String mdmMasterCacheMaxSizePolicy) {
		this.mdmMasterCacheMaxSizePolicy = mdmMasterCacheMaxSizePolicy;
	}

	public String getMdmMasterCacheEvictionPolicy() {
		return mdmMasterCacheEvictionPolicy;
	}

	public void setMdmMasterCacheEvictionPolicy(String mdmMasterCacheEvictionPolicy) {
		this.mdmMasterCacheEvictionPolicy = mdmMasterCacheEvictionPolicy;
	}

}
