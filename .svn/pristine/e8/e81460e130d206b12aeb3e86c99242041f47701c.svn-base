package com.global.meter.data.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DeltaLoadProfileSinglePhase;

public interface DeltaLoadProfileRepository extends CassandraRepository<DeltaLoadProfileSinglePhase, String> {

	public Optional<DeltaLoadProfileSinglePhase> findById(String deviceNo);
	
	public static final String IS_EXITS = "SELECT * FROM load_profile_data_singlephase"
			+ " where device_serial_number= :device_serial_number and interval_datetime = :interval_datetime ;";

	@Query(value = IS_EXITS)
	public Optional<DeltaLoadProfileSinglePhase> getData(@Param("device_serial_number") String device_serial_number, 
			@Param("interval_datetime") Date interval_datetime);
	
}
