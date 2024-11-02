package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.global.meter.business.model.DeviceBatchLogs;

public interface DeviceBatchLogsRepository extends CassandraRepository<DeviceBatchLogs, String> {
	public static final String FIND_DEVICES = "SELECT batch_id FROM device_batch_logs;";

	@Query(value = FIND_DEVICES)

	public Optional<DeviceBatchLogs> findByBatchId(String id);

}
