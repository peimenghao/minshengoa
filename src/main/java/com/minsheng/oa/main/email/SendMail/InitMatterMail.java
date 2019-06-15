package com.minsheng.oa.main.email.SendMail;

import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.email.SchedulerMail;
import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.main.matter.service.MatterService;
import com.minsheng.oa.utils.DateUtils;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitMatterMail {         //  查询整合成map资源 ，传值 创建多个SchedulerMail 启动多个线程

    @Autowired
    MatterService matterService;

    @Autowired
    UserService userService;

    @Autowired
    SchedulerMail schedulerMail;

    public void sendMail() {
        int no=0;         //map 键值对命名序号，
        System.out.println(matterService);
        List<Matter> matterList = matterService.findAllMatter();   //查询所有待办事项
        Map<String, String> mailMap = new HashMap<String, String>();
        Map<String, String> dateMap = new HashMap<String, String>();
        for (int i = 0; i < matterList.size(); i++) {
            String remindTime = matterList.get(i).getRemindTime();   //获得提醒时间
            System.out.println("提醒时间"+remindTime);
            Integer isOver=DateUtils.compareNowDate(remindTime);  // 和当前时间作比较  返回1 则表示未过期

            if(isOver==1){          //提醒时间如果没有过期

            Integer userId = matterList.get(i).getUserId();       //获得用户id
            String userEmail = userService.findUserByUserId(userId).getEmail();        //数据库查询数据并且获得用户emial
            System.out.println("userEmail"+userEmail);
            System.out.println("remindTime"+remindTime);
            dateMap.put("date"+no,remindTime);
            mailMap.put("email"+no,userEmail);
            no=no+1;
            }
        }
        try {

            schedulerMail.setTrigger(dateMap,mailMap);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
