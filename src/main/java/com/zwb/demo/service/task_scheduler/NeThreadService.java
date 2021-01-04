package com.zwb.demo.service.task_scheduler;

import com.zwb.demo.bean.SchedulerTasks;
import org.springframework.stereotype.Component;

public interface NeThreadService {
    void sendCmd(SchedulerTasks schedulerTasks);
}
