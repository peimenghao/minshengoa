package com.minsheng.oa.main.vote.service;


import com.minsheng.oa.main.vote.dao.VoteOptionDao;
import com.minsheng.oa.main.vote.dao.VoteThemeDao;
import com.minsheng.oa.main.vote.model.VoteOption;
import com.minsheng.oa.main.vote.model.VoteTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VoteService {

    @Autowired
    VoteThemeDao VoteThemeDao;

    @Autowired
    VoteOptionDao voteOptionDao;


    public List<VoteTheme> findAllVote(){      //查询投票所有信息

        return  VoteThemeDao.findAllVote();
    }

    public VoteTheme findVoteThemeByThemeId(Integer themeId){   //根据投票主题id 查询该投票的 下面所有信息
        return  VoteThemeDao.findVoteThemeByThemeId(themeId);
    }

    public Integer saveVoteTheme(VoteTheme voteTheme){     //保存一个投票主题

        VoteTheme  vt=VoteThemeDao.save(voteTheme);
        return  vt.getThemeId();

    }

    public void saveVoteOption(VoteOption voteOption){  //保存一个投票选项

        voteOptionDao.save(voteOption);
    }





//    public Integer newsCount(){
//
//        return newsDao.newsCount();
//    }


//    public Map<String,Object> findNews(Integer pageNo){
//        Integer tatolcount = newsDao.newsCount();
//        Page page = new Page();
//        page.setTotalCount(tatolcount);
//        page.setPageNo(pageNo);   //页码
//        Integer startNum = page.getStartNum(); //起始行号
//        List<News>  news = newsDao.findNews(startNum, 5);
//
//        Integer   totalPage= page.getTotalPage();
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("map",news);
//        map.put("totalPage",totalPage);
//        return map;
//    }


}
