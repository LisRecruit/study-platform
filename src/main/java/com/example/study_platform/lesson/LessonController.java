package com.example.study_platform.lesson;

import com.example.study_platform.lesson.dto.CreateLessonRequest;
import com.example.study_platform.lesson.dto.EditLessonRequest;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lesson")
public class LessonController {
    private final LessonService lessonService;

    @PostMapping("/create")
    @RolesAllowed("ROLE_TEACHER")
    public ResponseEntity<?> createLesson (@ModelAttribute CreateLessonRequest request){
        try {
            return lessonService.addLesson(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    @RolesAllowed("ROLE_TEACHER")
    public ResponseEntity<?> editLesson (@ModelAttribute EditLessonRequest request){
        try {
            return lessonService.editLesson(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_TEACHER")
    public ResponseEntity<?> deletLesson (@PathVariable Long id){
        try {
            return lessonService.deleteLessonById(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllLessons (){
        return ResponseEntity.ok(lessonService.getAllLessons());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getLesson (@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }


}
