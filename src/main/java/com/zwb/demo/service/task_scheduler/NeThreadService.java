package com.zwb.demo.service.task_scheduler;

import com.zwb.demo.bean.SchedulerTasks;
import org.springframework.stereotype.Component;

public interface NeThreadService {
    /**
     * 定时任务处理方法
     * @param schedulerTasks
     */
    void sendCmd(SchedulerTasks schedulerTasks);
}
