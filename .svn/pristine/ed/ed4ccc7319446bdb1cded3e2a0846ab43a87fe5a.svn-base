package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.ZoneMaster;

public interface ZoneMasterRepository extends CassandraRepository<ZoneMaster, Integer> {

	static final String FIND_BY_HES_ZONE_ID = "SELECT * FROM zone_master where hes_zone_id = :hes_zone_id allow filtering";

	@Query(value = FIND_BY_HES_ZONE_ID)
	public Optional<ZoneMaster> findByZoneId(@Param("hes_zone_id") String hes_zone_id);

	static final String FIND_BY_OWNER_NAME = "SELECT * FROM zone_master where owner_name = :owner_name allow filtering";

	@Query(value = FIND_BY_OWNER_NAME)
	public Optional<List<ZoneMaster>> findByOwnerName(@Param("owner_name") String owner_name);
	
	static final String FIND_BY_LEVEL_ID = "SELECT * FROM zone_master where level2_id = :level2_id allow filtering";

	@Query(value = FIND_BY_LEVEL_ID)
	public Optional<ZoneMaster> findByLevelId(@Param("level2_id") String level2_id);


}
