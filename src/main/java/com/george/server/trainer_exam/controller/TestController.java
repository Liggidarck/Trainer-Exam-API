package com.george.server.trainer_exam.controller;

import com.george.server.trainer_exam.auth.response.MessageResponse;
import com.george.server.trainer_exam.model.test.Test;
import com.george.server.trainer_exam.repository.test.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tests")
public class TestController {

    @Autowired
    TestRepository testRepository;

    @PostMapping("/create")
    ResponseEntity<?> createTest(@Valid @RequestBody Test test) {
        testRepository.save(test);

        return ResponseEntity.ok(new MessageResponse("Test successfully created"));
    }

    @GetMapping("/get")
    public List<Test> getAllTest() {
        return testRepository.findAll();
    }


}
