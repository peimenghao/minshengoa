package com.minsheng.oa.main.matter.dao;

import com.minsheng.oa.main.matter.model.Matter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MatterDao extends JpaRepository<Matter,Integer> {


     @Query(value = "select * from t_matter where user_id=?1",nativeQuery = true)
     List<Matter>  findMatterByUserId(Integer userId);         //查询   此用户的素有代办事项


     Matter  save(Matter matter);   //插入待办事项

     List<Matter>  findAll();

     @Query(value = "select * from t_matter where is_over=0",nativeQuery = true)  //查询未过期的matter
     List<Matter> findMatterByOver();

     @Modifying
     @Transactional
     @Query(value="update  t_matter set content=?1,remind_time=?2,title=?3 where matter_id=?4",nativeQuery = true)
     void updateMatter(String content,String remindTime,String title,Integer matterId);



     @Modifying
     @Transactional
     @Query(value="update  t_matter set is_over=1 where matter_id=?1",nativeQuery = true)
     void updateMatterOver(Integer matterId);

//
//
//     // 普通分页
//     Page<Matter> getPage(Integer num, Integer size);
}
