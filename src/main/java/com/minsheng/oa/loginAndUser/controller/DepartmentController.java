package com.minsheng.oa.loginAndUser.controller;


import com.minsheng.oa.loginAndUser.model.Department;
import com.minsheng.oa.loginAndUser.service.DepartmentService;
import com.minsheng.oa.loginAndUser.service.RoleService;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/department")
public class DepartmentController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ResultMap resultMap;

    @Autowired
    DepartmentService departmentService;


    @Path("/findDepartments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> findDepartments() {            //根据id 删除用户
        List<Department> departments = departmentService.findAll();
        return resultMap.resutSuccessDate(departments);
    }


//    @Path("/registPage")  //跳转到注册页面   获得部门信息   只能获得set 集合值，非json格式
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Map<String, Object> loginPage(){
//
//        Set<Object> departments = departmentService.findDepartIdAndName();
//
//
//        return resultMap.resutSuccessDate(departments);
//    }

}
