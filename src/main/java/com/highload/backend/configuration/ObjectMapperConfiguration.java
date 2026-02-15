package com.highload.backend.configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static java.time.format.DateTimeFormatter.ofPattern;

@Configuration
public class ObjectMapperConfiguration {

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd";

    @Bean
    @Primary
    public ObjectMapper customObjectMapper() {
        return new ObjectMapper()
            .disable(ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            .disable(FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(getJavaTimeModule());
    }

    private JavaTimeModule getJavaTimeModule() {
        var javaTimeModule = new JavaTimeModule();

        var localDateSerializer = new JsonSerializer<LocalDate>() {
            @Override
            public void serialize(
                LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
            ) throws IOException {
                jsonGenerator.writeString(localDate.format(ofPattern(BIRTHDAY_DATE_FORMAT)));
            }
        };

        var localDateDeserializer = new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
                return LocalDate.parse(jsonParser.readValueAs(String.class), ofPattern(BIRTHDAY_DATE_FORMAT));
            }
        };

        var localDateTimeSerializer = new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(
                LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
            ) throws IOException {
                jsonGenerator.writeString(localDateTime.format(ofPattern(LOCAL_DATE_TIME_FORMAT)));
            }
        };

        var localDateTimeDeserializer = new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
                return LocalDateTime.parse(jsonParser.readValueAs(String.class), ofPattern(LOCAL_DATE_TIME_FORMAT)
                );
            }
        };

        var offsetDateTimeSerializer = new JsonSerializer<OffsetDateTime>() {
            @Override
            public void serialize(
                OffsetDateTime offsetDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider
            ) throws IOException {
                jsonGenerator.writeString(offsetDateTime.format(ofPattern(ISO_DATE_FORMAT)));
            }
        };

        var offsetDateTimeDeserializer = new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException {
                return OffsetDateTime.parse(jsonParser.readValueAs(String.class), ofPattern(ISO_DATE_FORMAT));
            }
        };

        javaTimeModule.addSerializer(LocalDate.class, localDateSerializer);
        javaTimeModule.addDeserializer(LocalDate.class, localDateDeserializer);
        javaTimeModule.addSerializer(LocalDateTime.class, localDateTimeSerializer);
        javaTimeModule.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        javaTimeModule.addSerializer(OffsetDateTime.class, offsetDateTimeSerializer);
        javaTimeModule.addDeserializer(OffsetDateTime.class, offsetDateTimeDeserializer);

        return javaTimeModule;
    }
}
