package com.minsheng.oa.main.vote.dao;

import com.minsheng.oa.main.vote.model.VoteTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VoteThemeDao  extends JpaRepository<VoteTheme, Integer> {


    @Query(value="SELECT * FROM t_vote_theme", nativeQuery = true)
    List<VoteTheme> findAllVote();                     //根据id查询投票主题

    @Query(value = "select o.* from (select v.* from t_vote_theme  v order by theme_id desc) o limit ?1,?2", nativeQuery = true)
    List<VoteTheme> findPageVote(Integer startNum, Integer pageSize);  //分页查询主题

    VoteTheme findVoteThemeByThemeId(Integer themeId);  //查询单个


    VoteTheme  save(VoteTheme voteTheme);

    @Transactional
    @Modifying
    @Query(value="UPDATE t_vote_theme SET overtime=1 where theme_id=?1", nativeQuery = true)  //更新投票主题状态，设置过期
    void updateThemeStatus(Integer themeId);

    @Transactional
    @Modifying
    void deleteByThemeId(Integer themeId);
}
