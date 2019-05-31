package com.minsheng.oa.main.visitor.service;


import com.minsheng.oa.main.visitor.dao.VisitorDao;
import com.minsheng.oa.main.visitor.model.Visitor;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VisitorService {

    @Autowired
    VisitorDao visitorDao;

    @Autowired
    ResultMap resultMap;

    public Map<String, Object> findAll() {

        List<Visitor> visitors = visitorDao.findAll();

        return  resultMap.resutSuccessDate(visitors);
    }

    public void deleteByVisitorId(Integer visitorId) {

        visitorDao.deleteByVisitorId(visitorId);


    }

    public Map<String, Object> save(Visitor visitor) {

        visitorDao.save(visitor);

        return  resultMap.resutSuccess("保存成功");
    }
}
