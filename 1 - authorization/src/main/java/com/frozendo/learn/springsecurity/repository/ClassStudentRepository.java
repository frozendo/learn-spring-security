package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.ClassStudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClassStudentRepository extends CrudRepository<ClassStudentEntity, Integer> {
}
