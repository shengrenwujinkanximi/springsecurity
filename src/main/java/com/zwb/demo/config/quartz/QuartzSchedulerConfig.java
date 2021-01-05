package com.zwb.demo.config.quartz;

import com.zwb.demo.bean.SchedulerTasks;
import com.zwb.demo.repository.SchedulerTasksRepository;
import com.zwb.demo.task_scheduler.SchedulerQuartzJob1;
import com.zwb.demo.task_scheduler.SchedulerQuartzJobClass;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

import java.util.Iterator;
import java.util.logging.Logger;

/**
 * 定时任务老方法，没法设置串行化
 * @author zhouw
 */
@Configuration
@Slf4j(topic = "定时任务的topic")
public class QuartzSchedulerConfig {

    private Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private SchedulerTasksRepository schedulerTasksRepository;

    @Autowired
    public void setSchedulerTasksRepository(SchedulerTasksRepository schedulerTasksRepository) {
        this.schedulerTasksRepository = schedulerTasksRepository;
    }

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 开始定时任务
     *
     * @param jobName
     */
    public void startJob(String jobName) throws SchedulerException {
        startJobSingle(scheduler);
    }

    public void autoStartJob() throws SchedulerException {
        startJobAll(scheduler);
        scheduler.start();
    }

    public void startJobSingle(Scheduler scheduler) {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取job信息
     *
     * @param name
     * @param group
     * @return
     * @throws SchedulerException
     */
    public String getJobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(), scheduler.getTriggerState(triggerKey).name());
    }

    /**
     * 添加定时任务
     *
     * @param schedulerTasks
     * @throws SchedulerException
     */
    public Scheduler addJob(SchedulerTasks schedulerTasks) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob1.class).withIdentity(schedulerTasks.getName(), schedulerTasks.getGroupName()).build();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("schedulerTasks", schedulerTasks);
        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(schedulerTasks.getCron());
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(schedulerTasks.getName(), schedulerTasks.getGroupName()).withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return scheduler;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.deleteJob(jobKey);
    }

    /**
     * 开始任务
     *
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJobAll(Scheduler scheduler) throws SchedulerException {
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        Iterator<SchedulerTasks> schedulerTasksIterator = schedulerTasksRepository.findAll().iterator();
        while (schedulerTasksIterator.hasNext()) {
            /**
             * 循环生成触发任务
             */
            SchedulerTasks schedulerTasks = schedulerTasksIterator.next();
            String cron = schedulerTasks.getCron();
            String name = schedulerTasks.getName();
            String groupName = schedulerTasks.getGroupName();
            String methodName = schedulerTasks.getJobClass();
            String status = schedulerTasks.getStatus();
            if (Integer.parseInt(status) == 1) {
                System.out.println("这个是这个类：" + methodName);
                addJob(schedulerTasks);
            } else {
                log.info("该定时任务不能执行：" + name);
            }
        }
    }
}
