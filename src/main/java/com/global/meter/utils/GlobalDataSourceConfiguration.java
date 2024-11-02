package com.global.meter.utils;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.global.meter.v3.utils.PrestoConnectionPoolConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class GlobalDataSourceConfiguration {
	
	@Autowired
	PrestoConnectionPoolConfig prestoCPConfig;
	
	@Bean(name = "prestoDataSource")
    public DataSource prestoDataSource() {
	 HikariConfig config = new HikariConfig();
        config.setDriverClassName(prestoCPConfig.getDriverClassName());
        config.setJdbcUrl(prestoCPConfig.getDbUrl());
        config.setUsername(prestoCPConfig.getUsername());
        config.setMaximumPoolSize(prestoCPConfig.getMaxPoolSize());
        config.setMinimumIdle(prestoCPConfig.getMinIdle());
        config.setIdleTimeout(prestoCPConfig.getIdleTimeout());
        config.setMaxLifetime(prestoCPConfig.getMaxLifetime());
        config.setConnectionTimeout(prestoCPConfig.getConnectionTimeout());
        config.setPoolName("PrestoHikariCP");
        config.setLeakDetectionThreshold(prestoCPConfig.getLeakDetectionThreshold());
        return new HikariDataSource(config);
    }
	
	@Autowired
	@Qualifier("prestoDataSource") 
	DataSource dataSource ;
	
	@Bean(name = "prestoTemplate")
	public JdbcTemplate prestoJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
 
}