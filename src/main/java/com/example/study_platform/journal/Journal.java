package com.example.study_platform.journal;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.school.School;
import com.example.study_platform.schoolSubject.SchoolSubject;
import com.example.study_platform.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "journal")
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journal_seq")
    @SequenceGenerator(name = "journal_seq", sequenceName = "seq_journal_id", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "school_subject_id", nullable = false)
    private SchoolSubject schoolSubject;
    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;
    private int mark;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;



}
