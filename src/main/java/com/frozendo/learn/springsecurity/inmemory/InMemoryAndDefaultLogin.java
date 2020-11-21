package com.frozendo.learn.springsecurity.inmemory;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Configure routes for each user type
 * This uses form login provided by Spring
 */
//@EnableWebSecurity
public class InMemoryAndDefaultLogin extends InMemoryAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }
}
