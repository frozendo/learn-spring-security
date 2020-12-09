package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.ClassEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends CrudRepository<ClassEntity, Integer> {

    @Query(value = "select c.* from class c " +
            "inner join class_teacher ct on ct.id_class = c.id " +
            "inner join teacher t on t.id = ct.id_teacher " +
            "where lower(t.name) = lower(:name)", nativeQuery = true)
    List<ClassEntity> listClassByTeacherName(@Param("name") String name);

    @Query(value = "select c.* from class c " +
            "inner join class_student cs on cs.id_class = c.id " +
            "inner join student s on s.id = cs.id_student " +
            "where lower(s.name) = lower(:name)", nativeQuery = true)
    List<ClassEntity> listClassByStudentName(@Param("name") String name);

}
