package com.minsheng.oa.loginAndUser.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;

@Getter
@Setter
@Entity(name="t_user_role")
public class UserRole {

    @Id
    @Column(name = "id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value = "id")
    private Integer id;

    @Column(name = "user_id")
    @FormParam(value = "userId")
    private Integer userId;

    @Column(name = "role_id")
    @FormParam(value = "roleId")
    private Integer roleId;
}
