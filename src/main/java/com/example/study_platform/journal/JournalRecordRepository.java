package com.example.study_platform.journal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalRecordRepository extends JpaRepository<JournalRecord, Long>,
        JpaSpecificationExecutor <JournalRecord>{
    Optional<JournalRecord> findById(Long id);

}
