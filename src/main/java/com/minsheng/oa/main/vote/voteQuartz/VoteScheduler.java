package com.minsheng.oa.main.vote.voteQuartz;


import com.minsheng.oa.utils.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


public class VoteScheduler {                //quartz初始化


    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    public JobDetail addVoteJob(String jobName) {   //jobName 用themeid表示
        //任务实例
        JobDetail jobDetail = JobBuilder.newJob(VoteJob.class)         //Mailojob 必须实现job的接口
                .withIdentity(jobName, "voteJob")   //任务名和 任务组 名
               // .usingJobData("jobDateMessage", "job信息")  //传map 值
                .storeDurably()
                .build();

        return jobDetail;
    }

    public void addVoteTrigger(Integer themeId, String endTime) throws SchedulerException {   //添加投票主题时候触发
        System.out.println("Scheduler()-----------id"+themeId+"endtime"+endTime);
        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        String cronTime = DateUtils.stringtoCron(endTime);  //字符串转cron 时间格式
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(themeId.toString(), "matterTrigger")  //触发器名称和 触发器组  名
                .startNow()//马上启动
                .usingJobData("themeId", themeId)    //键值方式传递值
                .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))  //日历
                //  .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))  // 重复重发间隔时间
                // .startAt(startDate)
                // .endAt() 结束时间
                .build();

        scheduler.scheduleJob(addVoteJob(themeId.toString()), trigger);  //调度器关联任务和触发器，按照触发器定义的条件执行任务
        scheduler.start();//启动调度器

    }



    //调度器  从工厂中获取调度的实例  （new  StdSchedulerFactory ）
    public void setScheduler() throws SchedulerException {

    }
}
