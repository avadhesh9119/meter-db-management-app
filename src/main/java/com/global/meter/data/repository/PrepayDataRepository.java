package com.global.meter.data.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.global.meter.business.model.PrepayData;

public interface PrepayDataRepository extends CassandraRepository<PrepayData, String> {

}
