package com.highload.backend.service;

import com.highload.backend.dao.FriendRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final RedisService redisService;

    public final String FRIENDS_DICTIONARY_NAME = "Friends";

    public FriendService(FriendRepository friendRepository,
        RedisService redisService) {
        this.friendRepository = friendRepository;
        this.redisService = redisService;
    }

    @PostConstruct
    public void init() {
        var friends = friendRepository.getAll();
        redisService.putAllInHash(FRIENDS_DICTIONARY_NAME, friends);
    }

    public void add(UUID userId, UUID friendId) {
        friendRepository.add(userId, friendId);
        redisService.addValueToSetInHash(FRIENDS_DICTIONARY_NAME, userId, friendId);
    }

    public void delete(UUID userId, UUID friendId) {
        friendRepository.delete(userId, friendId);
        redisService.deleteValueFromSetInHash(FRIENDS_DICTIONARY_NAME, userId, friendId);
    }

    public Set<UUID> getFriendsBy(UUID userId) {
        return redisService.getAllFromHash(FRIENDS_DICTIONARY_NAME, userId);
    }
}
