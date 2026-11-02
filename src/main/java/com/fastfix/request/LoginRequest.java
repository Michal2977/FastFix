package com.fastfix.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "email is required")
    @Email(message = "incorrect email")
    String email;
    @NotBlank(message = "password is required")
    String password;

    public LoginRequest() {
    }
}
