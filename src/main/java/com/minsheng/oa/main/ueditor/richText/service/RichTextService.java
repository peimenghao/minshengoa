package com.minsheng.oa.main.ueditor.richText.service;

import com.minsheng.oa.main.matter.model.Matter;
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

    public List<Matter> findAlltext(Integer userId) {    //查询所有文本部分信息；
        return richTextDao.findAlltext(userId);
    }


    public RichText findByTextId(Integer textId) {   //查询单个文本
        return richTextDao.findByTextId(textId);
    }

    public List<RichText> findByUserId(Integer userId) {  //查询用户下的所有文本

        return richTextDao.findByUserId(userId);
    }


}
