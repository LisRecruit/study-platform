package com.example.study_platform.teacher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Cacheable("teachers")
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher Not Found"));
    }

    public Teacher getCurrentTeacher() {
        // Получаем имя текущего пользователя
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        // Ищем учителя по имени пользователя
        return teacherRepository.findByName(currentUsername)
                .orElseThrow(() -> new IllegalArgumentException("Teacher Not Found"));
    }


}
