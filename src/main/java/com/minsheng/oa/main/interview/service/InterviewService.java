package com.minsheng.oa.main.interview.service;


import com.minsheng.oa.main.interview.dao.InterviewDao;
import com.minsheng.oa.main.interview.model.Interview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    @Autowired
    InterviewDao interviewDao;

    public List<Interview>  findAll(){

        return  interviewDao.findAll();
    }

    public void  saveInterview(Interview interview){
        interviewDao.save(interview);

    }

    public void  saveAndFlush(Interview interview){
        interviewDao.save(interview);

    }






}
