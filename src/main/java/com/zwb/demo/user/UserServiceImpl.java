package com.zwb.demo.user;

import org.springframework.stereotype.Service;

/**
 * @author zhouw
 * @title: UserServiceImpl
 * @projectName zwbProjects
 * @description: TODO
 * @date 2021/1/2410:06
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void say() {
        System.out.println("hello");
    }
}
