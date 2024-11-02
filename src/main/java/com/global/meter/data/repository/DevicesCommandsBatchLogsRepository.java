
package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DevicesCommandsBatchLogs;

public interface DevicesCommandsBatchLogsRepository extends CassandraRepository<DevicesCommandsBatchLogs, String> {

	public Optional<DevicesCommandsBatchLogs> findById(String batchId);
	
}