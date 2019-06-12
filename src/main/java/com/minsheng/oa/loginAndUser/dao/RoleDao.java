package com.minsheng.oa.loginAndUser.dao;

import com.minsheng.oa.loginAndUser.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Integer> {

    //--------------------------------------------------------
    //查询所有角色
       @Query(value="select r.* from t_role r", nativeQuery = true)
        List<Role> findRole();     //查询所有role
}
