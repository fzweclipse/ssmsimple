package com.fuzhongwangcs.im.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContainer implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static void init() {
        String[] configFile = {"classpath:spring-mvc.xml", "classpath:spring-dao.xml", "classpath:spring-redis.xml"};
        applicationContext = new ClassPathXmlApplicationContext(configFile);
    }

    public static <T> T getBean(Class<T> paramClass) {
        return applicationContext.getBean(paramClass);
    }

    public static Object getBean(String paramString) {
        return applicationContext.getBean(paramString);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContainer.applicationContext = applicationContext;
    }
}
