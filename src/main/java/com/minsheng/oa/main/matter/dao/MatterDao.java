package com.minsheng.oa.main.matter.dao;

import com.minsheng.oa.main.matter.model.Matter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface MatterDao extends JpaRepository<Matter,Integer> {


     @Query(value = "select * from t_matter where user_id=?1",nativeQuery = true)
     List<Matter>  findMatterByUserId(Integer userId);         //查询   此用户的素有代办事项


     Matter  save(Matter matter);   //插入待办事项

     List<Matter>  findAll();
}
