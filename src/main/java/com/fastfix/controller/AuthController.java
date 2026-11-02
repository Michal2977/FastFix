package com.fastfix.controller;

import com.fastfix.entity.User;
import com.fastfix.request.LoginRequest;
import com.fastfix.response.ApiResponse;
import com.fastfix.response.JWTResponse;
import com.fastfix.security.JWTService;
import com.fastfix.service.Impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final JWTService jwtService;

    public AuthController(AuthServiceImpl authService, JWTService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPage(@Valid @RequestBody LoginRequest loginRequest){
        User user = authService.findByEmail(loginRequest.getEmail());

        if (user == null || !authService.passwordMatches(loginRequest.getPassword(),user)){
            return ResponseEntity.badRequest().body(new ApiResponse("Incorrect Data"));
        }

        String token = jwtService.generateToken(user.getEmail());
        ResponseCookie cookie = ResponseCookie.from("token", token).httpOnly(true).secure(false)
                .sameSite("LAX").path("/").maxAge(Duration.ofHours(24)).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).build();
    }
}
