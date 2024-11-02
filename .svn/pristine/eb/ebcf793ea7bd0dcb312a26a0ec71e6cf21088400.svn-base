package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DeviceConfigLogs;

public interface DeviceConfigLogsRepository extends CassandraRepository<DeviceConfigLogs, String> {
	
	public Optional<DeviceConfigLogs> findById(String id);
	
}
