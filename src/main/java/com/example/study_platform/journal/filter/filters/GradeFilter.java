package com.example.study_platform.journal.filter.filters;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.filter.JournalFilter;
import com.example.study_platform.journal.filter.JournalRecordSpecification;
import com.example.study_platform.student.Student;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class GradeFilter implements JournalFilter {
    private final Grade grade;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public GradeFilter(Grade grade, LocalDate startDate, LocalDate endDate) {
        this.grade = grade;
        this.startDate = (startDate != null) ? startDate : LocalDate.of(LocalDate.now().getYear(), 9, 1);
        this.endDate = (endDate != null) ? endDate : LocalDate.now().plusWeeks(1);
    }

    @Override
    public Specification<JournalRecord> toSpecification() {
        Specification <JournalRecord> specification = JournalRecordSpecification.hasGrade(grade);
        specification = specification.and(JournalRecordSpecification.dateBetween(startDate, endDate));
        return specification;
    }
}
