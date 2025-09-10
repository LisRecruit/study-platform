package com.example.study_platform.journal.dto.request;

import lombok.Builder;

@Builder
public record JournalRecordRequest(Long schoolSubjectId,
                                   Long studentId,
                                   Long lessonId,
                                   Long gradeId,
                                   int mark,
                                   Long schoolId) {
}
