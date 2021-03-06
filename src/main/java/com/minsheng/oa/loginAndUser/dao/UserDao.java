package com.minsheng.oa.loginAndUser.dao;


import com.minsheng.oa.loginAndUser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    //保存一条用户
    @Modifying
    User save(User user);

    //查询用户通过密码和姓名
    User findByUserNameAndPassword(String userName, String password);

    User findUserByUserId(Integer userId);

    //查询所有用户
    @Query(value = "select u.* from  t_user u", nativeQuery = true)
    List<User> find();               //用find 为方法名则可以不写注解


    //根据部门id查询 部门下面所有员工
    @Query(value = "select u.* from  t_user  u where u.depart_id=?1 ", nativeQuery = true)
    List<User> findUserByDepartId(Integer departId);


    //根据名字查询用户
    User findByUserName(String userName);

    @Query(value = "select u.password from t_user u  where u.user_name=?1", nativeQuery = true)
    String findPwsByName(String userName);

    @Query(value = "select u.* from t_user u  where u.user_name =?1", nativeQuery = true)
    User findUserByUsername(String userName);  //根据用户名查询角色


    //根据登录名字模糊查询角色
    @Query(value = "select u.* from t_user u  where u.user_name like  %?1%", nativeQuery = true)
    List<User> findUserbyLikeName(String userName);

    //根据真实姓名模糊查询用户
    @Query(value = "select u.* from t_user u  where u.real_name like  %?1%", nativeQuery = true)
    List<User> findUserbyRealName(String realName);

    @Transactional
    void removeByUserId(Integer id);

    @Transactional
    @Query(value = "update t_user set user_name=?1,password=?2 where user_id=?3", nativeQuery = true)
    @Modifying
    public void updatePwdOrName(String userName, String password, Integer userId);

    @Transactional
    @Query(value = "update t_user set password=?1 where user_id=?2", nativeQuery = true)
    @Modifying
    public void updatePasswordById(String password, Integer userId);


    @Transactional
    @Query(value = "update t_user set email=?1,birthday=?2,gender=?3,phone=?4,real_name=?5 ,user_name=?6,user_position =?7 where user_id=?8", nativeQuery = true)
    @Modifying
    public void updateUserData(String email, String birthday, Integer gender, String phone, String realName, String username, String userPosition, Integer UserId);
}