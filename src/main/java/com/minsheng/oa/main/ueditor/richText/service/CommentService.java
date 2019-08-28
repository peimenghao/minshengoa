package com.minsheng.oa.main.ueditor.richText.service;

import com.minsheng.oa.main.ueditor.richText.dao.CommentDao;
import com.minsheng.oa.main.ueditor.richText.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;


    public Comment findByCommentId(Integer  commentId) {  // 根据id 查询评论信息
      return  commentDao.findByCommentId(commentId);
    }
    public void deleteByCommentId(Integer  commentId) {  //根据id 删除评论
        commentDao.deleteByCommentId(commentId);
    }

    public Comment savecomment( Comment comment) {   // 保存评论
      return  commentDao.save(comment);
    }
}
