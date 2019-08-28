package com.minsheng.oa.main.ueditor.richText.service;

import com.minsheng.oa.main.ueditor.richText.dao.RichTextDao;
import com.minsheng.oa.main.ueditor.richText.model.RichText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RichTextService {
    @Autowired
    RichTextDao richTextDao;

    public void save(RichText richText) {  //保存修改 文本
        richTextDao.save(richText);
    }

//    public List<Matter> findAlltext(Integer userId) {    //查询所有文本部分信息；
//        return richTextDao.findAlltext(userId);
//    }


    public RichText findByTextId(Integer textId) {   //查询单个文本
        return richTextDao.findByTextIdOrderByTextIdAsc(textId);
    }

    public List<RichText> findByUserId(Integer userId) {  //查询用户下的所有文章

        return richTextDao.findByUserIdNum(userId);
    }

    public List<RichText> findByUserIdAndPublishType(Integer userId,Integer publishType){  //发布方式查询文章
        return richTextDao.findByUserIdNumAndPublishType(userId,publishType);
    }

    public List<RichText> findByPublishType(Integer publishType){  //发布方式查询文章
        return richTextDao.findByPublishType(publishType); //查询所有广场文章
    }

    public  void deleteByTextId(Integer textId){
        richTextDao.deleteByTextId(textId);
    }

    public void updateNoComment(String  noComment,Integer textId){
        richTextDao.updateNoComment(noComment,textId);
    }
}
