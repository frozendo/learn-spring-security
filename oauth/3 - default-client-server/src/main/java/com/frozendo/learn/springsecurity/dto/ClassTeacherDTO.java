package com.frozendo.learn.springsecurity.dto;

public class ClassTeacherDTO {

    private Integer id;
    private TeacherDTO teacherDTO;
    private ClassDTO classDTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeacherDTO getTeacherEntity() {
        return teacherDTO;
    }

    public void setTeacherEntity(TeacherDTO teacherDTO) {
        this.teacherDTO = teacherDTO;
    }

    public ClassDTO getClassEntity() {
        return classDTO;
    }

    public void setClassEntity(ClassDTO classDTO) {
        this.classDTO = classDTO;
    }
}