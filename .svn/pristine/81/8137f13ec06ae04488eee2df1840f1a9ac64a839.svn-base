package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DevicesConfig;

public interface DevicesConfigRepository extends CassandraRepository<DevicesConfig, String> {

	public Optional<DevicesConfig> findById(String deviceNo);
	
}
