package com.example.study_platform.dashboards.teacher;

import com.example.study_platform.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/teacher/dashboard")
public class TeacherDashboardController {

    private final LessonService lessonService;



}
