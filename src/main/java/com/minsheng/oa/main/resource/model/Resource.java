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

//    private String resourceName;   //存储时候的名字  ，即uuid 拼接后的名字

    private String url;   //图片地址

    private String  originName;   //原文件名字

    private String  filePath;  //  fastID --例：  group1/M00/00/00/wKhuwV0avOKAXrp6AAAB7SFsc3Q552.txt

    private Integer userId;

}
