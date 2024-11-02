package com.global.meter.data.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.UserMst;

public interface UserMstRepository extends CassandraRepository<UserMst, Integer> {
	
	static final String FIND_BY_USERID = "SELECT * FROM user_mst where user_id = :user_id allow filtering";

	@Query(value = FIND_BY_USERID)
	public Optional<UserMst> findByUserId(@Param("user_id") int user_id);
	
}
