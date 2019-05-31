package com.minsheng.oa.main.visitor.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


@Setter
@Getter
@Entity
@Table(name = "t_visitor")
@XmlRootElement  //jersey 接受传递对象用
public class Visitor {

    @Id
    @Column(name = "visitor_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value = "visitorId")  //jersey使用
    private Integer visitorId;

    @Column(name = "name")        //来访人姓名
    @FormParam(value = "name")
    private String name;

    @Column(name = "content")    //拜访事由
    @FormParam(value = "content")
    private String content;

    @Column(name = "phone")
    @FormParam(value = "phone")
    String phone;

    @Column(name = "note")      //备注
    @FormParam(value = "note")
    private String note;

    @Column(name = "vsisit_person")
    @FormParam(value = "vsisitPerson")
    private String vsisitPerson;

    @Column(name = "receptionist")        //接待人
    @FormParam(value = "receptionist")
    private String receptionist;

    @Column(name = "visit_time")        //来访时间
    @FormParam(value = "visitTime")
    private String visitTime;


}
