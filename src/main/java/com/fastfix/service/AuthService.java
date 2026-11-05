package com.fastfix.service;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;

public interface AuthService {
    public User findByEmail(String email);
    public boolean passwordMatches(String password,User user);
    UserDto findUserDtoByEmail(String email);
}
