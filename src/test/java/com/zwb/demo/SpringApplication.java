package com.zwb.demo;

import com.zwb.demo.user.UserService;
import com.zwb.demo.user.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhouw
 * @title: SpringApplication
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/1/249:58
 */
@EnableAspectJAutoProxy
@ComponentScan("com.zwb.demo.user.*")
public class SpringApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(SpringApplication.class);
        UserService userService = annotationConfigApplicationContext.getBean(UserService.class);
        userService.say();
    }
}
