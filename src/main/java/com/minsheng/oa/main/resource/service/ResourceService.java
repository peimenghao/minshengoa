package com.minsheng.oa.main.resource.service;


import com.minsheng.oa.main.resource.dao.ResourceDao;
import com.minsheng.oa.main.resource.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    ResourceDao resourceDao;

    public List<Resource> findAll() {
        List<Resource> resourceList = resourceDao.findAll();
        return resourceList;
    }

    public Resource findByResourceId(Integer resourceId) {   //根据id获得一个资源的信息
        Resource resource = resourceDao.findByResourceId(resourceId);
        return resource;
    }

    public List<Resource> findPublicFile() {   //获得公共有资源
        List<Resource> resourceList = resourceDao.findPublicFile();
        return resourceList;
    }
    public List<Resource> findByUserId(Integer userId) {   //获得用户私有资源
        List<Resource> resourceList = resourceDao.findByUserId(userId);
        return resourceList;
    }

    public Resource findByOriginName(String originName,Integer isPublic,Integer userId) {
        Resource resource = resourceDao.findByOriginNameAndIsPublicAndUserId(originName,isPublic,userId);
        return resource;
    }

    public void saveResouce(Resource resource) {
        resourceDao.save(resource);
    }

    public void deleteBySourceId(Integer resoureceId) {
        resourceDao.deleteByResourceId(resoureceId);
    }



}
