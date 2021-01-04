package com.zwb.demo.task_scheduler;

import com.zwb.demo.bean.SchedulerTasks;
import com.zwb.demo.service.task_scheduler.NeThreadService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class SchedulerQuartzJob1 implements Job {
    public static SchedulerQuartzJob1 neTaskJob;

    @Autowired
    NeThreadService neThreadService;

    public SchedulerQuartzJob1(){}
    //注入要调用的方法
    @PostConstruct
    public void init(){
        neTaskJob = this;
        neTaskJob.neThreadService = this.neThreadService;
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("任务开始："+System.currentTimeMillis());
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        SchedulerTasks schedulerTasks = (SchedulerTasks) jobDataMap.get("schedulerTasks");
        System.out.println("数据============" + schedulerTasks.toString());
        neTaskJob.neThreadService.sendCmd(schedulerTasks);
        System.out.println("任务结束："+ System.currentTimeMillis());
    }
}
