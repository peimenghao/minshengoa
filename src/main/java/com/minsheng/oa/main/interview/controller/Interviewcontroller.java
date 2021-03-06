package com.minsheng.oa.main.interview.controller;

import com.minsheng.oa.main.interview.model.Interview;
import com.minsheng.oa.main.interview.service.InterviewService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;


@Path("/interview")
public class Interviewcontroller {

    @Autowired
    InterviewService interviewService;

    @Autowired
    ResultMap resultMap;



    @Path("/getAllInterview")
    @GET
    @Produces("application/json")
    public Map<String, Object> getInterview() {
        List<Interview>  interview = interviewService.findAll();
        return resultMap.resutSuccessDate(interview);
    }


    @Path("/saveInterview")
    @POST
    @Produces("application/json")
    public Map<String, Object> saveInterview(@BeanParam Interview interview) {

        interviewService.saveInterview(interview);

        return resultMap.resutSuccess("success");

    }


    @Path("/updateInterview")
    @POST
    @Produces("application/json")
    public Map<String, Object> updateInterview(@BeanParam Interview interview) {
        System.out.println(interview.getInterviewId());
        interviewService.saveAndFlush(interview);

        return resultMap.resutSuccess("success");

    }



}
