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
import java.util.*;

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
    public Map<String, Object> saveMatter(@BeanParam RichText richText
    ) {
        User user = userService.findUserByUserId(richText.getUserIdNum());  //获得用户信息
        if (user.getRealName() == null || "".equals(user.getRealName())) {
            return resultMap.resutError("提交失败！请先去个人中心填写真实姓名");
        }
        richText.setCreateTime(getNowTime());
        richText.setUser(user);
        richTextService.save(richText);
        return resultMap.resutSuccess("保存成功");
    }

    @Path("/updateText")//  编辑更新富文本
    @POST
    @Produces("application/json")
    public Map<String, Object> updateText(@BeanParam RichText richText
    ) {

        richText.setUpdateTime(getNowTime());
        richTextService.updateText(richText.getTitle(), richText.getContent(),
                richText.getUpdateTime(), richText.getPublishType(), richText.getTag()
                , richText.getArticleType(), richText.getWordNum(), richText.getNoComment(), richText.getTextId());
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
            Collections.reverse(richText.getCommentlist()); // 集合倒叙 ，使评论按时间顺序展示
            for (Comment comment : richText.getCommentlist()) { //循环所有评论
                comment.setIsLike(0);
                if (comment.getLikes().size() != 0) { //判断评论是否有点赞   如果有
                    for (Like like : comment.getLikes()) { //循环赞 数据 对比userId
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

        if (richText.getVisitNumSet().size() != 0) {         //判断数据库是否存在  浏览记录
            for (VisitNum visitNum : richText.getVisitNumSet()) {
                if (visitNum.getUserId() == userId) {         //判断该文章的访问信息里  是存在该用户
                    return resultMap.resutSuccessDate(richText);  //如果有记录 不在操作 直接返回
                }
            }
        }
        //没有此用户的浏览记录  则添加数据
        VisitNum visit = new VisitNum();
        visit.setUserId(userId);
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
        if (publishType != null) {  //如果不为空  则条件拆查询
            //这个 userId  就是userIdNum 字段
            richTextList = richTextService.findByUserIdAndPublishType(userId, publishType);
        } else {
            richTextList = richTextService.findByUserId(userId);

        }

        return resultMap.resutSuccessDate(richTextList);
    }

    @Path("/getAllArticles") // 查询广场文章 ，公共发布类型
    @GET
    @Produces("application/json")
    public Map<String, Object> getAllArticles() {

        List<RichText> richTextList = richTextService.findPublicArticle();
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
        if ("true".equals(noComment)) {
            return resultMap.resutSuccess("禁言成功");
        }
        return resultMap.resutSuccess("您已允许评论");

    }


//    public static String arr(int[] x) {
//        Map<String, Integer> map = new HashMap<String, Integer>();  //用来存放重复出现过的数
//        List<Integer>  noRepeat=new ArrayList<Integer>(); //用来存放从来没有出重复的数
//        for (int i = 0; i < x.length; i++) {
//            for (int j = i + 1; j < x.length; j++) {
//                if (x[i] == x[j]) {
//                    System.out.println(String.valueOf(x[i]) + "出现重复");
//                    if (map.get(String.valueOf(x[i])) == null) {    //判断map里面是否有记录
//                        map.put(String.valueOf(x[i]), 1);
////                        System.out.println(x[i]+"重复的次数"+1);
//                    } else {
//                        int mapNum = map.get(String.valueOf(x[i])) + 1;  //如果有记录  就加1
//                        map.put(String.valueOf(x[i]), mapNum);
//                        System.out.println(String.valueOf(x[i]) + "重复的次数" + mapNum);
//                    }
//                }
//
//                }
//            if (map.get(String.valueOf(x[i])) == null) {
//                noRepeat.add(x[i]);
//            }
//            }
//            if (map.size() == 0) {              //无重复值得情况
//                return "无重复值";
//            } else if (map.size() <= 2) {        //重复值小于两个的情况
//                if(noRepeat!=null){
//                    return "重复的值小于两个"+map.toString();
//                }else {
//
//                }
//            } else if (x.length - map.size() >= 2) {
//                return "没有重复的数字大于等于2个:"+noRepeat.get(0)+"和"+noRepeat.get(1);
//            }
//         else if (map.size() > 2) {  //如果重复数组   个数大于2 则转换为数组，获得map value值最小的两个
//                System.out.println("key" + map.keySet());
//                System.out.println("value" + map.values());
//                System.out.println("entrySet" + map.entrySet());
//
//
//                List<Map.Entry<String, Integer>> list = new ArrayList(map.entrySet());
//                Collections.sort(list, (o1, o2) -> (o1.getValue() - o2.getValue()));
//                System.out.println(list.get(0).getKey());
//                System.out.println(list.get(1).getKey());
//
////            System.out.println(list.get(1).getKey());
//
//
//            }
//            return "";
//        }
//
//
//        public static void main (String[]args){
//            int[] x = {1, 2,3};
//            System.out.println(arr(x));
//        }


    }


