package com.example.study_platform.lesson;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.grade.GradeService;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.JournalRecordService;
import com.example.study_platform.lesson.dto.CreateLessonRequest;
import com.example.study_platform.lesson.dto.LessonResponse;
import com.example.study_platform.school.settings.SchoolSettingsService;
import com.example.study_platform.student.Student;
import com.example.study_platform.teacher.Teacher;
import com.example.study_platform.teacher.TeacherService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final TeacherService teacherService;
    private final SchoolSettingsService schoolSettingsService;
    private final GradeService gradeService;
    private final JournalRecordService journalRecordService;


    @Cacheable("lessons")
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lesson Not Found"));
    }

    @Transactional
    public ResponseEntity<?> saveLesson(CreateLessonRequest request) {


        Teacher teacher = teacherService.getTeacherById(request.teacherId());
        if(teacher == null) {
            throw new IllegalArgumentException("Teacher Not Found");
        }
        Grade grade = gradeService.getGradeById(request.gradeId());
        if(grade == null) {
            throw new IllegalArgumentException("Grade Not Found");
        }
        LocalTime lessonDuration = schoolSettingsService.getSchoolSettings().getLessonDuration();
        LocalTime lessonEndTime = request.lessonStartTime().plusHours(lessonDuration.getHour())
                .plusMinutes(lessonDuration.getMinute())
                .plusSeconds(lessonDuration.getSecond());

        Lesson lesson = Lesson.builder()
                .lessonDate(request.date())
                .lessonStartTime(request.lessonStartTime())
                .lessonEndTime(lessonEndTime)
                .lessonTopic(request.lessonTopic())
                .teacher(teacher)
                .grade(grade)
                .schoolSubject(teacher.getSchoolSubject())
                .build();

        List<JournalRecord> journalRecords = grade.getStudents().stream()
                .map(student -> JournalRecord.builder()
                        .lesson(lesson)
                        .student(student)
                        .grade(grade)
                        .school(teacher.getSchool())
                        .schoolSubject(teacher.getSchoolSubject())
                        .mark(null)
                        .build())
                .toList();
        journalRecordService.saveAllJournalRecords(journalRecords);
        lesson.setJournalRecords(journalRecords);

        journalRecords.forEach(record -> record.getStudent().getJournalRecords().add(record));
        LessonResponse response = lessonMapper.toLessonResponse(lessonRepository.save(lesson));
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

}
