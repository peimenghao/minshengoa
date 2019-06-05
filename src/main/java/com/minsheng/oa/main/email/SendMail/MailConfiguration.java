package com.minsheng.oa.main.email.SendMail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 因为之前JavaMailSender 注入一直报null, 暂且只知道这个 手动实例化， 手动new 出来用
 */
@Configuration
public class MailConfiguration { //手动实例化JavaMailSender
    @Bean
    public JavaMailSenderImpl JavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("281458696@qq.com");
        mailSender.setPassword("babrylfgijaqbjfg");
        return mailSender;
    }
}
