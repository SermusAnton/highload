package com.highload.backend.service;

import com.highload.backend.dao.UserRepository;
import com.highload.backend.model.User;
import com.highload.backend.model.UserRegisterBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User getBy(UUID userId) {
        return userRepository.getBy(userId);
    }

    public UUID register(UserRegisterBody body) {
        var hash = encoder.encode(body.getPassword());
        body.setPassword("");
        return userRepository.add(body, hash);
    }

    public List<User> search(String firstName, String lastName) {
        return userRepository.find(firstName, lastName);
    }
}
