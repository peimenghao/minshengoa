package com.minsheng.oa.loginAndUser.config;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean FilterFactoryBean = new ShiroFilterFactoryBean();
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> jwtFilterMap = new HashMap<String, Filter>();
        jwtFilterMap.put("jwt", new JWTFilter());    //设置自定义的JWT过滤器
        FilterFactoryBean.setFilters(jwtFilterMap);
        FilterFactoryBean.setSecurityManager(securityManager);  //必须设定

        Map<String, String> filterMap = new HashMap<>();//自定义权限
        //权限拦截
        // filterMap.put("/minsheng/interview/getAllInterview", "roles[admin]"); // roles用户角色拦截  ： 角色符合admin则通过
        // filterMap.put("","perms[select]");
       // filterMap.put("/minsheng/interview/saveInterview","roles[user]");
        filterMap.put("/logout", "logout");  //退出登录

        // 设置访问不拦截
        filterMap.put("/minsheng/login/*", "anon");
        // filterMap.put("/ddbb/user/register", "anon");
        filterMap.put("/**", "jwt"); // 所有请求通过定义的的JWT Filter      注释即无拦截

        //FilterFactoryBean.setLoginUrl("/minsheng/login/unlogin");        //无token认证跳转
        FilterFactoryBean.setUnauthorizedUrl("/minsheng/login/unAuth"); // 设置无权限时跳转的 url;
        FilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return FilterFactoryBean;
    }


    /**
     * 注入 securityManager
     */
    @Bean("SecurityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关闭shiro自带的session

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRealm(customRealm());
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 自定义身份认证 realm;
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {

        return new CustomRealm();
    }

//    "---------------------------------------------------------


    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}
