package com.example.study_platform.auth.role;

import com.example.study_platform.auth.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "role_seq", sequenceName = "seq_roles_id", allocationSize = 1)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<User> users = new ArrayList<>();
}
