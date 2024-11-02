package com.global.meter.v3.inventive.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.v3.inventive.business.model.ManufacturerMaster;

public interface ManufacturerMasterRepository extends CassandraRepository<ManufacturerMaster, String> {

	static final String FIND_BY_CODE = "SELECT * FROM manufacturer_masters where code = :code allow filtering";

	@Query(value = FIND_BY_CODE)
	public Optional<ManufacturerMaster> findByCode(@Param("code") String code);

}
