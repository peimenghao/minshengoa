package com.minsheng.oa.loginAndUser.dao;

import com.minsheng.oa.loginAndUser.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DepartmentDao extends JpaRepository<Department, Integer> {

    @Query(value = "select d.* from t_department d", nativeQuery = true)
    List<Department> find();

    @Query(value = "select depart_id,depart_name,depart_desc from t_department d", nativeQuery = true)
    Set<Object> findDepartIdAndName();
}
