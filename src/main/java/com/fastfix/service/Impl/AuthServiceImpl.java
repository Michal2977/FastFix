package com.fastfix.service.Impl;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;
import com.fastfix.exception.EmailUpdateException;
import com.fastfix.exception.UserUpdateConflictException;
import com.fastfix.mapper.UserMapper;
import com.fastfix.repository.UserRepository;
import com.fastfix.request.ChangeAccountDataRequest;
import com.fastfix.service.AuthService;
import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailServiceImpl emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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


    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','TECHNICIAN','RECEPTIONIST')")
    public UserDto changeAccountData(String email, ChangeAccountDataRequest request,String siteUrl) throws MessagingException {

        User user = userRepository.findByEmail(email);

        if (request.getFirstName() != null && !request.getFirstName().isBlank()){
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()){
            user.setLastName(request.getLastName());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()
         && !user.getEmail().equalsIgnoreCase(request.getEmail())){
          String code = RandomString.make(65);
          user.setVerificationCode(code);
          user.setAlternativeEmail(request.getEmail());
          user.setExpiresAt(LocalDateTime.now().plusHours(24));
          userRepository.save(user);

          emailService.updateUserAccountDataEmail(user,siteUrl);
          throw new EmailUpdateException("We've sent a verification email to your email address");
        }

        if (request.getPassword() != null && !request.getPassword().isBlank() &&
        !passwordEncoder.matches(request.getPassword(),user.getPassword())){

         user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);
        return UserMapper.userDto(user);
    }

    @Override
    public boolean emailVerification(String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user == null || user.getExpiresAt().isBefore(LocalDateTime.now())){
            return false;
        }else {
            user.setExpiresAt(null);
            user.setVerificationCode(null);
            user.setEmail(user.getAlternativeEmail());
            user.setAlternativeEmail(null);
            userRepository.save(user);
            return true;

        }
    }
}
