package com.minsheng.oa.main.ueditor.richText.service;

import com.minsheng.oa.main.ueditor.richText.dao.LikeDao;
import com.minsheng.oa.main.ueditor.richText.model.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeDao likeDao;

    public void deleteByLikeId(Integer likeId) {
        likeDao.deleteByLikeId(likeId);
    }

    public void saveLike(Like like) {
        likeDao.save(like);
    }
}
