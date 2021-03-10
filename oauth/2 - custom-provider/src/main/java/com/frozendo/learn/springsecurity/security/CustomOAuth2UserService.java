package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public CustomOAuth2UserService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        // Delegate to the default implementation for loading a user
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        var token = userRequest.getAccessToken();
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        var email = (String) oAuth2User.getAttribute("email");

        var userRole = userRoleRepository.findByUserEntityEmail(email);

        var role = userRole
                .map(item -> item.getRoleEntity().getName())
                .orElse("USER");

        mappedAuthorities.add(new OAuth2UserAuthority("ROLE_" + role, oAuth2User.getAttributes()));
        for (String authority : token.getScopes()) {
            mappedAuthorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
        }

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(mappedAuthorities, oAuth2User.getAttributes(), userNameAttributeName);
    }

}
