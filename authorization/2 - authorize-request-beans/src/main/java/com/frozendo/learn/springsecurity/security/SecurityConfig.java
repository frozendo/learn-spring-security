package com.frozendo.learn.springsecurity.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Use Spring Bean to validate and control access in a route.
 * In this examples, we use class {@link WebSecurity} to control the user right.
 * For student route, we use the id in path variable too.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("customUserDetails") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/grade", "/class", "/student")
                    .access("@webSecurity.validateAdminAccess(authentication)")
                .antMatchers("/student/{idStudent}")
                    .access("@webSecurity.validateStudentInfoAccess(authentication, #idStudent)")
                .antMatchers("/grade/student/{idStudent}")
                    .access("@webSecurity.validateAccessStudentId(authentication, #idStudent)")
                .antMatchers("/grade/class/{idClass}", "/class/{idClass}")
                    .access("@webSecurity.validateAccessClassId(authentication, #idClass)")
                .antMatchers("/grade/teacher/{idTeacher}")
                    .access("@webSecurity.validateAccessTeacherId(authentication, #idTeacher)")
                .antMatchers("/").permitAll()
                .anyRequest().denyAll()
                .and()
                .httpBasic()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
