package com.george.server.trainer_exam.repository.user;

import com.george.server.trainer_exam.model.user.ERole;
import com.george.server.trainer_exam.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findByRoles_Name(ERole name);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

}