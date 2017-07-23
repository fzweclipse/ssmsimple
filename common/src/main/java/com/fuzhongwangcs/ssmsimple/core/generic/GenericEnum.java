package com.fuzhongwangcs.ssmsimple.core.generic;

/**
 * @Author: lazyeclipse
 * @Description: 所有自定义枚举类型实现该接口
 * @Date: 2017/5/11 16:37
 */
public interface GenericEnum {

    /**
     * value: 为保存在数据库中的值
     */
    public String getValue();

    /**
     * text : 为前端显示值
     */
    public String getText();

}
