package com.highload.backend.service;

import com.highload.backend.dao.UserRepository;
import com.highload.backend.model.UserRegisterBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID register(UserRegisterBody body) {
        return userRepository.add(body);
    }
}
