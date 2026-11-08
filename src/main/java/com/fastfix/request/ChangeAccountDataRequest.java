package com.fastfix.request;

import lombok.Getter;

@Getter
public class ChangeAccountDataRequest {

    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public ChangeAccountDataRequest() {
    }
}
