package com.minsheng.oa.main.matter.controller;

import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.main.matter.service.MatterService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

@Path("/matter")
public class MatterController {

    @Autowired
    MatterService matterService;

    @Autowired
    ResultMap resultMap;



    @Path("/getMatter")
    @GET
    @Produces("application/json")        //获得所有待办事项
    public Map<String,Object> getMatter(@QueryParam("userId") Integer userId){
        Map<String,Object> map = matterService.findMatterByUserId(userId);
        return map;
    }

    @Path("/saveMatter")
    @POST
    @Produces("application/json")        //新建待办事项
    public Map<String,Object> saveMatter(@BeanParam Matter matter){
        Map<String,Object> map = matterService.save(matter);
        matter.getRemindTime();
        System.out.println(matter.getRemindTime());
        return map;
    }


    @Path("/getAllMatter")
    @GET
    @Produces("application/json")        //获得所有待办事项
    public Map<String,Object> getMatter(){
        List<Matter> map = matterService.findAllMatter();
        return resultMap.resutSuccessDate(map);
    }



}
