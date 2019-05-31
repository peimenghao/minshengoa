package com.minsheng.oa.main.vote.dao;

import com.minsheng.oa.main.vote.model.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteOptionDao extends JpaRepository<VoteOption, Integer> {


    VoteOption save(VoteOption voteOption);

}
