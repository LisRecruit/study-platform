package com.example.study_platform.auth.security;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretgeneration {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String base64Key = Base64.getEncoder().encodeToString(key);
        System.out.println("Generated JWT Secret: " + base64Key);
    }
}
