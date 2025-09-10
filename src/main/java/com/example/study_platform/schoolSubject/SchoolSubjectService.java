package com.example.study_platform.schoolSubject;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolSubjectService {
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final SchoolSubjectMapper schoolSubjectMapper;

    @Transactional
    public SchoolSubject createSchoolSubject(String name) {
        if (!schoolSubjectRepository.existsByName(name)){
            throw new IllegalArgumentException("School Subject Already Exists");
        }
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(name);
        return schoolSubjectRepository.save(schoolSubject);
    }
    @Cacheable("schoolSubjects")
    public SchoolSubject getSchoolSubjectById(Long id) {
        return schoolSubjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("School Subject Not Found"));
    }

}
