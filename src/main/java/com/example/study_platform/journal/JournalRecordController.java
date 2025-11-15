package com.example.study_platform.journal;

import com.example.study_platform.auth.security.JwtUtil;
import com.example.study_platform.auth.user.User;
import com.example.study_platform.auth.user.UserService;
import com.example.study_platform.journal.dto.request.EvaluateStudentRequest;
import com.example.study_platform.journal.dto.response.JournalRecordResponse;
import com.example.study_platform.journal.filter.JournalFilter;
import com.example.study_platform.journal.filter.filters.StudentFilter;
import com.example.study_platform.journal.filter.filters.TeacherFilter;
import com.example.study_platform.teacher.Teacher;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/journal")
public class JournalRecordController {
    private final JournalRecordService journalRecordService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/my")
    public ResponseEntity<List<JournalRecordResponse>> getUsersJournalRecords(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
        @RequestHeader("Authorization") String token
    ) {
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        String role = jwtUtil.extractRoles(token).getFirst();
        Long userId = jwtUtil.extractClaim(jwt, claims -> claims.get("userId", Long.class));
        User user = userService.getUserById(userId);
        JournalFilter filter;
        switch (role) {
            case "ROLE_STUDENT":
                filter = new StudentFilter(user.getStudent(), from, to);
                break;
            case "ROLE_TEACHER":
                filter = new TeacherFilter(user.getTeacher(), from, to);
                break;
            default:
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<JournalRecordResponse> responses = journalRecordService.getJournalRecords(filter);
        return ResponseEntity.ok(responses);

    }

    @PatchMapping("/evaluate/")
    @RolesAllowed("ROLE_TEACHER")
    public ResponseEntity<JournalRecordResponse> evaluateStudent(@RequestBody EvaluateStudentRequest request,
                                                                 @RequestHeader("Authorization") String token) {

        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
        Long userId = jwtUtil.extractClaim(jwt, claims -> claims.get("userId", Long.class));
        User user = userService.getUserById(userId);
        Teacher currentTeacher = user.getTeacher();
        JournalRecord jr = journalRecordService.getJournalRecordById(request.journalId());
        if (!currentTeacher.getId().equals(jr.getLesson().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            JournalRecordResponse response = journalRecordService.evaluateStudent(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
