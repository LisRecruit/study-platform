package com.example.study_platform.journal;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.homework.HomeWork;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.school.School;
import com.example.study_platform.schoolSubject.SchoolSubject;
import com.example.study_platform.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "journal_records")
public class JournalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journal_records_seq")
    @SequenceGenerator(name = "journal_records_seq", sequenceName = "seq_journal_record_id", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "school_subject_id", nullable = false)
    private SchoolSubject schoolSubject;
    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;
    private Integer mark;
    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "home_work_id")
    private HomeWork homeWork;


    @PrePersist
    @PreUpdate
    private void validateLessonOrHomeWork() {
        if ((lesson == null && homeWork == null) || (lesson != null && homeWork != null)) {
            throw new IllegalStateException("Either 'lesson' or 'homeWork' must be set, but not both at the same time.");
        }
    }


}
