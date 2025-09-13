package com.example.study_platform.lesson.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record LessonResponse(String teacherName, String subjectName, String gradeName,
                             LocalDate date, LocalTime lessonStartTime, String lessonTopic) {
}
