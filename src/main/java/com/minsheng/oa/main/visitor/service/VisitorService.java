package com.minsheng.oa.main.visitor.service;


import com.minsheng.oa.main.visitor.dao.VisitorDao;
import com.minsheng.oa.main.visitor.model.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {

    @Autowired
    VisitorDao visitorDao;


    public List<Visitor> findAll() {

        List<Visitor> visitors = visitorDao.findAll();

        return  visitors;
    }

    public void deleteByVisitorId(Integer visitorId) {

        visitorDao.deleteByVisitorId(visitorId);


    }

    public void save(Visitor visitor) {

        visitorDao.save(visitor);


    }
}
