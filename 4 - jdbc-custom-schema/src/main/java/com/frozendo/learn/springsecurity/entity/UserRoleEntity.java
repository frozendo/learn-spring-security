package com.frozendo.learn.springsecurity.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("USER_ROLE")
public class UserRoleEntity {
    @Column("ID")
    private Integer id;
    @Column("ID_USER")
    private Integer idUser;
    @Column("ID_ROLE")
    private Integer idRole;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }
}
