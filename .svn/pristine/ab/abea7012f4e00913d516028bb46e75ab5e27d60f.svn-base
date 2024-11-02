package com.global.meter.v3.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.ProtocolVersion;

@Configuration
@EnableCassandraRepositories
public class CassandraConnectionPoolConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspaceName;

    @Value("${spring.data.cassandra.contact-points}")
    private String contactPoints;

    @Value("${spring.data.cassandra.port}")
    private int port;

    @Value("${spring.data.cassandra.username}")
    private String username;

    @Value("${spring.data.cassandra.password}")
    private String password;

    @Value("${spring.data.cassandra.protocol-version}")
    private String protocolVersion;

    @Value("${spring.data.cassandra.pool.max-size}")
    private int maxSize;

    @Value("${spring.data.cassandra.pool.idle-timeout}")
    private int idleTimeout;

    @Value("${spring.data.cassandra.pool.heartbeat.interval}")
    private int heartbeatInterval;

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(contactPoints);
        cluster.setPort(port);
        cluster.setPoolingOptions(poolingOptions());

        // Set protocol version if specified
        if (protocolVersion != null) {
            cluster.setProtocolVersion(ProtocolVersion.V4);
        }

//         Set authentication credentials
        cluster.setAuthProvider(authProvider());

        return cluster;
    }

    @Bean
    public PoolingOptions poolingOptions() {
        PoolingOptions poolingOptions = new PoolingOptions();

        // Configure the connection pool settings
        poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, maxSize);
        poolingOptions.setIdleTimeoutSeconds(idleTimeout / 1000);
        poolingOptions.setHeartbeatIntervalSeconds(heartbeatInterval / 1000);

        // You can adjust other settings as needed
        poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 2);
        poolingOptions.setCoreConnectionsPerHost(HostDistance.REMOTE, 1);

        return poolingOptions;
    }

    @Bean
    public AuthProvider authProvider() {
        return new PlainTextAuthProvider(username, password);
    }
}
