package com.minsheng.oa.loginAndUser.service;


import com.minsheng.oa.loginAndUser.dao.RoleDao;
import com.minsheng.oa.loginAndUser.dao.UserDao;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    ResultMap resultMap;

    public User getUser(String username, String password) {         //根据用户名和密码查询用户
        return userDao.findByUserNameAndPassword(username, password);

    }

    public List<User> find() {               //查询所有用户
        return userDao.findAll();
    }

    public void save(User user) {          //存储一个用户
            userDao.save(user);

    }

    public User findByUserName(String userName) {                  //根据用户名查询用户信息

        return userDao.findByUserName(userName);
    }

    public void removeById(Integer id) {           //移除一个用户
        userDao.removeByUserId(id);
    }

    public void updatePwdOrName(User user) {           //更新用户的 姓名  密码    ID

        userDao.updatePwdOrName(user.getUserName(), user.getPassword(), user.getUserId());

    }

    public void updatePasswordById(User user) {           //更新用户的  密码

        userDao.updatePasswordById( user.getPassword(), user.getUserId());

    }

    public void updateUserData(User user) {           //更新用户基本信息

        userDao.updateUserData( user.getEmail(),user.getBirthday(),user.getGender(),user.getPhone(),user.getRealName(),user.getUserName(),user.getUserId());

    }

    public String findPwsByName(String userName) {     //根据用户名查询密码

        return userDao.findPwsByName(userName);
    }

    public User findUserByUserId(Integer userId) {     //根据用户名id 查询用户
      User user=  userDao.findUserByUserId(userId);

        return user;
    }

    public  List<User> findUserbyLikeName(String likeName) {   //  模糊查询用户
        List<User> userList=  userDao.findUserbyLikeName(likeName);
        return userList;
    }

    public List<User> findUserByDepartId(Integer departId) {   //根据用户名id 查询用户
        List<User> users=  userDao.findUserByDepartId(departId);

        return users;
    }


}
