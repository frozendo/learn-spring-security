package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class WebSecurity {

    private Map<String, List<String>> MAP_ROLES = Map.of(
            "ROLE_STUDENT", List.of("/student", "/grade/student"),
            "ROLE_TEACHER", List.of("/student", "", "/class", "/grade/class", "/grade/teacher"),
            "ROLE_COORDINATOR", List.of("/student", "", "/class", "/grade/class", "/grade/teacher"),
            "ROLE_REPRESENTATIVE", List.of("/class")
    );

    @Autowired
    private StudentRepository studentRepository;

    public boolean validateAccess(Authentication authentication, HttpServletRequest request) {
        if (Objects.isNull(authentication) || !authentication.isAuthenticated()) {
            return false;
        }
        var roles = getRoles(authentication);
        if (roles.contains("ROLE_ADMIN")) {
            return true;
        }
        return checkAccess(roles, removePathVariable(request.getRequestURI()));
    }

    public boolean validateAccessStudentId(Authentication authentication, HttpServletRequest request, int idStudent) {
        boolean hasAccess = validateAccess(authentication, request);
        if (hasAccess) {
            var student = studentRepository.findById(idStudent);
            if (student.isEmpty()) {
                hasAccess = false;
            } else {
                hasAccess = authentication.getName().equals(student.get().getName().toLowerCase());
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

    private String removePathVariable(String originalUri) {
        var split = originalUri.split("/");
        String uri = "";
        int count = 0;
        while (count < split.length) {
            var item = split[count];
            if (!tryParseInt(item) && !item.isEmpty()) {
                uri += "/" + item;
            }
            count++;
        }
        return uri;
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkAccess(List<String> roles, String uri) {
        boolean hasAccess = false;
        for (String role: roles) {
            var listUri = MAP_ROLES.get(role);
            if (listUri.contains(uri)) {
                hasAccess = true;
            }

        }
        return hasAccess;
    }

}
