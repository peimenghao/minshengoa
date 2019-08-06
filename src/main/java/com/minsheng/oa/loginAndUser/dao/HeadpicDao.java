package com.minsheng.oa.loginAndUser.dao;

import com.minsheng.oa.loginAndUser.model.Headpic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface HeadpicDao extends JpaRepository<Headpic, Integer> {



    @Modifying   //修改用这个
    @Transactional   //增删用这个
    Headpic  save(Headpic Headpic); //保存部门信息,如果带有id 则是更新部门信息     (用不上，user端维护关系)


    @Transactional
    void deleteByHeadpicId(Integer  headpicId);






}
