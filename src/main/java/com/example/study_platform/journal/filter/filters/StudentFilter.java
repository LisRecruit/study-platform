package com.example.study_platform.journal.filter.filters;

import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.filter.JournalRecordSpecification;
import com.example.study_platform.journal.filter.JournalFilter;
import com.example.study_platform.student.Student;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StudentFilter implements JournalFilter {
    private final Student student;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public StudentFilter(Student student, LocalDate startDate, LocalDate endDate) {
        this.student = student;
        this.startDate = (startDate != null) ? startDate : LocalDate.of(LocalDate.now().getYear(), 9, 1);
        this.endDate = (endDate != null) ? endDate : LocalDate.now().plusWeeks(1);
    }

    @Override
    public Specification<JournalRecord> toSpecification() {
        Specification <JournalRecord> specification = JournalRecordSpecification.hasStudent(student);
        specification = specification.and(JournalRecordSpecification.dateBetween(startDate, endDate));
        return specification;
    }

}
