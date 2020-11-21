package com.frozendo.learn.springsecurity.inmemory;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Configure routes for each user type
 * This uses a custom form, build in resources/templates/login.html
 * How form uses thymeleaf, we need to add 'org.springframework.boot:spring-boot-starter-thymeleaf' dependency
 */
//@EnableWebSecurity
public class InMemoryAndCustomLogin extends InMemoryAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login") //say to use the custom login page
                .permitAll();
    }

}
