package com.minsheng.oa.main.ueditor.richText.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "t_reply")
@XmlRootElement
public class Reply {

    @Id
    @Column(name = "reply_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value="replyId")
    private Integer replyId;

    @FormParam(value="fromUserName")   //回复用户的名字
    private String fromUserName;

    @FormParam(value="toUserName")     //目标用户的名字
    private String toUserName;

    @FormParam(value="content") // 回复内容
    private String content;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Like> likes = new HashSet<Like>();    //点赞
}
