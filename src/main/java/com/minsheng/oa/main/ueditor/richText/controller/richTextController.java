package com.minsheng.oa.main.ueditor.richText.controller;

import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.ueditor.richText.model.Comment;
import com.minsheng.oa.main.ueditor.richText.model.Like;
import com.minsheng.oa.main.ueditor.richText.model.RichText;
import com.minsheng.oa.main.ueditor.richText.model.VisitNum;
import com.minsheng.oa.main.ueditor.richText.service.RichTextService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;

import static com.minsheng.oa.utils.DateUtils.getNowTime;

@Path("richText")
public class richTextController {

    @Autowired
    RichTextService richTextService;

    @Autowired
    UserService userService;

    @Autowired
    ResultMap resultMap;


    @Path("/saveRichText")//  保存富文本
    @POST
    @Produces("application/json")
    public Map<String, Object> saveMatter(@BeanParam RichText richText) {
       User user =userService.findUserByUserId(richText.getUserIdNum()) ;  //获得用户信息
        if(user.getRealName()==null||"".equals(user.getRealName())){
            return resultMap.resutError("提交失败！请先去个人中心填写真实姓名");
        }
        richText.setCreateTime(getNowTime());
        richText.setUser(user);
        richTextService.save(richText);
        return resultMap.resutSuccess("保存成功");
    }

    @Path("/getArticleDetail") //  根据textid  和userId查询单个富文本  , 判断个人是否点赞,判断是否浏览过
    @GET
    @Produces("application/json")
    public Map<String, Object> getTextById(@QueryParam("textId") Integer textId,
                                           @QueryParam("userId") Integer userId
    ) {
        RichText richText = richTextService.findByTextId(textId);
        richText.setIsLike(0);
        if (richText.getLikes().size() != 0) {//判断当前用户在当前文章是否有赞
            for (Like like : richText.getLikes()) {
                if (like.getUserId() == userId) {
                    richText.setIsLike(1);
                    break;
                }
            }
        } else {
            richText.setIsLike(0);
        }
        if (richText.getCommentlist().size() != 0) {  //判断文章是否有评论    如果有
            for (Comment comment: richText.getCommentlist()) { //循环所有评论
                comment.setIsLike(0);
                if (comment.getLikes().size() != 0) { //判断评论是否有点赞   如果有
                    for (Like like:comment.getLikes()) { //循环赞 数据 对比userId
                        if (like.getUserId() == userId) {//如果当前用户 已经点赞
                            comment.setIsLike(1);  //设值
                            break;
                        }
                    }
                } else {   //如果没有赞 就直接设0
                    comment.setIsLike(0);
                }
            }
        }

        if(richText.getVisitNumSet().size()!=0){         //判断是否存在  浏览记录
            for(VisitNum visitNum:richText.getVisitNumSet()){
                if(visitNum.getUserId()==userId){
                    return resultMap.resutSuccessDate(richText);  //如果有记录 不在操作 直接返回
                }
            }
        }
        //没有此用户的浏览记录  则添加数据
        VisitNum visit=new VisitNum();
        visit.setUserId(userId);
        System.out.println("userId"+userId);
        richText.getVisitNumSet().add(visit);
        richTextService.save(richText);

        return resultMap.resutSuccessDate(richText);
    }

    @Path("/getMyArticles") //  根据id 和发布类型 查询我的文章
    @GET
    @Produces("application/json")
    public Map<String, Object> getByUserId(@QueryParam("userId") Integer userId,
                                           @QueryParam("publishType") Integer publishType) {

        List<RichText> richTextList;
        if (publishType != null) {  //如果为空，查询所有 除了草稿
           //这个 userId  就是userIdNum 字段
            richTextList = richTextService.findByUserIdAndPublishType(userId, publishType);
        } else {
            richTextList = richTextService.findByUserId(userId);

        }
        if(richTextList!=null){
            for(int i=0;i<richTextList.size();i++){
                System.out.println("getTextId"+richTextList.get(i).getTextId());
            }
        }

        return resultMap.resutSuccessDate(richTextList);
    }

    @Path("/getAllArticles") // 查询广场文章 ，公共发布类型
    @GET
    @Produces("application/json")
    public Map<String, Object> getAllArticles(@QueryParam("publishType") Integer publishType) {

        List<RichText> richTextList = richTextService.findByPublishType(publishType);

        return resultMap.resutSuccessDate(richTextList);
    }

    @Path("/deletArticleById") // 查询广场文章 ，公共发布类型
    @GET
    @Produces("application/json")
    public Map<String, Object> deletArticleById(@QueryParam("textId") Integer textId) {

        richTextService.deleteByTextId(textId);

        return resultMap.resutSuccess();
    }

    @Path("/setNoComment") // 查询广场文章 ，公共发布类型
    @GET
    @Produces("application/json")
    public Map<String, Object> setNoComment(@QueryParam("textId") Integer textId,
                                            @QueryParam("noComment") String noComment//false允许发言
    ) {
        richTextService.updateNoComment(noComment, textId);
   if("true".equals(noComment)){
    return resultMap.resutSuccess("禁言成功");
}
    return resultMap.resutSuccess("您已允许评论");

    }

}


