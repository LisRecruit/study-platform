package com.example.study_platform.journal.filter;

import com.example.study_platform.journal.JournalRecord;
import org.springframework.data.jpa.domain.Specification;

public interface JournalFilter {
    Specification<JournalRecord> toSpecification();
}
