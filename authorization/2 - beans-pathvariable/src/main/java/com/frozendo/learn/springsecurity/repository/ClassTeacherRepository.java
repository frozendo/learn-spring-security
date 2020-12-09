package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.ClassTeacherEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClassTeacherRepository extends CrudRepository<ClassTeacherEntity, Integer> {
}
