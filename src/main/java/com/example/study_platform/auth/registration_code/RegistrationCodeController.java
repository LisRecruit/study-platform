package com.example.study_platform.auth.registration_code;

import com.example.study_platform.auth.registration_code.dto.CreateRegistrationCodeRequest;

import com.example.study_platform.auth.registration_code.dto.RegistrationCodeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/registration-code")
@RequiredArgsConstructor
public class RegistrationCodeController {
    private final RegistrationCodeService registrationCodeService;

    @PostMapping("/new")
    public ResponseEntity<RegistrationCodeResponse> createRegistrationCode
            (@RequestBody CreateRegistrationCodeRequest request) {
            return ResponseEntity.ok(registrationCodeService.create(request));
    }
}
