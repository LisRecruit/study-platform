package com.example.study_platform.journal;

import com.example.study_platform.journal.dto.response.JournalRecordResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalRecordMapper {
    @Mapping(target = "schoolSubjectName", source = "schoolSubject.name")
    @Mapping(target = "lessonDate", source = "lesson.lessonDate")
    @Mapping(target = "studentName", source = "student.name")
    @Mapping(target = "gradeName", source = "grade.name")
    @Mapping(target = "schoolName", source = "school.name")
    JournalRecordResponse toResponse(JournalRecord journalRecord);

}
