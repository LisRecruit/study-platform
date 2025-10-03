package com.example.study_platform.journal.filter.filters;

import com.example.study_platform.grade.Grade;
import com.example.study_platform.journal.JournalRecord;
import com.example.study_platform.journal.filter.JournalFilter;
import com.example.study_platform.journal.filter.JournalRecordSpecification;
import com.example.study_platform.lesson.Lesson;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class LessonFilter implements JournalFilter {
    private final Lesson lesson;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public LessonFilter(Lesson lesson, LocalDate startDate, LocalDate endDate) {
        this.lesson = lesson;
        this.startDate = (startDate != null) ? startDate : LocalDate.of(LocalDate.now().getYear(), 9, 1);
        this.endDate = (endDate != null) ? endDate : LocalDate.now().plusWeeks(1);
    }

    @Override
    public Specification<JournalRecord> toSpecification() {
        Specification <JournalRecord> specification = JournalRecordSpecification.hasLesson(lesson);
        specification = specification.and(JournalRecordSpecification.dateBetween(startDate, endDate));
        return specification;
    }
}
