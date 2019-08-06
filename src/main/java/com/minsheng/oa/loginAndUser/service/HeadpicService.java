package com.minsheng.oa.loginAndUser.service;


import com.minsheng.oa.loginAndUser.dao.HeadpicDao;
import com.minsheng.oa.loginAndUser.model.Headpic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HeadpicService {

    @Autowired
    HeadpicDao headpicDao;


    public Headpic saveHeadpic(Headpic Headpic) {
        Headpic headpic = headpicDao.save(Headpic);
        return headpic;
    }

    public void deleteByHeadpicId(Integer headpicId) {
        headpicDao.deleteByHeadpicId(headpicId);

    }


}
