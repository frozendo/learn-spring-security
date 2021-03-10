package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserRoleRepository userRoleRepository;

    @Autowired
    public CustomOidcUserService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUserService delegate = new OidcUserService();

        // Delegate to the default implementation for loading a user
        OidcUser oidcUser = delegate.loadUser(userRequest);

        var token = userRequest.getAccessToken();
        Set<GrantedAuthority> mappedAuthorities = new LinkedHashSet<>();

        var email = (String) oidcUser.getAttribute("email");

        var userRole = userRoleRepository.findByUserEntityEmail(email);

        var role = userRole
                .map(item -> item.getRoleEntity().getName())
                .orElse("USER");

        mappedAuthorities.add(new OAuth2UserAuthority("ROLE_" + role, oidcUser.getAttributes()));
        for (String authority : token.getScopes()) {
            mappedAuthorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
        }

        return new DefaultOidcUser(mappedAuthorities, userRequest.getIdToken(), oidcUser.getUserInfo());
    }

}
