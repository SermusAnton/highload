package com.highload.backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
public class RedisConfiguration {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration("redis", 6379);

        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
            .connectTimeout(Duration.ofMillis(30000)) // Время на установку связи
            .readTimeout(Duration.ofMillis(60000))    // Время ожидания выполнения скрипта
            .usePooling()                           // Включаем пул соединений
            .build();

        return new JedisConnectionFactory(redisConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(ObjectMapper customObjectMapper) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        // Устанавливаем String-сериализатор для КЛЮЧЕЙ
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Для значений можно оставить JSON или тоже String
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(customObjectMapper));

        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(customObjectMapper));
        return template;
    }

    @Bean
    public RedisScript<List<String>> feedPostsScript() {
        DefaultRedisScript<List<String>> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("scripts/feed_posts.lua"));
        redisScript.setResultType((Class<List<String>>) (Class<?>) List.class); // Specify the return type
        return redisScript;
    }

    @Bean
    public RedisScript<Boolean> deletePostsScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("scripts/delete_post.lua"));
        redisScript.setResultType(Boolean.class); // Specify the return type
        return redisScript;
    }
}
