package com.minsheng.oa.main.ueditor.richText.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@Entity
@Table(name = "t_visit_num")
@XmlRootElement
public class VisitNum {

    @Id
    @Column(name = "visit_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value="visitId")
    private Integer visitId;

    @FormParam(value="userId")
    private Integer userId;
}
