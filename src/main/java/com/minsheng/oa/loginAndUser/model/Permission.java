package com.minsheng.oa.loginAndUser.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


@Setter
@Getter
@Entity
@Table(name="T_PERMISSION")
@XmlRootElement(name = "permission")  //jersey 接受传递对象用
public class Permission {

    @Id
    @Column(name="perm_id",length=50,nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)//自增长
    @FormParam(value="permId")  //jersey使用
    private Integer permId;


    @Column(name="PERM_NAME")
    @FormParam(value="name") //权限名
    private String permName;

    @Column(name="PERM_DESC")  //权限描述
    @FormParam(value="desc")
    private String permDesc;



}
