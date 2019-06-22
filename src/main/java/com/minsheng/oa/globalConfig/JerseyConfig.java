package com.minsheng.oa.globalConfig;

import com.minsheng.oa.main.resource.controller.UploadController;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/minsheng")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        //注册请求的上下文文件
        //register(RequestContextFilter.class);
        //注册类方式   register(WSSayHellokitty.class);
        //包扫描
        packages("com.minsheng.oa.loginAndUser.controller");
        packages("com.minsheng.oa.main.vote.controller");
        packages("com.minsheng.oa.main.interview.controller");
        packages("com.minsheng.oa.main.matter.controller");
        packages("com.minsheng.oa.main.visitor.controller");
        packages("com.minsheng.oa.main.resource.controller");
       // packages("com.minsheng.oa.main.ueditor.controller");


        register(MultiPartFeature.class);
        register(UploadController.class);

    }


}
