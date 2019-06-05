package com.minsheng.oa.main.email.SendMail;

import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.email.SchedulerMail;
import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.main.matter.service.MatterService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitMatterMail {         //  查询整合成map资源   调用Scheduler 启动多个线程
    @Autowired
    MatterService matterService;

    @Autowired
    UserService userService;
    public void sendMail() {
        System.out.println(matterService);
        List<Matter> matterList = matterService.findAllMatter();   //查询所有待办事项
        Map<String,String> mailMap=new HashMap<String, String>();
        Map<String,String> dateMap=new HashMap<String, String>();
        for (int i = 0; i < matterList.size(); i++) {
            String remindTime = matterList.get(i).getRemindTime();   //获得提醒时间
            Integer userId = matterList.get(i).getUserId();       //获得用户id
            String userEmail = userService.findUserByUserId(userId).getEmail();        //数据库查询数据并且获得用户emial
            System.out.println("userEmail"+userEmail);
            System.out.println("remaindTime"+remindTime);
            dateMap.put("date"+i,remindTime);
            mailMap.put("email"+i,userEmail);
        }
        SchedulerMail schedulerMail=new SchedulerMail();  // 创建线程
        try {

            schedulerMail.setTrigger(dateMap,mailMap);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
