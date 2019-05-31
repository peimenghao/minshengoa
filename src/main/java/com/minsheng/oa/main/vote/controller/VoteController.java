package com.minsheng.oa.main.vote.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.minsheng.oa.main.vote.model.VoteOption;
import com.minsheng.oa.main.vote.model.VoteTheme;
import com.minsheng.oa.main.vote.service.VoteService;
import com.minsheng.oa.utils.DateUtils;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Path("/vote")
public class VoteController {

    @Autowired
    VoteService voteService;

    @Autowired
    ResultMap resultMap;


    @Path("/getVoteById")  //根据id获得一个投票所有内容
    @GET
    @Produces("application/json")
    public Map<String, Object> getNewsById(@QueryParam("themeId") String themeId) {
        VoteTheme voteTheme = voteService.findVoteThemeByThemeId(Integer.valueOf(themeId));

        return resultMap.resutSuccessDate(voteTheme);
    }


    @Path("/getAllVote")  //根据投票所有内容
    @GET
    @Produces("application/json")
    public Map<String, Object> getAllVote() {
        List<VoteTheme> vote = voteService.findAllVote();

        return resultMap.resutSuccessDate(vote);
    }

    @Path("/saveVote")  //保存一个vote
    @POST
    @Produces("application/json")
    @ResponseBody
    public Map<String, Object> saveVote(Map<String, Object> vote) {
        //  {vote={content=谁最厉害, author=张三, endTime=12, anonymous=1, isSelectOne=1,
        // voteOptionList=[{content=孙悟空, optionThemeId=1},
        // {content=钢铁侠, optionThemeId=1},
        // {content=鸣人, optionThemeId=1}]}}    //投票的所有 信息       知识点：传过来的map 里面如果还有集合， 就是自动被转成list 格式
        System.out.println(vote);
        VoteTheme voteTheme = new VoteTheme();
        VoteOption voteOption = new VoteOption();
        Map<String, Object> map = (Map<String, Object>) vote.get("vote");
        String content = (String) map.get("content");
        String author = (String) map.get("author");
        String endTime = (String) map.get("endTime");
        Integer anonymous = (Integer) map.get("anonymous");
        Integer isSelectOne = (Integer) map.get("isSelectOne");

        voteTheme.setContent(content);
        voteTheme.setAuthor(author);
        voteTheme.setEndTime(endTime);
        voteTheme.setAnonymous(anonymous);
        voteTheme.setIsSelectOne(isSelectOne);
        voteTheme.setCreatTime(DateUtils.getTimestamp().toString());
        Integer thmemeId = voteService.saveVoteTheme(voteTheme);

        List<Object> voteOptionList = (ArrayList<Object>) map.get("voteOptionList");  //获得list
        JSONArray OptionjArray = (JSONArray) JSON.toJSON(voteOptionList); //list 转json 数组
        //json 数组转list  对象集合
        List<VoteOption> voteOptionlist = JSONObject.parseArray(OptionjArray.toJSONString(), VoteOption.class);
        for (int i = 0; i < voteOptionlist.size(); i++) {
            VoteOption voteOption1 = new VoteOption();
            voteOption1.setContent(voteOptionlist.get(i).getContent());  //保存投票选项内容
            voteOption1.setOptionThemeId(thmemeId);                    //保存投票主题

            voteService.saveVoteOption(voteOption1);
        }
        return resultMap.resutSuccess();
    }
}
