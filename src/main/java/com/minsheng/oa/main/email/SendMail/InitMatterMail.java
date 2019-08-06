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

/**
 * 查询出所有待办事项，重新启动邮件发送任务
 *
 * 查询整合成map资源 ，传值 创建多个SchedulerMail 启动多个线程
 */
@Component
public class InitMatterMail {

    @Autowired
    MatterService matterService;

    @Autowired
    UserService userService;

    @Autowired
    SchedulerMail schedulerMail;

    public void sendMail() {
        int no=0;         //map 键值对命名序号，
        List<Matter> matterList = matterService.findMatterByOver();   //查询所有未过期的matter

        Map<String, String> mailMap = new HashMap<String, String>();
        Map<String, String> dateMap = new HashMap<String, String>();
        Map<String, String> matterIdMap = new HashMap<String, String>();

        for (int i = 0; i < matterList.size(); i++) {
            Matter matter= matterList.get(i);      //获得matter 对象。

            String  remindTime = matter.getRemindTime();   //获得提醒时间   第二重验证，看matter时间是否过期
            Integer isOver=DateUtils.compareNowDate(remindTime);     // 和当前时间作比较  返回1 则表示未过期
            if(isOver==1){          //提醒时间如果没有过期
            Integer userId =matter.getUserId();       //获得用户id
            String userEmail = userService.findUserByUserId(userId).getEmail();        //数据库查询数据并且获得用户emial
            dateMap.put("date"+no,remindTime);
            mailMap.put("email"+no,userEmail);
            matterIdMap.put("matterId"+no,matter.getMatterId().toString());
            no=no+1;
            }
        }
        try {
            schedulerMail.setTrigger(dateMap,mailMap,matterIdMap);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
