package com.zwb.demo;

import com.zwb.demo.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

/**
 * @author zhouw
 * @title: LernningSpringTest
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/1/268:53
 */
public class LernningSpringTest {
    public static void main(String[] args) {
        UserService userService = GetBeanDefination.getBean(UserService.class);
        userService.say();
    }

    @Test
    public void method_3() {
        UserService autoMethodDemoService = GetBeanDefination.getBean(UserService.class);
        autoMethodDemoService.say();

    }
}
