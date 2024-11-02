package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DevicesInfoLogs;

public interface DevicesInfoLogsRepository extends CassandraRepository<DevicesInfoLogs, String> {

	public static final String FIND_ID = "SELECT * FROM devices_info_logs"
			+ " where tracking_id= :tracking_id allow filtering;";

	@Query(value = FIND_ID)
	public Optional<DevicesInfoLogs> findDeviceByTrackingId(@Param("tracking_id") String tracking_id);

}
