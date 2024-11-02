package com.global.meter.data.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.EventDataThreePhase;

public interface EventDataThreePhaseRepository extends CassandraRepository<EventDataThreePhase, String> {

	public Optional<EventDataThreePhase> findById(String deviceNo);
	
	public static final String IS_EXITS = "SELECT * FROM event_data_threephase"
			+ " where device_serial_number= :device_serial_number and event_datetime = :event_datetime and event_code = :event_code ;";

	@Query(value = IS_EXITS)
	public Optional<EventDataThreePhase> getData(@Param("device_serial_number") String device_serial_number, 
			 @Param("event_code") Integer event_code, @Param("event_datetime") Date event_datetime);
	
}
