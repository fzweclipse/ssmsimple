package com.fuzhongwangcs.ssmsimple.core.feature.orm.dialect;

/**
 * @Author: lazyeclipse
 * @Description: MySQL 数据库方言
 * @Date: 2017/6/1 10:14
 */
public class MySql5Dialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return MySql5PageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return MySql5PageHepler.getCountString(sql);
    }
}
