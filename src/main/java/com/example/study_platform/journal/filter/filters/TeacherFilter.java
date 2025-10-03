package com.example.study_platform.journal.filter.filters;

import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.filter.JournalFilter;
import com.example.study_platform.journal.filter.JournalRecordSpecification;
import com.example.study_platform.student.Student;
import com.example.study_platform.teacher.Teacher;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TeacherFilter implements JournalFilter {
    private final Teacher teacher;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TeacherFilter(Teacher teacher, LocalDate startDate, LocalDate endDate) {
        this.teacher = teacher;
        this.startDate = (startDate != null) ? startDate : LocalDate.of(LocalDate.now().getYear(), 9, 1);
        this.endDate = (endDate != null) ? endDate : LocalDate.now().plusWeeks(1);
    }

    @Override
    public Specification<JournalRecord> toSpecification() {
        Specification <JournalRecord> specification = JournalRecordSpecification.hasTeacher(teacher);
        specification = specification.and(JournalRecordSpecification.dateBetween(startDate, endDate));
        return specification;
    }
}
