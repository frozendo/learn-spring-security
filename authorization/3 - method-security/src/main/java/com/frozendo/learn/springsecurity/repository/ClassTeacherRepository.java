package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.ClassTeacherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassTeacherRepository extends CrudRepository<ClassTeacherEntity, Integer> {

    List<ClassTeacherEntity> findByClassEntityId(@Param("idClass") Integer idClass);

}
