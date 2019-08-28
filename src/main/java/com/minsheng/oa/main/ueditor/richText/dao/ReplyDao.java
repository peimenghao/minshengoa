package com.minsheng.oa.main.ueditor.richText.dao;

import com.minsheng.oa.main.ueditor.richText.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ReplyDao extends JpaRepository<Reply, Integer> {

    Reply save(Reply reply);

    Reply findByReplyId(Integer replyId);

    @Transactional
    void deleteByReplyId(Integer replyId);
}
