package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.Feeders;

public interface FeedersRepository extends CassandraRepository<Feeders, String> {
	
	static final String FIND_BY_SUBSTATION = "SELECT * FROM feeders where substation_name = :substation_name allow filtering";

	@Query(value = FIND_BY_SUBSTATION)
	public Optional<List<Feeders>> findBySubstationName(@Param("substation_name") String substation_name);
	
	static final String FIND_BY_UTILITY = "SELECT * FROM feeders where owner_name = :owner_name allow filtering";

	@Query(value = FIND_BY_UTILITY)
	public Optional<List<Feeders>> findByUtility(@Param("owner_name") String owner_name);
	
}
