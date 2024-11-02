package com.global.meter.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import com.global.meter.business.model.DtTransMaster;

public interface DtTransMasterRepository extends CassandraRepository<DtTransMaster, Integer> {
	
	static final String FIND_BY_HES_DT_TRANS_ID = "select * From dt_trans_master where dt_trans_id = :dt_trans_id allow filtering";
	
	@Query(value = FIND_BY_HES_DT_TRANS_ID)
	public Optional<DtTransMaster> findByHesDtTransId(@Param("dt_trans_id") String dt_trans_id);

    static final String FIND_BY_HES_FEEDER_ID = "select * From dt_trans_master where hes_feeder_id = :hes_feeder_id allow filtering";
	
	@Query(value = FIND_BY_HES_FEEDER_ID)
	public Optional<List<DtTransMaster>> findByHesFeederId(@Param("hes_feeder_id") String hes_feeder_id);

	static final String FIND_BY_SUBSTATION = "SELECT * FROM dt_trans_master where hes_substation_id = :hes_substation_id allow filtering";

	@Query(value = FIND_BY_SUBSTATION)
	public Optional<List<DtTransMaster>> findByHesSubstationId(@Param("hes_substation_id") String hes_substation_id);

	static final String FIND_BY_SUBDIVISION = "SELECT * FROM dt_trans_master where hes_subdivision_id = :hes_subdivision_id allow filtering";

	@Query(value = FIND_BY_SUBDIVISION)
	public Optional<List<DtTransMaster>> findByHesSubdivisionId(@Param("hes_subdivision_id") String hes_subdivision_id);

    static final String FIND_BY_UTILITY = "SELECT * FROM dt_trans_master where owner_name = :owner_name allow filtering";
	
	@Query(value = FIND_BY_UTILITY)
	public Optional<List<DtTransMaster>> findByUtility(@Param("owner_name") String owner_name);
	
    static final String FIND_BY_HES_FEEDER_NAME = "select * From dt_trans_master where feeder_name = :feeder_name allow filtering";
	
	@Query(value = FIND_BY_HES_FEEDER_NAME)
	public Optional<List<DtTransMaster>> findByFeederName(@Param("feeder_name") String feeder_name);

}
