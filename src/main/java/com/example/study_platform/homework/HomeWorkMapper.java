package com.example.study_platform.homework;

import com.example.study_platform.homework.dto.HomeWorkCreateRequest;
import com.example.study_platform.student.Student;
import com.example.study_platform.teacher.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface HomeWorkMapper {
    HomeWorkMapper INSTANCE = Mappers.getMapper(HomeWorkMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", source = "student")
    @Mapping(target = "teacher", source = "teacher")
    @Mapping(target = "task", source = "dto.task")
    @Mapping(target = "startDate", source = "dto.startDate")
    @Mapping(target = "endDate", source = "dto.endDate")
    HomeWork toEntity(HomeWorkCreateRequest dto, Student student, Teacher teacher);
}
