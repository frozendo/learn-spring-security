package com.frozendo.learn.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    @Autowired
    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                          CustomOidcUserService customOidcUserService) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOidcUserService = customOidcUserService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                    .anyRequest()
                    .authenticated())
                .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
                .oauth2Login(oauth2 -> oauth2
                    .loginPage("/login").permitAll()
                    .authorizationEndpoint(authorization -> authorization
                        .baseUri("/login/oauth2/authorization")
                    )
                    .userInfoEndpoint(userInfo -> userInfo
                        .userService(this.customOAuth2UserService)
                        .oidcUserService(this.customOidcUserService)
                    )
                );
    }

}
