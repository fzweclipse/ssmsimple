package com.fuzhongwangcs.im;

import com.fuzhongwangcs.im.app.App;
import com.fuzhongwangcs.im.spring.SpringContainer;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * author: vector.huang
 * date：2016/4/18 1:15
 */
public class Boot implements SignalHandler {

    public void boot() {
        App.instance().create();
    }

    @Override
    public void handle(Signal signal) {
        App.instance().destroy();
    }

    public static void main(String[] args) {
        SpringContainer.init();
        System.out.println("Spring容器初始化成功...");

        new Boot().boot();
    }
}



