//package com.minsheng.oa.main.matter.TimerTask;
//
//import com.alibaba.fastjson.JSONObject;
//import com.minsheng.oa.login.service.UserService;
//import com.minsheng.oa.main.massege.roducer.RegisterMailboxProducer;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import javax.jms.Destination;
//import java.util.TimerTask;
//
//@Slf4j
//public class MyTask extends TimerTask {      //timers线程启动后运行的内容
//
//    @Autowired
//    private RegisterMailboxProducer registerMailboxProducer;
//
//    @Autowired
//    private JavaMailSender mailSender; // 自动注入的Bean  发邮件
//
//    @Value("${messages.queue}")  //activemq队列名字
//    private String MESSAGES_QUEUE;
//
//    @Autowired
//    UserService userService;
//
//    String email;
//
//    String userName;
//
//    String title;
//
//
//
//
//    public MyTask(String  email,String userNama,String title) {
//             this.email=email;
//             this.userName=userNama;
//             this.title=title;
//    }
//
//    @Override
//    public void run() {
//
//        //调用下面封装好的方法 组装报文格式：用户邮箱和用户名
//        String mailMessages = mailMessage(email, userName,title);        //组装邮件内容需要的数据
//
//        log.info("###regist（）注册邮件发送报文" + mailMessages);
//        //队列
//        Destination activeMQQueue = new ActiveMQQueue("mail_queue"); // 配置文件队列名字
//        System.out.println(activeMQQueue);
//        registerMailboxProducer.send(activeMQQueue, mailMessages);          //注册队列和
//        this.cancel();
//    }
//
//    private String mailMessage(String email, String userName,String title) { //组装邮件报文
//        JSONObject root = new JSONObject();
//        JSONObject header = new JSONObject();
//        header.put("interfaceType", "sms_mail");  // 后面判断是否是发送邮件类型
//        JSONObject content = new JSONObject();
//        content.put("mail", email);               //邮箱
//        content.put("userName", userName);      //用户名
//        content.put("title",title);             //标题
//        root.put("header", header);
//        root.put("content", content);
//        return root.toJSONString();
//    }
//}
