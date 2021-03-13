package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.dto.GradeDTO;
import com.frozendo.learn.springsecurity.service.GradeServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/grade")
public class GradeController {

    private final GradeServiceRest gradeService;

    @Autowired
    public GradeController(GradeServiceRest gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public ResponseEntity<List<GradeDTO>> listGrades() {
        return ResponseEntity.ok(gradeService.listGrades());
    }

    @GetMapping("/student/{idStudent}")
    public ResponseEntity<List<GradeDTO>> listStudentGrades(@PathVariable("idStudent") Integer idStudent) {
        return ResponseEntity.ok(
                gradeService.listStudentGrades(idStudent)
        );
    }

    @GetMapping("/class/{idClass}")
    public ResponseEntity<List<GradeDTO>> listClassGrades(@PathVariable("idClass") Integer idClass) {
        return ResponseEntity.ok(
                gradeService.listClassGrades(idClass)
        );
    }

    @GetMapping("/teacher/{idTeacher}")
    public ResponseEntity<List<GradeDTO>> listTeacherGrades(@PathVariable("idTeacher") Integer idTeacher) {
        return ResponseEntity.ok(
                gradeService.listTeacherGrades(idTeacher)
        );
    }
}