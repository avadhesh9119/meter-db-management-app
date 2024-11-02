package com.global.meter.data.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DeltaLoadProfileCT;

public interface DeltaLoadProfileCTRepository extends CassandraRepository<DeltaLoadProfileCT, String> {

	public Optional<DeltaLoadProfileCT> findById(String deviceNo);
	
	public static final String IS_EXITS = "SELECT * FROM load_profile_data_ct"
			+ " where device_serial_number= :device_serial_number and interval_datetime = :interval_datetime ;";

	@Query(value = IS_EXITS)
	public Optional<DeltaLoadProfileCT> getData(@Param("device_serial_number") String device_serial_number, 
			@Param("interval_datetime") Date interval_datetime);
	
}
