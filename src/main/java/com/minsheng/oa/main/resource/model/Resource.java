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

    private String url;            //图片地址

    private String originName;   //原文件名字

    private String filePath;    //  fastID --例：group1/M00/00/00/wKhuwV0avOKAXrp6AAAB7SFsc3Q552.txt

    private Integer userId;

    private Integer isPublic = 0;  //是否是公共文件，0是私有文件，1是公共文件

    private String fileSize;       //文件大小

}
