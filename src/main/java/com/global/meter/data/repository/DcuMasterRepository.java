package com.global.meter.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DcuMasterInfo;

public interface DcuMasterRepository extends CassandraRepository<DcuMasterInfo, String>{

}
