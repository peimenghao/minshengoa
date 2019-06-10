package com.minsheng.oa.main.matter.service;


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

@Slf4j
@Service
public class MatterService {

    @Autowired
    MatterDao matterDao;

    @Autowired
    UserService userService;

    @Autowired
    ResultMap resultMap;


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

    public List<Matter> findAllMatter() {//根据userid数据库,查询此用户所有待办事项
        List<Matter> matterList = matterDao.findAll();
        System.out.println(matterList);
        return matterList;
    }

    public void updateMatter(Matter matter) {//根据userid数据库,查询此用户所有待办事项
         matterDao.updateMatter(matter.getContent(),matter.getRemindTime(),matter.getTitle(),matter.getMatterId());

    }


//    public List<Matter> findMatterPage(Integer num,Integer size) {//分页查询
//        List<Matter> matterList = (List<Matter>) matterDao.getPage(num, size);
//        return matterList;
//    }

}
