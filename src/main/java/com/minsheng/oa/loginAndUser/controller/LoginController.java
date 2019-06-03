package com.minsheng.oa.loginAndUser.controller;


import com.minsheng.oa.loginAndUser.model.Department;
import com.minsheng.oa.loginAndUser.model.Role;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.DepartmentService;
import com.minsheng.oa.loginAndUser.service.RoleService;
import com.minsheng.oa.loginAndUser.service.UserRoleService;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.utils.DateUtils;
import com.minsheng.oa.utils.JWTUtil;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/login")
public class LoginController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ResultMap resultMap;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    DepartmentService departmentService;


    @Path("/register")
    @POST
    @Produces("application/json")
    public Map<String, Object> register(@BeanParam User user, @FormParam("departId") Integer departId) {             //注册用户
        User u = userService.findByUserName(user.getUserName());  //根据用户名查询用户
        if (u != null) {                                   //判断用户是否已存在
            return resultMap.resutError("用户名重复");
        }
        user.setCreateTime(DateUtils.getTimestamp().toString());
        user.getDepartment().setDepartId(departId);

//        Role role=new Role();
//        role.setRoleId(2);
//        Set<Role> roles=new HashSet<Role>();
//        roles.add(role);
//        user.setRoleList(roles);

        userService.save(user);                 //保存到数据库
        User user1 = userService.findByUserName(user.getUserName());//获得杠保存到数据库的userid
        System.out.println(user1.getUserId());
        userRoleService.save(2, user1.getUserId());
        return resultMap.resutSuccess();
    }


    @Path("/login")
    @POST
    @Produces("application/json")
    public Map<String, String> doLogin(@FormParam("userName") String userName,
                                       @FormParam("password") String password) {
        System.out.println(userName + password);
        Map<String, String> map = new HashMap<String, String>();
        String realPws = userService.findPwsByName(userName);
        if (realPws == null) {
            map.put("msg", "用户名错误");
            return map;
        } else if (!realPws.equals(password)) {
            map.put("msg", "密码错误");
            return map;
        } else {
            map.put("msg", "success");
            map.put("token", JWTUtil.createToken(userName));
            return map;
        }
    }


    @Path("/loginPage")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> loginPage() {             //查询所有用户

        List<Department> departments = departmentService.findAll();


        return resultMap.resutSuccessDate(departments);
    }


    @Path("/getUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, List<User>> getUsers() {             //查询所有用户

        List<User> users = userService.find();

        Map<String, List<User>> map = new HashMap<String, List<User>>();
        map.put("users", users);
        return map;
    }

    @Path("/getRole")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getRole() {       //查询所有用户

        List<Role> roles = roleService.findRole();
        return resultMap.resutSuccessDate(roles);
    }


}
