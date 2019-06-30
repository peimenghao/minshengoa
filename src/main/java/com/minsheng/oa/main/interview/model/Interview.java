package com.minsheng.oa.main.interview.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


@Setter
@Getter
@Entity
@Table(name="t_interview")
@XmlRootElement
public class Interview{

    @Id
    @Column(name="interview_id",length=50,nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @FormParam("interviewId")
    private Integer interviewId;

    @Column(name="interviewer_name") //面试者姓名
    @FormParam("interviewerName")
    private String  interviewerName;

    @Column(name="position") //面试者岗位
    @FormParam("position")
    private String  position;

    @Column(name="phone")
    @FormParam("phone")
    private String  phone;

    @Column(name="start_time")  //开始时间
    @FormParam("startTime")
    private String startTime;

    @Column(name="end_time")     //结束时间
    @FormParam("endTime")
    private  String  endTime;

    @Column(name="result")    //面试结果
    @FormParam("result")
    private String  result;

    @Column(name="hr_name")   //hr姓名
    @FormParam("hrName")
    private  String  hrName;






}
