package com.example.study_platform.schoolSubject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolSubjectService {
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final SchoolSubjectMapper schoolSubjectMapper;

    public SchoolSubject createSchoolSubject(String name) {
        if (!schoolSubjectRepository.existsBySchoolSubjectName(name)){
            throw new IllegalArgumentException("School Subject Already Exists");
        }
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(name);
        return schoolSubjectRepository.save(schoolSubject);
    }
}
