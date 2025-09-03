package com.example.study_platform.teacher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    @Transactional
    public Teacher saveTeacher(String name) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        return teacherRepository.save(teacher);
    }

}
