package com.example.study_platform.auth.role;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    default Long roleToRoleId(Role role) {
        return role != null ? role.getId() : null;
    }
}
