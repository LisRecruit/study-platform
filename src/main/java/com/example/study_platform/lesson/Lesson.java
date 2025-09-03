package com.example.study_platform.lesson;

import com.example.study_platform.schoolSubject.SchoolSubject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessons_seq")
    @SequenceGenerator(name = "lessons_seq", sequenceName = "lessons_seq_id", allocationSize = 1)
    private Long id;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "school_subject_id", nullable = false)
    private SchoolSubject schoolSubject;

}
