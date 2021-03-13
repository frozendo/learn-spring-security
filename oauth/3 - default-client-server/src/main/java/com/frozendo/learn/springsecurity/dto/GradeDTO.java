package com.frozendo.learn.springsecurity.dto;

public class GradeDTO {

    private Integer id;
    private String value;
    private ClassStudentDTO classStudentDTO;
    private ClassTeacherDTO classTeacherDTO;

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

    public ClassStudentDTO getClassStudentEntity() {
        return classStudentDTO;
    }

    public void setClassStudentEntity(ClassStudentDTO classStudentDTO) {
        this.classStudentDTO = classStudentDTO;
    }

    public ClassTeacherDTO getClassTeacherEntity() {
        return classTeacherDTO;
    }

    public void setClassTeacherEntity(ClassTeacherDTO classTeacherDTO) {
        this.classTeacherDTO = classTeacherDTO;
    }
}