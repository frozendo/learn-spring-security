package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.entity.ClassEntity;
import com.frozendo.learn.springsecurity.entity.StudentEntity;
import com.frozendo.learn.springsecurity.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassSecurity {

    @Autowired
    private ClassRepository classRepository;

    public boolean checkClassAccess(Authentication authentication, Integer id) {
        var roles = getRoles(authentication);
        if (checkStudentRole(roles)) {
            return checkClassAccess(id, authentication.getName(), roles);
        }
        return false;
    }

    private boolean checkClassAccess(Integer id, String username, List<String> roles) {
        if (!checkAdminRoles(roles)) {
            boolean hasAccess = false;
            if (checkIsTeacher(roles)) {
                var list = classRepository.listClassByTeacherName(username);
                for (var classEntity: list) {
                    if (classEntity.getId() == id) {
                        hasAccess = true;
                    }
                }
            } else {
                var list = classRepository.listClassByStudentName(username);
                for (var classEntity: list) {
                    if (classEntity.getId() == id) {
                        hasAccess = true;
                    }
                }
            }
            return hasAccess;
        }
        return true;
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

    private boolean checkAdminRoles(List<String> roles) {
        boolean isAdminRole = false;
        for (String role: roles) {
            if (role.equals("ROLE_ADMIN") || role.equals("ROLE_COORDINATOR")) {
                isAdminRole = true;
            }
        }
        return isAdminRole;

    }

    private boolean checkStudentRole(List<String> roles) {
        boolean isStudent = false;
        for (String role: roles) {
            if (!role.equals("ROLE_STUDENT")) {
                isStudent = true;
            }
        }
        return isStudent;
    }

    private List<String> getRoles(Authentication authentication) {
        List<String> list = new ArrayList<>();
        for (var test: authentication.getAuthorities()) {
            list.add(test.getAuthority());
        };
        return list;
    }



}
