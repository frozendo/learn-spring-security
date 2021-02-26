package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.GradeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer> {

    @Query("select g from GradeEntity g join g.classStudentEntity cs " +
            "join cs.studentEntity s where s.id = :idStudent")
    List<GradeEntity> findStudentsGrades(@Param("idStudent") Integer idStudent);

    @Query("select g from GradeEntity g join g.classStudentEntity cs " +
            "join cs.classEntity c where c.id = :idClass")
    List<GradeEntity> findClassGrades(@Param("idClass") Integer idClass);

    @Query("select g from GradeEntity g join g.classTeacherEntity cs " +
            "join cs.teacherEntity t where t.id = :idTeacher")
    List<GradeEntity> findTeacherGrades(@Param("idTeacher") Integer idTeacher);

}
