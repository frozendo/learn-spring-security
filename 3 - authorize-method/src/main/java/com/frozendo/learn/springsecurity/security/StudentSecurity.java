package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.entity.StudentEntity;
import com.frozendo.learn.springsecurity.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentSecurity {

    @Autowired
    private StudentRepository studentRepository;

    public boolean validateStudentRead(Authentication authentication, StudentEntity student) {
        var roles = getRoles(authentication);
        if (checkRole(roles)) {
            return true;
        }
        return authentication.getName().equals(student.getName().toLowerCase());
    }

    public boolean checkStudent(Authentication authentication, Integer idStudent) {
        var roles = getRoles(authentication);
        if (checkRole(roles)) {
            return true;
        }

        var student = studentRepository.findById(idStudent);
        if (student.isEmpty()) {
            return false;
        }
        return authentication.getName().equals(student.get().getName().toLowerCase());
    }

    private boolean checkRole(List<String> roles) {
        boolean hasAccess = false;
        for (String role: roles) {
            if (!role.equals("ROLE_STUDENT") && !role.equals("ROLE_REPRESENTATIVE")) {
                hasAccess = true;
            }
        }
        return hasAccess;
    }

    private List<String> getRoles(Authentication authentication) {
        List<String> list = new ArrayList<>();
        for (var test: authentication.getAuthorities()) {
            list.add(test.getAuthority());
        };
        return list;
    }

}
