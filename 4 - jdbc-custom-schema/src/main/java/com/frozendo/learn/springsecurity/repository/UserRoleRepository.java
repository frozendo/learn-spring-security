package com.frozendo.learn.springsecurity.repository;

import com.frozendo.learn.springsecurity.entity.UserRoleEntity;
import org.springframework.data.repository.Repository;

public interface UserRoleRepository extends Repository<UserRoleEntity, Integer> {

    UserRoleEntity findByIdUser(Integer idUser);

}
