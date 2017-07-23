package com.fuzhongwangcs.ssmsimple.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lazyeclipse on 2017/6/1.
 */
public class CustomDateSerializer {

    /**
     * 当前时间格式化!
     *
     * @param format
     * @return
     */
    public static String getCurDateFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }
}
