package com.example.study_platform.auth.registration_code;

import lombok.Builder;
import lombok.Data;

import java.util.Random;


@Data
@Builder
public class CodeCreator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 16;

    public String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public String generateUniqueCode(RegistrationCodeRepository registrationCodeRepository) {
        String code;
        do {
            code = generateCode();
        } while (registrationCodeRepository.existsByCode(code));
        return code;
    }
}
