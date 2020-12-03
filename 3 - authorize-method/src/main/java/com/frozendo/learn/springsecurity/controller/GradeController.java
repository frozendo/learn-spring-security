package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.entity.GradeEntity;
import com.frozendo.learn.springsecurity.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeRepository repository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GradeEntity>> listGrades() {
        Iterable<GradeEntity> all = repository.findAll();
        List<GradeEntity> list = new ArrayList<>();
        all.forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/student/{idStudent}")
    @PreAuthorize("hasRole('STUDENT') and @studentSecurity.checkStudent(authentication, #idStudent)")
    public ResponseEntity<List<GradeEntity>> listStudentGrades(@PathVariable("idStudent") Integer idStudent) {
        return ResponseEntity.ok(
                repository.findStudentsGrades(idStudent)
        );
    }

    @GetMapping("/class/{idClass}")
    @PreAuthorize("hasAnyRole('COORDINATOR, TEACHER')")
    @PostFilter("@gradeSecurity.checkCanReadGrade(authentication, filterObject)")
    public List<GradeEntity> listStudentClassGrades(@PathVariable("idClass") Integer idClass) {
        return repository.findClassGrades(idClass);
    }

    @GetMapping("/teacher/{idTeacher}")
    @PreAuthorize("hasAnyRole('COORDINATOR, TEACHER') and @gradeSecurity.checkTeacherAccess(authentication, #idTeacher)")
    public List<GradeEntity> listTeacherGrades(@PathVariable("idTeacher") Integer idTeacher) {
        return repository.findTeacherGrades(idTeacher);
    }
}
