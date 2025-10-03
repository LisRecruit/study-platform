package com.example.study_platform.auth.user;

import com.example.study_platform.auth.role.Role;
import com.example.study_platform.auth.role.RoleMapper;
import com.example.study_platform.auth.user.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper (componentModel = "spring", uses = RoleMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping (target = "role", source = "role")

    UserResponse userToUserResponse(User user);

    List<UserResponse> usersToUserResponses(List<User> users);
    @Named("mapRoleToName")
    default String mapRoleToName(Role role) {
        return role.getName().toString();
    }

}
