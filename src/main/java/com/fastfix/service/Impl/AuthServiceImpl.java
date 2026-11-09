package com.fastfix.service.Impl;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.Role;
import com.fastfix.entity.User;
import com.fastfix.exception.EmailUpdateException;
import com.fastfix.exception.UserUpdateConflictException;
import com.fastfix.mapper.UserMapper;
import com.fastfix.repository.RoleRepository;
import com.fastfix.repository.UserRepository;
import com.fastfix.request.ChangeAccountDataRequest;
import com.fastfix.request.CreateNewEmployeeRequest;
import com.fastfix.service.AuthService;
import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailServiceImpl emailService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
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

        if (passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new EmailUpdateException("The new password cannot be the same as the current password");
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()){
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

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public UserDto createNewEmployee(CreateNewEmployeeRequest request){

        User user = new User();
        Role role = roleRepository.findByRole(request.getRole().iterator().next());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.createRole(role);

        if (userRepository.existsByEmail(request.getEmail())){
        throw new UserUpdateConflictException("Email already exists");
        }
        user.setEmail(request.getEmail());
        user.setCreateAt(LocalDateTime.now());

        userRepository.save(user);
        return UserMapper.userDto(user);

    }

    @Override
    public boolean resendEmailActivation(String email,String siteUrl) throws MessagingException {

        User user = userRepository.findByAlternativeEmail(email);

        if (user == null || user.getAlternativeEmail() == null){
            throw new UserUpdateConflictException("Email address not found. Please check and try again.");
        }else {
            String verificationCode = RandomString.make(65);
            user.setVerificationCode(verificationCode);
            user.setExpiresAt(LocalDateTime.now().plusHours(24));
            userRepository.save(user);
            emailService.updateUserAccountDataEmail(user,siteUrl);
            return true;
        }
    }

    @Override
    public boolean sendResetPasswordToken(String email,String siteUrl) throws MessagingException {
        User user  = userRepository.findByEmail(email);
        if (user == null || user.getEmail().isBlank()){
            throw new UserUpdateConflictException("Email address not found. Please check and try again");
        }else {
            String randomCode = RandomString.make(45);
            user.setVerificationToken(randomCode);
            user.setExpiresAt(LocalDateTime.now().plusHours(24));
            userRepository.save(user);
            emailService.sendForgotPasswordEmail(user,siteUrl);
            return true;
        }
    }

    @Override
    public User findByPasswordToken(String token) {
        return userRepository.findByVerificationToken(token);
    }

    @Override
    public void resetPassword(String newPassword,User user){
        if (newPassword == null || newPassword.isBlank()){
            throw new UserUpdateConflictException("Password Is Reqiured");
        }
        user.setVerificationToken(null);
        user.setExpiresAt(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
