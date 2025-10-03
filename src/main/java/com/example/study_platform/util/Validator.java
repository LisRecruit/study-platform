package com.example.study_platform.util;

import com.example.study_platform.auth.user.CustomUserDetails;
import com.example.study_platform.school.School;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Pattern;


public class Validator {
    private Validator(){}
    public static boolean isEmailValid(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    public static boolean isValidPassword(String password) {
        final String PASSWORD_PATTERN =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
    public static boolean isDatePast(LocalDate date) {
        LocalDate now = LocalDate.now();
        return date.isAfter(now);
    }
    public static boolean isUserBelongToThisSchool(Long schoolId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        return currentUser.getSchoolId().equals(schoolId);
    }

}
