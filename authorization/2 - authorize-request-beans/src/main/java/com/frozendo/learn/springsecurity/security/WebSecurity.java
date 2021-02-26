package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.ClassTeacherRepository;
import com.frozendo.learn.springsecurity.repository.StudentRepository;
import com.frozendo.learn.springsecurity.repository.TeacherRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class WebSecurity {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ClassTeacherRepository classTeacherRepository;

    public WebSecurity(StudentRepository studentRepository,
                       TeacherRepository teacherRepository,
                       ClassTeacherRepository classTeacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.classTeacherRepository = classTeacherRepository;
    }

    public boolean validateAdminAccess(Authentication authentication) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var roles = getRoles(authentication);
        return roles.contains("ROLE_ADMIN");
    }

    public boolean validateStudentInfoAccess(Authentication authentication, int idStudent) {
        boolean hasAccess = true;
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var roles = getRoles(authentication);
        if (roles.contains("ROLE_STUDENT")) {
            var student = studentRepository.findById(idStudent);
            if (student.isEmpty()) {
                hasAccess = false;
            } else {
                hasAccess = authentication.getName().equalsIgnoreCase(student.get().getName());
            }
        }
        return hasAccess;
    }

    public boolean validateAccessStudentId(Authentication authentication, int idStudent) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var roles = getRoles(authentication);
        if (roles.contains("ROLE_STUDENT")) {
            var student = studentRepository.findById(idStudent);
            if (student.isPresent()) {
                return authentication.getName().equalsIgnoreCase(student.get().getName());
            }
        }
        return false;
    }

    public boolean validateAccessTeacherId(Authentication authentication, int idTeacher) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var roles = getRoles(authentication);
        if (roles.contains("ROLE_TEACHER")) {
            var teacher = teacherRepository.findById(idTeacher);
            if (teacher.isPresent()) {
                return authentication.getName().equalsIgnoreCase(teacher.get().getName());
            }
        }
        return false;
    }

    public boolean validateAccessClassId(Authentication authentication, int idClass) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var roles = getRoles(authentication);
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        if (roles.contains("ROLE_TEACHER")) {
            var listClass = classTeacherRepository.findByClassEntityId(idClass);
            return listClass.stream()
                    .anyMatch(c -> authentication.getName().equalsIgnoreCase(c.getTeacherEntity().getName()));
        }
        return false;
    }

    private List<String> getRoles(Authentication authentication) {
        List<String> list = new ArrayList<>();
        for (var test: authentication.getAuthorities()) {
            list.add(test.getAuthority());
        }
        return list;
    }

}
