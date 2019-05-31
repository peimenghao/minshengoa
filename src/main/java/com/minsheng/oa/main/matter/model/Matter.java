package com.minsheng.oa.main.matter.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


@Setter
@Getter
@Entity
@Table(name = "t_matter")
@XmlRootElement
public class Matter {  //待办事项

    @Id
    @Column(name = "matter_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer matterId;

    @Column(name = "content")           //内容
    @FormParam(value="content")
    private String content;

    @Column(name = "create_time")       //创建时间
    @FormParam(value="createTime")
    private String createTime;

    @Column(name = "remind_time")        //提醒时间
    @FormParam(value="remindTime")
    private String remindTime;

    @Column(name = "title")             //  标题
    @FormParam(value="title")
    private String title;

    @Column(name = "user_id")             // 用户id
    @FormParam(value="userId")
    private Integer userId;


//    @ManyToOne(fetch = FetchType.EAGER)     //单项多对一
//    @JoinColumn(name = "TN_TYPE_CODE")      // 设置外键字段，注意：！！要把上面外键（文章类别）字段注释  不然报错
//    private InfoType infoType;


}
