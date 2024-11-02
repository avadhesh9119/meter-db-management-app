package com.global.meter.v3.utils;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HazelcastUtil {

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public HazelcastUtil(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public IMap<String, Object> devicesCache() {
        return hazelcastInstance.getMap("devicesCache");
    }

    public IMap<String, Object> commandCache() {
        return hazelcastInstance.getMap("commandCache");
    }

    public IMap<String, Object> resDataCache() {
        return hazelcastInstance.getMap("resDataCache");
    }
    
    public IMap<String, Object> manufacturerCache() {
    	return hazelcastInstance.getMap("manufacturerCache");
    }
    public IMap<String, Object> gatewayCache() {
    	return hazelcastInstance.getMap("mdmMasterCache");
    }
}
