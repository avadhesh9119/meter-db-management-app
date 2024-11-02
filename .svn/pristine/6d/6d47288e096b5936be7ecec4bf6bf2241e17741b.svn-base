package com.global.meter.v3.inventive.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.v3.inventive.business.model.MeterResponseRawData;

public interface MeterResponseRawDataRepository extends CassandraRepository<MeterResponseRawData, String> {

	public static final String GET_PROFILE_DATA_LOGS = "SELECT * FROM raw_data"
			+ " where device_serial_number= :device_serial_number and tracking_id = :tracking_id ;";

	@Query(value = GET_PROFILE_DATA_LOGS)
	public Optional<MeterResponseRawData> getProfileDataLog(@Param("tracking_id") String string, 
			@Param("device_serial_number") String device_serial_number);

}
