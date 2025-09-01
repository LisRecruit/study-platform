package com.example.study_platform.auth.user;

import com.example.study_platform.auth.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
    List<User> findByRoles(Role role);
    Page<User> findAll(Pageable pageable);
}
