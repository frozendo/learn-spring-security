package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.UserEntity;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserEntity, Integer> {

    UserEntity findByUsername(String username);

}
