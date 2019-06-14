package com.minsheng.oa.loginAndUser.controller;


import com.minsheng.oa.loginAndUser.model.Role;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.RoleService;
import com.minsheng.oa.loginAndUser.service.UserRoleService;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/user")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    ResultMap resultMap;




    @Path("/deleteByUserId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> deleteUser(@QueryParam("userId")Integer userId){             //
        userService.removeById(userId);
        return  resultMap.resutSuccess("删除成功");
    }

    @Path("/updatePwdOrNameById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> updatePwdOrNameById(@BeanParam User user){             //更改用户和密码

        userService.updatePwdOrName(user);
        return  resultMap.resutSuccess("更新成功");
    }

    @Path("/updatePasswordById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> updatePasswordById(@BeanParam User user){             //更改用户和密码

        userService.updatePasswordById(user);
        return  resultMap.resutSuccess("更新成功");
    }


    @Path("/updateUserData")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> updateUserData(@BeanParam User user){             //更改用户和密码
        userService.updateUserData(user);
        return  resultMap.resutSuccess("更新成功");
    }

    @Path("/getUsers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> getUsers(){             //查询所有用户

        List<User>  users = userService.find();

        return  resultMap.resutSuccessDate(users);
    }

    @Path("/getUserById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> getUserById(@QueryParam("userId") Integer userId){             //查询所有用户
        User user =userService.findUserByUserId(userId);
        return  resultMap.resutSuccessDate(user);
    }


    @Path("/getUserbyLikeName")   //根据登陆用户名查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> getUserbyLikeName(@QueryParam("likeName") String  likeName){
        List<User> userList =userService.findUserbyLikeName(likeName);
        return  resultMap.resutSuccessDate(userList);
    }

    @Path("/getUserbyRealName")  //根据真实姓名查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> getUserbyRealName(@QueryParam("realName") String  realName){
        List<User> userList =userService.findUserbyRealName(realName);
        return  resultMap.resutSuccessDate(userList);
    }

    @Path("/getUsersByDepartId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> getUsersByDepartId(@QueryParam("departId") Integer departId){             //根据部门id所有用户
        List<User> users =userService.findUserByDepartId(departId);
        return  resultMap.resutSuccessDate(users);
    }

    @Path("/getRoles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getRole(){             //查询所有角色  级联查询到角色

        List<Role>  roles = roleService.findRole();

        return  resultMap.resutSuccessDate(roles);
    }

    @Path("/updateUserRole")      //设置用户角色
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> updateUserRole(@FormParam("userId") Integer userId,
                                              @FormParam("roleId") Integer roleId){
            userRoleService.updateUserRole(userId,roleId);

        return  resultMap.resutSuccess();
    }


}
