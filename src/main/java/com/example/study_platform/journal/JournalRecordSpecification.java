package com.example.study_platform.journal;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.student.Student;
import org.springframework.data.jpa.domain.Specification;

public class JournalRecordSpecification {
    public static Specification<JournalRecord> hasGrade(Grade grade) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("grade"), grade);
    }

    public static Specification<JournalRecord> hasLesson(Lesson lesson) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("lesson"), lesson);
    }

    public static Specification<JournalRecord> hasStudent(Student student) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("student"), student);
    }

}
