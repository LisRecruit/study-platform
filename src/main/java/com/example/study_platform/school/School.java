package com.example.study_platform.school;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.schoolSubject.SchoolSubject;
import com.example.study_platform.student.Student;
import com.example.study_platform.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schools_seq")
    @SequenceGenerator(name = "schools_seq", sequenceName = "schools_seq_id", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SchoolSubject> schoolSubjects;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Teacher> teachers;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Student> students;
    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Grade> grades;
}
