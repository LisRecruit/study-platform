package com.example.study_platform.auth.registration_code.dto;

public record RegistrationCodeResponse(String code,
                                       String firstName,
                                       String lastName,
                                       String middleName,
                                       String roleName) {
}
