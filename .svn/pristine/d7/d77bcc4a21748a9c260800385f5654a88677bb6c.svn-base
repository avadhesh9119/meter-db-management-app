package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.UserHierMapping;

public interface UserHierMappingRepository extends CassandraRepository<UserHierMapping, Integer> {
	
	public static final String GET_USER = "SELECT * FROM user_hierarchy_mapping"
            + " where user_id = :user_id allow filtering;";

	@Query(value = GET_USER)
	public Optional<List<UserHierMapping>> getUserByUserId(@Param("user_id") int user_id);
	
	public static final String DELETE_USER = "delete FROM user_hierarchy_mapping"
            + " where user_id = :user_id";

	@Query(value = DELETE_USER)
	public  void deleteUserByUserId(@Param("user_id") int user_id);
}
