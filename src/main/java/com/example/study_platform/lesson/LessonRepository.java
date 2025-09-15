package com.example.study_platform.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findById(Long id);
   boolean existsByGradeIdAndLessonDateAndLessonStartTime(Long gradeId, LocalDate lessonDate,
                                                                    LocalTime lessonStartTime);
    @Query("SELECT l FROM Lesson l WHERE l.teacher.school.id = :schoolId")
    List<Lesson> findAllBySchoolId(@Param("schoolId") Long schoolId);

}
