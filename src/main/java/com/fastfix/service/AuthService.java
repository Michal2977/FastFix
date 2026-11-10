package com.fastfix.service;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;
import com.fastfix.request.ChangeAccountDataRequest;
import com.fastfix.request.CreateNewEmployeeRequest;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    public User findByEmail(String email);
    public boolean passwordMatches(String password,User user);
    UserDto findUserDtoByEmail(String email);
    UserDto changeAccountData(String email, ChangeAccountDataRequest request, String siteUrl,MultipartFile file) throws MessagingException, IOException;
    boolean emailVerification(String code);
    UserDto createNewEmployee(CreateNewEmployeeRequest request, MultipartFile file) throws IOException;
    boolean resendEmailActivation(String email,String siteUrl) throws MessagingException;
    boolean sendResetPasswordToken(String email,String siteUrl) throws MessagingException;
    User findByPasswordToken(String token);
    void resetPassword(String newPassword,User user);
    boolean verifyTurnstile(String token);
}
