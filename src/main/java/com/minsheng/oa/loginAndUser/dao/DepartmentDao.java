package com.minsheng.oa.loginAndUser.dao;

import com.minsheng.oa.loginAndUser.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentDao extends JpaRepository<Department, Integer> {



    @Modifying
    @Transactional
    Department  save(Department department); //保存部门信息,如果带有id 则是更新部门信息


    @Query(value = "select d.* from t_department d", nativeQuery = true)
    List<Department> find();  //查询所有部门


    Department getDepartmentByDepartId(Integer departId);  //根据id 查询部门

    @Transactional
    @Modifying
    void  deleteByDepartId(Integer departId);  //根据id删除部门




}
