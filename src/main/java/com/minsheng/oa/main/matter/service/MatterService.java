package com.minsheng.oa.main.matter.service;


import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.email.SchedulerMail;
import com.minsheng.oa.main.matter.dao.MatterDao;
import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.utils.DateUtils;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MatterService {

    @Autowired
    MatterDao matterDao;

    @Autowired
    UserService userService;

    @Autowired
    ResultMap resultMap;

    @Autowired
    SchedulerMail schedulerMail;

    public Map<String, Object> findMatterByUserId(Integer userId) {//根据userid数据库,查询此用户所有待办事项
        List<Matter> matters = matterDao.findMatterByUserId(userId);
        Map<String, Object> map = resultMap.resutSuccessDate(matters);
        return map;
    }

    public void save(Matter matter) {         //新建待办事项
        matter.setIsOver(0);
        matter.setCreateTime(DateUtils.getTimestamp().toString());
        matterDao.save(matter);                         //保存待办事项到数据库
        User user = userService.findUserByUserId(matter.getUserId());
        try {
            schedulerMail.setSaveMaterTrigger(matter.getMatterId(), matter.getRemindTime(), user.getEmail());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public List<Matter> findAllMatter() {//根据userid数据库,查询此用户所有待办事项
        List<Matter> matterList = matterDao.findAll();
        System.out.println(matterList);
        return matterList;
    }

    public List<Matter> findMatterByOver() {//查询 is_over
        List<Matter> matterList = matterDao.findMatterByOver();
        System.out.println(matterList);
        return matterList;
    }

    public void updateMatter(Matter matter) {
        matterDao.updateMatter(matter.getContent(), matter.getRemindTime(), matter.getTitle(), matter.getMatterId()); //保存matter
        User user = userService.findUserByUserId(matter.getUserId());        //获得用户信息
        String remindTime = matter.getRemindTime();                //获得提醒时间
        String cronTime = DateUtils.stringtoCron(remindTime);       //cron提醒时间

        System.out.println("matterId" + matter.getMatterId().toString() + "----email" + user.getEmail() + "");
        //启动触发器
        schedulerMail.modifyJobTime(matter.getMatterId().toString(), "matterTrigger",
                                   cronTime, user.getEmail(),
                                   matter.getMatterId());     //修改定时器时间，同时添加邮件信息

    }

    public void updateMatterOver(Integer matterId) {
        matterDao.updateMatterOver(matterId);
    }


//    public List<Matter> findMatterPage(Integer num,Integer size) {//分页查询
//        List<Matter> matterList = (List<Matter>) matterDao.getPage(num, size);
//        return matterList;
//    }

}
