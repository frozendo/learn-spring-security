package com.frozendo.learn.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String index(OAuth2AuthenticationToken authentication) {
        return "Welcome " + authentication.getPrincipal().getAttribute("name");
    }

    @RequestMapping("/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public String teacher() {
        return "I'm a teacher";
    }

    @RequestMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String student() {
        return "I'm a student";
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "I'm an admin";
    }

}
