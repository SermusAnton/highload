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

    public void add(UUID userId, UUID friendID) {
        friendRepository.add(userId, friendID);
    }

    public void delete(UUID userId, UUID friendID) {
        friendRepository.delete(userId, friendID);
    }
}
