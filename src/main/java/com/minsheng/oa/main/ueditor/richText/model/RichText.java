package com.minsheng.oa.main.ueditor.richText.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;


@Setter
@Getter
@Entity
@Table(name = "t_rich_text")
@XmlRootElement
public class RichText {  //富文本内容存储

    @Id
    @Column(name = "text_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value="textId")
    private Integer textId;

    @Column(name = "title")             //  标题
    @FormParam(value="title")
    private String title;



    @Lob    //大字段
    @Column(name = "content")            //内容
    @FormParam(value="content")
    private String content;

    @Column(name = "user_id")             // 用户id
    @FormParam(value="userId")
    private Integer userId;

    @Column(name = "create_time")       //创建时间 (自动设定)
    @FormParam(value="createTime")
    private String createTime;


//    @Column(name = "text_time")         //文本日期 (如果是新闻发表需要这个设置时间)
//    @FormParam(value="textTime")
//    private String textTime;

}
