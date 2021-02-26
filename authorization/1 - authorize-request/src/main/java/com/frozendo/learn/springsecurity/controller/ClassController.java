package com.frozendo.learn.springsecurity.controller;

import com.frozendo.learn.springsecurity.entity.ClassEntity;
import com.frozendo.learn.springsecurity.repository.ClassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class")
public class ClassController {

    private final ClassRepository repository;

    public ClassController(ClassRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<ClassEntity>> listClass() {
        Iterable<ClassEntity> all = repository.findAll();
        List<ClassEntity> list = new ArrayList<>();
        all.forEach(list::add);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassEntity> getClass(@PathVariable("id") Integer id) {
        Optional<ClassEntity> byId = repository.findById(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
