package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.entity.StudentEntity;
import com.frozendo.learn.springsecurity.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
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

    private final StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> listStudents() {
        Iterable<StudentEntity> all = repository.findAll();
        List<StudentEntity> list = new ArrayList<>();
        all.forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{idStudent}")
    public ResponseEntity<StudentEntity> getStudent(@PathVariable("idStudent") Integer id) {
        Optional<StudentEntity> byId = repository.findById(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
