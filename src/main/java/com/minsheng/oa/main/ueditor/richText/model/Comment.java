package com.minsheng.oa.main.ueditor.richText.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minsheng.oa.loginAndUser.model.User;
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
@Table(name = "t_comment")
@XmlRootElement
public class Comment {

    @Id
    @Column(name = "comment_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value = "commentId")
    private Integer commentId;

    @FormParam(value = "realName")          //评论用户名
    private String realName;

    @FormParam(value = "userIdNum")          //评论用id
    private Integer userIdNum;

    @FormParam(value = "content")     //评论内容
    private String content;

    @FormParam(value = "creatTime")    //评论发表时间
    private String creatTime;

    private  Integer  isLike ;  //是否点赞  0否 1是


    //单向 一对多
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Like> likes = new HashSet<Like>();    //点赞

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    @JsonIgnoreProperties(value = {"department", "roleList", "textSet"})   //断掉后面数据
    @JoinColumn(name = "user_id")
    private User user = new User();  //单项多对一


//    //单项一对多
//    @OneToMany(cascade = CascadeType.ALL)   //绑定
//    private List<Reply> reply = new ArrayList<Reply>();      //回复表
}
