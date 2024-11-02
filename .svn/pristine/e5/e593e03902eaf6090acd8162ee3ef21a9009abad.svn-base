package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DivisionMaster;

public interface DivisionMasterRepository extends CassandraRepository<DivisionMaster, Integer> {

	static final String FIND_BY_DIVISION_ID = "SELECT * FROM division_master where hes_division_id = :hes_division_id allow filtering";

	@Query(value = FIND_BY_DIVISION_ID)
	public Optional<DivisionMaster> findByhesDivisionId(@Param("hes_division_id") String hes_division_id);

	static final String FIND_BY_CIRCLE = "SELECT * FROM division_master where hes_circle_id = :hes_circle_id allow filtering";

	@Query(value = FIND_BY_CIRCLE)
	public Optional<List<DivisionMaster>> findByHesCircleId(@Param("hes_circle_id") String owner_name);

	static final String FIND_BY_ZONE = "SELECT * FROM division_master where hes_zone_id = :hes_zone_id allow filtering";

	@Query(value = FIND_BY_ZONE)
	public Optional<List<DivisionMaster>> findByHesSubdivisionId(@Param("hes_zone_id") String owner_name);

	static final String FIND_BY_UTILITY = "SELECT * FROM division_master where owner_name = :owner_name allow filtering";

	@Query(value = FIND_BY_UTILITY)
	public Optional<List<DivisionMaster>> findByUtility(@Param("owner_name") String owner_name);
	
	static final String FIND_BY_LEVEL_ID = "SELECT * FROM division_master where level4_id = :level4_id allow filtering";

	@Query(value = FIND_BY_LEVEL_ID)
	public Optional<DivisionMaster> findByLevelId(@Param("level4_id") String level4_id);


}
