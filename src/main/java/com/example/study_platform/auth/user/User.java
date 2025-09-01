package com.example.study_platform.auth.user;

import com.example.study_platform.auth.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "user_seq", sequenceName = "seq_users_id", allocationSize = 1)
    private Long id;
    @Column(name = "name", unique = true)
    private String userName;
    @JsonIgnore
    @Column(nullable = false, length = 60) //BCrypt generated length
    private String password;

    @ManyToMany(fetch = FetchType.LAZY) //to prevent load all roles each time
    @Builder.Default
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude //security
    @EqualsAndHashCode.Exclude //security
    @JsonManagedReference
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }
}