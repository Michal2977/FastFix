package com.fastfix.mapper;

import com.fastfix.dto.RoleDto;
import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto userDto(User user){
        Set<RoleDto> roleDto = user.getRole().stream().map(RoleMapper :: roleDto).collect(Collectors.toSet());

        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),roleDto);
    }
}
