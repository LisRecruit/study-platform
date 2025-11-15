package com.example.study_platform.auth.registration_code.dto;

public record CreateRegistrationCodeRequest(
                                            String firstName,
                                            String lastName,
                                            String middleName,
                                            String roleName) {
}
