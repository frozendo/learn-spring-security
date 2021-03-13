package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.dto.StudentDTO;
import com.frozendo.learn.springsecurity.service.StudentServiceRest;
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

    private final StudentServiceRest studentService;

    public StudentController(StudentServiceRest studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> listStudents() {
        return ResponseEntity.ok(studentService.listStudent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") Integer id) {
        Optional<StudentDTO> byId = studentService.getStudent(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}