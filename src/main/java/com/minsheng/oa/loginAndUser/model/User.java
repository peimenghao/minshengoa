package com.minsheng.oa.loginAndUser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="t_user")
@XmlRootElement(name = "user") //jersey 接受传递对象用
public class User implements Serializable {

    private static final long serialVersionUID = 2308418084162500432L;

    @Id
    @Column(name = "USER_ID", length = 50, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormParam(value = "userId")
    private Integer userId;

    @Column(name = "USER_NAME")
    @FormParam(value = "userName")
    private String userName;

    @Column(name = "password")
    @FormParam(value = "password")
    private String password;


    @Column(name = "PHONE")
    @FormParam(value = "phone")
    private String phone;


    @Column(name = "EMAIl")
    @FormParam(value = "email")
    private String email;

    @Column(name = "BIRTHDAY")
    @FormParam(value = "birthday")
    private String birthday;

    @Column(name = "GENDER")
    @FormParam(value = "gender")
    private Integer gender;

    @Column(name = "CREATE_TIME")
    @FormParam(value = "createTime")
    private String createTime;

    @Column(name = "UPDATE_TIME")
    @FormParam(value = "updateTime")
    private String updateTime;

    @Column(name = "REAL_NAME")
    @FormParam(value = "realName")
    private String realName;



    //  双向级联 防止 死循环, users为另一方的属性处，数据到此切断
    @JsonIgnoreProperties(value = {"users"}) //
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)//可选属性optional=false,表示company不能为空
    @JoinColumn(name = "depart_id")            //设置在关联字段(外键)
    private Department department =new Department();

    @JsonIgnoreProperties(value = {"userList"})   //  双向级联 防止 死循环,  userList为另一方的集合  表示运行到那就停止查询（不运行那个集合）
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)          //立即从数据库中进行加载数据;
    @JoinTable(name = "T_USER_ROLE", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleList = new HashSet<Role>();                   //  多对多
}



