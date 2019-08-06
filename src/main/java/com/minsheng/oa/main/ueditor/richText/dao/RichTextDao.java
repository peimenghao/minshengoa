package com.minsheng.oa.main.ueditor.richText.dao;

import com.minsheng.oa.main.matter.model.Matter;
import com.minsheng.oa.main.ueditor.richText.model.RichText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface RichTextDao extends JpaRepository<RichText, Integer> {


    @Modifying
    RichText save(RichText richText);    //保存修改富文本

    @Query(value = "select r.text_id,r.title,r.text_time,r.user_id from rich_text r where user_Id=1?", nativeQuery = true)
    List<Matter> findAlltext(Integer userId);         //查询所有文本部分信息；

    RichText findByTextId(Integer textId); //查根据textId,询单个文本

    List<RichText> findByUserId(Integer userId);  //根据userId 查询文本

    @Transactional
    void deleteByTextId(Integer textId);
}
