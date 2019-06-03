package com.minsheng.oa.main.interview.dao;

import com.minsheng.oa.main.interview.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

;


public interface InterviewDao extends JpaRepository<Interview, Integer> {

                   //根据id查询news
    @Query(value="SELECT * FROM t_interview ", nativeQuery = true)
    List<Interview>  findAll();                                //查询所有面试信息


    @Modifying
    @Transactional
    Interview save(Interview interview);



}
