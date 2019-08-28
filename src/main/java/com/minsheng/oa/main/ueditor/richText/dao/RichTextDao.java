package com.minsheng.oa.main.ueditor.richText.dao;

import com.minsheng.oa.main.ueditor.richText.model.RichText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RichTextDao extends JpaRepository<RichText, Integer> {


    @Modifying
    RichText save(RichText richText);    //保存修改富文本

    RichText findByTextIdOrderByTextIdAsc(Integer textId); //查根据textId,询单个文本

    List<RichText> findByUserIdNum(Integer userId);  //根据userId 查询文本

    List<RichText>  findByUserIdNumAndPublishType(Integer userId,Integer publishType);

    @Query(value = "select * from t_rich_text r where publish_type=?1 or publish_type=2", nativeQuery = true)
    List<RichText> findByPublishType(Integer publishType);  //查询所有广场文章

    @Modifying
    @Transactional
    @Query(value = "UPDATE t_rich_text SET no_comment=?1 where text_id=?2", nativeQuery = true)
    void updateNoComment(String  noComment,Integer textId);    //是否禁言

    @Transactional
    void deleteByTextId(Integer textId);
}
