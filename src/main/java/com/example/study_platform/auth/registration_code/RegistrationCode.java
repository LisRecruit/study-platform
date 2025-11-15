package com.example.study_platform.auth.registration_code;

import com.example.study_platform.auth.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "new_users")
public class RegistrationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "new_user_seq", sequenceName = "seq_new_users_id", allocationSize = 1)
    private Long id;
    @Column(name = "registration_code", nullable = false)
    private String code;

    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "date_of_creating")
    private ZonedDateTime dateOfCreation;

    @Column(name = "date_of_expiring")
    private ZonedDateTime dateOfExpiring;

}
