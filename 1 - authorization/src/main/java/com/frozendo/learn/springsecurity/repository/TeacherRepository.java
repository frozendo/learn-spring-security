package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.TeacherEntity;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {
}
