package com.fuzhongwangcs.ssmsimple.core.entity;

/**
 * @Author: lazyeclipse
 * @Description: 用户自定义异常
 * @Date: 2017/5/11 16:32
 */
public class UserException extends RuntimeException {

    /**
     * 异常发生时间
     */
    private long date = System.currentTimeMillis();

    public long getDate() {
        return date;
    }
}
