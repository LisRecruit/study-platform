package com.example.study_platform.teacher;

import com.example.study_platform.auth.user.User;
import com.example.study_platform.school.School;
import com.example.study_platform.student.Student;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teachers_seq")
    @SequenceGenerator(name = "teachers_seq", sequenceName = "seq_teachers_id", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    @JoinTable(name = "teachers_students",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}
