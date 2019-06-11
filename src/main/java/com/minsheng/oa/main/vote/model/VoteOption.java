package com.minsheng.oa.main.vote.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minsheng.oa.loginAndUser.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="t_vote_option")
@XmlRootElement
public class VoteOption {

    @Id
    @Column(name="option_id",length=50,nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @FormParam(value="optionId")
    private Integer optionId;

    @Column(name="content")   //选项内容
    @FormParam(value="content")
    private String content;

    @Column(name="option_theme_id")   //外键   给theme（投票主题表建立一对多关联）
    @FormParam(value="optionThemeId")
    private Integer optionThemeId;

    @JsonIgnoreProperties(value = {"department","roleList"}) //截断数据
    @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
    @JoinTable(name = "t_option_user", joinColumns = { @JoinColumn(name = "option_id") }, inverseJoinColumns ={@JoinColumn(name = "user_id") })
    private List<User> userList;//  多对多

}
