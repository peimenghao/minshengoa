package com.minsheng.oa.main.vote.voteQuartz;


import com.minsheng.oa.utils.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class VoteScheduler {                //quartz初始化

    @Autowired
    private Scheduler scheduler;


    public JobDetail addVoteJob(String jobName) {   //jobName 用themeid表示
        //任务实例
        JobDetail jobDetail = JobBuilder.newJob(VoteJob.class)         //Mailojob 必须实现job的接口
                .withIdentity(jobName, "voteJob")   //任务名和 任务组 名
                .storeDurably()
                .build();

        return jobDetail;
    }

    public void addVoteTrigger(Integer themeId, String endTime) {   //添加投票主题时候触发
        //创建调度器
        String cronTime = DateUtils.stringtoCron(endTime);  //字符串转cron 时间格式
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(themeId.toString(), "matterTrigger")  //触发器名称和 触发器组  名
                .startNow()//马上启动
                .usingJobData("themeId", themeId)    //键值方式传递值
                .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))  //日历
                .build();

        try {
            //调度器关联 job和trigger
            scheduler.scheduleJob(addVoteJob(themeId.toString()), trigger);
            scheduler.start();//启动调度器
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
