package com.zwb.demo.repository;

import com.zwb.demo.bean.SchedulerTasks;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @description: TODO
 * @param * @param null
 * @return
 * @throws
 * @author zhouw
 * @date 2021/1/5 9:57
 */
@Repository
@Transactional(rollbackOn = Exception.class)
@CacheConfig(cacheNames = "scheduler")
public interface SchedulerTasksRepository extends CrudRepository<SchedulerTasks, Integer> {
    /**
     * 根据定时任务名称查找任务对象
     * @description:
     * @param name 定时任务名称
     * @return SchedulerTasks
     * @throws
     * @author zhouw
     * @date 2021/1/5 9:59
     */
    @Cacheable(key = "#p0")
    SchedulerTasks findByName(String name);

    /**
     * 根据id更新字段
     * @param schedulerTasks
     * @return
     */
    @Cacheable(key = "#p1")
    SchedulerTasks updateBySchedulerTasks(SchedulerTasks schedulerTasks);
}
