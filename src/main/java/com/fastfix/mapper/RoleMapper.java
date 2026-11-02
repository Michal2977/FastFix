package com.fastfix.mapper;

import com.fastfix.dto.RoleDto;
import com.fastfix.entity.Role;

public class RoleMapper {
    public static RoleDto roleDto(Role role){
        return new RoleDto(role.getRole());
    }
}
