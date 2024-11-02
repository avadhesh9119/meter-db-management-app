package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.SubstationsMaster;

public interface SubstationsMasterRepository extends CassandraRepository<SubstationsMaster, Integer> {
	
	static final String FIND_BY_SUBSTATION_ID = "SELECT * FROM substations_master where hes_substation_id = :hes_substation_id allow filtering";

	@Query(value = FIND_BY_SUBSTATION_ID)
	public Optional<SubstationsMaster> findByhesSubstationId(@Param("hes_substation_id") String hes_substation_id);
	
	static final String FIND_BY_SUBDIVISION = "SELECT * FROM substations_master where hes_subdivision_id = :hes_subdivision_id allow filtering";

	@Query(value = FIND_BY_SUBDIVISION)
	public Optional<List<SubstationsMaster>> findBySubdivision(@Param("hes_subdivision_id") String owner_name);

    static final String FIND_BY_UTILITY = "SELECT * FROM substations_master where hes_owner_id = :hes_owner_id allow filtering";
	
	@Query(value = FIND_BY_UTILITY)
	public Optional<List<SubstationsMaster>> findByUtility(@Param("hes_owner_id") String owner_name);

	static final String FIND_BY_SUBDIVISION_NAME = "SELECT * FROM substations_master where subdivision_name = :subdivision_name allow filtering";

	@Query(value = FIND_BY_SUBDIVISION_NAME)
	public Optional<List<SubstationsMaster>> findBySubdivisionName(@Param("subdivision_name") String subdivision_name);

	static final String FIND_BY_SUBDIVISION_ID = "SELECT * FROM substations_master where hes_subdivision_id = :hes_subdivision_id allow filtering";

	@Query(value = FIND_BY_SUBDIVISION_ID)
	public Optional<List<SubstationsMaster>> findBySubdivisionId(@Param("hes_subdivision_id") String hes_subdivision_id);

}
