package com.minsheng.oa.loginAndUser.shiroConfig;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {  //如果带有请求头，进行token 校验
            try {
                executeLogin(request, response);
                 return  true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("执行方法isAccessAllowed");
        }

        return false; //false 表示关闭游客访问
    }

    /**
     *  取出token  登录校验
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        System.out.println("JWTFilter-即将进入reaml登录校验--token==" + token);
        JWTToken JWTtoken = new JWTToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        try {
            getSubject(request, response).login(JWTtoken); //校验token
        } catch (AuthenticationException a) {
            try {  //转发错误页面
                request.getRequestDispatcher("/minsheng/login/401").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 如果没有抛出异常则代表登入成功，返回true,false直接拦截
        return true;
    }



    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("token");
        return authorization != null; // 有请求头返回   true
    }
}
