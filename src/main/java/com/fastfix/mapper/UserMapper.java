package com.fastfix.mapper;

import com.fastfix.dto.RoleDto;
import com.fastfix.dto.UserDto;
import com.fastfix.entity.User;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto userDto(User user){

        String base64Picture = null;
        if (user.getPicture() != null){
            base64Picture  = Base64.getEncoder().encodeToString(user.getPicture());
        }
        Set<RoleDto> roleDto = user.getRole().stream().map(RoleMapper :: roleDto).collect(Collectors.toSet());

        return new UserDto(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),roleDto,base64Picture);
    }
}
