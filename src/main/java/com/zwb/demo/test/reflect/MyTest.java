package com.zwb.demo.test.reflect;
import com.zwb.demo.service.task_scheduler.NeThreadService;
import com.zwb.demo.service.task_scheduler.impl.NeThreadServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyTest {
    public int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }
    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        int c = myTest.compute();
        System.out.println(c);
        List list = new ArrayList();
        list.add(23);
        list.add(123);
        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        System.out.println(list);
    }
}