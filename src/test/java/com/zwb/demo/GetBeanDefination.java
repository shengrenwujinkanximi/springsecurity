package com.zwb.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhouw
 * @title: GetBeanDefination
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/1/269:09
 */
@Component
public class GetBeanDefination implements ApplicationContextAware {
    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        applicationContext.getBean(clazz);
        System.out.println("=====" + applicationContext);
        return applicationContext.getBean(clazz);
    }
}
