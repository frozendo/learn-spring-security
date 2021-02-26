package com.frozendo.learn.springsecurity.security;

import com.frozendo.learn.springsecurity.entity.UserEntity;
import com.frozendo.learn.springsecurity.entity.UserRoleEntity;
import com.frozendo.learn.springsecurity.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final UserRoleRepository userRoleRepository;

    public CustomUserDetails(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        var list = userRoleRepository.findByUserEntityUsername(username);
        UserEntity userEntity = null;
        List<String> roles = new ArrayList<>();
        for (UserRoleEntity userRole: list) {
            if (Objects.isNull(userEntity)) {
                userEntity = userRole.getUserEntity();
            }
            roles.add(userRole.getRoleEntity().getName());
        }
        if (Objects.isNull(userEntity) || roles.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
    }
}
