package com.example.study_platform.auth.registration_code;

import com.example.study_platform.auth.registration_code.dto.CodeRequest;
import com.example.study_platform.auth.registration_code.dto.RegistrationCodeResponse;
import com.example.study_platform.auth.role.Role;
import com.example.study_platform.auth.role.RoleService;
import com.example.study_platform.auth.registration_code.dto.CreateRegistrationCodeRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationCodeService {
    private final RegistrationCodeRepository registrationCodeRepository;
    private final CodeCreator codeCreator;
    private final RoleService roleService;
    private final RegistrationCodeMapper registrationCodeMapper;

    @Transactional
    public RegistrationCodeResponse create (CreateRegistrationCodeRequest request) {
        ZonedDateTime createdAt = ZonedDateTime.now();
        ZonedDateTime expiredAt = createdAt.plusDays(30L);
        String code = codeCreator.generateUniqueCode(registrationCodeRepository);
        Role role = roleService.getRoleByName(request.roleName());
        RegistrationCode newCode = RegistrationCode.builder()
                .code(code)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .middleName(request.middleName())
                .dateOfCreation(createdAt)
                .dateOfExpiring(expiredAt)
                .role(role)
                .build();
        return registrationCodeMapper.toResponse(registrationCodeRepository.save(newCode));
    }
    @Transactional
    public void delete (Long id) {
        registrationCodeRepository.deleteById(id);
    }

    public Boolean isCodeValid (RegistrationCode code) {
        return code.getDateOfExpiring().isAfter(ZonedDateTime.now());
    }
    @Transactional
    public RegistrationCodeResponse getByCode (CodeRequest request) {
            Optional<RegistrationCode> optionalRegistrationCode = registrationCodeRepository.findByCode(request.code());
            if (optionalRegistrationCode.isEmpty()){
                throw new EntityNotFoundException("Code not found");
            }
            RegistrationCode registrationCode = optionalRegistrationCode.get();
            if(!isCodeValid(registrationCode)) {
                registrationCodeRepository.deleteById(registrationCode.getId());
                throw new IllegalArgumentException("Code not valid");
            }
            return registrationCodeMapper.toResponse(registrationCode);
    }
}
