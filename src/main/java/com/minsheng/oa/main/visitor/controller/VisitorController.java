package com.minsheng.oa.main.visitor.controller;

import com.minsheng.oa.main.visitor.model.Visitor;
import com.minsheng.oa.main.visitor.service.VisitorService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Map;

@Path("/visitor")
public class VisitorController {

    @Autowired
    VisitorService visitorService;

    @Autowired
    ResultMap resultMap;
    @Path("/getAllVisitor")
    @GET
    @Produces("application/json")        //获得所有来访信息
    public Map<String,Object> getAllVisitor(){
        Map<String,Object> map = visitorService.findAll();
        return map;
    }

    @Path("/saveVisitor")
    @POST
    @Produces("application/json")        //添加来访信息
    public Map<String,Object> saveVisitor(@BeanParam  Visitor visitor){
        Map<String,Object> map = visitorService.save(visitor);
        return map;
    }

    @RequiresRoles({"admin"})
    @Path("/deleteVisitor")
    @GET
    @Produces("application/json")
    public Map<String,Object> deleteVisitor(@QueryParam("visitorId")Integer visitorId){
         visitorService.deleteByVisitorId(visitorId);
        return resultMap.resutSuccess("success");
    }

    public static void main(String[] args) {
        System.out.printf("as撒阿三阿德是");
    }
}
