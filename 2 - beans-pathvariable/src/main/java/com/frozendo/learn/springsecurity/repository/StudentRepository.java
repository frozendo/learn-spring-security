package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer> {
}
