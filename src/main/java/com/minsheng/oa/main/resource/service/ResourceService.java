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

    public Resource findByResourceId(Integer resourceId) {
        Resource resource = resourceDao.findByResourceId(resourceId);
        return resource;
    }

    public List<Resource> findByUserId(Integer userId) {
        List<Resource> resourceList = resourceDao.findByUserId(userId);
        return resourceList;
    }

    public Resource findByResourceName(String originName) {
        Resource resource = resourceDao.findByOriginName(originName);
        return resource;
    }

    public void saveResouce(Resource resource) {
        resourceDao.save(resource);
    }
}
