package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.FeedersMaster;

public interface FeedersMasterRepository extends CassandraRepository<FeedersMaster, Integer> {
	
	static final String FIND_BY_FEEDER_ID = "SELECT * FROM feeders_master where hes_feeder_id = :hes_feeder_id allow filtering";

	@Query(value = FIND_BY_FEEDER_ID)
	public Optional<FeedersMaster> findByhesFeederId(@Param("hes_feeder_id") String hes_substation_id);
	
	static final String FIND_BY_SUBSTATION = "SELECT * FROM feeders_master where hes_substation_id = :hes_substation_id allow filtering";

	@Query(value = FIND_BY_SUBSTATION)
	public Optional<List<FeedersMaster>> findByHesSubstationId(@Param("hes_substation_id") String owner_name);

	static final String FIND_BY_SUBDIVISION = "SELECT * FROM feeders_master where hes_subdivision_id = :hes_subdivision_id allow filtering";

	@Query(value = FIND_BY_SUBDIVISION)
	public Optional<List<FeedersMaster>> findByHesSubdivisionId(@Param("hes_subdivision_id") String owner_name);

    static final String FIND_BY_UTILITY = "SELECT * FROM feeders_master where owner_name = :owner_name allow filtering";
	
	@Query(value = FIND_BY_UTILITY)
	public Optional<List<FeedersMaster>> findByUtility(@Param("owner_name") String owner_name);
	
	static final String FIND_BY_SUBSTATION_NAME = "SELECT * FROM feeders_master where substation_name = :substation_name allow filtering";

	@Query(value = FIND_BY_SUBSTATION_NAME)
	public Optional<List<FeedersMaster>> findBySubstationName(@Param("substation_name") String substation_name);
	
	static final String FIND_BY_SUBSTATION_ID = "SELECT * FROM feeders_master where hes_substation_id = :hes_substation_id allow filtering";

	@Query(value = FIND_BY_SUBSTATION_ID)
	public Optional<List<FeedersMaster>> findBySubstationId(@Param("hes_substation_id") String hes_substation_id);
	
}
