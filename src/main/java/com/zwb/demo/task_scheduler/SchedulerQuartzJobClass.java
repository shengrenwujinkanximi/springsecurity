package com.zwb.demo.task_scheduler;

import com.zwb.demo.bean.SchedulerTasks;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 定时任务执行的方法
 * @description: TODO
 * @param * @param null
 * @return
 * @throws
 * @author zhouw
 * @date 2021/1/5 10:43
 */
@Configuration
@Component
@EnableScheduling
public class SchedulerQuartzJobClass {
    /**
     * 执行的方法
     * @param schedulerTasks
     */
    public void sayHello(SchedulerTasks schedulerTasks) {
        try {
            Thread.sleep(6000);
            System.out.println("正在执行该任务:" + schedulerTasks.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
