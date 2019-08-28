package com.minsheng.oa.main.ueditor.richText.dao;

import com.minsheng.oa.main.ueditor.richText.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LikeDao extends JpaRepository<Like, Integer> {

    Like save(Like like);

    @Transactional
    void deleteByLikeId(Integer likeId);

}
