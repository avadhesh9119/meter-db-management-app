package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.SubdivisionsMaster;

public interface SubdivisionsMasterRepository extends CassandraRepository<SubdivisionsMaster, Integer> {
	
	static final String FIND_BY_HES_SUBDIVISIONS_ID = "SELECT * FROM subdivisions_master where hes_subdivision_id = :hes_subdivision_id allow filtering";

	@Query(value = FIND_BY_HES_SUBDIVISIONS_ID)
	public Optional<SubdivisionsMaster> findBySubdivisionId(@Param("hes_subdivision_id") String hes_subdivision_id);
	
	static final String FIND_BY_OWNER_NAME = "SELECT * FROM subdivisions_master where owner_name = :owner_name allow filtering";

	@Query(value = FIND_BY_OWNER_NAME)
	public Optional<List<SubdivisionsMaster>> findByOwnerName(@Param("owner_name") String owner_name);

	static final String FIND_BY_HES_SUBDIVISIONS_NAME = "SELECT * FROM subdivisions_master where hes_subdivision_name = :hes_subdivision_name allow filtering";

	@Query(value = FIND_BY_HES_SUBDIVISIONS_NAME)
	public Optional<SubdivisionsMaster> findBySubdivisionName(@Param("hes_subdivision_name") String hes_subdivision_name);

}
