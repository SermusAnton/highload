package com.highload.backend.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        var dataSource = DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .url(writeDataSourceProperty.getUrl())
            .username(writeDataSourceProperty.getUsername())
            .password(writeDataSourceProperty.getPassword())
            .driverClassName(writeDataSourceProperty.getDriverClassName())
            .build();
        dataSource.setSchema(writeDataSourceProperty.getSchema());
        return dataSource;
    }

    @Bean
    public DataSource readDataSource(DBProperty readDataSourceProperty) {
        var dataSource = DataSourceBuilder.create()
            .type(HikariDataSource.class)
            .url(readDataSourceProperty.getUrl())
            .username(readDataSourceProperty.getUsername())
            .password(readDataSourceProperty.getPassword())
            .driverClassName(readDataSourceProperty.getDriverClassName())
            .build();
        dataSource.setSchema(readDataSourceProperty.getSchema());
        return dataSource;
    }

    @Bean
    @Primary
    public DataSource routingDataSource(
        @Qualifier("writeDataSource") DataSource writeDataSource,
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
