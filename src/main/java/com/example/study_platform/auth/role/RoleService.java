package com.example.study_platform.auth.role;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    public Role getRoleById (Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
    }
    public Role getRoleByName (String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException ("Role "+name+" notfound"));

    }
}
