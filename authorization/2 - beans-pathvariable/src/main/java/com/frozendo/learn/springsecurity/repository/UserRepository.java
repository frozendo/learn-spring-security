package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

}
