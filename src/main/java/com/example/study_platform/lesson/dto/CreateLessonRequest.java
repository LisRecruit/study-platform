package com.example.study_platform.lesson.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateLessonRequest(Long subjectId,
                                  Long teacherId,
                                  LocalDate date,
                                  LocalTime lessonStartTime,
                                  String lessonTopic,
                                  Long gradeId) {



}
