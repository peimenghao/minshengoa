package com.minsheng.oa.main.email;


import com.minsheng.oa.main.email.SendMail.MailConfiguration;
import com.minsheng.oa.main.email.SendMail.SendMail;
import com.minsheng.oa.utils.DateUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class MailJob implements Job {


    @Autowired
    SendMail sendMail ;

    @Autowired
    MailConfiguration mailConfiguration;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(DateUtils.getTimestamp());

        JobDataMap triggerDataMap=context.getTrigger().getJobDataMap();  //trigger的 map传值对象
        String  email=triggerDataMap.getString("email");      //根据触发器设置的 key取出 value

        System.out.println(email);
        System.out.println("job启动");


        sendMail.sendMail(email);  //发送邮件






    }
}







