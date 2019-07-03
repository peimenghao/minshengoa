package com.minsheng.oa.main.visitor.controller;

import com.minsheng.oa.main.visitor.model.Visitor;
import com.minsheng.oa.main.visitor.service.VisitorService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

@Path("/visitor")
public class VisitorController {

    @Autowired
    VisitorService visitorService;

    @Autowired
    ResultMap resultMap;


    @Path("/saveVisitor")
    @POST
    @Produces("application/json")        //添加来访信息
    public Map<String,Object> saveVisitor(@BeanParam  Visitor visitor){
     visitorService.save(visitor);
        return resultMap.resutSuccess();
    }

    @Path("/getAllVisitor")
    @GET
    @Produces("application/json")        //获得所有来访信息
    public Map<String,Object> getAllVisitor(){
        List<Visitor> visitors = visitorService.findAll();
        return resultMap.resutSuccessDate(visitors);
    }


    @Path("/updateVisitor")
    @POST
    @Produces("application/json")        //添加来访信息
    public Map<String,Object> updateVisitor(@BeanParam  Visitor visitor){
    visitorService.save(visitor);
        return resultMap.resutSuccess();
    }




    @Path("/deleteVisitor")
    @GET
    @Produces("application/json")
    public Map<String,Object> deleteVisitor(@QueryParam("visitorId")Integer visitorId){
         visitorService.deleteByVisitorId(visitorId);
        return resultMap.resutSuccess("success");
    }

}
