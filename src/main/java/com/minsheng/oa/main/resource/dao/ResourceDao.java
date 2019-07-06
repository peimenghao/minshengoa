package com.minsheng.oa.main.resource.dao;

import com.minsheng.oa.main.resource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ResourceDao extends JpaRepository<Resource, Integer> {

    Resource save(Resource resource);

    List<Resource> findAll();

//    List<Resource> findByUserId(Integer userId);  //查询该用户的所有文件

    @Query(value="select r.* from t_resource r where r.user_id=?1 and r.is_public=0", nativeQuery = true)
    List<Resource> findByUserId(Integer userId);  //查询该用户的私有文件

    @Query(value="select r.* from t_resource r where r.is_public=1", nativeQuery = true)
    List<Resource> findPublicFile();  //查询所有公共文件

    Resource findByResourceId(Integer resourceId);  //根据id查询文件信息

    Resource findByOriginName(String originName);  //根据文件名字查询文件

    @Transactional
    void  deleteByResourceId(Integer resoureceId);   //根据id删除文件
}
