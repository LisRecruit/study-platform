package com.example.study_platform.school.settings;

import com.example.study_platform.school.School;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "school_settings")

public class SchoolSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_settings_seq")
    @SequenceGenerator(name = "school_settings_seq", sequenceName = "school_settings_seq_id", allocationSize = 1)
    private Long id;

    @OneToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime lessonDuration;

    public LocalTime calculateLessonEndTime (LocalTime startTime) {
        return startTime.plusHours(lessonDuration.getHour())
                .plusMinutes(lessonDuration.getMinute())
                .plusSeconds(lessonDuration.getSecond());
    }

}
