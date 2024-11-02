package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.FirmwareConfig;

public interface FirmwareConfigRepository extends CassandraRepository<FirmwareConfig, String> {
	
	static final String FIND_BY_TRACKING_ID = "SELECT * FROM firmware_config where tracking_id = :tracking_id allow filtering"; 
	
	@Query(value = FIND_BY_TRACKING_ID)
	public Optional<FirmwareConfig> findByTrackingId(@Param("tracking_id") String tracking_id);

}
