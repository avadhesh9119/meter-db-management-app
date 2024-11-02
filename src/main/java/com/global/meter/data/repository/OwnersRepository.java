package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.Owners;

public interface OwnersRepository extends CassandraRepository<Owners, Integer> {

	public Optional<Owners> findById(int iAmId);

	static final String FIND_BY_OWNER_ID = "SELECT * FROM utility_master where hes_owner_id = :hes_owner_id allow filtering";

	@Query(value = FIND_BY_OWNER_ID)
	public Optional<Owners> findByOwnerId(@Param("hes_owner_id") String hes_owner_id);

	static final String FIND_MAX_ID = "SELECT ISNULL(max(i_am_id), 0) + 1 as id FROM utility_master allow filtering";

	@Query(value = FIND_MAX_ID)
	public Object findMaxId();
	
	static final String FIND_BY_LEVEL_ID = "SELECT * FROM utility_master where level1_id = :level1_id allow filtering";

	@Query(value = FIND_BY_LEVEL_ID)
	public Optional<Owners> findByLevelId(@Param("level1_id") String level1_id);

	static final String FIND_BY_IAM_ID = "SELECT * FROM utility_master where i_am_id = :i_am_id allow filtering";

	@Query(value = FIND_BY_IAM_ID)
	public Optional<Owners> findByIAMId(@Param("i_am_id") String i_am_id);

}
