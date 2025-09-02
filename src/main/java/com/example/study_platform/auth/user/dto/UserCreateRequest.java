package com.example.study_platform.auth.user.dto;

public record UserCreateRequest(String username, String password, Long roleId) {
}
