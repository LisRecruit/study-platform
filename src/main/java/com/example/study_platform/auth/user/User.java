package com.example.study_platform.auth.user;

import com.example.study_platform.auth.role.Role;
import com.example.study_platform.school.settings.SchoolSettings;
import com.example.study_platform.student.Student;
import com.example.study_platform.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "seq_users_id", allocationSize = 1)
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @JsonIgnore
    @Column(nullable = false, length = 60) //BCrypt generated length
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    @OneToOne (mappedBy = "user")
    private Teacher teacher;
    @OneToOne (mappedBy = "user")
    private Student student;

    public boolean isTeacher() {
        return this.teacher != null;
    }
    public boolean isStudent() {
        return this.student != null;
    }
    public Object getUserType(){
        if(this.isTeacher()){
            return teacher;
        }
        if(this.isStudent()){
            return student;
        } else return null;
    }
}