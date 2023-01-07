package com.george.server.trainer_exam.model.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    private Long userId;

    private Long teacherId;

    private String testName;

    private String testCategory;

    @ManyToMany
    @JoinTable(name = "tests_questions",
            joinColumns = @JoinColumn(name = "test_id"),
            inverseJoinColumns = @JoinColumn(name = "questions_id"))
    private Set<Question> questions = new LinkedHashSet<>();


}