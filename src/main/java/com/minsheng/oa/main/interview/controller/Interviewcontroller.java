package com.minsheng.oa.main.interview.controller;

import com.minsheng.oa.main.interview.model.Interview;
import com.minsheng.oa.main.interview.service.InterviewService;
import com.minsheng.oa.utils.resultMap.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        System.out.println(interview);
        return resultMap.resutSuccessDate(interview);
    }
}
