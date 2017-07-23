package com.fuzhongwangcs.im;

import com.fuzhongwangcs.im.spring.SpringContainer;
import com.fuzhongwangcs.ssmsimple.web.model.User;
import com.fuzhongwangcs.ssmsimple.web.service.UserService;


/**
 * Created by lazyeclipse on 2017/7/16.
 */
public class Test {

    public static void main(String[] args) {

        SpringContainer.init();
        System.out.println("Spring容器初始化成功...");

        UserService userService = SpringContainer.getBean(UserService.class);

        final User authUserInfo = userService.selectByUsername("fzw");
        System.out.print(authUserInfo.toString());
    }
}
