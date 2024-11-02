package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.Subdivisions;

public interface SubdivisionsRepository extends CassandraRepository<Subdivisions, String> {
	static final String FIND_BY_UTILITY = "SELECT * FROM subdevisions where owner_name = :owner_name allow filtering";

	@Query(value = FIND_BY_UTILITY)
	public Optional<List<Subdivisions>> findByUtility(@Param("owner_name") String owner_name);
	
	static final String FIND_SUBDIVISION_NAMES = "SELECT subdevision_name FROM subdevisions allow filtering";

	@Query(value = FIND_SUBDIVISION_NAMES)
	public Optional<List<Subdivisions>> getSubdivisionName();
}
