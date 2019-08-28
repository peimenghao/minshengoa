package com.minsheng.oa.main.ueditor.richText.dao;

import com.minsheng.oa.main.ueditor.richText.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentDao extends JpaRepository<Comment,Integer> {

    Comment save(Comment comment);

    Comment findByCommentId(Integer commentId);

    @Transactional
    void deleteByCommentId(Integer commentId);
}
