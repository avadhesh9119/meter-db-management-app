
package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DevicesConfigBatchLogs;

public interface DevicesConfigBatchLogsRepository extends CassandraRepository<DevicesConfigBatchLogs, String> {

	public Optional<DevicesConfigBatchLogs> findById(String batchId);
	
}