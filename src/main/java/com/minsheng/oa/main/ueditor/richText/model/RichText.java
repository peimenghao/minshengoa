package com.minsheng.oa.main.ueditor.richText.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minsheng.oa.loginAndUser.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "t_rich_text")
@XmlRootElement
public class RichText {  //富文本内容存储

    @Id
    @Column(name = "text_id", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value = "textId")
    private Integer textId;

    //  标题
    @FormParam(value = "title")
    private String title;

    @Lob    //大字段                //内容
    @FormParam(value = "content")
    private String content;

    /**
     * 这个userId字段作用：我的文章模块--直接用户id和文章type查询文章集合
     * 其实也可以用下面外键字段 自己写一个sql 语句也可以查询出来
     */
    @FormParam(value = "userIdNum")
    private Integer userIdNum;

    //创建时间 (自动设定)
    @FormParam(value = "createTime")
    private String createTime;

    @FormParam(value = "updateTime") //更新时间
    private String updateTime;

    @FormParam(value = "publishType")//发布方式  0个人 1广场，2广场匿名  3草稿
    private Integer publishType;


    @FormParam(value = "tag")//文章标签   自定义   字符串拼接
    private String tag;


    @FormParam(value = "articleType") //文章类型  0 原创，1转载
    private Integer articleType;

    @FormParam(value = "wordNum")  //文本字数
    private Integer wordNum;


    @FormParam(value = "noComment") //是否禁止评论 ture  允许评论
    private String noComment;

    private Integer isLike;   //0 未点赞，1 已经点赞

    private Integer isCollect;  //0  未收藏 ，1 已收藏


    //单向一对多
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)   //浏览次数表
    private Set<Like> likes = new HashSet<Like>();

    //单向一对多
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)   //点赞表
    private Set<VisitNum> visitNumSet = new HashSet<VisitNum>();

    //单向一对多
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)   //评论表
    private List<Comment> commentlist = new ArrayList<Comment>();

    //可选属性optional=false,表示user不能为空
    //  cascade表示当前表拥有的权限 ，例： CascadeType.ALL  删除的话会级联删除当前用户所有文章
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    @JsonIgnoreProperties(value = {"department", "roleList", "textSet"})   //断掉后面数据
    @JoinColumn(name = "user_id")
    private User user = new User();  //单项多对一
}
