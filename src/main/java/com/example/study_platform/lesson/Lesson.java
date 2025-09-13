package com.example.study_platform.lesson;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.school.School;
import com.example.study_platform.schoolSubject.SchoolSubject;
import com.example.study_platform.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessons_seq")
    @SequenceGenerator(name = "lessons_seq", sequenceName = "lessons_seq_id", allocationSize = 1)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "school_subject_id", nullable = false)
    private SchoolSubject schoolSubject;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate lessonDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime lessonStartTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime lessonEndTime;

    private String lessonTopic;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalRecord> journalRecords;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private LocalTime get;
}
