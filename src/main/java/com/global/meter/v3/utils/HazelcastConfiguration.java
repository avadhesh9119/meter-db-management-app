package com.global.meter.v3.utils;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfiguration {
	
	@Autowired
	HazelcastProperties hazelcastProperties;
	
   @Bean
   public HazelcastInstance hazelcastInstance() {
      Config config = new Config();
      config.setInstanceName(hazelcastProperties.getInstanceName());
      
      // Configure network settings
      NetworkConfig networkConfig = config.getNetworkConfig();

      JoinConfig joinConfig = networkConfig.getJoin();
      joinConfig.getMulticastConfig().setEnabled(false); // Disable multicast to avoid automatic cluster merging
      joinConfig.getTcpIpConfig().setEnabled(true)
                .setMembers(Arrays.asList(hazelcastProperties.getTcpIpMembers().split(","))); // Set specific cluster members
      
      MapStoreConfig mapStoreConfig = new MapStoreConfig()
            .setEnabled(true)
            .setImplementation(HazelcastMapStore.class);

      MapConfig deviceCache = new MapConfig("devicesCache")
            .setTimeToLiveSeconds(hazelcastProperties.getDevicesCacheTtl())
            .setEvictionConfig(new EvictionConfig()
                .setSize(hazelcastProperties.getDevicesCacheEvictionSize())
                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                .setEvictionPolicy(EvictionPolicy.LRU))
            .setMapStoreConfig(mapStoreConfig);
      
      MapConfig commandCache = new MapConfig("commandCache")
    		  .setTimeToLiveSeconds(hazelcastProperties.getCommandCacheTtl())
    		  .setEvictionConfig(new EvictionConfig()
    				  .setSize(hazelcastProperties.getCommandCacheEvictionSize())
    				  .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
    				  .setEvictionPolicy(EvictionPolicy.LRU))
    		  .setMapStoreConfig(mapStoreConfig);
      
      MapConfig resDatacache = new MapConfig("resDataCache")
    		  .setTimeToLiveSeconds(hazelcastProperties.getResDataCacheTtl())
    		  .setEvictionConfig(new EvictionConfig()
    				  .setSize(hazelcastProperties.getResDataCacheEvictionSize())
    				  .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
    				  .setEvictionPolicy(EvictionPolicy.LRU))
    		  .setMapStoreConfig(mapStoreConfig);

      MapConfig manufacturerCache = new MapConfig("manufacturerCache")
    		  .setTimeToLiveSeconds(hazelcastProperties.getResDataCacheTtl())
    		  .setEvictionConfig(new EvictionConfig()
    				  .setSize(hazelcastProperties.getResDataCacheEvictionSize())
    				  .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
    				  .setEvictionPolicy(EvictionPolicy.LRU))
    		  .setMapStoreConfig(mapStoreConfig);
      
      MapConfig mdmMasterCache = new MapConfig("mdmMasterCache")
    		  .setTimeToLiveSeconds(hazelcastProperties.getResDataCacheTtl())
    		  .setEvictionConfig(new EvictionConfig()
    				  .setSize(hazelcastProperties.getResDataCacheEvictionSize())
    				  .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
    				  .setEvictionPolicy(EvictionPolicy.LRU))
    		  .setMapStoreConfig(mapStoreConfig);
      
      config.addMapConfig(deviceCache);
      config.addMapConfig(commandCache);
      config.addMapConfig(resDatacache);
      config.addMapConfig(manufacturerCache);
      config.addMapConfig(mdmMasterCache);
      
      return Hazelcast.newHazelcastInstance(config);
   }
}
