package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.StudentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StudentSecurity {

    private final StudentRepository studentRepository;

    public StudentSecurity(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean validateAccessStudentId(Authentication authentication, int idStudent) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        return studentRepository.findById(idStudent)
                .filter(studentEntity -> authentication.getName().equalsIgnoreCase(studentEntity.getName()))
                .isPresent();
    }

}
