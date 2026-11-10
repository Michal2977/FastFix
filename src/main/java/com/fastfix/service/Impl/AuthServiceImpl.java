package com.fastfix.service.Impl;

import com.fastfix.dto.UserDto;
import com.fastfix.entity.Role;
import com.fastfix.entity.User;
import com.fastfix.exception.EmailUpdateException;
import com.fastfix.exception.InvalidFileException;
import com.fastfix.exception.UserUpdateConflictException;
import com.fastfix.mapper.UserMapper;
import com.fastfix.repository.RoleRepository;
import com.fastfix.repository.UserRepository;
import com.fastfix.request.ChangeAccountDataRequest;
import com.fastfix.request.CreateNewEmployeeRequest;
import com.fastfix.response.TurnStileResponse;
import com.fastfix.service.AuthService;
import jakarta.mail.MessagingException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${turnstile.verify-url}")
    private String turnstileUrl;

    @Value("${turnstile.secret-key}")
    private String turnstileSecret;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;
    private final RoleRepository roleRepository;
    private final RestTemplate restTemplate;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailServiceImpl emailService, RoleRepository roleRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.restTemplate = restTemplate;
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
    public boolean verifyTurnstile(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("secret",turnstileSecret);
        map.add("response",token);

        HttpEntity<MultiValueMap<String ,String>> request = new HttpEntity<>(map,headers);
        TurnStileResponse response = restTemplate.postForObject(turnstileUrl,request, TurnStileResponse.class);

        System.out.println("success" + response.isSuccess());
        System.out.println("hostName" + response.getHostName());
        System.out.println("challenge timeStamp" + response.getChallenge_ts());

        if (response.getError_codes() != null){
            for (String error : response.getError_codes()){
                System.out.println(error);
            }
        }
        return response.isSuccess();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','TECHNICIAN','RECEPTIONIST')")
    public UserDto changeAccountData(String email, ChangeAccountDataRequest request,
                                     String siteUrl,MultipartFile file) throws MessagingException, IOException {

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
        if (file != null && !file.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            if (fileName.contains("..")){
                throw new InvalidFileException("Invalid file name");
            }
            String contentType = file.getContentType();
            if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png")
            || contentType.equals("image/webp"))){
                throw new InvalidFileException("Only image files are allowed");
            }
            if (file.getSize() > 8 *1024 * 1024){
                throw new InvalidFileException("Maximum file size is 8 MB");
            }
            user.setPicture(file.getBytes());
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
    public UserDto createNewEmployee(CreateNewEmployeeRequest request, MultipartFile file) throws IOException {

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

        if (file != null &&  !file.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            if (fileName.contains("..")){
                throw new InvalidFileException("Invalid file name");
            }

            String contentType  = file.getContentType();
            if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") ||
                    contentType.equals("image/webp"))){
                throw new InvalidFileException("Only image files are allowed");
            }
            if (file.getSize() > 8 * 1024 * 1024){
                throw new InvalidFileException("Maximum file size is 8 MB");
            }
            user.setPicture(file.getBytes());
        }

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
