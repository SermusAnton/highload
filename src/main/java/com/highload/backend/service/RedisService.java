package com.highload.backend.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisScript<List<String>> feedPostsScript;
    private final RedisScript<Boolean> deletePostsScript;

    public RedisService(RedisTemplate<String, Object> redisTemplate, RedisScript<List<String>> feedPostsScript, RedisScript<Boolean> deletePostsScript) {
        this.redisTemplate = redisTemplate;
        this.feedPostsScript = feedPostsScript;
        this.deletePostsScript = deletePostsScript;
    }

    public void addValueToSetInHash(String redisKey, UUID hashKey, UUID newValue) {
        Set<UUID> uuidSet = (Set<UUID>) redisTemplate.opsForHash().get(redisKey, hashKey.toString());
        if (uuidSet == null) {
            uuidSet = new HashSet<>();
        }
        uuidSet.add(newValue);
        redisTemplate.opsForHash().put(redisKey, hashKey.toString(), uuidSet);
    }

    public void deleteValueFromSetInHash(String redisKey, UUID hashKey, UUID valueToRemove) {
        Set<UUID> uuidSet = (Set<UUID>) redisTemplate.opsForHash().get(redisKey, hashKey);
        if (uuidSet != null) {
            boolean removed = uuidSet.remove(valueToRemove);
            if (removed) {
                if (uuidSet.isEmpty()) {
                    redisTemplate.opsForHash().delete(redisKey, hashKey);
                } else {
                    redisTemplate.opsForHash().put(redisKey, hashKey, uuidSet);
                }
            }
        }
    }

    public <K, V> void putAllInHash(String redisKey, Map<K, V> hashMap) {
        redisTemplate.opsForHash().putAll(redisKey, hashMap);
    }

    public Set<UUID> getAllFromHash(String redisKey, UUID hashKey) {
        return (Set<UUID>) redisTemplate.opsForHash().get(redisKey, hashKey);
    }

    public List<String> feedPosts(String userId, Long offset, Long limit) {
        return redisTemplate.execute(feedPostsScript, Collections.emptyList(), userId, offset, limit);
    }

    public void saveInList(String key, String jsonString) {
        try {
            redisTemplate.opsForList().rightPush(key, jsonString);
            redisTemplate.opsForList().trim(key, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePostById(String key, String targetId, String newJsonString) {
        // Получаем список объектов
        List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
        if (list == null) return;

        for (int i = 0; i < list.size(); i++) {
            String currentJson = (String) list.get(i); // Принудительное приведение к String

            if (currentJson != null && currentJson.contains(targetId)) {
                // Заменяем по индексу
                redisTemplate.opsForList().set(key, i, newJsonString);
                return; // Выходим после первого совпадения
            }
        }
    }

    public Boolean deletePosts(String key,  String postId, String authorUserId) {
        return redisTemplate.execute(deletePostsScript, List.of(key), postId, authorUserId);
    }
}
