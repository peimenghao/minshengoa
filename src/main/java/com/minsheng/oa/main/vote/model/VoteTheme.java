package com.minsheng.oa.main.vote.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name="t_vote_theme")
@XmlRootElement(name = "voteTheme")
public class VoteTheme{             //投票主题

    @Id
    @Column(name="theme_id",length=50,nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @FormParam(value="themeId")
    private Integer themeId;

    @Column(name="content")         //主题内容
    @FormParam(value="content")
    private String  content;

    @Column(name="author")           //投票发起人
    @FormParam(value="author")
    private String   author;

    @Column(name="creat_time")      // 投票创建时间
    @FormParam(value="creatTime")
    private String creatTime;

    @Column(name="end_time")
    @FormParam(value="endTime")
    private String endTime;

    @Column(name="anonymous")       //是否允许匿名
    @FormParam(value="anonymous")
    private Integer  anonymous;

    @Column(name="is_select_one")   //单选多选  0 为多选   1为单选
    @FormParam(value="isSelectOne")
    private Integer isSelectOne;

    @Column(name="overtime")       //超时，0允许投票,  1 不允许投票
    @FormParam(value="overtime")
    private Integer  overtime=0;

    @Transient
    private Integer  isClose;  //查詢信息时候 判断用户是否投票， 关闭投票




//    @OneToMany(mappedBy = "option",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
//    //cascade=CascadeType.ALL:级联保存、更新、删除、刷新;延迟加载。当删除用户，会级联删除该用户的所有文章
//    //拥有mappedBy注解的实体类为关系被维护端
//    private List<VoteOption> VoteOptionList;  //投票选项

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL) //单项一对多（只需要写一的一端）,级联  增删改查都影响 多的一段
    @JoinColumn(name="option_theme_id")      //多的一端的外键
    private List<VoteOption> voteOptionList = new ArrayList<>();

}
