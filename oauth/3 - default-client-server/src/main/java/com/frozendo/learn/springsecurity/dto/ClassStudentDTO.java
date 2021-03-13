package com.frozendo.learn.springsecurity.dto;

public class ClassStudentDTO {

    private Integer id;
    private StudentDTO studentDTO;
    private ClassDTO classDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StudentDTO getStudentEntity() {
        return studentDTO;
    }

    public void setStudentEntity(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public ClassDTO getClassEntity() {
        return classDTO;
    }

    public void setClassEntity(ClassDTO classDTO) {
        this.classDTO = classDTO;
    }
}