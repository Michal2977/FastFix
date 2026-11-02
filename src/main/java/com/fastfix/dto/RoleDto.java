package com.fastfix.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private String role;

    public RoleDto() {
    }

    public RoleDto(String role) {
        this.role = role;
    }
}
