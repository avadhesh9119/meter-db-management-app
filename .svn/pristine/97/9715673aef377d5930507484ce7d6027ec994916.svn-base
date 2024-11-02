package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DtTrans;

public interface DtTransRepository extends CassandraRepository<DtTrans,String>{
	
	static final String FIND_BY_FEEDER = "SELECT * FROM dt_trans where feeder_name = :feeder_name allow filtering";

	@Query(value = FIND_BY_FEEDER)
	public Optional<List<DtTrans>> findByFeederName(@Param("feeder_name") String feeder_name);
	
	static final String FIND_BY_UTILITY = "SELECT * FROM dt_trans where owner_name = :owner_name allow filtering";

	@Query(value = FIND_BY_UTILITY)
	public Optional<List<DtTrans>> findByUtility(@Param("owner_name") String owner_name);

}
