package com.minsheng.oa.main.email;


import com.minsheng.oa.utils.DateUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SchedulerMail {                //quartz初始化


    @Autowired
    Scheduler scheduler;

    public JobDetail saveMatterJob(String jobName) {   //jobName 用matter 的id表示
        //任务实例
        JobDetail jobDetail = JobBuilder.newJob(MailJob.class)         //Mailojob 必须实现job的接口
                .withIdentity(jobName, "matterJob")   //任务名和 任务组 名
               // .usingJobData("jobDateMessage", "job信息")  //传map 值
                .storeDurably()
                .build();

        return jobDetail;
    }

    public void setSaveMaterTrigger(Integer matterId, String remindTime, String email) throws SchedulerException {   //保存matter时候触发

        String cronTime = DateUtils.stringtoCron(remindTime);  //字符串转cron 时间格式
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(matterId.toString(), "matterTrigger")  //触发器名称和 触发器组  名
                .startNow()//马上启动
                .usingJobData("email", email)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))  //日历
                //  .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))  // 重复重发间隔时间
                // .startAt(startDate)
                // .endAt() 结束时间
                .build();
        System.out.println(scheduler);
        scheduler.scheduleJob(saveMatterJob(matterId.toString()), trigger);  //调度器关联任务和触发器，按照触发器定义的条件执行任务
        scheduler.start();//启动调度器

    }


    public JobDetail setJobDetail(String jobName) {      //全局任务
        //任务实例
        JobDetail jobDetail = JobBuilder.newJob(MailJob.class)         //Mailojob 必须实现job的接口
                .withIdentity(jobName, "group1")   //任务名和 任务组 名
                .usingJobData("jobDateMessage", "job信息")  //传map 值
                .storeDurably()
                .build();
        return jobDetail;

    }

    public void setTrigger(Map<String, String> dateMap, Map<String, String> emailMap) throws SchedulerException {   //局扫描触发器

        for (int i = 0; i < dateMap.size(); i++) {

            //"2019-06-04 10:10:00"  ,  String 日期格式
            //创建调度器
            System.out.println("第一次datetime 转化" + dateMap.get("date" + i));

            String cronTime = DateUtils.stringtoCron(dateMap.get("date" + i));  //字符串转cron
            //触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(i + "trigger", "group1")  //触发器名称和 触发器组  名
                    .startNow()//马上启动
                    .usingJobData("email", emailMap.get("email" + i))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronTime))  //日历
                    //  .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatSecondlyForever(5))  // 重复重发间隔时间
                    // .startAt(startDate)
                    // .endAt() 结束时间
                    .build();

            scheduler.scheduleJob(setJobDetail("job" + i), trigger);  //调度器关联任务和触发器，按照触发器定义的条件执行任务
            scheduler.start();//启动调度器

        }

    }


    /**
     *  修改一个任务的触发时间
     */
    public  void modifyJobTime(String jobName, String jobGroupName,
                                     String triggerName, String triggerGroupName,
                                     String cron,String email) {
        System.out.println(triggerName+triggerGroupName+cron+email+scheduler);
        try {

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            System.out.println(triggerKey+"----------triggerKey");
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                triggerBuilder.usingJobData("email",email);
                // 创建Trigger对象,调用 rescheduleJob 结束
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);


                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

// 移除一个任务

    public  void removeJob(String jobName, String jobGroupName,
                                 String triggerName, String triggerGroupName) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
