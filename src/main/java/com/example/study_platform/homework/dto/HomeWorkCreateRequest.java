package com.example.study_platform.homework.dto;

import java.time.LocalDate;

public record HomeWorkCreateRequest(LocalDate startDate, LocalDate endDate, Long gradeId, String task, Long teacherId) {
}
