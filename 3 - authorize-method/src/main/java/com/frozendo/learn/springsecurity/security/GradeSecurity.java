package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.entity.GradeEntity;
import com.frozendo.learn.springsecurity.repository.StudentRepository;
import com.frozendo.learn.springsecurity.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GradeSecurity {

    @Autowired
    private TeacherRepository teacherRepository;

    public boolean checkCanReadGrade(Authentication authentication, GradeEntity gradeEntity) {
        if (checkIsCoordinator(getRoles(authentication))) {
            return true;
        }
        var teacher = gradeEntity.getClassTeacherEntity().getTeacherEntity();
        return teacher.getName().toLowerCase().equals(authentication.getName());
    }

    public boolean checkTeacherAccess(Authentication authentication, Integer id) {
        var roles = getRoles(authentication);
        if (checkIsTeacher(roles)) {
            var teacher = teacherRepository.findById(id);
            if (teacher.isEmpty()) {
                return false;
            }
            return authentication.getName().equals(teacher.get().getName().toLowerCase());
        }
        return checkIsCoordinator(roles);
    }

    private boolean checkIsCoordinator(List<String> roles) {
        boolean isCoordinator = false;
        for (String role: roles) {
            if (role.equals("ROLE_COORDINATOR")) {
                isCoordinator = true;
            }
        }
        return isCoordinator;
    }

    private boolean checkIsTeacher(List<String> roles) {
        boolean isTeacher = false;
        for (String role: roles) {
            if (role.equals("ROLE_TEACHER")) {
                isTeacher = true;
            }
        }
        return isTeacher;
    }

    private List<String> getRoles(Authentication authentication) {
        List<String> list = new ArrayList<>();
        for (var test: authentication.getAuthorities()) {
            list.add(test.getAuthority());
        };
        return list;
    }

}
