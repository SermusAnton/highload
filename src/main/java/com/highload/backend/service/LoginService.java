package com.highload.backend.service;

import com.highload.backend.dao.UserRepository;
import com.highload.backend.model.LoginBody;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final JwtCreate jwtCreate;

    @Autowired
    public LoginService(PasswordEncoder encoder, UserRepository userRepository, JwtCreate jwtCreate) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.jwtCreate = jwtCreate;
    }

    public String login(LoginBody body) {
        var hashPassword = userRepository.getHashPassword(body.getId());
        if (encoder.matches(body.getPassword(), hashPassword)) {
            return jwtCreate.newJwt(body.getId().toString());
        }

        return Strings.EMPTY;
    }
}
