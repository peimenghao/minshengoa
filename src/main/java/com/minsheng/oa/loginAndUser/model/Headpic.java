package com.minsheng.oa.loginAndUser.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name = "t_headpic")
public class Headpic {

    @Id
    @Column(name = "headpic_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer headpicId;

    private String url;            //图片地址

    private String picPath;      //  fastID --例：group1/M00/00/00/wKhuwV0avOKAXrp6AAAB7SFsc3Q552.txt

    //@OneToOne(mappedBy = "headpic")
   // private Integer userId;

  //  private Integer isPublic = 0;  //是否是公共文件，0是私有文件，1是公共文件

   // private String fileSize;       //文件大小

}
