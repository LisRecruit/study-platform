package com.example.study_platform.journal;

import com.example.study_platform.grade.GradeService;
import com.example.study_platform.journal.dto.request.EvaluateStudentRequest;
import com.example.study_platform.journal.dto.request.JournalRecordRequest;
import com.example.study_platform.journal.dto.response.JournalRecordResponse;
import com.example.study_platform.lesson.LessonService;
import com.example.study_platform.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/journal")
public class JournalRecordController {
    private final JournalRecordService journalRecordService;
    private final JournalRecordMapper journalRecordMapper;
    private final GradeService gradeService;
    private final LessonService lessonService;
    private final StudentService studentService;

    @GetMapping("/grade/{id}")
    public ResponseEntity<List<JournalRecordResponse>> getJournalByGrade (@PathVariable Long id) {
        try {
            List<JournalRecordResponse> responses = journalRecordService
                    .getJournalRecords(gradeService.getGradeById(id));
            return ResponseEntity.ok(responses);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/lesson/{id}")
    public ResponseEntity<List<JournalRecordResponse>> getJournalByLesson (@PathVariable Long id) {
        try {
            List<JournalRecordResponse> responses = journalRecordService
                    .getJournalRecords(lessonService.getLessonById(id));
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
    @GetMapping("/student/{id}")
    public ResponseEntity<List<JournalRecordResponse>> getJournalByStudent (@PathVariable Long id) {
        try {
            List<JournalRecordResponse> responses = journalRecordService
                    .getJournalRecords(studentService.getStudentById(id));
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PatchMapping("/evaluate/")
    public ResponseEntity<JournalRecordResponse> evaluateJournal (@RequestBody EvaluateStudentRequest request) {
        try {
            JournalRecordResponse response = journalRecordService.evaluateStudent(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
