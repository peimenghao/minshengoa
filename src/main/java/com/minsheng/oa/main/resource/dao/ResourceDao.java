package com.minsheng.oa.main.resource.dao;

import com.minsheng.oa.main.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ResourceDao extends JpaRepository<Resource, Integer> {

    Resource save(Resource resource);

    List<Resource> findAll();

    List<Resource> findByUserId(Integer userId);  //查询该用户的文件

    Resource findByResourceId(Integer resourceId);

    Resource findByOriginName(String originName);

    @Transactional
    void  deleteByResourceId(Integer resoureceId);
}
