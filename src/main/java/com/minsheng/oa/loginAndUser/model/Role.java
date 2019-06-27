package com.minsheng.oa.loginAndUser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="T_ROLE")
@XmlRootElement(name = "role")  //jersey 接受传递对象用
public class Role {
        @Id
        @Column(name="ROLE_ID",length=50,nullable=false)
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @FormParam(value="roleId")
        private Integer roleId;


        @Column(name="ROLE_NAME")
        @FormParam(value="roleName")
        private String roleName;


        @Column(name="ROLE_DESC")
        @FormParam(value="roleDesc")
        private String roleDesc;




        @ManyToMany(fetch= FetchType.EAGER)//立即从数据库中进行加载数据;
        @JoinTable(name = "T_ROLE_PERM", joinColumns = { @JoinColumn(name = "ROLE_Id") }, inverseJoinColumns ={@JoinColumn(name = "perm_id") })
        private List<Permission>  permList;// 一个角色可以有多个权限   多对多


        //  双向级联 防止 死循环, roleList为另一方的属性处，数据到此切断
        @JsonIgnoreProperties(value = {"roleList"}) //
        @ManyToMany(mappedBy = "roleList",fetch= FetchType.EAGER)
        private Set<User> userList =new HashSet<User>();
 }
