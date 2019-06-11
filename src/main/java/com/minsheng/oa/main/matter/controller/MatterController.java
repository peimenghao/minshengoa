package com.minsheng.oa.main.matter.controller;

import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.main.matter.service.MatterService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
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


    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";


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
    public Map<String,Object> saveMatter(@BeanParam Matter matter){   //后台判断时间： 提醒时间必须比当前时间大
        matterService.save(matter);

        return resultMap.resutSuccess();
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
    @Produces("application/json")         //更新指定待办事项
    public Map<String,Object> updateMatter(@BeanParam Matter matter){ //后台判断时间： 提醒时间必须比当前时间大
        System.out.println(matter.getMatterId());
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
