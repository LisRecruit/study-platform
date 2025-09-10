package com.example.study_platform.student;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    @Transactional
    public Student saveStudent(String name) {
        Student student = new Student();
        student.setName(name);
        return studentRepository.save(student);
    }
    @Cacheable("students")
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found"));
    }
}
