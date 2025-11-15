package com.example.study_platform.auth.registration_code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegistrationCodeRepository extends JpaRepository <RegistrationCode, Long> {
    Optional<RegistrationCode> findByCode (String code);
    Boolean existsByCode (String code);
}
