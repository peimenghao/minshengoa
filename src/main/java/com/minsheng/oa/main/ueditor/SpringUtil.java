package com.minsheng.oa.main.ueditor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {//服務器啓動時調用

	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext  = applicationContext;
    	}
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
