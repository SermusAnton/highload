package com.highload.backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    @Primary
    public ObjectMapper customObjectMapper() {
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDate.class,
            new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")));
        module.addDeserializer(LocalDate.class,
            new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        return mapper;
    }
}
