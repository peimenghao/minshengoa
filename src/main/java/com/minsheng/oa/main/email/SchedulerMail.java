package com.minsheng.oa.main.email;


import com.minsheng.oa.utils.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;


public class SchedulerMail{


    public JobDetail setJobDetail(String jobName) {
        //任务实例
        JobDetail jobDetail = JobBuilder.newJob(MailJob.class)         //Mailojob 必须实现job的接口
                .withIdentity(jobName, "group1")   //任务名和 任务组 名
                .usingJobData("jobDateMessage", "job信息")  //传map 值
                .storeDurably()
                .build();
        return jobDetail;

    }

    public void setTrigger(Map<String,String> dateMap, Map<String,String> emailMap) throws SchedulerException {

        for(int i=0;i<dateMap.size();i++) {

            //"2019-06-04 10:10:00"  ,  String 日期格式
            //创建调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            System.out.println("第一次datetime 转化"+dateMap.get("date"+i));

            String cronTime = DateUtils.stringtoCron(dateMap.get("date"+i));  //字符串转cron
            //触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(i + "trigger", "group1")  //触发器名称和 触发器组  名
                    .startNow()//马上启动
                    .usingJobData("email", emailMap.get("email"+i))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))  //日历
                    //  .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))  // 重复重发间隔时间
                    // .startAt(startDate)
                    // .endAt() 结束时间
                    .build();

            scheduler.scheduleJob(setJobDetail("job"+i), trigger);  //调度器关联任务和触发器，按照触发器定义的条件执行任务
            scheduler.start();//启动调度器

        }

    }
    //调度器  从工厂中获取调度的实例  （new  StdSchedulerFactory ）
    public void setScheduler() throws SchedulerException {

    }
}
