package com.example.study_platform.teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    default Long teacherToTeacherId(Teacher teacher) {return teacher != null ? teacher.getId() : null;}
}
