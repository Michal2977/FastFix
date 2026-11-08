package com.fastfix.controller;

import com.fastfix.Util.Utility;
import com.fastfix.entity.User;
import com.fastfix.exception.EmailUpdateException;
import com.fastfix.exception.UserUpdateConflictException;
import com.fastfix.request.ChangeAccountDataRequest;
import com.fastfix.request.LoginRequest;
import com.fastfix.response.ApiResponse;
import com.fastfix.response.JWTResponse;
import com.fastfix.security.JWTService;
import com.fastfix.service.Impl.AuthServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final JWTService jwtService;

    public AuthController(AuthServiceImpl authService, JWTService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Value("${app.frontend.url}")
    private String frontendUrl;




    @GetMapping("/csrf")
    public CsrfToken csrfToken(CsrfToken csrfToken){
        csrfToken.getToken();
        return csrfToken;
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from("token", "").httpOnly(true)
                .secure(false).path("/").sameSite("LAX").maxAge(0).build();

        response.addHeader(HttpHeaders.SET_COOKIE,cookie.toString());
        return ResponseEntity.ok().body(new ApiResponse("Logged out successfully"));
    }


    @GetMapping("/account-information")
    public ResponseEntity<?> accountInformation(Authentication authentication){
        return ResponseEntity.ok(authService.findUserDtoByEmail(authentication.getName()));
    }


    @PutMapping("/account-information/update")
    public ResponseEntity<?> updateUserAccountData(Authentication authentication, HttpServletRequest request,
                                                   @RequestBody ChangeAccountDataRequest changeRequest){

        User user = authService.findByEmail(authentication.getName());
        try {
            String siteUrl = Utility.servletRequest(request);
            authService.changeAccountData(user.getEmail(),changeRequest,siteUrl);
            return ResponseEntity.ok().body(new ApiResponse("Account Data Updated Correctly"));
        } catch (EmailUpdateException e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        } catch (UserUpdateConflictException e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponse("Something Went Wrong Try Again Later"));
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam("code")String code){
        boolean verify = authService.emailVerification(code);
        if (verify == false){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(frontendUrl + "/verify-failed"))
                    .build();
        }else {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(frontendUrl + "/login")).build();
        }
    }


}
