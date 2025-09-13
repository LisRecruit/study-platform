package com.example.study_platform.lesson;

import com.example.study_platform.lesson.dto.CreateLessonRequest;
import com.example.study_platform.lesson.dto.LessonResponse;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/create")
    @RolesAllowed("ROLE_TEACHER")
    public ResponseEntity<?> create (@ModelAttribute CreateLessonRequest request){
        try {
            return lessonService.saveLesson(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
