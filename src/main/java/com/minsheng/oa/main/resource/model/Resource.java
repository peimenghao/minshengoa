package com.minsheng.oa.main.resource.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;


@Setter
@Getter
@Entity
@Table(name = "t_resource")
@XmlRootElement
public class Resource {

    @Id
    @Column(name = "resource_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resourceId;

    private String resourceName;   //存储时候的名字  ，即uuid 拼接后的名字

    private String url;   //图片地址

    private  String  originName;   //传过来的文件名字


    private Integer userId;

}
