package com.zwb.demo.task_scheduler;

import com.zwb.demo.bean.SchedulerTasks;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@Configuration
@Component // 此注解必加
@EnableScheduling // 此注解必加
public class SchedulerQuartzJobClass {
    public void sayHello(SchedulerTasks schedulerTasks) {
        try {
            Thread.sleep(6000);
            System.out.println("正在执行该任务:" + schedulerTasks.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
