package com.minsheng.oa.main.vote.dao;

import com.minsheng.oa.main.vote.model.OptionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OptionUserDao extends JpaRepository<OptionUser,Integer> {

    @Transactional
    @Modifying
    @Query(value="insert into t_option_user (option_id,user_id) values(?1,?2) ",nativeQuery = true)
    void saverOptionUserId(Integer optionId,Integer userId);
}
