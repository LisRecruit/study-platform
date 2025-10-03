package com.example.study_platform.lesson;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.grade.GradeService;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.JournalRecordService;
import com.example.study_platform.lesson.dto.CreateLessonRequest;
import com.example.study_platform.lesson.dto.EditLessonRequest;
import com.example.study_platform.lesson.dto.LessonResponse;
import com.example.study_platform.school.School;
import com.example.study_platform.school.settings.SchoolSettings;
import com.example.study_platform.teacher.Teacher;
import com.example.study_platform.teacher.TeacherService;
import com.example.study_platform.util.Validator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final TeacherService teacherService;
    private final GradeService gradeService;
    private final JournalRecordService journalRecordService;

    @Cacheable("lessons")
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lesson Not Found"));
    }

    @Transactional
    public ResponseEntity<?> addLesson(CreateLessonRequest request) {


        Teacher teacher = teacherService.getTeacherById(request.teacherId());
        if(teacher == null) {
            throw new IllegalArgumentException("Teacher Not Found");
        }
        Grade grade = gradeService.getGradeById(request.gradeId());
        if(grade == null) {
            throw new IllegalArgumentException("Grade Not Found");
        }
        SchoolSettings schoolSettings = teacher.getSchool().getSchoolSettings();

        Lesson lesson = Lesson.builder()
                .lessonDate(request.date())
                .lessonStartTime(request.lessonStartTime())
                .lessonEndTime(schoolSettings.calculateLessonEndTime(request.lessonStartTime()))
                .lessonTopic(request.lessonTopic())
                .teacher(teacher)
                .grade(grade)
                .schoolSubject(teacher.getSchoolSubject())
                .build();

        if(isLessonExistsForGradeAndDateTime(lesson)){
            return ResponseEntity.badRequest().body("This grade already has a lesson at this date and time");
        }

        Lesson savedLesson = lessonRepository.save(lesson);

        List<JournalRecord> journalRecords = grade.getStudents().stream()
                .map(student -> JournalRecord.builder()
                        .date(savedLesson.getLessonDate())
                        .lesson(savedLesson)
                        .student(student)
                        .grade(grade)
                        .school(teacher.getSchool())
                        .schoolSubject(teacher.getSchoolSubject())
                        .mark(null)
                        .build())
                .toList();

        journalRecordService.saveAllJournalRecords(journalRecords);
        savedLesson.setJournalRecords(journalRecords);
        journalRecords.forEach(journalRecord -> journalRecord.getStudent().getJournalRecords().add(journalRecord));
        LessonResponse response = lessonMapper.toLessonResponse(savedLesson);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @Transactional
    public ResponseEntity<?> editLesson (EditLessonRequest request) {

        if (Validator.isDatePast(request.date())){
            return ResponseEntity.badRequest().body("You can't edit lesson in the past");
        }
        Optional<Lesson> lesson = lessonRepository.findById(request.lessonId());
        if(lesson.isEmpty()) {
            throw new EntityNotFoundException("Lesson Not Found");
        }
        Teacher teacher = teacherService.getTeacherById(request.teacherId());
        if(teacher == null) {
            throw new IllegalArgumentException("Teacher Not Found");
        }
        Grade grade = gradeService.getGradeById(request.gradeId());
        if(grade == null) {
            throw new IllegalArgumentException("Grade Not Found");
        }
        School school = teacher.getSchool();
        if (school == null) {
            throw new IllegalArgumentException("School Not Found for Teacher");
        }
        Teacher currentTeacher = teacherService.getCurrentTeacher();
        Lesson currentLesson = lesson.get();
        if (!currentLesson.getTeacher().getId().equals(currentTeacher.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You can only edit lessons you created");
        }
        SchoolSettings schoolSettings = school.getSchoolSettings();

        Lesson lessonEntity = lesson.get();
        lessonEntity.setLessonDate(request.date());
        lessonEntity.setLessonStartTime(request.lessonStartTime());
        lessonEntity.setLessonEndTime(schoolSettings.calculateLessonEndTime(request.lessonStartTime()));
        lessonEntity.setLessonTopic(request.lessonTopic());
        lessonEntity.setTeacher(teacher);
        lessonEntity.setGrade(grade);
        lessonEntity.setSchoolSubject(teacher.getSchoolSubject());
        if(isLessonExistsForGradeAndDateTime(lessonEntity)){
            return ResponseEntity.badRequest().body("This grade already has a lesson at this date and time");
        }
        LessonResponse response = lessonMapper.toLessonResponse(lessonRepository.save(lessonEntity));
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.badRequest().build();
    }

    @Transactional
    public ResponseEntity<?> deleteLessonById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        if(lesson.isEmpty()) {
            throw new EntityNotFoundException("Lesson Not Found");
        }
        Lesson currentLesson = lesson.get();
        Teacher teacher = teacherService.getTeacherById(currentLesson.getTeacher().getId());
        if (!currentLesson.getTeacher().getId().equals(teacher.getId())) {
            throw new IllegalArgumentException("You can only delete lessons you created");
        }
        lessonRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Cacheable("lessons")
    public List<LessonResponse> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        return lessons.stream()
                .map(lessonMapper::toLessonResponse)
                .toList();
    }

    public boolean isLessonExistsForGradeAndDateTime (Lesson lesson) {
        return lessonRepository.existsByGradeIdAndLessonDateAndLessonStartTime(lesson.getGrade().getId(),
                lesson.getLessonDate(), lesson.getLessonStartTime());
    }

    @Cacheable("lessons")
    public List<LessonResponse> getLessonsBySchool(Long schoolId) {
        List<Lesson> lessons = lessonRepository.findAllBySchoolId(schoolId);
        return lessons.stream()
                .map(lessonMapper::toLessonResponse)
                .toList();
    }


}
