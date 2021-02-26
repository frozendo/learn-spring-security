package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.ClassEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer> {

}
