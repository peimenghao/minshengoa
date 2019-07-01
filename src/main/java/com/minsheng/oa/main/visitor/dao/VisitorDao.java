package com.minsheng.oa.main.visitor.dao;

import com.minsheng.oa.main.visitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface VisitorDao extends JpaRepository<Visitor,Integer> {

     List<Visitor>  findAll();

     @Transactional
     void deleteByVisitorId(Integer visitorId);


     @Transactional
     @Modifying
     Visitor save(Visitor visitor);  //增改




}
