package com.minsheng.oa.loginAndUser.controller;


import com.google.common.io.ByteStreams;
import com.minsheng.oa.loginAndUser.model.Headpic;
import com.minsheng.oa.loginAndUser.model.Role;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.HeadpicService;
import com.minsheng.oa.loginAndUser.service.RoleService;
import com.minsheng.oa.loginAndUser.service.UserRoleService;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.resource.dfs.FastDFSClientUtils;
import com.minsheng.oa.utils.FileUtil;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Path("/user")
@Configuration              //注意！！！！！ 不加这个  读不到yml 的value值，不知道为什么 fdsController 可以取到
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    HeadpicService headpicService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    ResultMap resultMap;

    @Value("${fdfs.resHost}")
    private  String resHost;         //配置文件中获取服务器id

    @Value("${fdfs.storagePort}") //配置文件中获取端口号
    private    String storagePort;


    @Path("/deleteByUserId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deleteUser(@QueryParam("userId") Integer userId) {             //
        userService.removeById(userId);
        return resultMap.resutSuccess("删除成功");
    }

    @Path("/updatePwdOrNameById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> updatePwdOrNameById(@BeanParam User user) {             //更改用户和密码

        userService.updatePwdOrName(user);
        return resultMap.resutSuccess("更新成功");
    }

    @Path("/updatePasswordById")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> updatePasswordById(@BeanParam User user) {             //更改用户和密码

        userService.updatePasswordById(user);
        return resultMap.resutSuccess("更新成功");
    }


    @Path("/updateUserData")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> updateUserData(@BeanParam User user) {             //更改用户和密码
        userService.updateUserData(user);
        return resultMap.resutSuccess("更新成功");
    }

    @Path("/getUsers")            //查询所有用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUsers() {

        List<User> users = userService.find();

        return resultMap.resutSuccessDate(users);
    }

    @Path("/getUserById")              //根据id查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUserById(@QueryParam("userId") Integer userId) {
        User user = userService.findUserByUserId(userId);
        return resultMap.resutSuccessDate(user);
    }

    @Path("/getUserbyUserName")   //根据登陆用户名查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUserbyUserName(@QueryParam("userName") String userName) {
        User user = userService.findUserbyUsername(userName);
        System.out.println(user);
        return resultMap.resutSuccessDate(user);
    }


    @Path("/getUserbyLikeName")   //根据登陆用户名模糊查询查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUserbyLikeName(@QueryParam("likeName") String likeName) {
        List<User> userList = userService.findUserbyLikeName(likeName);
        return resultMap.resutSuccessDate(userList);
    }

    @Path("/getUserByRealname")  //根据真实姓名查询用户
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUserbyRealName(@QueryParam("realName") String realName) {
        List<User> userList = userService.findUserbyRealName(realName);
        return resultMap.resutSuccessDate(userList);
    }

    @Path("/getUsersByDepartId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getUsersByDepartId(@QueryParam("departId") Integer departId) {             //根据部门id所有用户
        List<User> users = userService.findUserByDepartId(departId);
        return resultMap.resutSuccessDate(users);
    }

    @Path("/getRoles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getRole() {             //查询所有角色  级联查询到角色

        List<Role> roles = roleService.findRole();

        return resultMap.resutSuccessDate(roles);
    }

    @Path("/updateUserRole")      //设置用户角色
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> updateUserRole(@FormParam("userId") Integer userId,
                                              @FormParam("roleId") Integer roleId) {
        userRoleService.updateUserRole(userId, roleId);

        return resultMap.resutSuccess();
    }

    @Path("/setHeadpic") //上传用户头像
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Map<String, Object> upload(@FormDataParam("file") InputStream picInputStream,
                                      @FormDataParam("file") FormDataContentDisposition picDetail,
                                      @Context ServletContext ctx,
                                      @FormDataParam("userId") Integer userId
    ) throws Exception {

        String picName = picDetail.getFileName();  //互获得文件名字
        String fileSuffix = FileUtil.getFileExt(picName); //文件后缀
        if (!fileSuffix.equals("jpg") && !fileSuffix.equals("png") && !fileSuffix.equals("jpeg") && !fileSuffix.equals("jpg")) {
            return resultMap.resutError("图片格式不正确");
        }

        byte[] bytes = ByteStreams.toByteArray(picInputStream);
        String picPath = FastDFSClientUtils.upload(bytes, picName);  //字节流方式上传图片服务器

        //拼装服务器url
        String url = resHost + ":" + storagePort + "/" + picPath;
        System.out.println("url" + url);


        //创建用户
        User user = userService.findUserByUserId(userId);  //查出此用户
        //建立头像图片 对象
        Headpic headpic = new Headpic();
        if (user.getHeadpic()!=null) { //如果之前存在图片,就执行更新图片信息，服务器删除旧的
            //删除上一个图片 ，数据库+服务器
            headpic.setHeadpicId(user.getHeadpic().getHeadpicId());
            headpic.setUrl(url);
            headpic.setPicPath(picPath);
            headpicService.saveHeadpic(headpic);
            FastDFSClientUtils.delete("group1", user.getHeadpic().getPicPath());//fastdfs服务器删除上一个头像
        } else {   //如果之前没有存在头像 重新创建对象保存
            headpic.setUrl(url);
            headpic.setPicPath(picPath);
            user.setHeadpic(headpic);  //图片用户级联
            userService.save(user);
        }
        return resultMap.resutSuccess("上传成功");
    }
}
