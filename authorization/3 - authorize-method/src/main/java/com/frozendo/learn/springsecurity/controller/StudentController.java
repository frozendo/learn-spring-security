package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.entity.StudentEntity;
import com.frozendo.learn.springsecurity.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @GetMapping
    @PostFilter("@studentSecurity.validateStudentRead(authentication, filterObject)")
    public List<StudentEntity> listStudents() {
        Iterable<StudentEntity> all = repository.findAll();
        List<StudentEntity> list = new ArrayList<>();
        all.forEach(list::add);
        return list;
    }

    @GetMapping("/{id}")
    @PreAuthorize("@studentSecurity.checkStudent(authentication, #id)")
    public ResponseEntity<StudentEntity> getStudent(@PathVariable("id") Integer id) {
        Optional<StudentEntity> byId = repository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(
                    byId.get()
            );
        }
        return ResponseEntity.notFound().build();
    }

}
