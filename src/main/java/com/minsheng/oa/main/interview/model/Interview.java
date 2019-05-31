package com.minsheng.oa.main.interview.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name="t_interview")
public class Interview{

    @Id
    @Column(name="interview_id",length=50,nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer interviewId;

    @Column(name="interviewer_name") //面试者姓名
    private String  interviewerName;

    @Column(name="phone")
    private String  phone;

    @Column(name="start_time")  //开始时间
    private Date startTime;

    @Column(name="endTime")     //结束时间
    private  Date  endTime;

    @Column(name="result")    //面试结果
    private String  result;

    @Column(name="hr_name")   //hr姓名
    private  String  hr_name;






}
