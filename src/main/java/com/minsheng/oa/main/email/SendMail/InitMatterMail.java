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
public class InitMatterMail {
    @Autowired
    MatterService matterService; //=new MatterService() ;

    @Autowired
    UserService userService; //= new UserService();

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

//    public static void main(String[] args) throws SchedulerException {


//        SchedulerMail  schedulerMail=new SchedulerMail();  // 创建线程
//        Map<String,String> dateMap=new HashMap<String, String>();
//        dateMap.put("date"+0,"2019-06-05 09:08:59");
//        dateMap.put("date"+1,"2019-06-05 09:08:59");
//        Map<String,String> mailMap=new HashMap<String, String>();
//        mailMap.put("toMail"+0,"281458696@qq.com");
//        mailMap.put("toMail"+1,"815311575@qq.com");
//        schedulerMail.setTrigger(dateMap,mailMap);
//    }
}
