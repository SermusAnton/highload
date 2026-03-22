package com.highload.backend.configuration;

import io.tarantool.driver.api.TarantoolServerAddress;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import io.tarantool.driver.auth.TarantoolCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.config.AbstractTarantoolDataConfiguration;
import org.springframework.data.tarantool.repository.config.EnableTarantoolRepositories;

@Configuration
@EnableTarantoolRepositories(basePackages = "com.highload.backend.dao.tarantool")
public class TarantoolConfiguration extends AbstractTarantoolDataConfiguration {

    @Value("${tarantool.host}")
    private String host;

    @Value("${tarantool.port}")
    private int port;

    @Value("${tarantool.username}")
    private String username;

    @Value("${tarantool.password}")
    private String password;

    @Override
    protected TarantoolServerAddress tarantoolServerAddress() {
        return new TarantoolServerAddress(host, port);
    }

    @Override
    public TarantoolCredentials tarantoolCredentials() {
        return new SimpleTarantoolCredentials(username, password);
    }
}
