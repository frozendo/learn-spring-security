package com.frozendo.learn.springsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class GradeEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "grade_value")
    private String value;

    @JoinColumn(name = "id_class_student")
    @ManyToOne
    private ClassStudentEntity classStudentEntity;

    @JoinColumn(name = "id_class_teacher")
    @ManyToOne
    private ClassTeacherEntity classTeacherEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ClassStudentEntity getClassStudentEntity() {
        return classStudentEntity;
    }

    public void setClassStudentEntity(ClassStudentEntity classStudentEntity) {
        this.classStudentEntity = classStudentEntity;
    }

    public ClassTeacherEntity getClassTeacherEntity() {
        return classTeacherEntity;
    }

    public void setClassTeacherEntity(ClassTeacherEntity classTeacherEntity) {
        this.classTeacherEntity = classTeacherEntity;
    }
}