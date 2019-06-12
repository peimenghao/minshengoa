package com.minsheng.oa.main.email.SendMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SendMail {
      @Autowired
    MailConfiguration mailConfiguration ;

    public void sendMail(String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("281458696@qq.com");
        mailMessage.setTo(to);
        mailMessage.setSubject("民生温馨提示");
        mailMessage.setText("您有一个代办事项未处理哦");
        try {
            System.out.println(mailMessage);
            System.out.println(mailConfiguration.JavaMailSender());
            mailConfiguration.JavaMailSender().send(mailMessage);
//            System.out.println("发送简单邮件");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送简单邮件失败");
        }
    }


}
