package com.example.study_platform.homework;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.journal.JournalRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "home_works")
public class HomeWork {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "homeworks_seq")
    @SequenceGenerator(name = "homeworks_seq", sequenceName = "homeworks_seq_id", allocationSize = 1)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;
    private String task;
    private String studentTask;
    @OneToMany(mappedBy = "home_work", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalRecord> lessons = new ArrayList<>();

}
