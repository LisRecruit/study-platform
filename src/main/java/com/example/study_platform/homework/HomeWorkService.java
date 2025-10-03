package com.example.study_platform.homework;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.grade.GradeService;
import com.example.study_platform.homework.dto.HomeWorkCreateRequest;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.JournalRecordService;
import com.example.study_platform.student.Student;
import com.example.study_platform.student.StudentService;
import com.example.study_platform.teacher.Teacher;
import com.example.study_platform.teacher.TeacherService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeWorkService {
    private final HomeWorkRepository homeWorkRepository;
    private final HomeWorkMapper homeWorkMapper;
    private final GradeService gradeService;
    private final JournalRecordService journalRecordService;
    private final TeacherService teacherService;

    public HomeWork getHomeWork(Long id) {
        return homeWorkRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Homework Not Found"));
    }

    @Transactional
    public void saveHomeWork(HomeWorkCreateRequest request) {

        if (request.endDate().isBefore(request.startDate())){
            throw new IllegalArgumentException("End date is before start date");
        }
        Grade grade = gradeService.getGradeById(request.gradeId());
        if(grade == null) {
            throw new IllegalArgumentException("Grade Not Found");
        }
        if (request.task() == null || request.task().isBlank()) {
            throw new IllegalArgumentException("Task must be set up");
        }
        Teacher teacher = this.teacherService.getTeacherById(request.teacherId());
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher Not Found");
        }
        List<Student> students = grade.getStudents();

        List<HomeWork> homeWorks = new ArrayList<>();
        List<JournalRecord> journalRecords = new ArrayList<>();

        for (Student student : students) {
            HomeWork homeWork = HomeWork.builder()
                    .student(student)
                    .startDate(request.startDate())
                    .endDate(request.endDate())
                    .task(request.task())
                    .teacher(teacher)
                    .build();
            JournalRecord journalRecord = JournalRecord.builder()
                    .date(homeWork.getStartDate())
                    .schoolSubject(teacher.getSchoolSubject())
                    .student(student)
                    .grade(grade)
                    .school(teacher.getSchool())
                    .homeWork(homeWork)
                    .mark(null)
                    .build();

            homeWork.setJournalRecord(journalRecord);
            homeWorks.add(homeWork);
            journalRecords.add(journalRecord);
        }
        homeWorkRepository.saveAll(homeWorks);
        journalRecordService.saveAllJournalRecords(journalRecords);
    }
}
