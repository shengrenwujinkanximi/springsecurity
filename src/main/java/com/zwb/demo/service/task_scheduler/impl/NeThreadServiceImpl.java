package com.zwb.demo.service.task_scheduler.impl;

import com.zwb.demo.bean.SchedulerTasks;
import com.zwb.demo.service.task_scheduler.NeThreadService;
import com.zwb.demo.task_scheduler.SchedulerQuartzJobClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class NeThreadServiceImpl implements NeThreadService {
    @Autowired
    ApplicationContext applicationContext;
    @Override
    public void sendCmd(SchedulerTasks schedulerTasks) {
        SchedulerQuartzJobClass schedulerQuartzJobClass = (SchedulerQuartzJobClass) applicationContext.getBean(schedulerTasks.getJobClass());
        schedulerQuartzJobClass.sayHello(schedulerTasks);
    }
}
