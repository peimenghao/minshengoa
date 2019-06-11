package com.minsheng.oa.utils;

import com.minsheng.oa.main.email.SendMail.InitMatterMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class startRun implements ApplicationRunner {

    //private static Logger logger = LoggerFactory.getLogger(StartService.class);
    @Autowired
    InitMatterMail initMatterMail;

    @Override
    public void run(ApplicationArguments args) throws Exception {  //初始化待办事项
        initMatterMail.sendMail();
    }
}

