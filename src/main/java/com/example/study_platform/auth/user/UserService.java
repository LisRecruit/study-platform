package com.example.study_platform.auth.user;

import com.example.study_platform.auth.role.Role;
import com.example.study_platform.auth.role.RoleMapper;
import com.example.study_platform.auth.role.RoleRepository;
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
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Transactional
    public User createUser (UserCreateRequest request) {
        if (userRepository.existsByUserName(request.username())){
            throw new RuntimeException("Username already exists");
        }
        Role role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        User user = User.builder()
                .userName(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername (String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException ("User with "+username+" notfound"));
    }


    public Page<UserResponse> listAll (PageRequest pageRequest){
        return userRepository.findAll(pageRequest)
                .map(userMapper::userToUserResponse);

    }
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole (Role role){
        List<User> users = userRepository.findByRoles(role);
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

}
