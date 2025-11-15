package com.example.study_platform.auth;

import com.example.study_platform.auth.registration_code.RegistrationCode;
import com.example.study_platform.auth.registration_code.RegistrationCodeMapper;
import com.example.study_platform.auth.registration_code.RegistrationCodeService;
import com.example.study_platform.auth.registration_code.dto.CodeRequest;
import com.example.study_platform.auth.user.UserMapper;
import com.example.study_platform.auth.user.dto.request.UserCreateRequest;
import com.example.study_platform.auth.user.dto.response.RegistrationResponse;
import com.example.study_platform.auth.role.RoleService;
import com.example.study_platform.auth.security.JwtUtil;
import com.example.study_platform.auth.user.User;
import com.example.study_platform.auth.user.UserService;
import com.example.study_platform.auth.user.dto.request.UserLoginRequest;
import com.example.study_platform.auth.user.dto.response.LoginResponse;
import com.example.study_platform.student.StudentService;
import com.example.study_platform.teacher.TeacherService;
import com.example.study_platform.util.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final RegistrationCodeService registrationCodeService;
    private final RegistrationCodeMapper registrationCodeMapper;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody UserLoginRequest request){
        try {
            User user = userService.getUserByEmail(request.email());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            if(user == null){
                throw new RuntimeException("User not found");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
            Long id = user.getId();
            String token = jwtUtil.generateToken(userDetails, id);
            LoginResponse response = new LoginResponse(token, "login successful");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException | UsernameNotFoundException | EntityNotFoundException e) {
            throw new RuntimeException("Invalid username or password");
        } catch (Exception e) {
            throw new RuntimeException("Login failed");
        }
    }

    @PostMapping ("/reistration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RegistrationResponse> registration (@RequestBody UserCreateRequest request){
        if (!Validator.isEmailValid(request.email())) {
            throw new RuntimeException("Invalid email");
        }
        if (!Validator.isValidPassword(request.password())) {
            throw new RuntimeException("\"Password must contain at least 8 characters, including digits, \" +\n" +
                    "                    \"uppercase and lowercase letters.\"");
        }
        RegistrationCode registrationCode =  registrationCodeMapper
                .responseToEntity(registrationCodeService.getByCode(new CodeRequest(request.code())));
        User createdUser = userService.createUser(request, registrationCode);
        registrationCodeService.delete(registrationCode.getId());
        if (createdUser == null) {
            throw new RuntimeException("User already exists");
        }
        switch (createdUser.getRole().getName()) {
            case "ROLE_TEACHER" -> teacherService.saveTeacher(request.username());
            case "ROLE_STUDENT" -> studentService.saveStudent(request.username());
            default -> throw new IllegalStateException("Unexpected value: " + createdUser.getRole().getName());
        }
        RegistrationResponse response = new RegistrationResponse("User registered successfully");
        return ResponseEntity.ok(response);
    }
}
