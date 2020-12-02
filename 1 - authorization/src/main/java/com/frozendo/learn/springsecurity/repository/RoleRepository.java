package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
}
