package com.highload.backend.configuration;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TarantoolSchemaInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TarantoolSchemaInitializer.class);

    private final TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient;

    public TarantoolSchemaInitializer(TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient) {
        this.tarantoolClient = tarantoolClient;
    }

    @Override
    public void run(String... args) throws Exception {
        // Читаем файл из ресурсов
        String luaScript = new String(Files.readAllBytes(
                Paths.get(Objects.requireNonNull(getClass()
                                .getClassLoader()
                                .getResource("lua/schema.lua"))
                        .toURI())
        ));

        tarantoolClient.eval(luaScript, Collections.emptyList());
        logger.info("Tarantool schema updated!");
    }
}
