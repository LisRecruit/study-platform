package com.example.study_platform.lesson;

import com.example.study_platform.lesson.dto.LessonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonMapper INSTANCE = Mappers.getMapper(LessonMapper.class);

    @Mapping(source = "teacher.name", target = "teacherName")
    @Mapping(source = "schoolSubject.name", target = "subjectName")
    @Mapping(source = "grade.name", target = "gradeName")
    @Mapping(source = "lessonDate", target = "date")
    LessonResponse toLessonResponse(Lesson lesson);

}
