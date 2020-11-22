package com.frozendo.learn.springsecurity.jdbc;

import com.frozendo.learn.springsecurity.repository.RoleRepository;
import com.frozendo.learn.springsecurity.repository.UserRepository;
import com.frozendo.learn.springsecurity.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private RoleRepository roleRepository;

    public CustomUserDetails(UserRepository userRepository,
                             UserRoleRepository userRoleRepository,
                             RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = userRepository.findByUsername(username);
        var userRole = userRoleRepository.findByIdUser(userEntity.getId());
        var role = roleRepository.findById(userRole.getId());
        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(role.getName())
                .build();
    }

}
