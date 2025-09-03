package com.example.study_platform.student;

import com.example.study_platform.auth.user.User;
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
    @SequenceGenerator(name = "students_seq", sequenceName = "students_seq_id", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @Builder.Default
    private List<Teacher> teachers = new ArrayList<>();
//    @OneToOne
//    @JoinColumn(name = "user_id", unique = true)
//    private User user;
}
