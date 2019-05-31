package com.minsheng.oa.loginAndUser.service;


import com.minsheng.oa.loginAndUser.dao.RoleDao;
import com.minsheng.oa.loginAndUser.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService  {
    @Autowired
    RoleDao roleDao;

    //------------------------
    public List<Role> findRole(){
        return  roleDao.findRole();
    }     //查询所有role

}
