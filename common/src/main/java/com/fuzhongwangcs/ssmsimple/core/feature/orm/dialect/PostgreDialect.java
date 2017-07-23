package com.fuzhongwangcs.ssmsimple.core.feature.orm.dialect;

/**
 * @Author: lazyeclipse
 * @Description: Postgre 数据库方言
 * @Date: 2017/6/1 10:15
 */
public class PostgreDialect extends Dialect {

    protected static final String SQL_END_DELIMITER = ";";

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return PostgrePageHepler.getLimitString(sql, offset, limit);
    }

    @Override
    public String getCountString(String sql) {
        return PostgrePageHepler.getCountString(sql);
    }
}
