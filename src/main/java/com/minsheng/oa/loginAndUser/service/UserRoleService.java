package com.minsheng.oa.loginAndUser.service;


import com.minsheng.oa.loginAndUser.dao.UserRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    UserRoleDao userRoleDao;

    //------------------------
    public void save(Integer  roleId ,Integer userId){
         userRoleDao.saverRoleAndUser(roleId,userId);


    }
}
