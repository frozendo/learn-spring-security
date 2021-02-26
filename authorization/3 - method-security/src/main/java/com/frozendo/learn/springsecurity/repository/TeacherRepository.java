package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.TeacherEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer> {

    Optional<TeacherEntity> findByName(String name);

}
