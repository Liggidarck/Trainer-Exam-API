package com.george.server.trainer_exam.repository.group;

import com.george.server.trainer_exam.model.group.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}