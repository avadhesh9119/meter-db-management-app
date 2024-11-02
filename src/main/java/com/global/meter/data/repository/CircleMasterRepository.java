package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.CircleMaster;

public interface CircleMasterRepository extends CassandraRepository<CircleMaster, Integer> {

	static final String FIND_BY_CIRCLE_ID = "SELECT * FROM circle_master where hes_circle_id = :hes_circle_id allow filtering";

	@Query(value = FIND_BY_CIRCLE_ID)
	public Optional<CircleMaster> findByhesCircleId(@Param("hes_circle_id") String hes_circle_id);

	static final String FIND_BY_ZONE = "SELECT * FROM zone_master where hes_zone_id = :hes_zone_id allow filtering";

	@Query(value = FIND_BY_ZONE)
	public Optional<List<CircleMaster>> findByZone(@Param("hes_zone_id") String owner_name);

	static final String FIND_BY_UTILITY = "SELECT * FROM circle_master where hes_owner_id = :hes_owner_id allow filtering";

	@Query(value = FIND_BY_UTILITY)
	public Optional<List<CircleMaster>> findByUtility(@Param("hes_owner_id") String owner_name);
	
	static final String FIND_BY_LEVEL_ID = "SELECT * FROM circle_master where level3_id = :level3_id allow filtering";

	@Query(value = FIND_BY_LEVEL_ID)
	public Optional<CircleMaster> findByLevelId(@Param("level3_id") String level3_id);


}
