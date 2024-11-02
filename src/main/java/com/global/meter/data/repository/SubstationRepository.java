package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.Substations;

public interface SubstationRepository extends CassandraRepository<Substations, String> {

	static final String FIND_BY_SUBDIVISION = "SELECT * FROM substations where subdevision_name = :subdevision_name allow filtering";

	@Query(value = FIND_BY_SUBDIVISION)
	public Optional<List<Substations>> findBySubdivision(@Param("subdevision_name") String owner_name);

	static final String FIND_BY_UTILITY = "SELECT * FROM substations where owner_name = :owner_name allow filtering";
	
	@Query(value = FIND_BY_UTILITY)
	public Optional<List<Substations>> findByUtility(@Param("owner_name") String owner_name);

}
