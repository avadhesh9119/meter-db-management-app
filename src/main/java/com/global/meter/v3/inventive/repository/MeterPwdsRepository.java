package com.global.meter.v3.inventive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.MeterPwds;

public interface MeterPwdsRepository  extends CassandraRepository<MeterPwds, String>  {
	
	public static final String FIND_DEVICES = "SELECT manufacturer FROM meter_pwds;";
	public Optional<MeterPwds> findById(String manufacturer);
  
	public List<MeterPwds> findAll() ;
	
	public static final String IS_EXISTS = "SELECT * FROM meter_pwds where manufacturer = :manufacturer and"
			+ " device_type = :device_type allow filtering;";
	
	@Query(value = IS_EXISTS)
	public Optional<MeterPwds> getData(@Param("manufacturer") String manufacturer, 
			@Param("device_type") String device_type);
	
	
	public static final String IS_TRACKING_EXISTS = "SELECT * FROM meter_pwds where tracking_id = :tracking_id allow filtering;";
	
	@Query(value = IS_TRACKING_EXISTS)
	public Optional<MeterPwds> getData(@Param("tracking_id") String tracking_id);
	
	public static final String IS_EXIST = "SELECT * FROM meter_pwds where manufacturer = :manufacturer and"
			+ " device_type = :device_type and mode_of_comm = :mode_of_comm allow filtering;";
	
	@Query(value = IS_EXIST)
	public Optional<MeterPwds> getManufacturarData(@Param("manufacturer") String manufacturer, 
			@Param("device_type") String device_type, @Param("mode_of_comm") String mode_of_comm);

}
