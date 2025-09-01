package com.example.study_platform.auth.user.dto;

import java.util.List;

public record UserResponse(String userName,
                           List<String> roleNames) {
}
