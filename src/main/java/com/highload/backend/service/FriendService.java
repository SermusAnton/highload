package com.highload.backend.service;

import com.highload.backend.dao.FriendRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public void add(UUID userId, UUID friendId) {
        friendRepository.add(userId, friendId);
    }

    public void delete(UUID userId, UUID friendId) {
        friendRepository.delete(userId, friendId);
    }
}
