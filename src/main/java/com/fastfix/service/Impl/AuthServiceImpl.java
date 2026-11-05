package com.fastfix.service.Impl;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;
import com.fastfix.mapper.UserMapper;
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


    @Override
    public UserDto findUserDtoByEmail(String email){
        User user = userRepository.findByEmail(email);
        return UserMapper.userDto(user);
    }
}
