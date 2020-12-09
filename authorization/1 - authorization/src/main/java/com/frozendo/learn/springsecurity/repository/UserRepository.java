package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
