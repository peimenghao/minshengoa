package com.minsheng.oa.loginAndUser.dao;

import com.minsheng.oa.loginAndUser.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentDao extends JpaRepository<Department, Integer> {

    @Query(value = "select d.* from t_department d", nativeQuery = true)
    List<Department> find();
}
