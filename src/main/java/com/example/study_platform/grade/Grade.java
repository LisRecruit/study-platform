package com.example.study_platform.grade;


import com.example.study_platform.homework.HomeWork;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.school.School;
import com.example.study_platform.student.Student;
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
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grades_seq")
    @SequenceGenerator(name = "grades_seq", sequenceName = "seq_grades_id", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id")
    private School school;
    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();
}
