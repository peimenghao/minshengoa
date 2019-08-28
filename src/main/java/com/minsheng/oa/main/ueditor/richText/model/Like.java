package com.minsheng.oa.main.ueditor.richText.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@Entity
@Table(name = "t_like")
@XmlRootElement
public class Like {    //点赞功能
    //多的一端
    /**
     *  点赞原理：
     *  用户点击 ，根据用户id  查询是否存在like
     *  如果不存在  则插入插入一条like 数据 ，保存用户id
     *  如果存在  则修改删除表
     *   展示赞的数量就是  级联查询到的数量
     *
     */
    @Id
    @Column(name = "like_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value="likeId")
    private Integer likeId;

    @FormParam(value="userId")
    private Integer userId;

//    private  Integer  type;   //0文章 1 评论 3 回复
//@JsonIgnoreProperties(value = {"userList"})
//    @ManyToOne(cascade = {CascadeType.ALL})//可选属性optional=false,表示company不能为空
//    @JoinColumn(name = "comment_id")
//    private Comment comment =new Comment();

//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "text_id")
//    private RichText richText =new RichText();

//    @ManyToOne(cascade = {CascadeType.ALL})//可选属性optional=false,表示company不能为空
//    @JoinColumn(name = "reply_id")            //设置在关联字段(外键)
//    private Reply reply =new Reply();
}
