package com.example.study_platform.auth.user.dto.request;

public record UserCreateRequest(String email, String username, String password, String code) {
}
