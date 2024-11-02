package com.global.meter.v3.inventive.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.v3.inventive.business.model.ManufacturerSpecificObis;

public interface ManufacturerSpecificObisRepository extends CassandraRepository<ManufacturerSpecificObis, String> {

	static final String FIND_BY_CODE = "SELECT * FROM manufacturer_specific_obis_code where manufacturer = :manufacturer and device_type = :device_type and profile_type = :profile_type and version = :version allow filtering";

	@Query(value = FIND_BY_CODE)
	public Optional<ManufacturerSpecificObis> findData(@Param("manufacturer") String manufacturer,
			@Param("device_type") String device_type, @Param("profile_type") String profile_type,
			@Param("version") String version);

	static final String FIND_INACTIVE_OBIS_CODE = "SELECT * FROM manufacturer_specific_obis_code where status = :Inactive allow filtering";

	@Query(value = FIND_INACTIVE_OBIS_CODE)
	public Optional<List<ManufacturerSpecificObis>> findInactiveObisCode();

}
