package com.example.study_platform.auth.user;

import com.example.study_platform.auth.registration_code.RegistrationCode;
import com.example.study_platform.auth.role.Role;
import com.example.study_platform.auth.role.RoleRepository;
import com.example.study_platform.auth.role.RoleService;
import com.example.study_platform.auth.user.dto.request.UserCreateRequest;
import com.example.study_platform.auth.user.dto.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    @Transactional
    public User createUser (UserCreateRequest request, RegistrationCode registrationCode) {
        if (userRepository.existsByEmail(request.email())){
            throw new RuntimeException("Username already exists");
        }
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(registrationCode.getRole())
                .firstName(registrationCode.getFirstName())
                .lastName(registrationCode.getLastName())
                .middleName(registrationCode.getMiddleName())
                .build();
        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User getUserByUsername (String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException ("User with "+username+" notfound"));
    }
    @Transactional
    public User getUserByEmail (String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException ("User with "+email+" notfound"));
    }


    public Page<UserResponse> listAll (PageRequest pageRequest){
        return userRepository.findAll(pageRequest)
                .map(userMapper::userToUserResponse);

    }
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole (Role role){
        List<User> users = userRepository.findByRole(role);
        return userMapper.usersToUserResponses(users);

    }
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        return userRepository.findById(userId)  .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
    @Transactional
    public void deleteUserById(Long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public Long resolveSchoolId(User user) {
        if (user.isTeacher() && user.getTeacher() != null) {
            return user.getTeacher().getSchool() != null ? user.getTeacher().getSchool().getId() : null;
        }
        if (user.isStudent() && user.getStudent() != null) {
            return user.getStudent().getSchool() != null ? user.getStudent().getSchool().getId() : null;
        }


        return null;
    }


}
