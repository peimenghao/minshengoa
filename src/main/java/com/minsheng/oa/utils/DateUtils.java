package com.minsheng.oa.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    /**
     * 年-月-日 时:分:秒 显示格式
     */
    // 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
    public static String DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 年-月-日 显示格式
     */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private static SimpleDateFormat simpleDateFormat;


//	//    时间转换工具，保证线程安全
//	public static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
//		@Override
//		protected DateFormat initialValue() {
//			return new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
//		}
//
//	};


    //转化为yyyy-MM-dd HH:mm:ss 的日期格式
    public static String dateFomat(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        return simpleDateFormat.format(source);
    }

    /**
     * Date类型转为指定格式的String类型
     */
    public static String DateToString(Date source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(source);
    }

    /**
     * unix时间戳转为指定格式的String类型
     * <p>
     * <p>
     * System.currentTimeMillis()获得的是是从1970年1月1日开始所经过的毫秒数
     * unix时间戳:是从1970年1月1日（UTC/GMT的午夜）开始所经过的秒数,不考虑闰秒
     */
    public static String timeStampToString(long source, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(source * 1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期转换为时间戳(unix时间戳,单位秒)
     */
    public static long dateToTimeStamp(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime() / 1000;

    }

    /**
     * 字符串转换为对应日期
     */
    public static Date stringToDate(String time, String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println("字符串转日期"+time);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            log.error("字符串转换日期异常", e);
        }
        return date;
    }

    /**
     * 获得当前时间对应的指定格式
     */
    public static String currentFormatDate(String pattern) {
        simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date());

    }

    // 功能描述:(获取当前系统时间戳) 2019-07-12 14:05:09.399
    public static Timestamp getTimestamp() {
        return new Timestamp(new Date().getTime());
    }


    //(获取当前系统时间戳) 2019-07-12 14:05:09
    public static  String getNowTime(){
        String  time= getTimestamp().toString();
        time =time.substring(0,time.lastIndexOf("."));
        return  time;
    }



    /**
     * 日期转化为cron表达式
     *
     * @param date
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return DateUtils.fmtDateToStr(date, dateFormat);
    }

    /**
     * Description:格式化日期,String字符串转化为Date
     *
     * @param date
     * @param dtFormat 例如:yyyy-MM-dd HH:mm:ss yyyyMMdd
     * @return
     */
    public static String fmtDateToStr(Date date, String dtFormat) {
        if (date == null)
            return "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * cron表达式转为日期
     *
     * @param cron
     * @return
     */
    public static Date getCronToDate(String cron) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }



    //字符串转cron
    public static String  stringtoCron(String  strCron){
        System.out.println("时间字符串"+strCron);
        Date date = stringToDate(strCron, "yyyy-MM-dd HH:mm:ss"); //字符串转化为指定格式日期
        String crondate = getCron(date);      //日期获得cron 字符串
        StringBuffer  sb=new StringBuffer(crondate);  //cron 字符串转化为Stringbuffer 数组
        sb.append("-2020");
        System.out.println(sb);
        String crondate1=sb.toString();
        return  crondate1;
    }

    //和当前时间作比较
    public static int compareNowDate(String date) {

        DateFormat df = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        try {
            Date dt = df.parse(date);
            Date nowTime = getTimestamp();
            if (dt.getTime() > nowTime.getTime()) {
                System.out.println("这个时间未过期");
                return 1;
            } else if (dt.getTime() < nowTime.getTime()) {
                System.out.println("这个时间已过期");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    //两个时间作比较
    public static int compareDate(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1是未来");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt2是未来");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


}


