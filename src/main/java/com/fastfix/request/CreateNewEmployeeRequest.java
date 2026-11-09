package com.fastfix.request;

import com.fastfix.entity.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateNewEmployeeRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> role;

    public CreateNewEmployeeRequest() {
    }
}
