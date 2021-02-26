package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.ClassTeacherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClassSecurity {

    private final ClassTeacherRepository classTeacherRepository;

    public ClassSecurity(ClassTeacherRepository classTeacherRepository) {
        this.classTeacherRepository = classTeacherRepository;
    }

    public boolean checkClassAccess(Authentication authentication, Integer id) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var listClass = classTeacherRepository.findByClassEntityId(id);
        return listClass.stream()
                .anyMatch(c -> authentication.getName().equalsIgnoreCase(c.getTeacherEntity().getName()));
    }

}
