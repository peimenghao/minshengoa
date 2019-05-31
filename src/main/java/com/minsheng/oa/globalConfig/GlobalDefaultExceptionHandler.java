//package com.minsheng.oa.globalConfig;
//
//import org.apache.shiro.authz.UnauthorizedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//    @ControllerAdvice
//    public class GlobalDefaultExceptionHandler {
//
//
//
//        @ExceptionHandler(UnauthorizedException.class)
//        @ResponseBody
//        public String defaultExceptionHandler(HttpServletRequest req, Exception e){
//
//
//            return "对不起，你没有访问权限！";
//        }
//    }
//
//
//
