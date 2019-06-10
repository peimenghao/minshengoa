package com.minsheng.oa.loginAndUser.service;


import com.minsheng.oa.loginAndUser.dao.DepartmentDao;
import com.minsheng.oa.loginAndUser.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DepartmentService {

    @Autowired
    DepartmentDao departmentDao;

    public List<Department> findAll() {
        List<Department> departments = departmentDao.find();
        return departments;
    }


    public Department findDepartmentByDepartId(Integer departId){
        Department department=  departmentDao.getDepartmentByDepartId(departId);
        department.getUsers().toString();
        return department;
    }

}
