package com.minsheng.oa.main.ueditor.richText.service;

import com.minsheng.oa.main.ueditor.richText.dao.ReplyDao;
import com.minsheng.oa.main.ueditor.richText.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    public void saveReply(Reply reply) {
        replyDao.save(reply);
    }

    public Reply findByReplyId(Integer replyId) {
        return replyDao.findByReplyId(replyId);
    }

    @Transactional
    public void deleteByReplyId(Integer replyId) {
        replyDao.deleteByReplyId(replyId);
    }
}
