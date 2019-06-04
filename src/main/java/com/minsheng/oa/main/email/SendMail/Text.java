package com.minsheng.oa.main.email.SendMail;

import com.minsheng.oa.main.email.SchedulerMail;
import org.quartz.SchedulerException;

import java.util.HashMap;
import java.util.Map;

public class Text {
    public static void main(String[] args) throws SchedulerException {
        SchedulerMail  schedulerMail=new SchedulerMail();  // 创建线程
        Map<String,String> dateMap=new HashMap<String, String>();
        dateMap.put("date"+0,"2019-06-04 16:37:59");
        dateMap.put("date"+1,"2019-06-04 16:37:59");
        Map<String,String> mailMap=new HashMap<String, String>();
        mailMap.put("toMail"+0,"281458696@qq.com");
        mailMap.put("toMail"+1,"815311575@qq.com");
        schedulerMail.setTrigger(dateMap,mailMap);
    }
}
