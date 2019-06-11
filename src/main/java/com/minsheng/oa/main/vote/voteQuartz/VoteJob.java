package com.minsheng.oa.main.vote.voteQuartz;


import com.minsheng.oa.main.vote.service.VoteService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;



@Component
public class VoteJob implements Job {

    private String jobDateMessage;

    private String triggerMessage;

    @Autowired
    private VoteService voteService;

    public void setTriggerMessage(String triggerMessage) {
        this.triggerMessage = triggerMessage;
    }


    public void setJobDateMessage(String jobDateMessage) {
        this.jobDateMessage = jobDateMessage;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("voteJob启动");
        JobDataMap triggerDataMap = context.getTrigger().getJobDataMap();  //trigger的 map传值对象
        Object themeId = triggerDataMap.get("themeId");    //根据触发器设置的 key取出 value
        System.out.println("job-----------id" + themeId);
        System.out.println(voteService);
        voteService.updateThemeStatus((Integer) themeId);
    }
}







