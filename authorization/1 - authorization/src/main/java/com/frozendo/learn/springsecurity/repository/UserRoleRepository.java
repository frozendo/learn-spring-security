package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Integer> {

    List<UserRoleEntity> findByUserEntityUsername(String username);

}
