package com.minsheng.oa.loginAndUser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "t_department")
@XmlRootElement  //jersey 接受接收传递对象用
public class Department {

    @Id
    @Column(name = "depart_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增长
    @FormParam(value = "departId")
    private Integer departId;


    @Column(name = "depart_name")
    @FormParam(value = "departName")   //部门名字
    private String departName;

    @Column(name = "depart_desc")  //部门描述
    @FormParam(value = "departDesc")
    private String departDesc;


    @OneToMany(mappedBy = "department", cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value = {"department","roleList"})   //断掉后面数据
    //拥有mappedBy注解的实体类为关系  被  维护端
    //mappedBy="company"中的company是Employee中的company属性
    private List<User> users = new ArrayList<User>();

}
