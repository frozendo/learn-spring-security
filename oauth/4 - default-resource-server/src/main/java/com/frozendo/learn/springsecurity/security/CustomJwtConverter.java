package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.repository.UserRoleRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Component
public class CustomJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    private final UserRoleRepository userRoleRepository;

    public CustomJwtConverter(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        var authorities = this.jwtGrantedAuthoritiesConverter.convert(source);

        if (Objects.isNull(authorities)) {
            authorities = new ArrayList<>();
        }

        var email = (String) source.getClaims().get("email");

        var userRole = userRoleRepository.findByUserEntityEmail(email);

        var role = userRole
                .map(item -> item.getRoleEntity().getName())
                .orElse("USER");

        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return new JwtAuthenticationToken(source, authorities);
    }
}
