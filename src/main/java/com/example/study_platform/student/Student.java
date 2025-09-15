package com.example.study_platform.student;

import com.example.study_platform.auth.user.User;
import com.example.study_platform.grade.Grade;
import com.example.study_platform.homework.HomeWork;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.school.School;
import com.example.study_platform.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "students_seq")
    @SequenceGenerator(name = "students_seq", sequenceName = "seq_students_id", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @Builder.Default
    private List<Teacher> teachers = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    private Grade grade;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id")
    private School school;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalRecord> journalRecords = new ArrayList<>();
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HomeWork> homeWork = new ArrayList<>();

}
