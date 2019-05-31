package com.minsheng.oa.main.interview.dao;

import com.minsheng.oa.main.interview.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

;


public interface InterviewDao extends JpaRepository<Interview, Integer> {


    // @Query(value="SELECT tn_tidtle,tn_user_content FROM tbl_news n WHERE tn_code=?1", nativeQuery = true)
//    Interview findInterviewById(Integer id);                     //根据id查询news
    @Query(value="SELECT * FROM t_interview ", nativeQuery = true)
    List<Interview>  findAll();                                //查询所有面试信息

//    @Query(value = "select COUNT(1) from tbl_news n", nativeQuery = true)
//    Integer newsCount();                                    //查询所有Interview数量

//    @Query(value = "select o.* from (select n.* from tbl_news n order by tn_publish_time desc) o limit ?1,?2", nativeQuery = true)
//    List<Interview> findInterviews(Integer startNum, Integer endNum);    //分页查询新闻




}
