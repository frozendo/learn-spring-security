package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.dto.ClassDTO;
import com.frozendo.learn.springsecurity.service.ClassServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class")
public class ClassController {

    private final ClassServiceRest classService;

    @Autowired
    public ClassController(ClassServiceRest classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> listClass() {
        return ResponseEntity.ok(classService.listClass());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClass(@PathVariable("id") Integer id) {
        Optional<ClassDTO> byId = classService.getClassEntity(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
