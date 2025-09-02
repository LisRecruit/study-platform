package com.example.study_platform.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
    Optional<Teacher> findByTeacherName(String teacherName);
    boolean existsByTeacherName(String teacherName);
}
