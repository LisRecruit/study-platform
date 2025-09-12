package com.example.study_platform.homework;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeWorkRepository extends JpaRepository<HomeWork, Long> {
}
