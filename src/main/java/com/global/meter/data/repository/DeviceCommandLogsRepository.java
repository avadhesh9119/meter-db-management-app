package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DeviceCommandLogs;

public interface DeviceCommandLogsRepository extends CassandraRepository<DeviceCommandLogs, String> {

	public static final String FIND_COMMAND_LOGS = "SELECT * FROM devices_commands_logs where tracking_id= :tracking_id allow filtering;";

	@Query(value = FIND_COMMAND_LOGS)
	public Optional<DeviceCommandLogs> findByTrackingId(@Param("tracking_id") String tracking_id);

	public Optional<DeviceCommandLogs> findById(String id);

}
