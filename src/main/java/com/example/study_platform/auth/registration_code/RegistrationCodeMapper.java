package com.example.study_platform.auth.registration_code;

import com.example.study_platform.auth.role.RoleMapper;
import com.example.study_platform.auth.registration_code.dto.RegistrationCodeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface RegistrationCodeMapper {
    RegistrationCodeResponse toResponse (RegistrationCode registrationCode);
    RegistrationCode responseToEntity (RegistrationCodeResponse response);
}
