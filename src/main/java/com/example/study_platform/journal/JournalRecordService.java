package com.example.study_platform.journal;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.grade.GradeService;
import com.example.study_platform.journal.dto.request.EvaluateStudentRequest;
import com.example.study_platform.journal.dto.request.JournalRecordRequest;
import com.example.study_platform.journal.dto.response.JournalRecordResponse;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.lesson.LessonService;
import com.example.study_platform.school.School;
import com.example.study_platform.school.SchoolRepository;
import com.example.study_platform.school.SchoolService;
import com.example.study_platform.schoolSubject.SchoolSubject;
import com.example.study_platform.schoolSubject.SchoolSubjectService;
import com.example.study_platform.student.Student;
import com.example.study_platform.student.StudentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalRecordService {
    private final JournalRecordRepository journalRecordRepository;
    private final JournalRecordMapper journalRecordMapper;
    private final StudentService studentService;
    private final LessonService lessonService;
    private final GradeService gradeService;
    private final SchoolService schoolService;
    private final SchoolSubjectService schoolSubjectService;
    @Cacheable
    public JournalRecord getJournalRecordById (Long id) {
        return journalRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Journal Record Not Found"));
    }
    @Transactional
    public JournalRecordResponse addJournalRecord(JournalRecordRequest journalRecordRequest) {
        SchoolSubject schoolSubject = schoolSubjectService.getSchoolSubjectById(journalRecordRequest.schoolSubjectId());
        Lesson lesson = lessonService.getLessonById(journalRecordRequest.lessonId());
        Student student = studentService.getStudentById(journalRecordRequest.studentId());
        Grade grade = gradeService.getGradeById(journalRecordRequest.gradeId());
        School school = schoolService.getSchoolById(journalRecordRequest.schoolId());
        JournalRecord journalRecord = JournalRecord.builder()
                .schoolSubject(schoolSubject)
                .lesson(lesson)
                .student(student)
                .grade(grade)
                .mark(journalRecordRequest.mark())
                .school(school)
                .build();
        JournalRecord savedRecord = journalRecordRepository.save(journalRecord);
        return journalRecordMapper.toResponse(savedRecord);
    }
    @Transactional
    public List<JournalRecord> saveAllJournalRecords(List<JournalRecord> journalRecords) {
        return journalRecordRepository.saveAll(journalRecords);
    }

    @Transactional
    public List<JournalRecordResponse> getJournalRecords(Object filter) {
        List<JournalRecord> records;

        if (filter instanceof Grade grade) {
            records = journalRecordRepository.findAll(JournalRecordSpecification.hasGrade(grade));
        } else if (filter instanceof Lesson lesson) {
            records = journalRecordRepository.findAll(JournalRecordSpecification.hasLesson(lesson));
        } else if (filter instanceof Student student) {
            records = journalRecordRepository.findAll(JournalRecordSpecification.hasStudent(student));
        } else {
            throw new IllegalArgumentException("Unsupported filter type");
        }

        return records.stream()
                .map(journalRecordMapper::toResponse)
                .collect(Collectors.toList());

    }

    @Transactional
    public JournalRecordResponse evaluateStudent (EvaluateStudentRequest request) {
        Optional<JournalRecord> record = journalRecordRepository.findById(request.journalId());
        if (record.isEmpty()) {
            throw new IllegalArgumentException("Journal Record Not Found");
        }
        JournalRecord journalRecord = record.get();
        journalRecord.setMark(request.mark());
        return journalRecordMapper.toResponse(journalRecordRepository.save(journalRecord));
    }



}
