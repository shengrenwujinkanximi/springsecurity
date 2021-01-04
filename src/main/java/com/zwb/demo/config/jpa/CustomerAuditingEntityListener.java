package com.zwb.demo.config.jpa;

import com.zwb.demo.bean.SchedulerTasks;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class CustomerAuditingEntityListener {
    @PrePersist
    public void PrePersist(Object entity){
        System.out.println("开始保存--"+entity.toString());
    }
    @PreUpdate
    public void PreUpdate(Object entity){
//        SchedulerTasks schedulerTasks = (SchedulerTasks) entity;
//        schedulerTasks.setStatus("2");
        System.out.println("开始更新--"+entity.toString());
    }

    @PostPersist
    public void PostPersist(Object entity){
        System.out.println("结束保存--"+entity.toString());
    }

    @PostUpdate
    public void PostUpdate(Object entity){
        System.out.println("结束更新--"+entity.toString());
    }
}
