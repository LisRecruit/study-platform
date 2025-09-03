package com.example.study_platform.auth.user.dto.response;

import java.util.List;

public record UserResponse(String userName,
                           List<String> roleNames) {
}
