package com.minsheng.oa.main.interview.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cASA {

    @GetMapping("/article")
    @RequiresRoles("user")
    public String requireAuth(){
        return "suecess";
    }
}
