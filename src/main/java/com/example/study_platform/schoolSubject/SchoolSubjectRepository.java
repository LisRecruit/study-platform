package com.example.study_platform.schoolSubject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolSubjectRepository extends JpaRepository<SchoolSubject, Long> {
    boolean existsBySchoolSubjectName(String schoolSubjectName);
}
