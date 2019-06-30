package com.minsheng.oa.loginAndUser.controller;


import com.minsheng.oa.loginAndUser.model.Department;
import com.minsheng.oa.loginAndUser.service.DepartmentService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/department")
public class DepartmentController {

    @Autowired
    ResultMap resultMap;

    @Autowired
    DepartmentService departmentService;

    @Path("/saveDepart")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> saveDepart(@BeanParam Department department) {            //查询所有的部门信息
         departmentService.saveDepart(department);
        return resultMap.resutSuccess();
    }

    @Path("/updateDapart")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> updateDapart(@BeanParam Department department) {            //查询所有的部门信息
        departmentService.saveDepart(department);
        return resultMap.resutSuccess();
    }



    @Path("/findDepartments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> findDepartments() {            //查询所有的部门信息
        List<Department> departments = departmentService.findAll();
        return resultMap.resutSuccessDate(departments);
    }


    @Path("/findDepartByDepartId")   //根据部门id 查询部门
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> findDepartmentByDepartId(@QueryParam("departId") Integer departId) {            //查询所有的部门信息
        Department department = departmentService.findDepartmentByDepartId(departId);
        return resultMap.resutSuccessDate(department);
    }

    @Path("/deleteDepatById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deleteDepatById(@QueryParam("departId") Integer departId) {            //查询所有的部门信息
        departmentService.deleteDepatById(departId);
        return resultMap.resutSuccess();
    }
}
