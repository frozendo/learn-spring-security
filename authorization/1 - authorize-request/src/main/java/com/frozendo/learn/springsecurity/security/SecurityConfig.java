package com.frozendo.learn.springsecurity.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * The simplest way to configure routes in Spring Security.
 * Each route can be liberate for one or more roles, authorities or scopes.
 * For this, we can use methods hasRole, hasAnyRole, hasAuthority or hasAnyAuthority.
 * It's a better choice, restrict all and then allow or deny each individual route.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/class/**").hasAnyRole("ADMIN, TEACHER")
                .antMatchers("/student/**").authenticated()
                .antMatchers("/grade").hasRole("ADMIN")
                .antMatchers("/grade/student/**").hasRole("STUDENT")
                .antMatchers("/grade/class/**", "/grade/teacher/**").access("hasRole('ADMIN') or hasRole('TEACHER')")
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
