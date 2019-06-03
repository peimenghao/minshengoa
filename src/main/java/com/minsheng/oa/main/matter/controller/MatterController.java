package com.minsheng.oa.main.matter.controller;

import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.main.matter.service.MatterService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Map;
import java.util.Timer;

@Path("/matter")
public class MatterController {

    @Autowired
    MatterService matterService;

    @Autowired
    ResultMap resultMap;

    private static Timer timer=new Timer();

    @Path("/getMatter")
    @GET
    @Produces("application/json")        //获得所有待办事项
    public Map<String,Object> getMatter(@QueryParam("userId") Integer userId){
        Map<String,Object> Map = matterService.findMatterByUserId(userId);
        return Map;
    }

    @Path("/saveMatter")
    @POST
    @Produces("application/json")        //新建待办事项
    public Map<String,Object> saveMatter(@BeanParam Matter matter){
        Map<String,Object> Map = matterService.save(matter);
        return Map;
    }



}
