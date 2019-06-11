package com.minsheng.oa.main.vote.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.main.vote.model.VoteOption;
import com.minsheng.oa.main.vote.model.VoteTheme;
import com.minsheng.oa.main.vote.service.VoteService;
import com.minsheng.oa.utils.DateUtils;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    //@ResponseBody
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


    @Path("/saveOptionUser") //用户投票接口    //保存用户和选项id，返回此投票所有投票信息
    @POST
    @Produces("application/json")
    public Map<String, Object> saveOptionUser(@FormParam("optionId1") Integer optionId1,
                                              @FormParam("optionId2") Integer optionId2,
                                              @FormParam("optionId3") Integer optionId3,
                                              @FormParam("optionId4") Integer optionId4,
                                              @FormParam("optionId5") Integer optionId5,
                                              @FormParam("optionId6") Integer optionId6,
                                              @FormParam("optionId7") Integer optionId7,
                                              @FormParam("userId") Integer userId,
                                              @FormParam("themeId") Integer themeId) {
        /**
         *判断是否超时
         */
        VoteTheme voteTheme = voteService.findVoteThemeByThemeId(Integer.valueOf(themeId)); //查询此投票所有信息
        if (voteTheme.getOvertime() == 1) {
            return resultMap.resutError("overtime");
        }
        /**
         *判断用户是否已经投票,   如果未投票保存投票信息并且返回总投票数
         */
        int voteNum = 1;
        List<VoteOption> optionList = voteTheme.getVoteOptionList();  //查询出选项集合
        for (VoteOption option : optionList) {             //遍历选项集合
            List<User> userlist = option.getUserList();   //查询用户集合
            for (User user : userlist) {                     //遍历用户集合
                if (user.getUserId() == userId) {            //获取用户id  判断是否相同，相同即是重复，返回错误操作
                    return resultMap.resutError("repeatVoting");
                }
            }
            voteNum = userlist.size() + voteNum;
        }
        if (optionId1 != null) {
            voteService.saveOptionUserId(optionId1, userId);
        }

        if (optionId2 != null) {
            voteService.saveOptionUserId(optionId2, userId);
            voteNum = voteNum + 1;
        }
        if (optionId3 != null) {
            voteService.saveOptionUserId(optionId3, userId);
            voteNum = voteNum + 1;
        }
        if (optionId4 != null) {
            voteService.saveOptionUserId(optionId4, userId);
            voteNum = voteNum + 1;
        }
        if (optionId5 != null) {
            voteService.saveOptionUserId(optionId5, userId);
            voteNum = voteNum + 1;
        }
        if (optionId6 != null) {
            voteService.saveOptionUserId(optionId6, userId);
            voteNum = voteNum + 1;
        }
        //保存选项和用户
        VoteTheme voteTheme1 = voteService.findVoteThemeByThemeId(Integer.valueOf(themeId)); //查询此选项所有信息

        //如果需要后端处理匿名 ， 在此处循环设置用户名姓名为空 返回数据给后台
        Map<String, Object> map = new HashMap<String, Object>();
        // 已经存在的投票数+刚保存的投票数量
        System.out.println(voteNum);
        map.put("voteNum", voteNum);
        map.put("voteTheme", voteTheme1);
        return resultMap.resutSuccessDate(map);
    }

}
