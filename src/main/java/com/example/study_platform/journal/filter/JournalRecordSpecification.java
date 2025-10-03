package com.example.study_platform.journal.filter;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.student.Student;
import com.example.study_platform.teacher.Teacher;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

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

    public static Specification<JournalRecord> hasTeacher(Teacher teacher) {
        return (root, query, criteriaBuilder) ->{
            var lessonJoin = root.join("lesson");
            return criteriaBuilder.equal(lessonJoin.get("teacher"), teacher);
        };
    }

    public static Specification<JournalRecord> dateBetween(LocalDate from, LocalDate to) {
        return (root, query, cb) -> cb.between(root.get("date"), from, to);
    }

}
