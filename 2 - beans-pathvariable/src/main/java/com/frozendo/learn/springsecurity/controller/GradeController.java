package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.entity.GradeEntity;
import com.frozendo.learn.springsecurity.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeRepository repository;

    @GetMapping
    public ResponseEntity<List<GradeEntity>> listGrades() {
        Iterable<GradeEntity> all = repository.findAll();
        List<GradeEntity> list = new ArrayList<>();
        all.forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/student/{idStudent}")
    public ResponseEntity<List<GradeEntity>> listStudentGrades(@PathVariable("idStudent") Integer idStudent) {
        return ResponseEntity.ok(
                repository.findStudentsGrades(idStudent)
        );
    }

    @GetMapping("/class/{idClass}")
    public ResponseEntity<List<GradeEntity>> listStudentClassGrades(@PathVariable("idClass") Integer idClass) {
        return ResponseEntity.ok(
                repository.findClassGrades(idClass)
        );
    }

    @GetMapping("/teacher/{idTeacher}")
    public ResponseEntity<List<GradeEntity>> listTeacherGrades(@PathVariable("idTeacher") Integer idTeacher) {
        return ResponseEntity.ok(
                repository.findTeacherGrades(idTeacher)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeEntity> getClass(@PathVariable("id") Integer id) {
        Optional<GradeEntity> byId = repository.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(
                    byId.get()
            );
        }
        return ResponseEntity.notFound().build();
    }
}
