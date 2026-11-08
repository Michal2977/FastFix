package com.fastfix.service;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;
import com.fastfix.request.ChangeAccountDataRequest;
import jakarta.mail.MessagingException;

public interface AuthService {
    public User findByEmail(String email);
    public boolean passwordMatches(String password,User user);
    UserDto findUserDtoByEmail(String email);
    UserDto changeAccountData(String email, ChangeAccountDataRequest request, String siteUrl) throws MessagingException;
    boolean emailVerification(String code);
}
