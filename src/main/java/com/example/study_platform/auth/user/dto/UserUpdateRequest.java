package com.example.study_platform.auth.user.dto;

import java.util.List;

public record UserUpdateRequest(String username,
                                List<Long> roleIds) {
}
