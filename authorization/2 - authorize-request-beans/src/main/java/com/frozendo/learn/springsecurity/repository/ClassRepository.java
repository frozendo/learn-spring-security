package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.ClassEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer> {
}
