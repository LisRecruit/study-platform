package com.example.study_platform.school;

import jakarta.persistence.*;
import lombok.*;

//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name = "schools")
public class School {
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schools_seq")
//    @SequenceGenerator(name = "schools_seq", sequenceName = "schools_seq_id", allocationSize = 1)
    private Long id;
    private String name;
}
