package com.fuzhongwangcs.ssmsimple.web.service;

import com.fuzhongwangcs.ssmsimple.core.generic.GenericService;
import com.fuzhongwangcs.ssmsimple.web.model.User;

/**
 * @Author: lazyeclipse
 * @Description: 用户 业务 接口
 * @Date: 2017/5/11 16:38
 */
public interface UserService extends GenericService<User, Long> {

    /**
     * 用户验证
     * 
     * @param user
     * @return
     */
    User authentication(User user);

    /**
     * 根据用户名查询用户
     * 
     * @param username
     * @return
     */
    User selectByUsername(String username);
}
