package com.minsheng.oa.main.matter.service;


import com.alibaba.fastjson.JSONObject;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.matter.dao.MatterDao;
import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.utils.resultMap.ResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Timer;

@Slf4j
@Service
public class MatterService {

    @Autowired
    MatterDao matterDao;

    @Autowired
    UserService userService;

    @Autowired
    ResultMap resultMap;



    private static Timer timer = new Timer();

    public Map<String, Object> findMatterByUserId(Integer userId) {//根据userid数据库,查询此用户所有待办事项
        List<Matter> matters = matterDao.findMatterByUserId(userId);
        Map<String, Object> map = resultMap.resutSuccessDate(matters);
        return map;
    }

    public Map<String, Object> save(Matter matter) {         //新建待办事项
        matterDao.save(matter);                         //保存待办事项到数据库
        User user = userService.findUserByUserId(matter.getUserId());  //查询用户信息
        String userName = user.getUserName();
        String email = user.getEmail();
        String title = matter.getTitle();
        String remindTime = matter.getRemindTime();            //获得待办时间

        Map<String, Object> map = resultMap.resutSuccess();
        return map;
    }


    private String mailMessage(String email, String userName, String title, String remindTime) { //组装邮件报文
        JSONObject root = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", "sms_mail");  // 后面判断是否是发送邮件类型
        JSONObject content = new JSONObject();
        content.put("mail", email);               //邮箱
        content.put("userName", userName);      //用户名
        content.put("title", title);             //标题
        content.put("remindTime", remindTime);             //代办时间
        root.put("header", header);
        root.put("content", content);
        return root.toJSONString();
    }

    /*
    保存以后定时发送邮件流程：
    MatterService（save）————注入MyTask邮件需要组装的数据                                 ————到时间运行任务MyTask
                        —————————————————————————————————————创建待办时间点ReadyRunTask

     */
}
