package com.jnshu.uitl.encoder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 容联短信所需的时间格式化方法
 * */
public class DateUtil {
    public static String dateFormat(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }
}
