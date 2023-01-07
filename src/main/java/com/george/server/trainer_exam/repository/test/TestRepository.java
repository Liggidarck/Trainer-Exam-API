package com.george.server.trainer_exam.repository.test;

import com.george.server.trainer_exam.model.test.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}