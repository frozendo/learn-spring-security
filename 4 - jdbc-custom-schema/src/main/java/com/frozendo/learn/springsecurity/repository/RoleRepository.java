package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.RoleEntity;
import org.springframework.data.repository.Repository;

public interface RoleRepository extends Repository<RoleEntity, Integer> {

    RoleEntity findById(Integer id);

}
