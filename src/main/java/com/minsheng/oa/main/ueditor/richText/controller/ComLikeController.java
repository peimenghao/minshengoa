package com.minsheng.oa.main.ueditor.richText.controller;


import com.minsheng.oa.loginAndUser.model.User;
import com.minsheng.oa.loginAndUser.service.UserService;
import com.minsheng.oa.main.ueditor.richText.model.Comment;
import com.minsheng.oa.main.ueditor.richText.model.Like;
import com.minsheng.oa.main.ueditor.richText.model.Reply;
import com.minsheng.oa.main.ueditor.richText.model.RichText;
import com.minsheng.oa.main.ueditor.richText.service.CommentService;
import com.minsheng.oa.main.ueditor.richText.service.LikeService;
import com.minsheng.oa.main.ueditor.richText.service.ReplyService;
import com.minsheng.oa.main.ueditor.richText.service.RichTextService;
import com.minsheng.oa.utils.DateUtils;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Map;

@Path("comment")
public class ComLikeController {   //评论  点赞操作
    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private RichTextService richTextService;

    @Autowired
    ResultMap resultMap;

    @Path("/findByCommentId")//
    @GET
    @Produces("application/json")
    public Map<String, Object> findByCommentId(@QueryParam("commentId") Integer commentId
    ) {
        Comment comment = commentService.findByCommentId(commentId);

        return resultMap.resutSuccessDate(comment);
    }

    @Path("/saveComment")//
    @POST
    @Produces("application/json")
    public Map<String, Object> SaveComment(@BeanParam Comment comment,
                                           @FormParam("userId") Integer userId,
                                           @FormParam("textId") Integer textId
    ) {
         User user= userService.findUserByUserId(userId);
        comment.setCreatTime(DateUtils.getNowTime());
        comment.setUser(user);        //评论模块添加用户
       Comment comment1= commentService.savecomment(comment);
        RichText richText = richTextService.findByTextId(textId);  //获得所在富文本
        richText.getCommentlist().add(comment1);   // 富文本添加评论
        richTextService.save(richText);     //全部保存
        return resultMap.resutSuccess();
    }

    @Path("/saveReply")//
    @POST
    @Produces("application/json")
    public Map<String, Object> saveReply(@BeanParam Reply reply
    ) {
        replyService.saveReply(reply);
        return resultMap.resutSuccess();
    }


    @Path("/submitLike")//   执行点赞  点击事件
    @GET
    @Produces("application/json")
    public Map<String, Object> submitLike(@QueryParam("userId") Integer userId,
                                          @QueryParam("commentId") Integer commentId,
                                          @QueryParam("replyId") Integer replyId,
                                          @QueryParam("textId") Integer textId
    ) {
        if (commentId != null) {    //评论表点赞
            Comment comment = commentService.findByCommentId(commentId);
            if (comment.getLikes().size() != 0) {        //如果点赞数有值
                for (Like like :comment.getLikes()) {
                    if (like.getUserId() == userId) { //判断是用户是否已经点赞 //如果点赞了  就删掉
                        Integer likeId=like.getLikeId();
                        comment.getLikes().remove(like);
                        commentService.savecomment(comment);//中间表信息删除
                        likeService.deleteByLikeId(likeId);  //like 表信息删除
                        return resultMap.resutSuccess("您已取消赞");
                    }
                }
            }
            Like like = new Like();
            like.setUserId(userId);
            comment.getLikes().add(like);
            commentService.savecomment(comment);
            return resultMap.resutSuccess("感谢您的赞");
        }
        else if (textId != null) {
            RichText richText = richTextService.findByTextId(textId);
            if (richText.getLikes().size() != 0) {        //如果点赞数有值  进入判断
                for (Like like: richText.getLikes()) {
                    if (like.getUserId() == userId) { //判断是用户是否已经点赞
                        //如果点赞了  就删掉
                        Integer likeId=like.getLikeId();
                        richText.getLikes().remove(like);
                        richTextService.save(richText);//中间表级联删除
                        likeService.deleteByLikeId(likeId);
                        return resultMap.resutSuccess("您已取消赞");
                    }
                }
            }
            Like like = new Like();
            like.setUserId(userId);
            richText.getLikes().add(like);
            richTextService.save(richText);
            return resultMap.resutSuccess("感谢您的赞");
        }
        return resultMap.resutSuccess("感谢您的赞");
    }


    @Path("/deleteByCommentId")//
    @GET
    @Produces("application/json")
    public Map<String, Object> deleteByCommentId(@QueryParam("commentId") Integer commentId
    ) {
        commentService.deleteByCommentId(commentId);
        return resultMap.resutSuccess();
    }



}
