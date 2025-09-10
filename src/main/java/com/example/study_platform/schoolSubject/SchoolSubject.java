package com.example.study_platform.schoolSubject;

import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.school.School;
import com.example.study_platform.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="school_subjects")
public class SchoolSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_subjects_seq")
    @SequenceGenerator(name = "school_subjects_seq", sequenceName = "school_subjects_seq_id", allocationSize = 1)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "schoolSubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
    @OneToMany(mappedBy = "SchoolSubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "schoolSubject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalRecord> journalRecords;

}
