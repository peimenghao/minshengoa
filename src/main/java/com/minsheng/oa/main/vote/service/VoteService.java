package com.minsheng.oa.main.vote.service;


import com.minsheng.oa.main.vote.dao.OptionUserDao;
import com.minsheng.oa.main.vote.dao.VoteOptionDao;
import com.minsheng.oa.main.vote.dao.VoteThemeDao;
import com.minsheng.oa.main.vote.model.VoteOption;
import com.minsheng.oa.main.vote.model.VoteTheme;
import com.minsheng.oa.main.vote.voteQuartz.VoteScheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VoteService {

    @Autowired
    VoteThemeDao voteThemeDao;

    @Autowired
    VoteOptionDao voteOptionDao;

    @Autowired
    OptionUserDao optionUserDao;

    @Autowired
    VoteScheduler voteScheduler;


    public List<VoteTheme> findAllVote() {      //查询投票所有信息

        return voteThemeDao.findAllVote();
    }

    public VoteTheme findVoteThemeByThemeId(Integer themeId) {   //根据投票主题id 查询该投票的 下面所有信息
        return voteThemeDao.findVoteThemeByThemeId(themeId);
    }

    public Integer saveVoteTheme(VoteTheme voteTheme) {     //保存一个投票主题
        VoteTheme vt = voteThemeDao.save(voteTheme);     //保存到数据库 并返回实体数据；
        Integer themeId = vt.getThemeId();
            voteScheduler.addVoteTrigger(themeId,vt.getEndTime());  //启动定时线程。到结束时间修改投票状态；


        return themeId;

    }

    public void saveVoteOption(VoteOption voteOption) {  //保存一个投票选项

        voteOptionDao.save(voteOption);
    }


    public void saveOptionUserId(Integer optionId, Integer userId) {  //保存option_user表
        System.out.println("optionId+userId--------" + optionId + userId);
        optionUserDao.saverOptionUserId(optionId, userId);

    }

    public void updateThemeStatus(Integer themeId){    //更新投票主题状态，设置过期
        System.out.println("voteThemeDao"+voteThemeDao);
        voteThemeDao.updateThemeStatus(themeId);
    }

}
