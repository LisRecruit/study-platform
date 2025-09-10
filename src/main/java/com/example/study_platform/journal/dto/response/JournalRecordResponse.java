package com.example.study_platform.journal.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record JournalRecordResponse(Long id,
                                    String schoolSubjectName,
                                    LocalDate lessonDate,
                                    String studentName,
                                    String gradeName,
                                    int mark,
                                    String schoolName
) {
}
