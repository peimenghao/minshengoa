package com.minsheng.oa.main.vote.dao;

import com.minsheng.oa.main.vote.model.VoteTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteThemeDao  extends JpaRepository<VoteTheme, Integer> {


    @Query(value="SELECT * FROM t_vote_theme", nativeQuery = true)
    List<VoteTheme> findAllVote();                     //根据id查询投票主题


    VoteTheme findVoteThemeByThemeId(Integer themeId);  //查询所有主题

    VoteTheme  save(VoteTheme voteTheme);

   // VoteTheme save();
    //    _________________________________查询____________________________
//
//    List<VoteTheme>  findAll();                                    //查询所有新闻
//
//    @Query(value = "select COUNT(1) from tbl_news n", nativeQuery = true)
//    Integer newsCount();                                    //查询所有news数量
//
//    @Query(value = "select o.* from (select n.* from t n order by tn_publish_time desc) o limit ?1,?2", nativeQuery = true)
//    List<VoteTheme> findNews(Integer startNum,Integer endNum);    //分页查询新闻
//
//
//    @Transactional  //修改提交事务
//    @Modifying(clearAutomatically = true) //自动清除实体内数据
//    @Query(value = "update tbl_news set tn_good=?1 where tn_code=?2" , nativeQuery = true)
//    void  good(Integer good, Integer tnCode);
//
//

}
