package com.minsheng.oa.main.matter.controller;

import com.minsheng.oa.main.email.SendMail.InitMatterMail;
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

    @Autowired
    InitMatterMail initMatterMail;



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
        initMatterMail.sendMail();
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

    @Path("/updateMatter")
    @POST
    @Produces("application/json")        //更新指定待办事项
    public Map<String,Object> updateMatter(@BeanParam Matter matter){
        matterService.updateMatter(matter);
        return resultMap.resutSuccess();
    }

//    @Path("/getMatterPage")
//    @GET
//    @Produces("application/json")
//    public Map<String,Object> findMatterPage(@QueryParam("num") Integer num){
//        int pageSize=3;
//        List<Matter> MatterList = matterService.findMatterPage(num,pageSize);
//        return resultMap.resutSuccessDate(MatterList);
//    }
}
