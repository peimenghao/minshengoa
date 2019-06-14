package com.minsheng.oa.loginAndUser.dao;

import com.minsheng.oa.loginAndUser.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRoleDao extends JpaRepository<UserRole,Integer> {

    @Transactional
    @Modifying
    @Query(value="insert into t_user_role (role_id,user_id) values(?1,?2) ",nativeQuery = true)
    void saverRoleAndUser(Integer roleId,Integer userId);

    @Transactional
    @Modifying
    @Query(value="update t_user_role set role_id=?2 where user_id=?1",nativeQuery = true)
    void updateUserRole(Integer userId,Integer roleId);
}
