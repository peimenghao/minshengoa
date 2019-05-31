//package com.minsheng.oa.main.matter.TimerTask;
//
//
//import com.minsheng.oa.utils.DateUtils;
//
//import java.text.ParseException;
//import java.util.Timer;
//
//public class ReadyRunTask {//创建启动任务
//
//    private static Timer timer = new Timer();
//
//    public void creatTimer(String date, String mail, String userName, String title) {
//        try {
//            MyTask myTask = new MyTask(mail, userName, title);
//            timer.schedule(myTask, DateUtils.df.get().parse(date));  //(启动任务,设置启动时间)
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
