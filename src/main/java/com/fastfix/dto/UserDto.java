package com.fastfix.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleDto> roleDto;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email, Set<RoleDto> roleDto) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleDto = roleDto;
    }
}
