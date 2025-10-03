package com.example.study_platform.journal;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.grade.GradeService;
import com.example.study_platform.journal.dto.request.EvaluateStudentRequest;
import com.example.study_platform.journal.dto.request.JournalRecordRequest;
import com.example.study_platform.journal.dto.response.JournalRecordResponse;
import com.example.study_platform.journal.filter.JournalFilter;
import com.example.study_platform.lesson.Lesson;
import com.example.study_platform.lesson.LessonService;
import com.example.study_platform.school.School;
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
    public void saveAllJournalRecords(List<JournalRecord> journalRecords) {
        journalRecordRepository.saveAll(journalRecords);
    }

    @Transactional
    public List<JournalRecordResponse> getJournalRecords(JournalFilter filter) {
        List<JournalRecord> records = journalRecordRepository.findAll(filter.toSpecification());

        return records.stream()
                .map(journalRecordMapper::toResponse)
                .collect(Collectors.toList());

    }

    @Transactional
    public JournalRecordResponse evaluateStudent (EvaluateStudentRequest request) {
        Optional<JournalRecord> oldRecord = journalRecordRepository.findById(request.journalId());
        if (oldRecord.isEmpty()) {
            throw new IllegalArgumentException("Journal Record Not Found");
        }
        JournalRecord journalRecord = oldRecord.get();
        journalRecord.setMark(request.mark());
        return journalRecordMapper.toResponse(journalRecordRepository.save(journalRecord));
    }



}
