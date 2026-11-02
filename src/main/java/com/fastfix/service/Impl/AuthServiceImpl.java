package com.fastfix.service.Impl;

import com.fastfix.entity.User;
import com.fastfix.repository.UserRepository;
import com.fastfix.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean passwordMatches(String password,User user){
        return passwordEncoder.matches(password,user.getPassword());
    }
}
