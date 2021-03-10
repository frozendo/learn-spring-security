package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Integer> {

    Optional<UserRoleEntity> findByUserEntityEmail(String email);


}
