package com.example.study_platform.lesson;

import com.example.study_platform.auth.user.CustomUserDetails;
import com.example.study_platform.lesson.dto.CreateLessonRequest;
import com.example.study_platform.lesson.dto.EditLessonRequest;
import com.example.study_platform.util.Validator;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<?> getAllLessonsBySchool (){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                Long schoolId = userDetails.getSchoolId();

                if (schoolId == null) {
                    return ResponseEntity.badRequest().body("School ID is not associated with the user.");
                }
                return ResponseEntity.ok(lessonService.getLessonsBySchool(schoolId));
            }

            return ResponseEntity.status(401).body("User is not authenticated.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLesson (@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        Long schoolId = lesson.getTeacher().getSchool().getId();
        if (Validator.isUserBelongToThisSchool(schoolId)){
            return ResponseEntity.ok(lessonService.getLessonById(id));
        } else {
            return ResponseEntity.badRequest().body("User is not authorized to access this resource.");
        }
    }


}
