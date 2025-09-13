package com.example.study_platform.school.settings;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolSettingsService {
    private final SchoolSettingsRepository schoolSettingsRepository;

    @Cacheable("schoolSettings")
    public SchoolSettings getSchoolSettings() {
        return schoolSettingsRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("School Settings Not Found"));
    }


}
