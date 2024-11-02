package com.global.meter.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.DcuPowerData;

public interface DcuPowerRepository extends CassandraRepository<DcuPowerData, String>{

}
