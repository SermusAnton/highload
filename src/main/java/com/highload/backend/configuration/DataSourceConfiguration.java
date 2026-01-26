package com.highload.backend.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DBProperty writeDataSourceProperty() {
        return new DBProperty();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.replica")
    public DBProperty readDataSourceProperty() {
        return new DBProperty();
    }

    @Bean
    public DataSource writeDataSource(DBProperty writeDataSourceProperty) {
        return createDataSource("WriteHikariPool", writeDataSourceProperty);
    }

    @Bean
    public DataSource readDataSource(DBProperty readDataSourceProperty) {
        return createDataSource("ReadHikariPool", readDataSourceProperty);
    }

    private DataSource createDataSource(String name,
                                        DBProperty dataSourceProperty) {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSourceProperty.getUrl());
        config.setSchema(dataSourceProperty.getSchema());
        config.setUsername(dataSourceProperty.getUsername());
        config.setPassword(dataSourceProperty.getPassword());
        config.setDriverClassName(dataSourceProperty.getDriverClassName());
        config.setPoolName(name);

        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    public DataSource routingDataSource(@Qualifier("writeDataSource") DataSource writeDataSource,
                                        @Qualifier("readDataSource") DataSource readDataSource) {

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("WRITE", writeDataSource);
        dataSources.put("READ", readDataSource);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Bean
    public DSLContext getDSLContext(DataSource routingDataSource) {
        return DSL.using(new DefaultConfiguration()
            .set(routingDataSource)
            .set(SQLDialect.POSTGRES));
    }
}
