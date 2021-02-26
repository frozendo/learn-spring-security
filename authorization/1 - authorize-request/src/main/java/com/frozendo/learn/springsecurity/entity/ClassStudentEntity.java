package com.frozendo.learn.springsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "class_student")
public class ClassStudentEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id_student")
    @ManyToOne
    private StudentEntity studentEntity;

    @JoinColumn(name = "id_class")
    @ManyToOne
    private ClassEntity classEntity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StudentEntity getStudentEntity() {
        return studentEntity;
    }

    public void setStudentEntity(StudentEntity studentEntity) {
        this.studentEntity = studentEntity;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }
}
