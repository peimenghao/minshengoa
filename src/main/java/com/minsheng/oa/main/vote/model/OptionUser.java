package com.minsheng.oa.main.vote.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.ws.rs.FormParam;

@Setter
@Getter
@Entity
@Table(name="t_option_user")
public class OptionUser {

    @Id
    @Column(name="id",length=50,nullable=false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @FormParam(value="id")
    private Integer id;

    @Column(name="option_id")   //选项id
    @FormParam(value="optionid")
    private Integer optionid;

    @Column(name="user_id")   //用户id
    @FormParam(value="userId")
    private Integer userId;

}
