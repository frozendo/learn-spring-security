package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.entity.GradeEntity;
import com.frozendo.learn.springsecurity.repository.TeacherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class GradeSecurity {

    private final TeacherRepository teacherRepository;

    public GradeSecurity(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public boolean checkCanReadGrade(Authentication authentication, GradeEntity gradeEntity) {
        var roles = getRoles(authentication);
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        var teacher = gradeEntity.getClassTeacherEntity().getTeacherEntity();
        return teacher.getName().equalsIgnoreCase(authentication.getName());
    }

    public boolean validateAccessTeacherId(Authentication authentication, int idTeacher) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        return teacherRepository.findById(idTeacher)
                .filter(teacherEntity -> authentication.getName().equalsIgnoreCase(teacherEntity.getName()))
                .isPresent();
    }

    private List<String> getRoles(Authentication authentication) {
        List<String> list = new ArrayList<>();
        for (var test: authentication.getAuthorities()) {
            list.add(test.getAuthority());
        }
        return list;
    }

}
