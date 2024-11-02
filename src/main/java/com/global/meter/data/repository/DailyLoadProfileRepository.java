package com.global.meter.data.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DailyLoadProfileSinglePhase;

public interface DailyLoadProfileRepository extends CassandraRepository<DailyLoadProfileSinglePhase, String> {

	public Optional<DailyLoadProfileSinglePhase> findById(String deviceNo);
	
	public static final String IS_EXITS = "SELECT * FROM daily_load_profile_singlephase"
			+ " where device_serial_number= :device_serial_number and datetime = :datetime ;";

	@Query(value = IS_EXITS)
	public Optional<DailyLoadProfileSinglePhase> getData(@Param("device_serial_number") String device_serial_number, 
			@Param("datetime") Date datetime);
	
}
